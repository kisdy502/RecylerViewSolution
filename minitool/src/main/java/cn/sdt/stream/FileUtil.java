package cn.sdt.stream;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;

/**
 * Created by sdt13411 on 2018/1/10.
 */

public class FileUtil {
    private final static String TAG = FileUtil.class.getSimpleName();

    public static void readBytes(byte[] data) {
        int buff_size = 1024;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[buff_size];
        int len = 0;
        try {
            while ((len = byteArrayInputStream.read(readBuffer)) > 0) {
                byteArrayOutputStream.write(readBuffer, 0, len);
            }
            byte[] outBytes = byteArrayOutputStream.toByteArray();
            String text = new String(outBytes);
            Log.d(TAG, "text:" + text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {

            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {

            }

        }
    }

    public void CopyFile(File sourceFile, File destFile) {
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void readString(String text) {
        StringReader reader=new StringReader(text);
    }

}
