package nju.wordparser.entity;/*
 * @ClassName MDocument
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 21:24
 * @Version 1.0
 */

import java.util.List;

public class MDocument {
    public List<MParagraph> mParagraphs;
    public List<MPicture> mPictures;
    public List<MTable> mTables;
    public List<MTitle> mTitles;

    public List<MParagraph> getmParagraphs() {
        return mParagraphs;
    }

    public void setmParagraphs(List<MParagraph> mParagraphs) {
        this.mParagraphs = mParagraphs;
    }

    public List<MPicture> getmPictures() {
        return mPictures;
    }

    public void setmPictures(List<MPicture> mPictures) {
        this.mPictures = mPictures;
    }

    public List<MTable> getmTables() {
        return mTables;
    }

    public void setmTables(List<MTable> mTables) {
        this.mTables = mTables;
    }

    public List<MTitle> getmTitles() {
        return mTitles;
    }

    public void setmTitles(List<MTitle> mTitles) {
        this.mTitles = mTitles;
    }
}
