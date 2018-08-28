package com.theworldofluster.example.ziang.tremblenumber.bean;

import java.util.List;

/**
 * Created by liupeng on 2018/8/22.
 */

public class LevelBean {

    /**
     * backGroupUrl :
     * headUrl :
     * id : 19890528
     * level : 0
     * levelInfoVo : {"growthMax":-1,"id":1,"level":0,"levelAuthorityVoList":[{"id":10000,"logoUrl":"测试url","title":"等级0"}]}
     * userName :
     */

    private String backGroupUrl;
    private String headUrl;
    private int id;
    private int level;
    private LevelInfoVoBean levelInfoVo;
    private String userName;

    public String getBackGroupUrl() {
        return backGroupUrl;
    }

    public void setBackGroupUrl(String backGroupUrl) {
        this.backGroupUrl = backGroupUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LevelInfoVoBean getLevelInfoVo() {
        return levelInfoVo;
    }

    public void setLevelInfoVo(LevelInfoVoBean levelInfoVo) {
        this.levelInfoVo = levelInfoVo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class LevelInfoVoBean {
        /**
         * growthMax : -1
         * id : 1
         * level : 0
         * levelAuthorityVoList : [{"id":10000,"logoUrl":"测试url","title":"等级0"}]
         */

        private int growthMax;
        private int id;
        private int level;
        private List<LevelAuthorityVoListBean> levelAuthorityVoList;

        public int getGrowthMax() {
            return growthMax;
        }

        public void setGrowthMax(int growthMax) {
            this.growthMax = growthMax;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<LevelAuthorityVoListBean> getLevelAuthorityVoList() {
            return levelAuthorityVoList;
        }

        public void setLevelAuthorityVoList(List<LevelAuthorityVoListBean> levelAuthorityVoList) {
            this.levelAuthorityVoList = levelAuthorityVoList;
        }

        public static class LevelAuthorityVoListBean {
            /**
             * id : 10000
             * logoUrl : 测试url
             * title : 等级0
             */

            private int id;
            private String logoUrl;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
