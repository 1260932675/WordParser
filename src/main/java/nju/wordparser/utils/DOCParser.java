package nju.wordparser.utils;
import java.util.Base64;
import java.util.Base64.Encoder;
import nju.wordparser.entity.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DOCParser {

    InputStream inputStream ;
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

    public void parseParagraph (int paraIndex){

        //按索引号获取段落
        Paragraph paragraph = range.getParagraph(paraIndex);
        MParagraph mParagraph = new MParagraph();
        MPrSType mPrSType = new MPrSType();


        //段落格式
        mPrSType.setLvl(paragraph.getLvl());
        mPrSType.setLineSpacing(paragraph.getLineSpacing().toInt());
        mPrSType.setFirstLineIndent(paragraph.getFirstLineIndent());
        mPrSType.setIndentFromLeft(paragraph.getIndentFromLeft());
        mPrSType.setIndentFromRight(paragraph.getIndentFromRight());

        mParagraph.setMPrSType(mPrSType);

        //段落详细信息
        mParagraph.setMPrSType(mPrSType);
        mParagraph.setParagraphId(paraIndex+1);
        mParagraph.setParagraphText(paragraph.text());
        mParagraph.setInTable(paragraph.isInTable());
        mParagraph.setTableRowEnd(paragraph.isTableRowEnd());



        //标题设置
        if(paragraph.getLvl()<9) {
            mParagraph.setTitle(true);
            MTitle mTitle = new MTitle();
            mTitle.setParagraphId(paraIndex+1);
            mTitle.setParagraphText(paragraph.text());
            //设置起止
            mTitle.setStart(paraIndex+1);
            //将当前标题范围设置至文章最后一段,如果之后还有标题会迭代结尾位置
            mTitle.setEnd(range.numParagraphs());
            if(mTitles.size()>0){
                //设置上一个标题的结尾
                mTitles.get(mTitles.size()-1).setEnd(paraIndex);
            }
            mTitle.setFirstLineIndent(paragraph.getFirstLineIndent());
            mTitle.setIndentFromLeft(paragraph.getIndentFromLeft());
            mTitle.setIndentFromRight(paragraph.getIndentFromRight());
            mTitle.setLineSpacing(paragraph.getLineSpacing().toInt());
            mTitles.add(mTitle);
        }else{
            mParagraph.setTitle(false);
        }

        //段落字单元解析
        List<MFontSType> paraFontSTypes = new ArrayList<>();
        List<MPicture> paraPictures = new ArrayList<>();

        for( int j = 0 ; j < paragraph.numCharacterRuns() ; j++){
            CharacterRun characterRun = paragraph.getCharacterRun(j);
            //判断是否为图片
            if(picturesTable.hasPicture(characterRun)){
                Picture picture = picturesTable.extractPicture(characterRun,true);
                MPicture mPicture = parsePicture(picture);
                if(j>0) {
                    mPicture.setTextBefore(paragraph.getCharacterRun(j - 1).text());
                }
                if(j+1<paragraph.numCharacterRuns()){
                    mPicture.setTextAfter(paragraph.getCharacterRun(j+1).text());
                }
                mPictures.add(mPicture);
                paraPictures.add(mPicture);
            }
            //解析字体格式

            MFontSType mFontSType = parseFont(characterRun);

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
        mParagraphs.add(mParagraph);
    }

    public MFontSType parseFont(CharacterRun characterRun){
        String text = characterRun.text();
        if (null == text || "".equals(text) || "\n".equals(text) || "\r\n".equals(text)){
            return null;
        }
        MFontSType mFontSType = new MFontSType();
        mFontSType.setText(text.trim().replace(" ", ""));
        mFontSType.setBold(characterRun.isBold());
        mFontSType.setItalic(characterRun.isItalic());
        mFontSType.setColor(characterRun.getColor());
        mFontSType.setFontName(characterRun.getFontName());
        mFontSType.setFontSize(characterRun.getFontSizeAsDouble());
        mFontSType.setFontAlignment(characterRun.getCharacterSpacing());

        return mFontSType;
    }

    public MPicture parsePicture(Picture picture){

        MPicture mPicture = new MPicture();
        mPicture.setHeight((float) picture.getHeight());
        mPicture.setWidth((float) picture.getWidth());
        mPicture.setSuggestFileExtension(picture.suggestFileExtension());
        mPicture.setFileName(picture.suggestFullFileName());
        mPicture.setBase64Content(Base64.getEncoder().encodeToString(picture.getContent()));
        return mPicture;
    }

    public void parseTable(){
        int tableEndIndex = 0;
        boolean intable = false;
        //是否解析表格
        while (tableIterator.hasNext()){
            MTable mTable = new MTable();
            Table table = (Table) tableIterator.next();
            StringBuilder content = new StringBuilder("");
            //获取表格起止位置
            for(int x = tableEndIndex; x < range.numParagraphs(); x++){
                if(range.getParagraph(x).isInTable() && (!intable)){
                    intable = true;
                    if(x>0) {
                        Paragraph paraBefore = range.getParagraph(x-1);
                        mTable.setParagraphBefore(x - 1);
                        mTable.setTextBefore(paraBefore.text());
                    }
                }else if ((!range.getParagraph(x).isInTable()) && intable){
                    intable=false;
                    tableEndIndex=x;
                    mTable.setParagraphAfter(x);
                    mTable.setTextAfter(range.getParagraph(x).text());
                    break;
                }
            }
            for (int i = 0; i < table.numRows(); i++){
                TableRow tableRow = table.getRow(i);
                for ( int j = 0; j < tableRow.numCells(); j++){
                    TableCell tableCell = tableRow.getCell(j);
                    StringBuilder cellText = new StringBuilder("");
                    for ( int k = 0; k < tableCell.numParagraphs(); k++){
                        Paragraph paragraph = tableCell.getParagraph(k);
                        cellText.append(paragraph.text());
                        //表格段落解析
                    }
                    content.append(cellText);
                    content.append('\t');
                }
                content.append('\n');
            }
            mTable.setTableContent(content.toString());
            mTables.add(mTable);
        }
    }

    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/static/(2012)辰民初字第3445号-民事判决书（一审民事案件用）-1.doc";
        File file = new File(filepath);
        DOCParser docParser = new DOCParser();
        docParser.init(file);

        for ( int i = 0 ; i < docParser.range.numParagraphs() ; i++){
            docParser.parseParagraph(i);
        }
        docParser.parseTable();
        for(MParagraph p : docParser.mParagraphs){
            for(MFontSType f : p.mFontSTypes){
                System.out.println(f.toString());
                System.out.println("------------------------------");
            }
        }
        for(MTable t : docParser.mTables){
            System.out.println(t.getParagraphBefore()+":"+t.getTextBefore());
            System.out.println(t.getParagraphAfter()+":"+t.getTextAfter());
        }
        for (MTitle tt : docParser.mTitles){
            System.out.println(tt.toString());
        }

        for(MPicture pic : docParser.mPictures){
            System.out.println(pic.toString());
        }
    }

}
