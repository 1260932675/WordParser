package nju.wordparser.Controller;/*
 * @ClassName WordParser
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 15:10
 * @Version 1.0
 */

import io.swagger.annotations.ApiOperation;
import nju.wordparser.Service.ParserService;
import nju.wordparser.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word_parser/")
public class WordParserController {
    @Autowired
    private ParserService parserService;

    @GetMapping("{token}/all_paragraphs")
    @ApiOperation(value = "获取指定token文档的全部段落信息", tags = "word_parser", notes = "根据token定位文档")
    public ResponseData getAllParagraphs(@PathVariable String token) {
        List<MParagraph> mParagraphs = parserService.getAllParagraphs(token);
        if (null == mParagraphs) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mParagraphs);
    }

    @GetMapping("{token}/all_tables")
    @ApiOperation(value = "获取指定token文档的全部表格信息", tags = "word_parser")
    public ResponseData getAllTables(@PathVariable String token) {
        List<MTable> mTables = parserService.getAllTables(token);
        if (null == mTables) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mTables);
    }

    @GetMapping("{token}/all_pics")
    @ApiOperation(value = "获取指定token文档的全部图片信息", tags = "word_parser")
    public ResponseData getAllPics(@PathVariable String token) {
        List<MPicture> mPictures = parserService.getAllPics(token);
        if (null == mPictures) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mPictures);
    }

    @GetMapping("{token}/all_titles")
    @ApiOperation(value = "获取指定token文档的全部标题信息", tags = "word_parser")
    public ResponseData getAllTitles(@PathVariable String token) {
        List<MTitle> mTitles = parserService.getAllTitles(token);
        if (null == mTitles) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mTitles);
    }

    @GetMapping("{token}/paragraph/{paragraph_id}")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落信息", tags = "word_parser")
    public ResponseData getParagraphByID(@PathVariable String token, @PathVariable int paragraph_id) {
        MParagraph mParagraph = parserService.getParagraph(token, paragraph_id);
        if (null == mParagraph) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mParagraph);
    }

    @GetMapping("{token}/paragraph/{paragraph_id}/paragraph_stype")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落格式", tags = "word_parser")
    public ResponseData getParagraphSTypeByID(@PathVariable String token, @PathVariable int paragraph_id) {
        MPrSType mPrSType = parserService.getPrType(token, paragraph_id);
        if (null == mPrSType) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mPrSType);
    }

    @GetMapping("{token}/paragraph/{paragraph_id}/font_stype")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落字体格式", tags = "word_parser")
    public ResponseData getFontSTypeByID(@PathVariable String token, @PathVariable int paragraph_id) {
        List<MFontSType> mFontSTypes = parserService.getFontsType(token, paragraph_id);
        if (null == mFontSTypes) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mFontSTypes);
    }

    @GetMapping("{token}/title/{paragraph_id}/all_paragraphs")
    @ApiOperation(value = "获取指定token下文档的标题下的全部段落信息", tags = "word_parser")
    public ResponseData getContentByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        List<MParagraph> mParagraphs = parserService.getPrsByTitle(token, paragraph_id);
        if (null == mParagraphs) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mParagraphs);
    }

    @GetMapping("{token}/title/{paragraph_id}/all_pics")
    @ApiOperation(value = "获取指定token下文档的标题下的全部图片信息", tags = "word_parser")
    public ResponseData getPicsByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        List<MPicture> mPictures = parserService.getPicsByTitle(token, paragraph_id);
        if (null == mPictures) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mPictures);
    }

    @GetMapping("{token}/title/{paragraph_id}/all_tables")
    @ApiOperation(value = "获取指定token下文档的标题下的全部表格信息", tags = "word_parser")
    public ResponseData getTablesByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        List<MTable> mTables = parserService.getTableByTitle(token, paragraph_id);
        if (null == mTables) {
            return new ResponseData(-1, "fail", null);
        }
        return new ResponseData(mTables);
    }

    @DeleteMapping("{token}")
    @ApiOperation(value = "删除指定token文档内容解析", tags = "word_parser")
    public ResponseData deleteFile(@PathVariable String token) {
        parserService.deleteDocument(token);
        Map<String, String> map = new HashMap<>();
        map.put("result", "true");
        return new ResponseData(map);
    }
}
