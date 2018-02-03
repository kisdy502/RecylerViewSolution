package cn.sdt.fmanager;

import java.util.Comparator;

/**
 * Created by SDT13411 on 2018/1/22.
 */

public class ComparatorResultType implements Comparator<FileInfo> {

    @Override
    public int compare(FileInfo f1, FileInfo f2) {
        if (f1.isDirectry && f2.isDirectry) {
            return f1.fileName.compareTo(f2.fileName);
        } else if (!f1.isDirectry && !f2.isDirectry) {
            return f1.fileName.compareTo(f2.fileName);
        } else if (f1.isDirectry && !f2.isDirectry) {
            return -1;
        } else if (!f1.isDirectry && f2.isDirectry) {
            return 1;
        } else {
            return 0;
        }
    }
}
