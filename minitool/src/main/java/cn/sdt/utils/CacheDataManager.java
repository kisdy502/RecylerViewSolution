package cn.sdt.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by SDT13411 on 2018/1/25.
 */

public class CacheDataManager {

    //计算缓存大小
    public long getTotalCacheSize(Context context) throws Exception {
        //内部缓存
        long cacheSize = getFolderSize(context.getCacheDir());
        //SD卡缓存
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return cacheSize;
    }

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static void deleteDir(File cacheDir) {
        FileUtil.deleteDir(cacheDir);
    }


    private static String getFormatSize(long size) {
        double kiloByte = size / 1024;

        if (kiloByte < 1) {

            return size + "Byte";

        }

        double megaByte = kiloByte / 1024;

        if (megaByte < 1) {

            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));

            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";

        }

        double gigaByte = megaByte / 1024;

        if (gigaByte < 1) {

            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));

            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";

        }

        double teraBytes = gigaByte / 1024;

        if (teraBytes < 1) {

            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));

            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";

        }

        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";


    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        if (file.isDirectory()) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size += getFolderSize(fileList[i]);
                } else {
                    size += fileList[i].length();
                }
            }
        } else {
            size += file.length();
        }
        return size;
    }
}