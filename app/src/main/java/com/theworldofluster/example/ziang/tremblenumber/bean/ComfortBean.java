package com.theworldofluster.example.ziang.tremblenumber.bean;

/**
 * Created by liupeng on 2018/7/23.
 */

public class ComfortBean {

    /**
     * comfortContext : 你需要我的拥抱来进行安慰
     * comfortId : 2
     * createDate : 2018-07-23 09:25:29
     * headUrl :
     * mindId : 13
     * nickName : 小鹏
     * userId : 19890528
     */

    private String comfortContext;
    private int comfortId;
    private String createDate;
    private String headUrl;
    private int mindId;
    private String nickName;
    private int userId;
    private boolean flag_open=false;

    public boolean isFlag_open() {
        return flag_open;
    }

    public void setFlag_open(boolean flag_open) {
        this.flag_open = flag_open;
    }

    public String getComfortContext() {
        return comfortContext;
    }

    public void setComfortContext(String comfortContext) {
        this.comfortContext = comfortContext;
    }

    public int getComfortId() {
        return comfortId;
    }

    public void setComfortId(int comfortId) {
        this.comfortId = comfortId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getMindId() {
        return mindId;
    }

    public void setMindId(int mindId) {
        this.mindId = mindId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
