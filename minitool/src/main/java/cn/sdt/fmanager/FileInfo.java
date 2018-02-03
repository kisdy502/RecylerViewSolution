package cn.sdt.fmanager;

import java.util.LinkedList;

/**
 * Created by SDT13411 on 2018/1/4.
 */

public class FileInfo {

    public boolean isDirectry;
    public String fullPath;
    public String fileName;
    public String fileExtention;                     //文件扩展名
    public FileType.FileCategory fileCategory;       //文件类型
    public FileInfo parent;
    public String createTime;
    public LinkedList<FileInfo> children;
    public int childDirCount;
    public int childFileCount;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[")
                .append("isDir:").append(isDirectry)
                .append(",fileName:")
                .append(fileName)
                .append("\nparent:")
                .append(parent!=null?parent.toString():"[]").append("]");
        return stringBuilder.toString();
    }
}
