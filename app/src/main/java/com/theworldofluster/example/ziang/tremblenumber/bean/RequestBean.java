package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/7/28.
 */

public class RequestBean {

    /**
     * directionX : 20
     * directionY : 14
     * directionZ : 0
     * latitude : 39.871262
     * light : 200
     * longitude : 121.221832
     * optList : [{"optDetail":"打开屏幕","optTime":"2018-07-11 15:30:00"}]
     * speed : 14
     * time : 2018-07-11 15:40:31
     * userId : 19890516
     * walk : 1231
     */

    private float directionX;
    private float directionY;
    private float directionZ;
    private double latitude;
    private float light;
    private double longitude;
    private int speed;
    private String time;
    private String startTime;
    private String userId;
    private int walk;
    private List<OptData> optList;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public float getDirectionX() {
        return directionX;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }

    public float getDirectionZ() {
        return directionZ;
    }

    public void setDirectionZ(float directionZ) {
        this.directionZ = directionZ;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public List<OptData> getOptList() {
        return optList;
    }

    public void setOptList(List<OptData> optList) {
        this.optList = optList;
    }

    public static class OptListBean {
        /**
         * optDetail : 打开屏幕
         * optTime : 2018-07-11 15:30:00
         */

        private String optDetail;
        private String optTime;

        public String getOptDetail() {
            return optDetail;
        }

        public void setOptDetail(String optDetail) {
            this.optDetail = optDetail;
        }

        public String getOptTime() {
            return optTime;
        }

        public void setOptTime(String optTime) {
            this.optTime = optTime;
        }
    }
}
