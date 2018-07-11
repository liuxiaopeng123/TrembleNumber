package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/7/9.
 */

public class HealthConsultBean {


    /**
     * categoryCode : 1
     * categoryName : 生理资讯
     * secondCategoryList : [{"categoryCode":3,"categoryName":"大脑"},{"categoryCode":4,"categoryName":"鼻部"},{"categoryCode":5,"categoryName":"肠"},{"categoryCode":6,"categoryName":"胆"},{"categoryCode":7,"categoryName":"耳部"},{"categoryCode":8,"categoryName":"肺部"},{"categoryCode":9,"categoryName":"腹膜"},{"categoryCode":10,"categoryName":"肝"}]
     */

    private int categoryCode;
    private String categoryName;
    private List<SecondCategoryListBean> secondCategoryList;

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<SecondCategoryListBean> getSecondCategoryList() {
        return secondCategoryList;
    }

    public void setSecondCategoryList(List<SecondCategoryListBean> secondCategoryList) {
        this.secondCategoryList = secondCategoryList;
    }

    public static class SecondCategoryListBean {
        /**
         * categoryCode : 3
         * categoryName : 大脑
         */

        private int categoryCode;
        private String categoryName;

        public int getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(int categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
