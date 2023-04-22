package com.tencent.wxcloudrun.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 文件
 */
public class FileUtil {
    /**
     * 写文件
     *
     * @param fileData
     * @param filePath
     * @throws IOException
     */
    public static File writeFile(byte[] fileData, String filePath) throws IOException {
        if (fileData != null) {
            File file = new File(filePath);

            FileOutputStream fos = new FileOutputStream(file);

            try {
                fos.write(fileData, 0, fileData.length);
            } finally {
                fos.flush();
                fos.close();
            }

            return file;
        }

        return null;
    }

    /**
     * 写文件
     *
     * @param filedata
     * @param file
     * @throws IOException
     */
    public static void writeTo(byte[] filedata, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(filedata);
        } finally {
            fos.close();
        }
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String filePath) throws IOException {

        RandomAccessFile raf = null;
        byte[] data;
        try {
            raf = new RandomAccessFile(filePath, "r");
            data = new byte[(int) raf.length()];
            raf.read(data);
            return data;
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }
}
