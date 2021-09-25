package nju.wordparser.entity;/*
 * @ClassName MTable
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:47
 * @Version 1.0
 */

public class MTable {
    String textBefore;//在表格之前的文本
    String textAfter;//在表格之后的文本
    int paragraphBefore;//表格前的段落
    int paragraphAfter;//表格后的段落
    String tableContent;//表格文本内容

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public int getParagraphBefore() {
        return paragraphBefore;
    }

    public void setParagraphBefore(int paragraphBefore) {
        this.paragraphBefore = paragraphBefore;
    }

    public int getParagraphAfter() {
        return paragraphAfter;
    }

    public void setParagraphAfter(int paragraphAfter) {
        this.paragraphAfter = paragraphAfter;
    }

    public String getTableContent() {
        return tableContent;
    }

    public void setTableContent(String tableContent) {
        this.tableContent = tableContent;
    }

    public String toString() {
        return "表格内容：" + tableContent + "  表格前段落：" + paragraphBefore +
                "  表格后段落：" + paragraphAfter + "  表格前文字：" + textBefore + "  表格后文字：" + textAfter;
    }
}
