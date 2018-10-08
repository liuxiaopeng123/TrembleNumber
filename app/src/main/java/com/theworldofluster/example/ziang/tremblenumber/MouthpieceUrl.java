package com.theworldofluster.example.ziang.tremblenumber;


import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/3/8.
 *
 * http://47.104.197.109:8080/jeasyvip/swagger-ui.html  api
 *
 * http://47.104.197.109:8080/jeasyvip/admin 后台管理
 *
 */

public class MouthpieceUrl {

    //基础的url
    public static String base_url = "http://39.105.118.62/sliver/";
    //加载图片的url
    public static String base_loading_img = "http://39.105.118.62/image/";
    //用户主页信息
    public static String base_device = base_url+"account/createAccount/device";
    //获取验证法
    public static String base_code = base_url+"account/getVerifyCode";
    //注册
    public static String base_login_mobilenotecode = base_url+"account/createAccount/phone";
    //验证码修改密码
    public static String base_perfectmemberinformation = base_url+"account/resetPwd/verifyCode";
    //登录
    public static String base_updatemembertonickname = base_url+"account/login";
    //获取用户信息
    public static String base_useruserinfo = base_url+"user/userInfo";
    //更新用户头像
    public static String base_obtaininformations = base_url+"user/changeHeadUrl";
    //补充用户信息
    public static String base_edituser = base_url+"/user/editUser";

    //小鹏的接口user/comment

    //验证码修改密码
    public static String base_user_comment= base_url+"user/comment";

    //验证码修改密码
    public static String base_login_by_vertify= base_url+"account/loginWithVerify";

    //验证码修改密码
    public static String base_updatepwduseoldpwd = base_url+"account/resetPwd/oldPwd";

    //查询用户设置
    public static String base_list_user_setting=base_url+"user/listUserSetting";
    //更新用户设置
    public static String base_update_user_setting=base_url+"user/updateUserSetting";
    //等级权限接口
    public static String base_level=base_url+"growthLevel/userLevelInfo";

    //获取测试列表
    public static String base_psy_test_list = base_url+"test/cate";
    //获取测试题集
    public static String base_psy_test_tiji = base_url+"test/set";
    //获取题目列表
    public static String base_psy_test_question_list = base_url+"test/question";
    //提交测试得结果
    public static String base_psy_test_result = base_url+"test/result";
    //更多结果
    public static String base_psy_test_more_result = base_url+"test/result/more";
    //已测试列表
    public static String base_psy_test_completed = base_url+"test/set/completed";
    //心理分析
    public static String base_psy_test_analysis = base_url+"test/analysis";

    //健康资讯列表
    public static String base_health_consult_list = base_url+"health/cate";
    //健康资讯文章
    public static String base_health_consult_articles = base_url+"health/articles";
    //健康资讯收藏文章
    public static String base_health_consult_collect = base_url+"health/collect";

    //健康提醒列表
    public static String base_health_alert_list = base_url+"remind/list";
    //已阅读
    public static String base_health_alert_read = base_url+"remind/read";
    //相同病症人群
    public static String base_health_alert_symptom = base_url+"remind/symptom";

    //查询龙湖榜个人
    public static String base_pk_recored_rank_self = base_url+"rank/self";
    //查询龙湖榜前三
    public static String base_pk_recored_rank_top = base_url+"rank/top";
    //查询龙湖榜列表
    public static String base_pk_recored_rank_list = base_url+"rank/list";
    //查询龙湖榜总榜
    public static String base_pk_recored_rank_total = base_url+"rank/total";

    //查询PK列表
    public static String base_pk_list = base_url+"pk/list";
    //查询PK统计
    public static String base_pk_statistic = base_url+"pk/statistic";
    //查询PK状态
    public static String base_pk_status = base_url+"pk/status";
    //发起PK
    public static String base_pk_launch = base_url+"pk/launch";
    //确认PK
    public static String base_pk_confirm = base_url+"pk/confirm";
    //PK用户查询
    public static String base_pk_user = base_url+"pk/user";
    //PK状态查询
    public static String base_pk_info = base_url+"pk/info";

    //查询心情日记
    public static String base_mood_getbyday = base_url+"mood/getByDay";
    //查询心情日记统计
    public static String base_mood_getMoodStatistics = base_url+"mood/getMoodStatistics";
    //上传心情
    public static String base_mood_save = base_url+"mood/save";
    //更新心情
    public static String base_mood_update = base_url+"mood/update";

    //查询心事
    public static String base_mind_list = base_url+"mind/list";

    //添加心事
    public static String base_mind_add = base_url+"mind/add";

    //删除心事
    public static String base_mind_del = base_url+"mind/del";

    //安慰列表
    public static String base_mind_comfortList = base_url+"mind/comfortList";

    //发表安慰
    public static String base_mind_comfort = base_url+"mind/comfort";

    //删除安慰
    public static String base_mind_delComfort = base_url+"mind/delComfort";

    //抱抱
    public static String base_mind_hug = base_url+"mind/hug";

    //好友列表
    public static String base_mind_friendList = base_url+"friend/friendList";

    //关注列表
    public static String base_mind_addfouces = base_url+"friend/addFocus";

    //收集数据
    public static String base_monitor_dataGrab = base_url+"monitor/dataGrab";

    //获取周健康数据
    public static String base_health_report_week = base_url+"health/report/week";

    //获取健康趋势图
    public static String base_health_report_graph = base_url+"health/report/graph";

    //发布返程需求
    public static String base_adddriverbackdata = base_url+"/appApi/driverBackData/addDriverBackData";
    //获取返程列表
    public static String base_canceldriverbackdata= base_url+"/appApi/driverBackData/cancelDriverBackData";
    //取消返程
    public static String base_obtainclickfarmingdata= base_url+"/appApi/driverBackData/obtainClickFarmingData";
    //获取融云Token
    public static String base_approngcloudgettoken = base_url+"/appApi/member/appRongCloudGetToken";
    // 教程说明接口
    public static String base_obtaintutorials = base_url+"/appApi/tutorials/obtainTutorials";

    //发布代驾出行需求
    public static String base_adddrivertravelorder = base_url+"/appApi/driverTravelOrder/addDriverTravelOrder";
    //司机完成订单
    public static String base_driverconfirmdrivertravelorder = base_url+"/appApi/driverTravelOrder/DriverConfirmDriverTravelOrder";
    //取消代驾行程
    public static String base_canceldrivertravelorder = base_url+"/appApi/driverTravelOrder/cancelDriverTravelOrder";
    //获取我发布的代驾数据
    public static String base_obtainmydrivertravelorder = base_url+"/appApi/driverTravelOrder/obtainMyDriverTravelOrder";
    //新增认证
    public static String base_updatedriver = base_url+"/appApi/driver/updateDriver";
    //更新注册城市
    public static String base_vupdateregisteredcity = base_url+"/appApi/member/updateRegisteredCity";
    //新增计费模板
    public static String base_addbillingtemplate = base_url+"/appApi/billingTemplate/addBillingTemplate";
    //删除计费模板
    public static String base_deletebillingtemplate = base_url+"/appApi/billingTemplate/deleteBillingTemplate";
    //获取我的计费模板信息
    public static String base_obtainmybillingtemplate = base_url+"/appApi/billingTemplate/obtainMyBillingTemplate";
    //更新计费模板
    public static String base_obtainallagent = base_url+"/appApi/billingTemplate/updateBillingTemplate";
    //通过手机号重置密码
    public static String base_resetpwdbycellphone = base_url+"/appApi/login/resetPwdByCellPhone";
    //获取APP的最新版本信息
    public static String base_obtainappversion = base_url+"/appApi/appVersioin/obtainAppVersion";
    //根据ID获取会员信息
    public static String base_obtainmemberbyid= base_url+"/appApi/member/obtainMemberById";
    //第三方登录
    public static String base_appmemeberthirdpartyoauth = base_url+"/appApi/login/appMemeberThirdPartyOauth";
    //新增建议
    public static String base_addguestbook = base_url+"/appApi/guestbook/addGuestbook";
    //获取我的建议列表
    public static String base_obtainmyguestbook = base_url+"/appApi/guestbook/obtainMyGuestbook";
    //第三方登录后绑定手机号码
    public static String base_updatemobile = base_url+"/appApi/member/updateMobile";
    //查询收费城市是否存在 true：存在 false不存在
    public static String base_existareaname = base_url+"/appApi/areaCharge/existAreaName";
    //获取我的司机认证信息列表
    public static String base_obtainmydrivers = base_url+"/appApi/driver/obtainMyDrivers";
    //微信支付入口
    public static String base_getpayparams = base_url+"/appApi/payment/toPayParams";
    //医生更新患者的病例报告签名图片
    public static String base_doctorupdatecasereportsignatureimgdata= base_url+"/appApi/doctor/doctorUpdateCaseReportSignatureImgData";

}


