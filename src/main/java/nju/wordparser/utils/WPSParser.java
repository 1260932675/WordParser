package nju.wordparser.utils;/*
 * @ClassName Tmp
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 10:44
 * @Version 1.0
 */

import nju.wordparser.entity.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class WPSParser {
    Base64.Encoder encoder = Base64.getEncoder();

    public MDocument parse(File file) {
        MDocument mDocument = new MDocument();
        List<MParagraph> mParagraphs = new ArrayList<>();
        List<MPicture> mPictures = new ArrayList<>();
        List<MTable> mTables = new ArrayList<>();
        List<MTitle> mTitles = new ArrayList<>();
        HWPFDocument hwpfDocument = null;
        try {
            hwpfDocument = new HWPFDocument(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException("读取文档为HWPFDocument失败: " + file.getName());
        }
        Range range = hwpfDocument.getRange();
        PicturesTable picturesTable = hwpfDocument.getPicturesTable();
        TableIterator tableIterator = new TableIterator(range);

        for (int i = 0; i < range.numParagraphs(); i++) {
            parseParagraph(mParagraphs, picturesTable, range.getParagraph(i), i);
        }
        parseTable(mTables, tableIterator);

        int i = 0;
        int tableIdx = 0;
        while (i < mParagraphs.size()) {
            MParagraph mParagraph = mParagraphs.get(i);
            if (mParagraph.getInTable()) {
                int numParagraphs = mTables.get(tableIdx).getNumParagraphs();
                StringBuilder tableContent = new StringBuilder();
                if (i != 0) {
                    mTables.get(tableIdx).setTextBefore(mParagraphs.get(i - 1).getParagraphText());
                    mTables.get(tableIdx).setParagraphBefore(mParagraphs.get(i - 1).getParagraphId());
                } else {
                    mTables.get(tableIdx).setTextBefore("");
                    mTables.get(tableIdx).setParagraphBefore(-1);
                }
                for (; i < i + numParagraphs && mParagraphs.get(i).getInTable(); i++) {
                    if (null != mParagraph.getmPictures() && mParagraph.getmPictures().size() != 0) {
                        mPictures.addAll(mParagraph.getmPictures());
                    }
                    tableContent.append("\t").append(mParagraphs.get(i).getParagraphText());
                    if (mParagraphs.get(i).getTableRowEnd()) {
                        tableContent.append("\n");
                    }
                }
                mTables.get(tableIdx).setTableContent(tableContent.toString());
                mTables.get(tableIdx).setParagraphAfter(i);
                mTables.get(tableIdx).setTextAfter(mParagraphs.get(i).getParagraphText());
                tableIdx++;
            } else if (mParagraph.getTitle()) {
                //TODO
                if (null != mParagraph.getmPictures() && mParagraph.getmPictures().size() != 0) {
                    mPictures.addAll(mParagraph.getmPictures());
                }
                MTitle mTitle = mParagraph.getmTitle();
                mTitle.setStart(i + 1);
                mTitle.setEnd(mParagraphs.size() - 1);
                mTitles.add(mTitle);
                i++;
            } else {
                if (null != mParagraph.getmPictures() && mParagraph.getmPictures().size() != 0) {
                    mPictures.addAll(mParagraph.getmPictures());
                }
                i++;
            }
        }
        for (MTitle mTitle : mTitles) {
            mTitle.setStart(mTitle.getParagraphId() + 1);
            mTitle.setEnd(mParagraphs.size() - 1);
        }
        mDocument.setmParagraphs(mParagraphs);
        mDocument.setmPictures(mPictures);
        mDocument.setmTitles(mTitles);
        mDocument.setmTables(mTables);
        System.out.println(mPictures.size());
        return mDocument;
    }

    private void parseParagraph(List<MParagraph> mParagraphs, PicturesTable picturesTable, Paragraph paragraph, int i) {
        MParagraph mParagraph = new MParagraph();
        MPrSType mPrSType = new MPrSType();
        mParagraph.setMPictures(new ArrayList<>());
        //TODO 段落格式
        mPrSType.setLineSpacing(paragraph.getLineSpacing().toInt());
        mPrSType.setFirstLineIndent(paragraph.getFirstLineIndent());
        mPrSType.setIndentFromLeft(paragraph.getIndentFromLeft());
        mPrSType.setIndentFromRight(paragraph.getIndentFromRight());
        mPrSType.setLvl(paragraph.getLvl());// 8为正文，但等于8不一定不是标题
        mParagraph.setMPrSType(mPrSType);

        //TODO 段落详细信息
        mParagraph.setParagraphId(i);
        mParagraph.setParagraphText(paragraph.text().trim()); //TODO 段落内容应该去掉空格和word中的特殊字符
        mParagraph.setInTable(paragraph.isInTable());
        mParagraph.setTableRowEnd(paragraph.isTableRowEnd());
        mParagraph.setFontAlignment(paragraph.getFontAlignment());

        //TODO 段落是否为标题
        int justification = paragraph.getJustification();//居中：1，右对齐：2，左对齐：3，两端对齐：3
        if (!paragraph.isInTable() && (paragraph.getLvl() != 9 || justification == 1)) {
            mParagraph.setTitle(true);
            MTitle mTitle = new MTitle();
            mTitle.setParagraphText(paragraph.text());
            mTitle.setParagraphId(i);
            mTitle.setLineSpacing(paragraph.getLineSpacing().toInt());
            mTitle.setIndentFromLeft(paragraph.getIndentFromLeft());
            mTitle.setIndentFromRight(paragraph.getIndentFromRight());
            mTitle.setLvl(paragraph.getLvl());
            mTitle.setFirstLineIndent(paragraph.getFirstLineIndent());
            mParagraph.setmTitle(mTitle);
        } else {
            mParagraph.setTitle(false);
            mParagraph.setMTitle(null);
        }

        //TODO 段落字单元解析
        List<MFontSType> mFontSTypes = new ArrayList<>();
        boolean flag = true;
        for (int j = 0; j < paragraph.numCharacterRuns(); j++) {
            CharacterRun run = paragraph.getCharacterRun(j);
            run.getFontSize();
            //TODO 段落图片解析
            if (picturesTable.hasPicture(run)) {
                MPicture mPicture = parsePicture(picturesTable, run);
                mPicture.setParagraphId(i);
                mPicture.setFontsTypeId(mFontSTypes.size());
                mParagraph.mPictures.add(mPicture);
                continue;
            }
            String text = run.text();
            if (null == text || "".equals(text) || "\n".equals(text) || "\r\n".equals(text)) continue;

            //TODO 字体格式解析
            text = text.trim().replace(" ", "");
            MFontSType mFontSType = new MFontSType();
            mFontSType.setText(text);
            mFontSType.setColor(run.getColor());
            mFontSType.setFontSize(run.getFontSizeAsDouble());
            mFontSType.setFontName(run.getFontName());
            mFontSType.setBold(run.isBold());
            mFontSType.setItalic(run.isItalic());
            mFontSType.setFontAlignment(run.getCharacterSpacing());

            //合并相同格式
            if (mFontSTypes.size() != 0) {
                if (mFontSTypes.get(mFontSTypes.size() - 1).equals(mFontSType)) {
                    mFontSTypes.get(mFontSTypes.size() - 1).setText(
                            mFontSTypes.get(mFontSTypes.size() - 1).getText() + text);
                }
            } else {
                mFontSTypes.add(mFontSType);
            }

            //段落其余信息
            if (flag) {
                mParagraph.setBold(run.isBold());
                mParagraph.setItalic(run.isItalic());
                mParagraph.setFontName(run.getFontName());
                mParagraph.setFontSize(run.getFontSize());
                flag = false;
            }
        }
        mParagraph.setMFontSTypes(mFontSTypes);

        mParagraphs.add(mParagraph);
    }

    private MPicture parsePicture(PicturesTable picturesTable, CharacterRun run) {
        Picture picture = picturesTable.extractPicture(run, true);
        MPicture mPicture = new MPicture();
        mPicture.setHeight((float) picture.getHeight());
        mPicture.setWidth((float) picture.getWidth());
        mPicture.setSuggestFileExtension(picture.suggestFileExtension());
        mPicture.setFileName(picture.suggestFullFileName());
        mPicture.setBase64Content(encoder.encodeToString(picture.getContent()));
        return mPicture;
    }

    private void parseTable(List<MTable> mTables, TableIterator tableIterator) {
        while (tableIterator.hasNext()) {
            Table table = tableIterator.next();
            MTable mTable = new MTable();
            mTable.setNumParagraphs(table.numParagraphs());
            mTables.add(mTable);
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        String path = "C:\\Users\\ling\\Desktop\\WordParser\\src\\main\\resources\\static\\test2.wps";
        File file = new File(path);
        WPSParser wpsParser = new WPSParser();
        wpsParser.parse(file);
    }
}
