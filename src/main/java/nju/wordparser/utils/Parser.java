package nju.wordparser.utils;/*
 * @ClassName Parser
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 22:58
 * @Version 1.0
 */

import nju.wordparser.entity.MDocument;

import java.io.File;
import java.io.IOException;

public class Parser {
    public PDFParser pdfParser;
    public WPSParser wpsParser = new WPSParser();

    public MDocument parse(File file) throws IOException {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        MDocument mDocument = new MDocument();
        if (suffix.equals("wps")) {
            mDocument = wpsParser.parse(file);
        }
        return mDocument;
    }
}
