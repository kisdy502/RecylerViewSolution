package cn.ikan;

/**
 * Created by SDT13411 on 2018/1/31.
 */

public class Content {

    public Content() {
    }

    public Content(String contentTitle, String contentDesc, int level, String tag) {
        this.contentTitle = contentTitle;
        this.contentDesc = contentDesc;
        this.level = level;
        this.tag = tag;
    }

    private String contentTitle;
    private String contentDesc;
    private int level;
    private String tag;

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
