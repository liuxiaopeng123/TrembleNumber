package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/7/4.
 */

public class PsyTestBean {


    /**
     * categoryCode : 10001
     * categoryName : 专业级
     * categoryPic : cate/00001.jpg
     * secondCategoryList : [{"categoryCode":"10003","categoryName":"情绪","categoryPic":"cate/00003.jpg","isRecommended":false},{"categoryCode":"10004","categoryName":"人格性格","categoryPic":"cate/00004.jpg","isRecommended":false},{"categoryCode":"10005","categoryName":"家庭","categoryPic":"cate/00005.jpg","isRecommended":false},{"categoryCode":"10006","categoryName":"人际","categoryPic":"cate/00006.jpg","isRecommended":false},{"categoryCode":"10007","categoryName":"亲密关系","isRecommended":false},{"categoryCode":"10008","categoryName":"婚姻","isRecommended":false},{"categoryCode":"10009","categoryName":"成长","isRecommended":false}]
     */

    private String categoryCode;
    private String categoryName;
    private String categoryPic;
    private List<SecondCategoryListBean> secondCategoryList;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }

    public List<SecondCategoryListBean> getSecondCategoryList() {
        return secondCategoryList;
    }

    public void setSecondCategoryList(List<SecondCategoryListBean> secondCategoryList) {
        this.secondCategoryList = secondCategoryList;
    }

    public static class SecondCategoryListBean {
        /**
         * categoryCode : 10003
         * categoryName : 情绪
         * categoryPic : cate/00003.jpg
         * isRecommended : false
         */

        private String categoryCode;
        private String categoryName;
        private String categoryPic;
        private boolean isRecommended;

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryPic() {
            return categoryPic;
        }

        public void setCategoryPic(String categoryPic) {
            this.categoryPic = categoryPic;
        }

        public boolean isIsRecommended() {
            return isRecommended;
        }

        public void setIsRecommended(boolean isRecommended) {
            this.isRecommended = isRecommended;
        }
    }
}
