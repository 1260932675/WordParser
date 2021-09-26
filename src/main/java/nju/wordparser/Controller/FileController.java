package nju.wordparser.Controller;/*
 * @ClassName FileController
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 21:47
 * @Version 1.0
 */

import nju.wordparser.Service.FileService;
import nju.wordparser.Service.ParserService;
import nju.wordparser.entity.ResponseData;
import nju.wordparser.utils.EncryptUtil;
import nju.wordparser.utils.FileTransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private ParserService parserService;

    @PostMapping("load_file")
    public ResponseData loadFile(@RequestParam("file") MultipartFile multipartFile,
                                 @RequestParam("fileName") String fileName) throws Exception {
        EncryptUtil encryptUtil = new EncryptUtil();
        FileTransferUtil fileTransferUtil = new FileTransferUtil();
        File file = fileTransferUtil.multipartFileToFile(multipartFile);
        multipartFile = null;
        String token = encryptUtil.getFileSHA1(file);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        parserService.addDocument(token, file);
        return new ResponseData(map);
    }

}
