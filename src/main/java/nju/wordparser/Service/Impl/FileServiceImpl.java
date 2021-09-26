package nju.wordparser.Service.Impl;/*
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 11:46
 * @Version 1.0
 */

import nju.wordparser.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service("fileService")
public class FileServiceImpl implements FileService {
    private final String path = "WPWPOI/files";

    @Override
    public void init() {
        File dir = new File(path);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
    }

    @Override
    public void save(MultipartFile multipartFile) {
        File file = new File(path);
        //Files.copy(multipartFile.getInputStream(), this.path.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
    }
}
