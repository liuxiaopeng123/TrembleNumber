package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/9/8.
 */

public class SamePersonBean {

    /**
     * list : [{"age":2,"headImagePath":"","nickName":"哈哈","score":600,"sex":0,"trend":1,"userId":19890533}]
     * userNumber : 1
     * percent : 3.3333333333333335
     */

    private int userNumber;
    private double percent;
    private List<ListBean> list;

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * age : 2
         * headImagePath :
         * nickName : 哈哈
         * score : 600
         * sex : 0
         * trend : 1
         * userId : 19890533
         */

        private int age;
        private String headImagePath;
        private String nickName;
        private double score;
        private int sex;
        private int trend;
        private int userId;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getHeadImagePath() {
            return headImagePath;
        }

        public void setHeadImagePath(String headImagePath) {
            this.headImagePath = headImagePath;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getTrend() {
            return trend;
        }

        public void setTrend(int trend) {
            this.trend = trend;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
