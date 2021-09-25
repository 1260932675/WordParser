package nju.wordparser.entity;/*
 * @ClassName MTitle
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:49
 * @Version 1.0
 */

import org.apache.poi.hwpf.usermodel.LineSpacingDescriptor;

public class MTitle {
    String paragraphText;//标题文本
    int paragraphId;//标题编号
    int lineSpacing;//行距
    int indentFromLeft;//左方缩排
    int indentFromRight;//右方缩排
    int firstLineIndent;//第一行缩排
    int lvl;//大纲级别
    int start;//标题下的开始段落id
    int end;//标题下的最后一个段落id

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

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getIndentFromLeft() {
        return indentFromLeft;
    }

    public void setIndentFromLeft(int indentFromLeft) {
        this.indentFromLeft = indentFromLeft;
    }

    public int getIndentFromRight() {
        return indentFromRight;
    }

    public void setIndentFromRight(int indentFromRight) {
        this.indentFromRight = indentFromRight;
    }

    public int getFirstLineIndent() {
        return firstLineIndent;
    }

    public void setFirstLineIndent(int firstLineIndent) {
        this.firstLineIndent = firstLineIndent;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String toString() {
        return "大纲级别：" + lvl + "  标题文字：" + paragraphText + "  段落id：" + paragraphId +
                "  第一行缩排：" + firstLineIndent + "  左方缩排：" + indentFromLeft + "  右方缩排：" + indentFromRight +
                "  行距：" + lineSpacing + "  标题范围：" + start + "--" + end;
    }
}
