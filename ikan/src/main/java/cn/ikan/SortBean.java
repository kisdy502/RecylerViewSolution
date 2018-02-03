package cn.ikan;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SortBean {
    private int fromId;
    private String sortContent;

    public SortBean() {
    }

    public SortBean(int fromId, String sortContent) {
        this.fromId = fromId;
        this.sortContent = sortContent;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getSortContent() {
        return sortContent;
    }

    public void setSortContent(String sortContent) {
        this.sortContent = sortContent;
    }


}
