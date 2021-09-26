package nju.wordparser.entity;/*
 * @ClassName MPicture
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 17:43
 * @Version 1.0
 */

public class MPicture {
    int paragraphId;//所在段落ID
    String textBefore;//图片之前的文本
    String textAfter;//图片之后的文本
    Float height;//图片高度
    Float width;//图片宽度
    String suggestFileExtension;//建议使用的扩展名
    String base64Content;//使用base64编码后的数据
    String fileName;//文件名
    int fontsTypeId;//字格式id

    public int getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(int paragraphId) {
        this.paragraphId = paragraphId;
    }

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

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public String getSuggestFileExtension() {
        return suggestFileExtension;
    }

    public void setSuggestFileExtension(String suggestFileExtension) {
        this.suggestFileExtension = suggestFileExtension;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFontsTypeId() {
        return fontsTypeId;
    }

    public void setFontsTypeId(int fontsTypeId) {
        this.fontsTypeId = fontsTypeId;
    }

    public String toString() {
        return "高：" + height + "  宽：" + width + "  文件扩展名：" + suggestFileExtension +
                "  文件名：" + fileName + "  base64编码：" + base64Content + "  图片前文字：" + textBefore + "  图片后文字：" + textAfter;
    }
}
