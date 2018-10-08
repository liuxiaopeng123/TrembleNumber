package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/9/14.
 */

public class MoodStatisticBean {

    /**
     * badDayNumber : 0
     * days : [{"date":"2018-09-06","moodScore":92,"moodType":1,"userId":19890528},{"date":"2018-09-07","moodScore":95,"moodType":1,"userId":19890528},{"date":"2018-09-08","moodScore":95,"moodType":1,"userId":19890528},{"date":"2018-09-09","moodScore":83,"moodType":1,"userId":19890528},{"date":"2018-09-10","moodScore":55,"moodType":2,"userId":19890528},{"date":"2018-09-11","moodScore":95,"moodType":1,"userId":19890528}]
     * goodDayNumber : 5
     * sosoDayNumber : 1
     */

    private int badDayNumber;
    private int goodDayNumber;
    private int sosoDayNumber;
    private List<DaysBean> days;

    public int getBadDayNumber() {
        return badDayNumber;
    }

    public void setBadDayNumber(int badDayNumber) {
        this.badDayNumber = badDayNumber;
    }

    public int getGoodDayNumber() {
        return goodDayNumber;
    }

    public void setGoodDayNumber(int goodDayNumber) {
        this.goodDayNumber = goodDayNumber;
    }

    public int getSosoDayNumber() {
        return sosoDayNumber;
    }

    public void setSosoDayNumber(int sosoDayNumber) {
        this.sosoDayNumber = sosoDayNumber;
    }

    public List<DaysBean> getDays() {
        return days;
    }

    public void setDays(List<DaysBean> days) {
        this.days = days;
    }

    public static class DaysBean {
        /**
         * date : 2018-09-06
         * moodScore : 92
         * moodType : 1
         * userId : 19890528
         */

        private String date;
        private int moodScore;
        private int moodType;
        private int userId;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getMoodScore() {
            return moodScore;
        }

        public void setMoodScore(int moodScore) {
            this.moodScore = moodScore;
        }

        public int getMoodType() {
            return moodType;
        }

        public void setMoodType(int moodType) {
            this.moodType = moodType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
