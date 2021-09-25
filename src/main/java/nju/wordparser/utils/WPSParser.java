package nju.wordparser.utils;/*
 * @ClassName WPSParser
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 20:34
 * @Version 1.0
 */

import nju.wordparser.entity.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WPSParser {
    BASE64Encoder base64Encoder = new BASE64Encoder();
    InputStream inputStream;
    HWPFDocument hwpfDocument;
    PicturesTable picturesTable;
    TableIterator tableIterator;
    Range range;
    List<MParagraph> mParagraphs;
    List<MPicture> mPictures;
    List<MTable> mTables;
    List<MTitle> mTitles;

    public void init(File file) throws IOException {
        inputStream = new FileInputStream(file);
        hwpfDocument = new HWPFDocument(inputStream);
        range = hwpfDocument.getRange();
        picturesTable = hwpfDocument.getPicturesTable();
        tableIterator = new TableIterator(range);

        mParagraphs = new ArrayList<>();
        mPictures = new ArrayList<>();
        mTables = new ArrayList<>();
        mTitles = new ArrayList<>();
    }

    public void parseParagraph(Paragraph paragraph, int i) {
        MParagraph mParagraph = new MParagraph();
        MPrSType mPrSType = new MPrSType();

        //段落格式
        mPrSType.setLineSpacing(paragraph.getLineSpacing().toInt());
        mPrSType.setFirstLineIndent(paragraph.getFirstLineIndent());
        mPrSType.setIndentFromLeft(paragraph.getIndentFromLeft());
        mPrSType.setIndentFromRight(paragraph.getIndentFromRight());
        mPrSType.setLvl(paragraph.getLvl());

        //段落详细信息
        mParagraph.setParagraphId(i + 1);
        mParagraph.setParagraphText(paragraph.text().trim());
        mParagraph.setInTable(paragraph.isInTable());
        mParagraph.setTableRowEnd(paragraph.isTableRowEnd());
        mParagraph.setMPrSType(mPrSType);

        mParagraph.setMPictures(new ArrayList<>());
        //段落是否为标题
        if (paragraph.getLvl() < 8) {
            mParagraph.setTitle(true);
            MTitle mTitle = new MTitle();
            mTitle.setParagraphText(paragraph.text());
            mTitle.setParagraphId(i + 1);
            mTitle.setLineSpacing(paragraph.getLineSpacing().toInt());
            mTitle.setIndentFromLeft(paragraph.getIndentFromLeft());
            mTitle.setIndentFromRight(paragraph.getIndentFromRight());
            mTitle.setLvl(paragraph.getLvl());
            mTitle.setFirstLineIndent(paragraph.getFirstLineIndent());
        } else {
            mParagraph.setTitle(false);
        }
        //段落字单元解析
        List<MFontSType> mFontSTypes = new ArrayList<>();
        for (int j = 0; j < paragraph.numCharacterRuns(); j++) {
            CharacterRun run = paragraph.getCharacterRun(j);
            if (picturesTable.hasPicture(run)) {
                MPicture mPicture = parsePicture(run);
                mPictures.add(mPicture);
                mParagraph.mPictures.add(mPicture);
            }
            String text = run.text();
            if (null == text || "".equals(text) || "\n".equals(text) || "\r\n".equals(text)) continue;

            //字体格式解析
            MFontSType mFontSType = new MFontSType();
            mFontSType.setText(text.trim().replace(" ", ""));
            mFontSType.setColor(run.getColor());
            mFontSType.setFontSize(run.getFontSizeAsDouble());
            mFontSType.setFontName(run.getFontName());
            mFontSType.setBold(run.isBold());
            mFontSType.setItalic(run.isItalic());
            mFontSType.setFontAlignment(run.getCharacterSpacing());

            if (mFontSTypes.size() != 0) {
                if (mFontSTypes.get(mFontSTypes.size() - 1).equals(mFontSType)) {
                    mFontSTypes.get(mFontSTypes.size() - 1).setText(
                            mFontSTypes.get(mFontSTypes.size() - 1).getText() + text);
                }
            } else {
                mFontSTypes.add(mFontSType);
            }
        }
        mParagraph.setMFontSTypes(mFontSTypes);
    }

    public MPicture parsePicture(CharacterRun run) {
        Picture picture = picturesTable.extractPicture(run, true);
        MPicture mPicture = new MPicture();
        mPicture.setHeight((float) picture.getHeight());
        mPicture.setWidth((float) picture.getWidth());
        mPicture.setSuggestFileExtension(picture.suggestFileExtension());
        mPicture.setFileName(picture.suggestFullFileName());
        mPicture.setBase64Content(base64Encoder.encode(picture.getContent()));

        return mPicture;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\ling\\Desktop\\WordParser\\src\\main\\resources\\static\\test1.wps";
        File file = new File(filePath);
        WPSParser wpsParser = new WPSParser();
        wpsParser.init(file);

        for (int i = 0; i < wpsParser.range.numParagraphs(); i++) {
            wpsParser.parseParagraph(wpsParser.range.getParagraph(i), i);
        }
    }
}
