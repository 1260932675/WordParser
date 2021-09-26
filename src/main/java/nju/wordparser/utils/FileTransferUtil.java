package nju.wordparser.utils;/*
 * @ClassName FileTransferUtil
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 15:01
 * @Version 1.0
 */

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FileTransferUtil {

    public File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File toFile = null;
        if (multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = multipartFile.getInputStream();
            toFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            toFile = inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    private static File inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
