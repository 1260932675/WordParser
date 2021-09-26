package nju.wordparser.utils;/*
 * @ClassName EncryptUtil
 * @Description TODO
 * @Author ling
 * @Date 2021/9/26 14:35
 * @Version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class EncryptUtil {
    public String getFileSHA1(File file) {
        String str = "";
        try {
            str = getHash(file, "SHA-256");
        } catch (Exception e) {
            throw new RuntimeException("获取文件SHA256 token失败，文件名：" + file.getName());
        }
        return str;
    }

    private static String getHash(File file, String hashType) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest messageDigest = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = inputStream.read(buffer)) > 0; ) {
            messageDigest.update(buffer, 0, numRead);
        }
        inputStream.close();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            stringBuilder.append(Integer.toHexString(b & 0xFF));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\ling\\Desktop\\WordParser\\src\\main\\resources\\static\\test1.wps");
        EncryptUtil encryptUtil = new EncryptUtil();
        System.out.println(encryptUtil.getFileSHA1(file));
    }
}
