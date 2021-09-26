package nju.wordparser.Config;/*
 * @ClassName FileConfig
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 11:58
 * @Version 1.0
 */

import nju.wordparser.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class FileConfig implements CommandLineRunner {
    @Autowired
    FileService fileService;

    @Override
    public void run(String... args) throws Exception {
        fileService.init();
    }
}
