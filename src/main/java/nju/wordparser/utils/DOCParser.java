package nju.wordparser.utils;
import java.util.Base64;
import java.util.Base64.Encoder;
import nju.wordparser.entity.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import org.bouncycastle.util.encoders.Base64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DOCParser {
    Base64Encoder base64Encoder = new Base64Encoder();
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
        inputStream = new FileInputStream()
    }

}
