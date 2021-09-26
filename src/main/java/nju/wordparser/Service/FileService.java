package nju.wordparser.Service;/*
 * @ClassName FileService
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 11:41
 * @Version 1.0
 */

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void init();

    void save(MultipartFile multipartFile);
}
