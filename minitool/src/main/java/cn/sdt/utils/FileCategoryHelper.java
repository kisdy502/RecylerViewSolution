package cn.sdt.utils;

import java.util.HashMap;

import cn.sdt.cardview.R;

/**
 * Created by SDT13411 on 2018/1/23.
 */

public class FileCategoryHelper {

    public final static int FileCategory_All=0;
    public final static int FileCategory_Music=0;
    public final static int FileCategory_Video=0;
    public final static int FileCategory_Picture=0;
    public final static int FileCategory_Theme=0;
    public final static int FileCategory_Doc=0;
    public final static int FileCategory_Zip=0;
    public final static int FileCategory_Apk=0;
    public final static int FileCategory_Custom=0;
    public final static int FileCategory_Other=0;
    public final static int FileCategory_Favorite=0;



    public static HashMap<Integer, Integer> categoryNames = new HashMap<Integer, Integer>();

    static {
        categoryNames.put(FileCategory_All, R.string.category_all);
        categoryNames.put(FileCategory_Music, R.string.category_music);
        categoryNames.put(FileCategory_Video, R.string.category_video);
        categoryNames.put(FileCategory_Picture, R.string.category_picture);
        categoryNames.put(FileCategory_Theme, R.string.category_theme);
        categoryNames.put(FileCategory_Doc, R.string.category_document);
        categoryNames.put(FileCategory_Zip, R.string.category_zip);
        categoryNames.put(FileCategory_Apk, R.string.category_apk);
        categoryNames.put(FileCategory_Other, R.string.category_other);
        categoryNames.put(FileCategory_Favorite, R.string.category_favorite);
    }

    public static int[] sCategories = new int[] {
            FileCategory_Music, FileCategory_Video, FileCategory_Picture, FileCategory_Theme,
            FileCategory_Doc, FileCategory_Zip, FileCategory_Apk, FileCategory_Other
    };
}
