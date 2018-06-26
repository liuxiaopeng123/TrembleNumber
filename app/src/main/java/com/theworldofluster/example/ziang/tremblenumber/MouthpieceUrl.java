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
    public static String base_loading_img = "http://47.104.197.109:8080/jeasyvip/";
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


