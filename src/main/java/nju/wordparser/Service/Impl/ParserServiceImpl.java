package nju.wordparser.Service.Impl;/*
 * @ClassName ParserServiceImpl
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 15:17
 * @Version 1.0
 */

import nju.wordparser.Service.ParserService;
import nju.wordparser.entity.*;
import nju.wordparser.utils.Parser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ParserService")
public class ParserServiceImpl implements ParserService {
    Parser parser = new Parser();
    Map<String, MDocument> documentMap = new HashMap<>();

    @Override
    public void addDocument(String token, File file) {
        if (!documentMap.containsKey(token)) {
            MDocument mDocument = null;
            try {
                mDocument = parser.parse(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            documentMap.put(token, mDocument);
        }
    }


    @Override
    public List<MParagraph> getAllParagraphs(String token) {
        if (documentMap.containsKey(token)) {
            return documentMap.get(token).mParagraphs;
        }
        return null;
    }

    @Override
    public List<MTable> getAllTables(String token) {
        if (documentMap.containsKey(token)) {
            return documentMap.get(token).mTables;
        }
        return null;
    }

    @Override
    public List<MPicture> getAllPics(String token) {
        if (documentMap.containsKey(token)) {
            return documentMap.get(token).mPictures;
        }
        return null;
    }

    @Override
    public List<MTitle> getAllTitles(String token) {
        if (documentMap.containsKey(token)) {
            return documentMap.get(token).getmTitles();
        }
        return null;
    }

    @Override
    public MParagraph getParagraph(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            assert paragraphId < documentMap.get(token).mParagraphs.size();
            return documentMap.get(token).getmParagraphs().get(paragraphId);
        }
        return null;
    }

    @Override
    public MPrSType getPrType(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            assert paragraphId < documentMap.get(token).mParagraphs.size();
            return documentMap.get(token).getmParagraphs().get(paragraphId).getmPrSType();
        }
        return null;
    }

    @Override
    public List<MFontSType> getFontsType(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            assert paragraphId < documentMap.get(token).mParagraphs.size();
            return documentMap.get(token).getmParagraphs().get(paragraphId).getMFontSTypes();
        }
        return null;

    }

    @Override
    public List<MParagraph> getPrsByTitle(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            assert paragraphId < documentMap.get(token).mParagraphs.size();
            MTitle mTitle = documentMap.get(token).getmParagraphs().get(paragraphId).getmTitle();
            int start = mTitle.getStart();
            int end = mTitle.getEnd();
            List<MParagraph> mParagraphs = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                mParagraphs.add(documentMap.get(token).getmParagraphs().get(i));
            }
            return mParagraphs;
        }
        return null;
    }

    @Override
    public List<MPicture> getPicsByTitle(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            MParagraph mParagraph = documentMap.get(token).getmParagraphs().get(paragraphId);
            if (mParagraph.getTitle()) {
                MTitle mTitle = mParagraph.getmTitle();
                List<MPicture> mPictures = new ArrayList<>();
                for (MPicture mPicture : documentMap.get(token).getmPictures()) {
                    if (mPicture.getParagraphId() >= mTitle.getStart() && mPicture.getParagraphId() <= mTitle.getEnd()) {
                        mPictures.add(mPicture);
                    }
                }
                return mPictures;
            }
        }
        return null;
    }

    @Override
    public List<MTable> getTableByTitle(String token, int paragraphId) {
        if (documentMap.containsKey(token)) {
            MParagraph mParagraph = documentMap.get(token).mParagraphs.get(paragraphId);
            if (mParagraph.getTitle()) {
                MTitle mTitle = mParagraph.getmTitle();
                List<MTable> mTables = new ArrayList<>();
                for (MTable mTable : documentMap.get(token).getmTables()) {
                    int id = mTable.getParagraphBefore() + 1;
                    if (id >= mTitle.getStart() && id <= mTitle.getEnd()) {
                        mTables.add(mTable);
                    }
                }
                return mTables;
            }
        }
        return null;
    }

    @Override
    public void deleteDocument(String token) {
        documentMap.remove(token);
    }
}
