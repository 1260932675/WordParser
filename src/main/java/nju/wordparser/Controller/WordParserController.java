package nju.wordparser.Controller;/*
 * @ClassName WordParser
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 15:10
 * @Version 1.0
 */

import io.swagger.annotations.ApiOperation;
import nju.wordparser.entity.ResponseData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word_parser/")
public class WordParserController {
    @GetMapping("{token}/all_paragraphs")
    @ApiOperation(value = "获取指定token文档的全部段落信息", tags = "word_parser", notes = "根据token定位文档")
    public ResponseData getAllParagraphs(@PathVariable String token) {
        return new ResponseData();
    }

    @GetMapping("{token}/all_tables")
    @ApiOperation(value = "获取指定token文档的全部表格信息", tags = "word_parser")
    public ResponseData getAllTables(@PathVariable String token) {
        return new ResponseData();
    }

    @GetMapping("{token}/all_pics")
    @ApiOperation(value = "获取指定token文档的全部图片信息", tags = "word_parser")
    public ResponseData getAllPics(@PathVariable String token) {
        return new ResponseData();
    }

    @GetMapping("{token}/all_titles")
    @ApiOperation(value = "获取指定token文档的全部标题信息", tags = "word_parser")
    public ResponseData getAllTitles(@PathVariable String token) {
        return new ResponseData();
    }

    @GetMapping("{token}/paragraph/{paragraph_id}")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落信息", tags = "word_parser")
    public ResponseData getParagraphByID(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @GetMapping("{token}/paragraph/{paragraph_id}/paragraph_stype")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落格式", tags = "word_parser")
    public ResponseData getParagraphSTypeByID(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @GetMapping("{token}/paragraph/{paragraph_id}/font_stype")
    @ApiOperation(value = "获取指定token下指定paragraph_id的段落字体格式", tags = "word_parser")
    public ResponseData getFontSTypeByID(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @GetMapping("{token}/title/{paragraph_id}/all_paragraphs")
    @ApiOperation(value = "获取指定token下文档的标题下的全部段落信息", tags = "word_parser")
    public ResponseData getContentByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @GetMapping("{token}/title/{paragraph_id}/all_pics")
    @ApiOperation(value = "获取指定token下文档的标题下的全部图片信息", tags = "word_parser")
    public ResponseData getPicsByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @GetMapping("{token}/title/{paragraph_id}/all_tables")
    @ApiOperation(value = "获取指定token下文档的标题下的全部表格信息", tags = "word_parser")
    public ResponseData getTablesByTitle(@PathVariable String token, @PathVariable int paragraph_id) {
        return new ResponseData();
    }

    @DeleteMapping("{token}")
    @ApiOperation(value = "删除指定token文档内容解析", tags = "word_parser")
    public ResponseData deleteFile(@PathVariable String token) {
        return new ResponseData();
    }
}
