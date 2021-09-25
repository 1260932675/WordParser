package nju.wordparser.entity;/*
 * @ClassName MPrSType
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:52
 * @Version 1.0
 */

public class MPrSType {
    int lineSpacing;//行间距
    int indentFromLeft;//左方缩排
    int indentFromRight;//右方缩排
    int firstLineIndent;//第一行缩排
    int lvl;//大纲级别

    public String toString() {
        return "第一行缩排：" + firstLineIndent + "  左方缩排：" + indentFromLeft + "  右方缩排：" + indentFromRight +
                "  大纲级别：" + lvl + "  行间距：" + lineSpacing;
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
}
