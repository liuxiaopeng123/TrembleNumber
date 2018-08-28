package com.theworldofluster.example.ziang.tremblenumber.bean;

/**
 * Created by liupeng on 2018/8/23.
 */

public class SettingBean {

    /**
     * id : 10
     * onOff : false
     * settingType : RECOMMEND
     */

    private int id;
    private boolean onOff;
    private String settingType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }
}
