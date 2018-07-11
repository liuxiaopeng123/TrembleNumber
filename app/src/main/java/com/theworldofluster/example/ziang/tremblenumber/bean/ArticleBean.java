package com.theworldofluster.example.ziang.tremblenumber.bean;

/**
 * Created by liupeng on 2018/7/9.
 */

public class ArticleBean {

    /**
     * articleCode : 1
     * articleContext : 记忆减退是一种记忆障碍， 临床上较多见，可以表现为远记忆力和近记忆力的减退。主要见于脑器质性精神障碍。脑器质性损害患者最早出现的是近记忆力的减退，患者记不住最近几天，甚至当天的进食情况，或记不住近几天谁曾前来看望等等。病情严重后远记忆力也减退，例如，回忆不起本人经历等。
     东西不记得放在哪了，工作中总是丢三落四……现代生活中，“呀，忘了”这句话已经频繁地成为年轻人的口头禅。越来越多的年轻人也开始像老年人—样“忘性大”。年轻人记忆力减退的原因不同于老年人，由疾病所引起的占极少数，一般都是由于学习生活等因素造成精神高度紧张或连续用脑过度使神经疲劳所致。
     1、睡眠质量不好
     由于现在社会工作生活压力大，导致大量年轻人有失眠的症状，失眠质量低下的人，记忆力容易衰退。人体的睡眠质量如何，对记忆力有重要影响。睡眠质量低下，大脑得不到有效休息，记忆能够受损，就常常忘记事情。另外，患神经衰弱之所以出现健忘情况，多于失眠障碍、注意力分散等因素有关。
     2、过于依赖电脑
     电脑极大的方便了我们的生活、工作以及学习。但平时多于电脑过度依赖，是现时不少20、30岁年轻人出现记忆力衰退的原因。依赖电脑完成计算任务、记录事情等等，都会减少日常脑力劳动，进而大脑功能会慢慢变弱。
     3、过于依赖手机
     手机微波对血脑屏障有损伤，还会伤及大脑皮层以及记忆中枢，严重损伤海马区的神经细胞，导致睡眠紊乱，失眠，记忆力减退，短期记忆力散失，甚至长期记忆力丢失。
     * articleTitle : 记忆减退
     * collected : false
     * createDate : 2018-07-05 18:22:02
     * firstCategoryCode : 1
     * secondCategoryCode : 3
     */

    private int articleCode;
    private String articleContext;
    private String articleTitle;
    private boolean collected;
    private String createDate;
    private int firstCategoryCode;
    private int secondCategoryCode;

    public int getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(int articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleContext() {
        return articleContext;
    }

    public void setArticleContext(String articleContext) {
        this.articleContext = articleContext;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getFirstCategoryCode() {
        return firstCategoryCode;
    }

    public void setFirstCategoryCode(int firstCategoryCode) {
        this.firstCategoryCode = firstCategoryCode;
    }

    public int getSecondCategoryCode() {
        return secondCategoryCode;
    }

    public void setSecondCategoryCode(int secondCategoryCode) {
        this.secondCategoryCode = secondCategoryCode;
    }
}
