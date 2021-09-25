package nju.wordparser.entity;/*
 * @ClassName MParagraph
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:53
 * @Version 1.0
 */

import java.util.List;

public class MParagraph {
    String paragraphText;//段落文本
    int paragraphId;//段落id
    Boolean isTableRowEnd;//是否到表尾
    Boolean isInTable;//是否在表格中

    Boolean isTitle;//是否是标题
    MPrSType mPrSType;//段落格式
    public List<MFontSType> mFontSTypes;//详细字体格式
    public List<MPicture> mPictures;//段落内图片
    MTitle mTitle;

    public String getParagraphText() {
        return paragraphText;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public int getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(int paragraphId) {
        this.paragraphId = paragraphId;
    }

    public Boolean getTableRowEnd() {
        return isTableRowEnd;
    }

    public void setTableRowEnd(Boolean tableRowEnd) {
        isTableRowEnd = tableRowEnd;
    }

    public Boolean getInTable() {
        return isInTable;
    }

    public void setInTable(Boolean inTable) {
        isInTable = inTable;
    }

    public Boolean getTitle() {
        return isTitle;
    }

    public void setTitle(Boolean title) {
        isTitle = title;
    }

    public MPrSType getMPrSType() {
        return mPrSType;
    }

    public void setMPrSType(MPrSType mPrSType) {
        this.mPrSType = mPrSType;
    }

    public List<MFontSType> getMFontSTypes() {
        return mFontSTypes;
    }

    public void setMFontSTypes(List<MFontSType> mFontSTypes) {
        this.mFontSTypes = mFontSTypes;
    }

    public List<MPicture> getMPictures() {
        return mPictures;
    }

    public void setMPictures(List<MPicture> mPictures) {
        this.mPictures = mPictures;
    }

    public MTitle getMTitle() {
        return mTitle;
    }

    public void setMTitle(MTitle mTitle) {
        this.mTitle = mTitle;
    }
}
