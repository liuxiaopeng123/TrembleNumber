package com.theworldofluster.example.ziang.tremblenumber.bean;

/**
 * Created by liupeng on 2018/9/13.
 */

public class ReportBean {

    /**
     * avgScore : 503.386
     * healthRanking : 1
     * latterUserHeadUrl :
     * periodBegin : 2018-09-03
     * periodEnd : 2018-09-09
     * physiologyAnalysis : 撒花~~你近期健康状态很不错，身体运转良好，身体很棒，吃嘛嘛香~你现在的感觉也相当不错吧，请继续保持良好的生活习惯，疾病将会离你远远滴！
     * physiologyScore : 400
     * psychologyAnalysis : 太棒了，最近你的心情一直不错，心情舒畅、动力满满，不论是家庭还是工作看来你都处理的游刃有余，不过良好的情绪也是要靠有效的自我调节保持，让自己保持良好的生活习惯，有效的沟通以及适当的运动，都是不错的选择，不过，好友推荐的资讯也是不容错过的，会让你的情绪得到良好的调节。
     * psychologyScore : 223.03
     * score : 623.03
     * scoreTrend : 1
     */

    private double avgScore;
    private int healthRanking;
    private String latterUserHeadUrl;
    private String periodBegin;
    private String periodEnd;
    private String physiologyAnalysis;
    private double physiologyScore;
    private String psychologyAnalysis;
    private double psychologyScore;
    private double score;
    private int scoreTrend;

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public int getHealthRanking() {
        return healthRanking;
    }

    public void setHealthRanking(int healthRanking) {
        this.healthRanking = healthRanking;
    }

    public String getLatterUserHeadUrl() {
        return latterUserHeadUrl;
    }

    public void setLatterUserHeadUrl(String latterUserHeadUrl) {
        this.latterUserHeadUrl = latterUserHeadUrl;
    }

    public String getPeriodBegin() {
        return periodBegin;
    }

    public void setPeriodBegin(String periodBegin) {
        this.periodBegin = periodBegin;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getPhysiologyAnalysis() {
        return physiologyAnalysis;
    }

    public void setPhysiologyAnalysis(String physiologyAnalysis) {
        this.physiologyAnalysis = physiologyAnalysis;
    }

    public double getPhysiologyScore() {
        return physiologyScore;
    }

    public void setPhysiologyScore(double physiologyScore) {
        this.physiologyScore = physiologyScore;
    }

    public String getPsychologyAnalysis() {
        return psychologyAnalysis;
    }

    public void setPsychologyAnalysis(String psychologyAnalysis) {
        this.psychologyAnalysis = psychologyAnalysis;
    }

    public double getPsychologyScore() {
        return psychologyScore;
    }

    public void setPsychologyScore(double psychologyScore) {
        this.psychologyScore = psychologyScore;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getScoreTrend() {
        return scoreTrend;
    }

    public void setScoreTrend(int scoreTrend) {
        this.scoreTrend = scoreTrend;
    }
}
