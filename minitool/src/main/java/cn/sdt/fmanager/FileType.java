package cn.sdt.fmanager;

import java.util.HashMap;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/16.
 */

public class FileType {

    public enum FileCategory {
        All, Music, Video, Picture, Theme, Doc, Zip, Apk, Custom, Other, Favorite
    }

    public static HashMap<FileCategory, Integer> categoryNames = new HashMap<FileCategory, Integer>();

    static {
        categoryNames.put(FileCategory.All, R.string.category_all);
        categoryNames.put(FileCategory.Music, R.string.category_music);
        categoryNames.put(FileCategory.Video, R.string.category_video);
        categoryNames.put(FileCategory.Picture, R.string.category_picture);
        categoryNames.put(FileCategory.Theme, R.string.category_theme);
        categoryNames.put(FileCategory.Doc, R.string.category_document);
        categoryNames.put(FileCategory.Zip, R.string.category_zip);
        categoryNames.put(FileCategory.Apk, R.string.category_apk);
        categoryNames.put(FileCategory.Other, R.string.category_other);
        categoryNames.put(FileCategory.Favorite, R.string.category_favorite);
    }

    public static FileCategory[] sCategories = new FileCategory[]{
            FileCategory.Music,
            FileCategory.Video,
            FileCategory.Picture,
            FileCategory.Theme,
            FileCategory.Doc,
            FileCategory.Zip,
            FileCategory.Apk,
            FileCategory.Other
    };
}
