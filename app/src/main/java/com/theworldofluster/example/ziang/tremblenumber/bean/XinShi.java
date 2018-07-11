package com.theworldofluster.example.ziang.tremblenumber.bean;

/**
 * Created by liupeng on 2018/7/11.
 */

public class XinShi {

    /**
     * createDate : 2018-07-11 13:52:29
     * hasHug : false
     * hugNumber : 0
     * mindContext : 心情真不好啊垃圾
     * mindId : 8
     * userId : 19890528
     */

    private String createDate;
    private boolean hasHug;
    private int hugNumber;
    private String mindContext;
    private int mindId;
    private int userId;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isHasHug() {
        return hasHug;
    }

    public void setHasHug(boolean hasHug) {
        this.hasHug = hasHug;
    }

    public int getHugNumber() {
        return hugNumber;
    }

    public void setHugNumber(int hugNumber) {
        this.hugNumber = hugNumber;
    }

    public String getMindContext() {
        return mindContext;
    }

    public void setMindContext(String mindContext) {
        this.mindContext = mindContext;
    }

    public int getMindId() {
        return mindId;
    }

    public void setMindId(int mindId) {
        this.mindId = mindId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
