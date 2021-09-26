package nju.wordparser.Service;/*
 * @ClassName ParserService
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 14:23
 * @Version 1.0
 */

import nju.wordparser.entity.*;

import java.io.File;
import java.util.List;

public interface ParserService {
    void addDocument(String token, File file);

    List<MParagraph> getAllParagraphs(String token);

    List<MTable> getAllTables(String token);

    List<MPicture> getAllPics(String token);

    List<MTitle> getAllTitles(String token);

    MParagraph getParagraph(String token, int paragraphId);

    MPrSType getPrType(String token, int paragraphId);

    List<MFontSType> getFontsType(String token, int paragraphId);

    List<MParagraph> getPrsByTitle(String token, int paragraphId);

    List<MPicture> getPicsByTitle(String token, int paragraphId);

    List<MTable> getTableByTitle(String token, int paragraphId);

    void deleteDocument(String token);
}
