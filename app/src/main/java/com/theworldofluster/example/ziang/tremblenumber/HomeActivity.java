package com.theworldofluster.example.ziang.tremblenumber;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.activity.CalendarActivity;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.RankBean;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.login.RegisterActivity;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthAlertActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsychologicalTestActivity;
import com.theworldofluster.example.ziang.tremblenumber.services.PostDataService;
import com.theworldofluster.example.ziang.tremblenumber.user.EditNickNameActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.HelpFeedbackActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends Activity {

    @ViewInject(R.id.home_activity_my)
    ImageView home_activity_my;
    @ViewInject(R.id.home_activity_my_health)
    ImageView home_activity_my_health;
    @ViewInject(R.id.home_activity_yizi)
    ImageView home_activity_yizi;
    @ViewInject(R.id.home_activity_paobuji)
    ImageView home_activity_paobuji;
    @ViewInject(R.id.home_activity_bg)
    RelativeLayout home_activity_bg;

    RelativeLayout home_activity_duihua_rl;

    CircularImage home_activity_duihua_img;

    HorizontalScrollView horizontalScrollView;

    TextView home_activity_duihua_text;

    private TextView home_activity_yindao_zhidaole, home_yindao_tv;
    private RelativeLayout home_glass, home_rl_taili, home_rl_guahua, home_rl_feibiao, home_rl_diannao, home_rl_wenjianjia, home_rl_mofaqiu;


    GifImageView home_activity_pao, home_activity_zuozhe, home_activity_shuijiao, home_activity_taili, home_activity_zhanzhe, home_activity_wenjianjia, home_activity_diannao, home_activity_feibiao, home_activity_guahua, home_activity_mofaqiu;
    GifDrawable paoDrawable, zuozheDrawable, shuijiaoDrawable, tailiDrawable, zhanzheDrawable, jiaxueDrawable, diannaoDrawable, feibiaoDrawable, guahuaDrawable, mofaqiuDrawable;
    SimpleDateFormat sfHour = new SimpleDateFormat("HH");
    int nowHour = -1;
    private  RelativeLayout.LayoutParams lp;
    int home_activity_bg_height;
    int home_activity_bg_width;
    private AnimationDrawable frameAnim;
    private LocationManager locationManager;
    String provider; //位置提供器
    private int flag_jiazai = 5;

    public int screenHeight;

    public int screenWidth;

    private void initWindow() {

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;

        //窗口高度
        screenHeight = dm.heightPixels-getStatusBarHeight(HomeActivity.this);




        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        home_activity_bg.measure(width, height);
        home_activity_bg_height = screenHeight;
        home_activity_bg_width = screenHeight*4/3;
        //坐着的人
//        lp = new RelativeLayout.LayoutParams(home_activity_bg_width, 800);
//        lp.setMargins(0,0, 0, 0);
        home_activity_bg.setMinimumWidth(home_activity_bg_width);
        home_activity_paobuji.setMinimumWidth(home_activity_bg_width);
        home_activity_yizi.setMinimumWidth(home_activity_bg_width);
        Log.i("xiaopeng--背景大图", getStatusBarHeight(HomeActivity.this)+"Height--" + home_activity_bg_height + "Width--" + home_activity_bg_width+"Screen--W" + screenWidth+"Screen--H" + screenHeight);

        //坐姿
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1736), (int) (home_activity_bg_height * 0.4630));
        lp.setMargins((int) (home_activity_bg_width * 0.1639), (int) (screenHeight *0.3333), 0, 0);
        home_activity_zuozhe.setLayoutParams(lp);

        //跑步
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.3889), (int) (home_activity_bg_height * 0.7037));
        lp.setMargins((int) (home_activity_bg_width * 0.0625), (int) (screenHeight *0.2870), 0, 0);
        home_activity_pao.setLayoutParams(lp);

        if ("".equals(PreferenceUtil.getString("isFinishYinDao",""))){
            //站着
            lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1944), (int) (home_activity_bg_height * 0.5185));
            lp.setMargins((int) (home_activity_bg_width * 0.1667), (int) (screenHeight *0.3667), 0, 0);
            home_activity_zhanzhe.setLayoutParams(lp);
        }else {
            //站着
            lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1944), (int) (home_activity_bg_height * 0.5185));
            lp.setMargins((int) (home_activity_bg_width * 0.2667), (int) (screenHeight *0.3667), 0, 0);
            home_activity_zhanzhe.setLayoutParams(lp);
        }


        //睡觉
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.4028), (int) (home_activity_bg_height * 0.2685));
        lp.setMargins((int) (home_activity_bg_width * 0.5514), (int) (screenHeight *0.4704), 0, 0);
        home_activity_shuijiao.setLayoutParams(lp);


        //台历
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.0694), (int) (home_activity_bg_height * 0.0815));
        lp.setMargins((int) (home_activity_bg_width * 0.3160), (int) (screenHeight *0.5231), 0, 0);
        home_rl_taili.setLayoutParams(lp);
        home_activity_taili.setLayoutParams(lp);

        //挂画
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.0764), (int) (home_activity_bg_height * 0.2241));
        lp.setMargins((int) (home_activity_bg_width * 0.0618), (int) (screenHeight *0.2565), 0, 0);
        home_rl_guahua.setLayoutParams(lp);
        home_activity_guahua.setLayoutParams(lp);

        //飞镖
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1250), (int) (home_activity_bg_height * 0.1602));
        lp.setMargins((int) (home_activity_bg_width * 0.3688), (int) (screenHeight *0.3444), 0, 0);
        home_rl_feibiao.setLayoutParams(lp);
        home_activity_feibiao.setLayoutParams(lp);

        //电脑
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.2083), (int) (home_activity_bg_height * 0.1444));
        lp.setMargins((int) (home_activity_bg_width * 0.1674), (int) (screenHeight *0.4907), 0, 0);
        home_rl_diannao.setLayoutParams(lp);
        home_activity_diannao.setLayoutParams(lp);

        //文件夹
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1319), (int) (home_activity_bg_height * 0.1889));
        lp.setMargins((int) (home_activity_bg_width * 0.0750), (int) (screenHeight *0.4833), 0, 0);
        home_rl_wenjianjia.setLayoutParams(lp);
        home_activity_wenjianjia.setLayoutParams(lp);

        //魔法球
        lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1042), (int) (home_activity_bg_height * 0.1343));
        lp.setMargins((int) (home_activity_bg_width * 0.2292), (int) (screenHeight *0.3917), 0, 0);
        home_rl_mofaqiu.setLayoutParams(lp);
        home_activity_mofaqiu.setLayoutParams(lp);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
////去掉Activity上面的状态栏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色

        window.setStatusBarColor(Color.parseColor("#ffffff"));
        if (!PreferenceUtil.getBool("userIsFirstLogin", false)) {
            startActivity(new Intent(HomeActivity.this, SplashActivity.class));
            finish();
        }
        initView();

        initWindow();

        if (PreferenceUtil.getString("finishFirstYinDao", "").equals("")) {
            showYinSiBaoHuDialog();
        }
        checkYinDao();


        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            init();//init为定位方法
        }

        //启动服务
        Intent intent = new Intent(this, PostDataService.class);
        startService(intent);

    }

    private void checkYinDao() {
        if ("".equals(PreferenceUtil.getString("finishFirstYinDao",""))){
            firstYinDao();
            return;
        }else {
            if ("".equals(PreferenceUtil.getString("secondYinDao",""))){
                secondYinDao();
                return;
            }else {
                if ("".equals(PreferenceUtil.getString("finishRegister", ""))) {
                    yinDaoRegister();
                    return;
                } else {
                    if ("".equals(PreferenceUtil.getString("finishNickNameYinDao", ""))) {
                        nickNameYinDao();
                        return;
                    } else {
                        if ("".equals(PreferenceUtil.getString("isLogin", ""))) {
                            if (dialog!=null){
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                    home_activity_duihua_rl.setVisibility(View.GONE);
                                    showLoginDialog();
                                }else {
                                    home_activity_duihua_rl.setVisibility(View.GONE);
                                    showLoginDialog();
                                }
                            }else {
                                showLoginDialog();
                            }
                        } else {
                            home_activity_duihua_rl.setVisibility(View.GONE);
                            getList("");
                        }
                    }
                }
            }

        }
    }

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            init();
        }
    }

    private void init() {
        // 获取系统LocationManager服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 从GPS获取最近的定位信息
        //置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "没有定位权限，请手动开启定位权限！", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 0);
            return;
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // 显示当前设备的位置信息
            PreferenceUtil.putString("MyLongitude", location.getLongitude() + "");
            PreferenceUtil.putString("MyLatitude", location.getLatitude() + "");
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        locationManager.requestLocationUpdates(provider, 6000, 10, locationListener);
    }

    RankBean rankBeanMySelf;
    private void getRankSelf(String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        Log.i("xiaopeng", "url----1:" + MouthpieceUrl.base_pk_recored_rank_self + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<RankBean>>(MouthpieceUrl.base_pk_recored_rank_self , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<RankBean> response, String result) {
                if (response.code==200){
                    rankBeanMySelf=response.data;
                    updateBiaoqing();
                }

                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void updateBiaoqing() {
        if (rankBeanMySelf!=null){
            if (rankBeanMySelf.getTotalScore()<300){
                home_activity_duihua_img.setImageResource(R.drawable.nanguobeishang);
            }else if (rankBeanMySelf.getTotalScore()<350){
                home_activity_duihua_img.setImageResource(R.drawable.yaoyaqiechi);
            }else if (rankBeanMySelf.getTotalScore()<400){
                home_activity_duihua_img.setImageResource(R.drawable.zuohenghengshengqi);
            }else if (rankBeanMySelf.getTotalScore()<450){
                home_activity_duihua_img.setImageResource(R.drawable.mianwubiaoqing);
            }else if (rankBeanMySelf.getTotalScore()<500){
                home_activity_duihua_img.setImageResource(R.drawable.kelianxixibankeai);
            }else if (rankBeanMySelf.getTotalScore()<550){
                home_activity_duihua_img.setImageResource(R.drawable.anwei);
            }else if (rankBeanMySelf.getTotalScore()<600){
                home_activity_duihua_img.setImageResource(R.drawable.pingjingweixiao);
            }else if (rankBeanMySelf.getTotalScore()<650){
                home_activity_duihua_img.setImageResource(R.drawable.liechixiao);
            }else if (rankBeanMySelf.getTotalScore()<700){
                home_activity_duihua_img.setImageResource(R.drawable.jimeinongyanhuaixiao);
            }else {
                home_activity_duihua_img.setImageResource(R.drawable.bixindianzan);
            }
        }else {
            home_activity_duihua_img.setImageResource(R.drawable.pingjingweixiao);
        }

    }

    //获取列表
    List<AleartBean> listAlertBean=null;
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", "0");
        params.addQueryStringParameter("readed", "0");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<AleartBean>>>(MouthpieceUrl.base_health_alert_list , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<AleartBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                listAlertBean=response.data;
                if (response.code==200){
                    if (listAlertBean==null){
                        home_activity_duihua_rl.setVisibility(View.GONE);
                    }else {
                        userZhanzhe();
                        getRankSelf("1");
                        home_activity_duihua_rl.setVisibility(View.VISIBLE);
                        home_activity_duihua_text.setText(""+listAlertBean.get(0).getRemindContext());
                        home_activity_duihua_text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                home_activity_duihua_rl.setVisibility(View.GONE);
                                startActivity(new Intent(HomeActivity.this,HealthAlertActivity.class));
                            }
                        });
                    }

                }

            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void firstYinDao(){
        home_activity_duihua_rl.setVisibility(View.VISIBLE);
        home_activity_duihua_text.setText("早上好，我是MR.卧龙，是你在“抖擞”世界里最忠诚的伙伴！我将及时关注你的心理和身体的健康，并为你定制专属的健康提醒和改善策略，只要你能根据我的建议积极修正日常行为及情绪，即可实现身心健康与平衡。以后你遇到任何困惑，都请与我分享，我将帮你分担、助你解决。");
        home_yindao_tv.setVisibility(View.VISIBLE);
        home_yindao_tv.setText("好的");
        home_yindao_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.putString("finishFirstYinDao","yes");
                home_activity_duihua_rl.setVisibility(View.GONE);
                checkYinDao();
            }
        });
    }

    private void nickNameYinDao() {
        home_activity_duihua_rl.setVisibility(View.VISIBLE);
        home_activity_duihua_text.setText("你好，朋友，以后请多关照！如果你不喜欢我的名字，可以重新帮我取一个");
        home_yindao_tv.setVisibility(View.VISIBLE);
        home_yindao_tv.setText("好的");
        home_yindao_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.putString("finishNickNameYinDao","yes");
                home_activity_duihua_rl.setVisibility(View.GONE);
                lastYinDao();
            }
        });
    }

    private void lastYinDao() {
        home_activity_duihua_rl.setVisibility(View.VISIBLE);
        home_activity_duihua_text.setText("好了，这就是守护你健康的【抖擞的世界】。现在你可以去忙了，有事的时候我喊你。");
        home_yindao_tv.setVisibility(View.VISIBLE);
        home_yindao_tv.setText("好的");
        home_yindao_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.putString("isFinishYinDao","yes");
                home_activity_duihua_rl.setVisibility(View.GONE);
            }
        });
    }

    //首页引导
    private void secondYinDao() {
        home_activity_duihua_rl.setVisibility(View.VISIBLE);
        home_yindao_tv.setVisibility(View.VISIBLE);
        home_activity_duihua_text.setText("下面，我来带你浏览一下“抖擞”的主要功能。");
        home_yindao_tv.setText("好的");

        home_activity_yindao_zhidaole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.putString("secondYinDao","yes");
                home_activity_duihua_rl.setVisibility(View.VISIBLE);
                home_glass.setVisibility(View.GONE);
                checkYinDao();
            }
        });
        home_yindao_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("好的".equals(home_yindao_tv.getText().toString().trim())){
                    home_activity_duihua_rl.setVisibility(View.GONE);
                    home_glass.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    //引导注册登录
    private void yinDaoRegister() {
        home_activity_duihua_rl.setVisibility(View.VISIBLE);
        home_yindao_tv.setVisibility(View.VISIBLE);
        if (PreferenceUtil.getString("isLogin", "").equals("")) {
            home_activity_duihua_text.setText("你喜欢我怎么称呼你？");
            home_yindao_tv.setVisibility(View.VISIBLE);
            home_yindao_tv.setText("去设置你的“昵称”");
            home_yindao_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("去设置你的“昵称”".equals(home_yindao_tv.getText().toString().trim())){
                        startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                        PreferenceUtil.putString("finishRegister","yes");
                        home_activity_duihua_rl.setVisibility(View.GONE);
                    }
                }
            });
        }else {
            home_activity_duihua_rl.setVisibility(View.GONE);

        }

    }

    private void showLoginDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(this, R.layout.dialog_home_login, null);
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                dialog.dismiss();
            }
        });
        TextView cancle = view.findViewById(R.id.cancle);
        cancle.setText("继续浏览");
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_activity_duihua_rl.setVisibility(View.GONE);
                if ("".equals(PreferenceUtil.getString("finishNickNameYinDao", ""))) {
                    checkYinDao();
                }

                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
    Dialog dialog;
    private void showYinSiBaoHuDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_yinsibaohu_home, null);
        dialog.setCanceledOnTouchOutside(false);
        TextView confirm = view.findViewById(R.id.dialog_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYinDao();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


    private void initView() {
        horizontalScrollView=findViewById(R.id.horizontalScrollView);
        horizontalScrollView.scrollTo((int)(screenWidth*0.12),0);
        home_rl_feibiao=findViewById(R.id.home_rl_feibiao);
        home_rl_diannao=findViewById(R.id.home_rl_diannao);
        home_rl_wenjianjia=findViewById(R.id.home_rl_wenjianjia);
        home_rl_mofaqiu=findViewById(R.id.home_rl_mofaqiu);
        home_rl_guahua=findViewById(R.id.home_rl_guahua);
        home_rl_taili=findViewById(R.id.home_rl_taili);
        home_activity_yizi=findViewById(R.id.home_activity_yizi);
        home_activity_paobuji=findViewById(R.id.home_activity_paobuji);
        home_activity_bg = findViewById(R.id.home_activity_bg);
        home_yindao_tv=findViewById(R.id.home_yindao_tv);
        home_activity_yindao_zhidaole=findViewById(R.id.home_activity_yindao_zhidaole);
        home_glass = findViewById(R.id.home_glass);
        home_activity_duihua_rl=findViewById(R.id.home_activity_duihua_rl);
        home_activity_duihua_img=findViewById(R.id.home_activity_duihua_img);
        home_activity_duihua_text=findViewById(R.id.home_activity_duihua_text);
        home_activity_duihua_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                }else {
                    startActivity(new Intent(HomeActivity.this, EditNickNameActivity.class));
                }

            }
        });
//        Utils.BJSloadImg(this,PreferenceUtil.getString("userheadUrl",""),home_activity_duihua_img);

        home_activity_pao=findViewById(R.id.home_activity_pao);
        home_activity_shuijiao=findViewById(R.id.home_activity_shuijiao);
        home_activity_zuozhe=findViewById(R.id.home_activity_zuozhe);
        home_activity_taili=findViewById(R.id.home_activity_taili);
        home_activity_zhanzhe=findViewById(R.id.home_activity_zhanzhe);
        home_activity_wenjianjia=findViewById(R.id.home_activity_wenjianjia);
        home_activity_diannao=findViewById(R.id.home_activity_diannao);
        home_activity_feibiao=findViewById(R.id.home_activity_feibiao);
        home_activity_guahua=findViewById(R.id.home_activity_guahua);
        home_activity_mofaqiu=findViewById(R.id.home_activity_mofaqiu);
        String format = sfHour.format(new Date(System.currentTimeMillis()));

        if ("0".equals(format.substring(0,1))){
            nowHour =Integer.parseInt(format.substring(1,2));
        }else {
            nowHour =Integer.parseInt(format);
        }

        if ("".equals(PreferenceUtil.getString("isFinishYinDao",""))){
            userZhanzhe();
        }else {
            if (nowHour==7){
                userPaobu();
            }else if (nowHour==14){
                userPaobu();
            }else if (nowHour==18){
                userPaobu();
            }else if (nowHour>22||nowHour<6){
                home_activity_yizi.setBackgroundResource(R.drawable.home_bg_yewan);
                home_activity_paobuji.setBackgroundResource(R.drawable.paubuji_yewan);
                home_activity_bg.setBackgroundResource(R.drawable.yizi_yewan);
                userShuijiao();
            }else {
                userZuozhe();
            }
        }

        guahuaDrawable=(GifDrawable) home_activity_guahua.getDrawable();
        guahuaDrawable.setLoopCount(65535);

//        guahuaDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
////                home_activity_guahua.setVisibility(View.INVISIBLE);
//                flag_jiazai++;
//                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthAlertActivity.class));}
//            }
//        });
        feibiaoDrawable=(GifDrawable) home_activity_feibiao.getDrawable();
        feibiaoDrawable.setLoopCount(65535);
//        feibiaoDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
////                home_activity_feibiao.setVisibility(View.INVISIBLE);
//                flag_jiazai++;
//                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthIntegralTableActivity.class));}
//            }
//        });
        diannaoDrawable=(GifDrawable) home_activity_diannao.getDrawable();
        diannaoDrawable.setLoopCount(65535);

//        diannaoDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
////                home_activity_diannao.setVisibility(View.INVISIBLE);
//                flag_jiazai++;
//                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthConsultActivity.class));}
//            }
//        });
        jiaxueDrawable=(GifDrawable) home_activity_wenjianjia.getDrawable();
        jiaxueDrawable.setLoopCount(65535);
//
//        jiaxueDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
////                home_activity_wenjianjia.setVisibility(View.INVISIBLE);
////                flag_jiazai++;
////                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, PersonalActivity.class));}
//            }
//        });
        tailiDrawable = (GifDrawable) home_activity_taili.getDrawable();
        tailiDrawable.setLoopCount(65535);
//        tailiDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
//                flag_jiazai++;
//                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, CalendarActivity.class));}
//            }
//        });
        mofaqiuDrawable = (GifDrawable) home_activity_mofaqiu.getDrawable();
        mofaqiuDrawable.setLoopCount(65535);
//        mofaqiuDrawable.addAnimationListener(new AnimationListener() {
//            @Override
//            public void onAnimationCompleted(int loopNumber) {
////                home_activity_mofaqiu.setVisibility(View.INVISIBLE);
//                flag_jiazai++;
//                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, PsychologicalTestActivity.class));}
//            }
//        });
    }

    private void userZuozhe() {
        home_activity_zuozhe.setVisibility(View.VISIBLE);
        zuozheDrawable = (GifDrawable) home_activity_zuozhe.getDrawable();
        zuozheDrawable.start(); //开始播放
        home_activity_pao.setVisibility(View.GONE);
        home_activity_shuijiao.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.GONE);


    }

    //只显示跑步
    private void userPaobu() {

        paoDrawable = (GifDrawable) home_activity_pao.getDrawable();
        paoDrawable.start(); //开始播放
        home_activity_pao.setVisibility(View.VISIBLE);
        home_activity_zuozhe.setVisibility(View.GONE);
        home_activity_shuijiao.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.GONE);

    }

    //只显示睡觉
    private void userShuijiao() {

        home_activity_pao.setVisibility(View.GONE);
        home_activity_zuozhe.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.GONE);

        home_activity_shuijiao.setVisibility(View.VISIBLE);
        shuijiaoDrawable = (GifDrawable) home_activity_shuijiao.getDrawable();
        shuijiaoDrawable.start(); //开始播放

    }
    //只显示站着
    private void userZhanzhe() {
        home_activity_pao.setVisibility(View.GONE);
        home_activity_zuozhe.setVisibility(View.GONE);
        home_activity_shuijiao.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.VISIBLE);
        zhanzheDrawable = (GifDrawable) home_activity_zhanzhe.getDrawable();
        zhanzheDrawable.start();

    }

    @OnClick({R.id.home_activity_my,R.id.home_activity_my_health,R.id.home_rl_feibiao,R.id.home_rl_diannao,R.id.home_rl_guahua,R.id.home_rl_wenjianjia,R.id.home_rl_mofaqiu,R.id.home_rl_taili})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.home_activity_my:
                if(PreferenceUtil.getString("finishRegister","").equals("")){
                    return;
                }else {
                    if (PreferenceUtil.getString("isLogin", "").equals("")){
                        startActivity(new Intent(HomeActivity.this, HelpFeedbackActivity.class));
                    }else {
                        startActivity(new Intent(HomeActivity.this, MyActivity.class));
                    }
                }

                break;
            case R.id.home_activity_my_health:
                if(PreferenceUtil.getString("finishRegister","").equals("")){
                    return;
                }else {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
//                if (PreferenceUtil.getString("isLogin", "").equals("")) {
//                    ToastUtil.showContent(this,"登录才可访问！");
//                }else {
//                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                }
                break;
            case R.id.home_rl_wenjianjia:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("2")){
                    home_activity_wenjianjia.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, PersonalActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    jiaxueDrawable.reset(); //开始播放
                }
                break;
            case R.id.home_rl_guahua:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("1")) {
                    home_activity_guahua.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, HealthAlertActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    guahuaDrawable.reset(); //开始播放
                }
                break;
            case R.id.home_rl_diannao:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("3")) {
                    home_activity_diannao.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, HealthConsultActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    diannaoDrawable.reset(); //开始播放
                }
                break;
            case R.id.home_rl_feibiao:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("6")) {
                    home_activity_feibiao.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, HealthIntegralTableActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    feibiaoDrawable.reset(); //开始播放
                }
                break;
            case R.id.home_rl_mofaqiu:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("5")) {
                    home_activity_mofaqiu.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, PsychologicalTestActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    mofaqiuDrawable.reset(); //开始播放
                }
                break;
            case R.id.home_rl_taili:
                if (PreferenceUtil.getString("isLogin", "").equals("")) {
                    return;
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("4")) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
                        }
                    }, 100); //延迟2秒跳转
//                    tailiDrawable.reset(); //开始播放
                }
                break;
            default:
                break;
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            PreferenceUtil.putString("MyLongitude",location.getLongitude()+"");
            PreferenceUtil.putString("MyLatitude",location.getLatitude()+"");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if ("".equals(PreferenceUtil.getString("isFinishYinDao",""))){
            //站着
            lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1944), (int) (home_activity_bg_height * 0.5185));
            lp.setMargins((int) (home_activity_bg_width * 0.1667), (int) (screenHeight *0.3667), 0, 0);
            home_activity_zhanzhe.setLayoutParams(lp);
        }else {
            //站着
            lp = new RelativeLayout.LayoutParams((int) (home_activity_bg_width * 0.1944), (int) (home_activity_bg_height * 0.5185));
            lp.setMargins((int) (home_activity_bg_width * 0.2667), (int) (screenHeight *0.3667), 0, 0);
            home_activity_zhanzhe.setLayoutParams(lp);
        }
        if ("".equals(PreferenceUtil.getString("finishRegister",""))){
            tailiDrawable.stop();
//            yinDaoRegister();
        }else {
            checkYinDao();
            if ("".equals(PreferenceUtil.getString("isLogin",""))){
                tailiDrawable.stop();
                mofaqiuDrawable.stop();
                feibiaoDrawable.stop();
                diannaoDrawable.stop();
                guahuaDrawable.stop();
                jiaxueDrawable.stop();
                home_activity_wenjianjia.setVisibility(View.INVISIBLE);
                home_activity_guahua.setVisibility(View.INVISIBLE);
                home_activity_diannao.setVisibility(View.INVISIBLE);
                home_activity_feibiao.setVisibility(View.INVISIBLE);
                home_activity_mofaqiu.setVisibility(View.INVISIBLE);
                if (dialog!=null){
                    if (dialog.isShowing()){
                        dialog.dismiss();
                        home_activity_duihua_rl.setVisibility(View.GONE);
                        showLoginDialog();
                    }else {
                        home_activity_duihua_rl.setVisibility(View.GONE);
                        showLoginDialog();
                    }
                }
            }else {
//                home_activity_duihua_rl.setVisibility(View.GONE);
//                getList("1");
                tailiDrawable.stop();
                mofaqiuDrawable.stop();
                feibiaoDrawable.stop();
                diannaoDrawable.stop();
                guahuaDrawable.stop();
                jiaxueDrawable.stop();
                home_activity_wenjianjia.setVisibility(View.INVISIBLE);
                home_activity_guahua.setVisibility(View.INVISIBLE);
                home_activity_diannao.setVisibility(View.INVISIBLE);
                home_activity_feibiao.setVisibility(View.INVISIBLE);
                home_activity_mofaqiu.setVisibility(View.INVISIBLE);
                if (PreferenceUtil.getString("mainIcons","0000").contains("2")){
                    home_activity_wenjianjia.setVisibility(View.VISIBLE);
                    jiaxueDrawable.start(); //开始播放
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("1")) {
                    home_activity_guahua.setVisibility(View.VISIBLE);
                    guahuaDrawable.start(); //开始播放
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("3")) {
                    home_activity_diannao.setVisibility(View.VISIBLE);
                    diannaoDrawable.start(); //开始播放
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("6")) {
                    home_activity_feibiao.setVisibility(View.VISIBLE);
                    feibiaoDrawable.start(); //开始播放
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("5")) {
                    home_activity_mofaqiu.setVisibility(View.VISIBLE);
                    mofaqiuDrawable.start(); //开始播放
                }
                if (PreferenceUtil.getString("mainIcons","0000").contains("4")) {
                    home_activity_taili.setVisibility(View.VISIBLE);
                    tailiDrawable.start(); //开始播放
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            init();
        }
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    /**
     * 退出程序的逻辑
     *
     * @param keyCode
     * @param event
     * @return
     */
    private static boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
