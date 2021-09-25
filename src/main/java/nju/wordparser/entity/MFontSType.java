package nju.wordparser.entity;/*
 * @ClassName MFontSType
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:51
 * @Version 1.0
 */

public class MFontSType {
    int color;//字体颜色
    Double fontSize;//字体大小
    String fontName;//字体名称
    Boolean isBold;//是否为粗体
    Boolean isItalic;//是否为斜体
    int fontAlignment;//字间距
    String text;//该段文字

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Boolean getBold() {
        return isBold;
    }

    public void setBold(Boolean bold) {
        isBold = bold;
    }

    public Boolean getItalic() {
        return isItalic;
    }

    public void setItalic(Boolean italic) {
        isItalic = italic;
    }

    public int getFontAlignment() {
        return fontAlignment;
    }

    public void setFontAlignment(int fontAlignment) {
        this.fontAlignment = fontAlignment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object object) {
        if (object instanceof MFontSType) {
            MFontSType mFontSType = (MFontSType) object;
            return mFontSType.getColor() == color
                    && mFontSType.getFontSize().equals(fontSize)
                    && mFontSType.getFontName().equals(fontName)
                    && mFontSType.getBold() == isBold
                    && mFontSType.getItalic() == isItalic
                    && mFontSType.getFontAlignment() == fontAlignment;
        }
        return false;
    }

    public String toString() {
        return "文字内容：" + this.getText() + "  字体名：" + fontName + "  字体大小：" + fontSize + "  字间距：" + fontAlignment +
                "  粗体：" + isBold + "  斜体：" + isItalic + "  颜色：" + color;
    }
}
