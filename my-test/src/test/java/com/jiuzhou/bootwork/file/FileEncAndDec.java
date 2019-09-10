package com.jiuzhou.bootwork.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEncAndDec {
    private static final int numOfEncAndDec = 0x99; //加密解密秘钥
    private static int dataOfFile = 0; //文件字节内容

    public static void main(String[] args) {

        File srcFile = new File("桌面.jpg"); //初始文件
        File encFile = new File("encFile.tif"); //加密文件
        File decFile = new File("decFile.bmp"); //解密文件

        try {
            EncFile(srcFile, encFile); //加密操作
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void EncFile(File srcFile, File encFile) throws Exception {
        if (!srcFile.exists()) {
            System.out.println("source file not exixt");
            return;
        }

        if (!encFile.exists()) {
            System.out.println("encrypt file created");
            encFile.createNewFile();
        }
        InputStream fis = new FileInputStream(srcFile);
        OutputStream fos = new FileOutputStream(encFile);

        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile ^ numOfEncAndDec);
        }

        fis.close();
        fos.flush();
        fos.close();
    }

    private static void DecFile(File encFile, File decFile) throws Exception {
        if (!encFile.exists()) {
            System.out.println("encrypt file not exixt");
            return;
        }

        if (!decFile.exists()) {
            System.out.println("decrypt file created");
            decFile.createNewFile();
        }

        InputStream fis = new FileInputStream(encFile);
        OutputStream fos = new FileOutputStream(decFile);

        while ((dataOfFile = fis.read()) > -1) {
            fos.write(dataOfFile ^ numOfEncAndDec);
        }

        fis.close();
        fos.flush();
        fos.close();
    }


}