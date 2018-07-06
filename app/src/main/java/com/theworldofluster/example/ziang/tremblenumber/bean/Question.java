package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/7/5.
 */

public class Question {

    /**
     * isSingle : true
     * optionList : [{"optionCode":20101,"optionNum":"A","optionTitle":"A、视野开阔的原野"},{"optionCode":20102,"optionNum":"B","optionTitle":"B、繁华拥挤的都市；"},{"optionCode":20103,"optionNum":"C","optionTitle":"C、神秘莫测的森林；"},{"optionCode":20104,"optionNum":"D","optionTitle":"D、驰名中外的景区"}]
     * questionCode : 201
     * questionNum : 1
     * questionTitle : 1、首先选择一下图画纸的背景
     */

    private boolean isSingle;
    private int questionCode;
    private int questionNum;
    private String questionTitle;
    private List<OptionListBean> optionList;

    public boolean isIsSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public int getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(int questionCode) {
        this.questionCode = questionCode;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public List<OptionListBean> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionListBean> optionList) {
        this.optionList = optionList;
    }

    public static class OptionListBean {
        /**
         * optionCode : 20101
         * optionNum : A
         * optionTitle : A、视野开阔的原野
         */

        private int optionCode;
        private String optionNum;
        private String optionTitle;

        public int getOptionCode() {
            return optionCode;
        }

        public void setOptionCode(int optionCode) {
            this.optionCode = optionCode;
        }

        public String getOptionNum() {
            return optionNum;
        }

        public void setOptionNum(String optionNum) {
            this.optionNum = optionNum;
        }

        public String getOptionTitle() {
            return optionTitle;
        }

        public void setOptionTitle(String optionTitle) {
            this.optionTitle = optionTitle;
        }
    }
}
