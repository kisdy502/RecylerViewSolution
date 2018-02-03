package cn.sdt.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SDT13411 on 2018/1/21.
 */

public class FileUtil {

    private final static String TAG = "FileUtil";

    // /data/data/packageName/cache
    public static File getInnerCacheDirectory(Context context) {
        File cacheDir = context.getCacheDir();
        return cacheDir;
    }

    public static File getExternalCacheDirectory(Context context, String packageName) {
        if (!Environment.isExternalStorageEmulated()) {
            return null;
        }
        File cacheFile = context.getExternalCacheDir();
        if (cacheFile != null && cacheFile.getParentFile() != null) {
            File parent = cacheFile.getParentFile();
            if (parent.getParentFile() != null) {
                File gradParent = parent.getParentFile();
                StringBuilder sb = new StringBuilder();
                sb.append(gradParent.getAbsoluteFile()).append(File.separator).append(packageName);
                return new File(sb.toString());
            }
        }
        return null;
    }

    // /data/data/packageName/
    public static File getApkDataDirectory(Context context) {
        File dataFile = context.getCacheDir().getParentFile();
        return dataFile;
    }


    //SDCard/packageName/  example::: /storage/emulated/0/cn.mipt.ad/
    public static File getInnerSdcardDirectory(Context context, String packageName) {
        if (Environment.isExternalStorageEmulated()) {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            StringBuilder sb = new StringBuilder();
            sb.append(dir).append(File.separator).append(packageName);
            File file = new File(sb.toString());
            return file;
        } else {
            return null;
        }
    }


    public static boolean deleteDir(File dir) {
        return deleteDir(dir, false);
    }

    public static boolean deleteDir(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        return deleteDir(path, false);
    }

    /**
     * 删除目录
     *
     * @param path
     * @param delRoot 是否删除根目录
     * @return
     */
    public static boolean deleteDir(String path, boolean delRoot) {
        return deleteDir(new File(path), delRoot);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir     将要删除的文件目录
     * @param delRoot 是否需要将根目录页删除掉
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir, boolean delRoot) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]), true);
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        if (delRoot) {
            return dir.delete();
        }
        return true;
    }


    //获取sd卡目录
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.getPath();
    }

    /**
     * 获得SD卡总大小
     *
     * @return
     */
    private String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    private String getSDAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    private String getRomTotalSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    private String getRomAvailableSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }


    /**
     * 文件拷贝 ,耗时操作
     *
     * @param fileIn
     * @param destDir
     */
    public static void copyFile(File fileIn, File destDir) {
        if (!fileIn.exists()) {
            Log.d("FileUitl", "copyFile " + fileIn + " not exist");
            return;
        }
//        LogUtil.debug(mTag, "copyFile" + fileIn.getAbsolutePath());
//        LogUtil.debug(mTag, "copyFile" + fileOut.getAbsolutePath());
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(fileIn);
            fileOutputStream = new FileOutputStream(destDir);
            byte[] buffer = new byte[4096];
            int len = fileInputStream.read(buffer);
            while (len > 0) {
                fileOutputStream.write(buffer, 0, len);
                len = fileInputStream.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝目录 ,耗时操作
     *
     * @param srcDir
     * @param destDir
     */
    public static void copyDir(File srcDir, File destDir) {
        Log.d(TAG, "srcFile:" + srcDir.getAbsolutePath());
        Log.d(TAG, "srcFile:" + destDir.getAbsolutePath());
        String name = srcDir.getName();
        String realDirStr = destDir + File.separator + name;
        File realDirFile = new File(realDirStr);
        if (realDirFile.mkdir()) {
            File[] fileList = srcDir.listFiles();
            for (File f : fileList) {
                if (f.isFile()) {
                    copyFile(f, new File(realDirFile.getAbsolutePath() + File.separator + f.getName()));
                } else {
                    copyDir(f, realDirFile);
                }
            }
        }
    }


}

