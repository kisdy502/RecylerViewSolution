package cn.sdt.utils;

import java.util.HashMap;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/23.
 */

public class FileIconHelper {

    private static HashMap<String, Integer> fileExtToIcons = new HashMap<String, Integer>();

    static {
        addItem(new String[]{
                "mp3"
        }, R.drawable.file_icon_mp3);
        addItem(new String[]{
                "wma"
        }, R.drawable.file_icon_wma);
        addItem(new String[]{
                "wav"
        }, R.drawable.file_icon_wav);
        addItem(new String[]{
                "mid"
        }, R.drawable.file_icon_mid);
        addItem(new String[]{
                "mp4", "wmv", "mpeg", "m4v", "3gp", "3gpp", "3g2", "3gpp2", "asf"
        }, R.drawable.file_icon_video);
        addItem(new String[]{
                "jpg", "jpeg", "gif", "png", "bmp", "wbmp"
        }, R.drawable.file_icon_picture);
        addItem(new String[]{
                "txt", "log", "xml", "ini", "lrc"
        }, R.drawable.file_icon_txt);
        addItem(new String[]{
                "doc", "ppt", "docx", "pptx", "xsl", "xslx",
        }, R.drawable.file_icon_office);
        addItem(new String[]{
                "pdf"
        }, R.drawable.file_icon_pdf);
        addItem(new String[]{
                "zip"
        }, R.drawable.file_icon_zip);
        addItem(new String[]{
                "mtz"
        }, R.drawable.file_icon_theme);
        addItem(new String[]{
                "rar"
        }, R.drawable.file_icon_rar);
    }


    private static void addItem(String[] exts, int resId) {
        if (exts != null) {
            for (String ext : exts) {
                fileExtToIcons.put(ext.toLowerCase(), resId);
            }
        }
    }

    public static int getFileIcon(String ext) {
        Integer i = fileExtToIcons.get(ext.toLowerCase());
        if (i != null) {
            return i.intValue();
        } else {
            return R.drawable.file_icon_default;
        }
    }


    public static String getExtion(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String ext = fileName.substring(index + 1, fileName.length());
            return ext;
        } else {
            return "";
        }
    }
}
