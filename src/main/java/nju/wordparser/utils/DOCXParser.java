package nju.wordparser.utils;
import io.swagger.models.auth.In;
import nju.wordparser.entity.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.IBodyElement;


import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.io.*;

public class DOCXParser {

    InputStream inputStream ;
    XWPFDocument xwpfDocument;
    XWPFStyles xwpfStyles;
    List<XWPFParagraph> paragraphs;
    List<IBodyElement> bodyElementList;

    List<MParagraph> mParagraphs;
    List<MPicture> mPictures;
    List<MTable> mTables;
    List<MTitle> mTitles;

    public void init(File file) throws IOException {
        inputStream = new FileInputStream(file);
        xwpfDocument = new XWPFDocument(inputStream);
        xwpfStyles = xwpfDocument.getStyles();
        paragraphs = xwpfDocument.getParagraphs();


        mParagraphs = new ArrayList<>();
        mPictures = new ArrayList<>();
        mTables = new ArrayList<>();
        mTitles = new ArrayList<>();


        int index = 0;
        for(int i = 0 ;i < paragraphs.size(); i++){
            parseParagraph(i);
        }

        parseTable();
    }

    public void parseParagraph(int paraIndex){
        XWPFParagraph xwpfParagraph = paragraphs.get(paraIndex);
        MParagraph mParagraph = new MParagraph();
        MPrSType mPrSType = new MPrSType();

        //段落格式
        //段落大纲级别
        try {
            if(xwpfParagraph.getCTP().getPPr().getOutlineLvl() != null){
                if(xwpfParagraph.getCTP().getPPr().getOutlineLvl().getVal()!=null) {
                    mPrSType.setLvl(xwpfParagraph.getCTP().getPPr().getOutlineLvl().getVal().intValue());
                }
            }
        }catch (Exception e){ }
        try {
            if (xwpfStyles.getStyle(xwpfParagraph.getStyle()).getCTStyle().getPPr().getOutlineLvl() != null) {
                if (xwpfStyles.getStyle(xwpfParagraph.getStyleID())
                        .getCTStyle().getPPr().getOutlineLvl().getVal() != null) {
                    mPrSType.setLvl(xwpfStyles.getStyle(xwpfParagraph.getStyleID())
                            .getCTStyle().getPPr().getOutlineLvl().getVal().intValue());
                }
            }
        }catch (Exception e) { }
        try {
            if (xwpfStyles.getStyle(xwpfStyles.getStyle(xwpfParagraph.getStyle()).getCTStyle().getBasedOn().getVal())
                    .getCTStyle().getPPr().getOutlineLvl() != null) {
                String styleName = xwpfStyles.getStyle(xwpfParagraph.getStyle()).getCTStyle().getBasedOn().getVal();
                mPrSType.setLvl(xwpfStyles.getStyleWithName(styleName).getCTStyle().getPPr().getOutlineLvl().getVal().intValue());

            }
        }catch (Exception e){ }
        mPrSType.setLineSpacing((int)xwpfParagraph.getSpacingBetween());
        mPrSType.setIndentFromRight(xwpfParagraph.getIndentFromRight());
        mPrSType.setIndentFromLeft(xwpfParagraph.getIndentFromLeft());
        mPrSType.setFirstLineIndent(xwpfParagraph.getFirstLineIndent());

        //标题设置
        if(mPrSType.getLvl()<8 && mPrSType.getLvl()>0){
            mParagraph.setTitle(true);
            MTitle mTitle = new MTitle();
            mTitle.setStart(paraIndex+1);
            mTitle.setEnd(paragraphs.size());
            if(mTitles.size()>0){
                mTitles.get(mTitles.size()-1).setEnd(paraIndex);
            }
            mTitle.setIndentFromRight(xwpfParagraph.getIndentFromRight());
            mTitle.setIndentFromLeft(xwpfParagraph.getIndentFromLeft());
            mTitle.setIndentFromRight(xwpfParagraph.getIndentFromRight());
            mTitle.setParagraphText(xwpfParagraph.getParagraphText());
            mTitle.setParagraphId(paraIndex+1);
            mTitle.setFirstLineIndent(xwpfParagraph.getFirstLineIndent());
            mTitle.setLineSpacing((int)xwpfParagraph.getSpacingBetween());
            mTitle.setLvl(mPrSType.getLvl());
            mTitles.add(mTitle);
        }else{
            mParagraph.setTitle(false);
        }

        //段落详细信息
        mParagraph.setMPrSType(mPrSType);
        mParagraph.setParagraphId(paraIndex+1);
        mParagraph.setParagraphText(xwpfParagraph.getParagraphText());
        //docx文件中表格和段落分离，段落不包含表格段
        mParagraph.setTableRowEnd(false);
        mParagraph.setInTable(false);


        mParagraphs.add(mParagraph);

        List<MPicture> paraPictures = new ArrayList<>();
        List<MFontSType> paraFontSTypes = new ArrayList<>();
        List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
        for(int j = 0; j < xwpfRuns.size(); j++){
            XWPFRun xwpfRun = xwpfRuns.get(j);
            if(xwpfRun.getEmbeddedPictures().size()>0){
                //字单元有图片
                for(XWPFPicture xwpfPicture: xwpfRun.getEmbeddedPictures()){
                    MPicture mPicture = parsePicture(xwpfPicture);
                    mPicture.setParagraphId(paraIndex+1);
                    if(j>0){
                        mPicture.setTextBefore(xwpfRuns.get(j-1).text());
                    }
                    if(j+1 < xwpfRuns.size()){
                        mPicture.setTextAfter(xwpfRuns.get(j+1).text());
                    }
                    mPictures.add(mPicture);
                    paraPictures.add(mPicture);
                }
            }

            MFontSType mFontSType = parseFont(xwpfRun);

            if(mFontSType != null) {
                if (paraFontSTypes.size() != 0) {
                    //字体去重,字单元合并
                    if (paraFontSTypes.get(paraFontSTypes.size() - 1).equals(mFontSType)) {
                        paraFontSTypes.get(paraFontSTypes.size() - 1).setText(
                                paraFontSTypes.get(paraFontSTypes.size() - 1).getText() + mFontSType.getText());
                    }
                } else {
                    paraFontSTypes.add(mFontSType);
                }
            }
        }

        mParagraph.setMPictures(paraPictures);
        mParagraph.setMFontSTypes(paraFontSTypes);

    }

    public MFontSType parseFont(XWPFRun xwpfRun){
        String text = xwpfRun.text();
        if (null == text || "".equals(text) || "\n".equals(text) || "\r\n".equals(text)){
            return null;
        }
        if(xwpfRun.getFontName() == null){
            return null;
        }
        MFontSType mFontSType = new MFontSType();
        mFontSType.setText(text.trim().replace(" ", ""));
        mFontSType.setFontAlignment(xwpfRun.getCharacterSpacing());
        mFontSType.setFontName(xwpfRun.getFontName());
        mFontSType.setFontSize(xwpfRun.getFontSizeAsDouble());
        mFontSType.setColor(xwpfRun.getColor()==null ? -1:Integer.valueOf(xwpfRun.getColor()));
        mFontSType.setBold(xwpfRun.isBold());
        mFontSType.setItalic(xwpfRun.isItalic());
        return mFontSType;
    }

    public MPicture parsePicture(XWPFPicture xwpfPicture){
        MPicture mPicture = new MPicture();
        XWPFPictureData xwpfPictureData = xwpfPicture.getPictureData();
        mPicture.setFileName(xwpfPictureData.getFileName());
        mPicture.setSuggestFileExtension(xwpfPictureData.suggestFileExtension());
        mPicture.setHeight((float)xwpfPicture.getCTPicture().getSpPr().getXfrm().getExt().getCy());
        mPicture.setWidth((float)xwpfPicture.getCTPicture().getSpPr().getXfrm().getExt().getCx());
        mPicture.setBase64Content(Base64.getEncoder().encodeToString(xwpfPictureData.getData()));
        return mPicture;
    }

    public void parseTable(){
        List<XWPFTable> xwpfTables = xwpfDocument.getTables();
        int elementOffset = 0;
        for(XWPFTable xwpfTable : xwpfTables) {
            MTable mTable = new MTable();
            StringBuilder content = new StringBuilder("");
            //表格起始段落,index是bodyelement里的index不是paragraphs
            int tablePos = xwpfDocument.getPosOfTable(xwpfTable);
            if (tablePos > 0) {
                mTable.setParagraphBefore(tablePos-1-elementOffset);
                mTable.setTextBefore(paragraphs.get(tablePos-1-elementOffset).getText());
                mTable.setParagraphAfter(tablePos-elementOffset);
                mTable.setTextAfter(paragraphs.get(tablePos-elementOffset).getText());

            }
            List<XWPFTableRow> xwpfTableRows = xwpfTable.getRows();
            for (int i = 0; i < xwpfTableRows.size(); i++) {
                List<XWPFTableCell> xwpfTableCells = xwpfTableRows.get(i).getTableCells();
                for (int j = 0; j < xwpfTableCells.size(); j++) {
                    List<XWPFParagraph> xwpfParagraphs = xwpfTableCells.get(j).getParagraphs();
                    for (int k = 0; k < xwpfParagraphs.size(); k++) {
                        content.append(xwpfParagraphs.get(k).getText());

                    }
                    content.append('\t');
                }

                content.append('\n');
            }

            mTable.setTableContent(content.toString());
            mTables.add(mTable);
            elementOffset++;
        }
    }

    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/static/(2012)辰民初字第3445号-民事判决书（一审民事案件用）-1.docx";
        File file = new File(filepath);
        DOCXParser docxParser = new DOCXParser();
        docxParser.init(file);
        for(MParagraph p : docxParser.mParagraphs){
            for(MFontSType f : p.mFontSTypes){
                System.out.println(f.toString());
                System.out.println("------------------------------");
            }

        }

        for(MTable t : docxParser.mTables){
            //System.out.println(t.getParagraphBefore()+":"+t.getTextBefore());
            //System.out.println(t.getParagraphAfter()+":"+t.getTextAfter());
            System.out.println(t.toString());
        }
        for (MTitle tt : docxParser.mTitles){
            System.out.println(tt.toString());
        }
        System.out.println(docxParser.paragraphs.get(102).getText());
        for(MPicture pic : docxParser.mPictures){
            System.out.println(pic.toString());
        }


    }
}
