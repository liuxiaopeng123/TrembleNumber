package com.theworldofluster.example.ziang.tremblenumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.activity.CalendarActivity;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthAlertActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsychologicalTestActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends Activity {

    @ViewInject(R.id.home_activity_my)
    ImageView home_activity_my;
    @ViewInject(R.id.home_activity_my_health)
    ImageView home_activity_my_health;



    GifImageView home_activity_pao,home_activity_zuozhe,home_activity_shuijiao,home_activity_taili,home_activity_zhanzhe,home_activity_wenjianjia,home_activity_diannao,home_activity_feibiao,home_activity_guahua,home_activity_mofaqiu;
    GifDrawable paoDrawable,zuozheDrawable,shuijiaoDrawable,tailiDrawable,zhanzheDrawable,jiaxueDrawable,diannaoDrawable,feibiaoDrawable,guahuaDrawable,mofaqiuDrawable;
    SimpleDateFormat sfHour = new SimpleDateFormat("HH");
    int nowHour=-1;
    private AnimationDrawable frameAnim;
    private LocationManager locationManager;
    String provider; //位置提供器
    private int flag_jiazai=5;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#ffffff"));
        if(PreferenceUtil.getString("isLogin","").equals("")){
            startActivity(new Intent(this, LoginActivity.class));
        }


        initView();


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
            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // 显示当前设备的位置信息
            PreferenceUtil.putString("MyLongitude",location.getLongitude()+"");
            PreferenceUtil.putString("MyLatitude",location.getLatitude()+"");
        }

        locationManager.requestLocationUpdates(provider, 6000, 10, locationListener);

    }

    private void initView() {
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
        if (nowHour==7){
            userPaobu();
        }else if (nowHour==14){
            userPaobu();
        }else if (nowHour==18){
            userPaobu();
        }else if (nowHour>22||nowHour<6){
            userShuijiao();
        }else {
            userZuozhe();
        }
        guahuaDrawable=(GifDrawable) home_activity_guahua.getDrawable();
        guahuaDrawable.setLoopCount(1);

        guahuaDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                home_activity_guahua.setVisibility(View.INVISIBLE);
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthAlertActivity.class));}
            }
        });
        feibiaoDrawable=(GifDrawable) home_activity_feibiao.getDrawable();
        feibiaoDrawable.setLoopCount(1);
        feibiaoDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                home_activity_feibiao.setVisibility(View.INVISIBLE);
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthIntegralTableActivity.class));}
            }
        });
        diannaoDrawable=(GifDrawable) home_activity_diannao.getDrawable();
        diannaoDrawable.setLoopCount(1);

        diannaoDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                home_activity_diannao.setVisibility(View.INVISIBLE);
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, HealthConsultActivity.class));}
            }
        });
        jiaxueDrawable=(GifDrawable) home_activity_wenjianjia.getDrawable();
        jiaxueDrawable.setLoopCount(1);

        jiaxueDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                home_activity_wenjianjia.setVisibility(View.INVISIBLE);
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, PersonalActivity.class));}
            }
        });
        tailiDrawable = (GifDrawable) home_activity_taili.getDrawable();
        tailiDrawable.setLoopCount(1);
        tailiDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, CalendarActivity.class));}
            }
        });
        mofaqiuDrawable = (GifDrawable) home_activity_mofaqiu.getDrawable();
        mofaqiuDrawable.setLoopCount(1);
        mofaqiuDrawable.addAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                home_activity_mofaqiu.setVisibility(View.INVISIBLE);
                flag_jiazai++;
                if (flag_jiazai>6){startActivity(new Intent(HomeActivity.this, PsychologicalTestActivity.class));}
            }
        });
    }

    private void userZuozhe() {
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
        home_activity_zuozhe.setVisibility(View.GONE);
        home_activity_shuijiao.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.GONE);

    }

    //只显示睡觉
    private void userShuijiao() {

        home_activity_pao.setVisibility(View.GONE);
        home_activity_zuozhe.setVisibility(View.GONE);
        home_activity_zhanzhe.setVisibility(View.GONE);
        shuijiaoDrawable = (GifDrawable) home_activity_shuijiao.getDrawable();
        shuijiaoDrawable.start(); //开始播放

    }

    @OnClick({R.id.home_activity_my,R.id.home_activity_my_health,R.id.home_rl_feibiao,R.id.home_rl_diannao,R.id.home_rl_guahua,R.id.home_rl_wenjianjia,R.id.home_rl_mofaqiu,R.id.home_rl_taili})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.home_activity_my:
                startActivity(new Intent(HomeActivity.this, MyActivity.class));
                break;
            case R.id.home_activity_my_health:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;
            case R.id.home_rl_wenjianjia:
                home_activity_wenjianjia.setVisibility(View.VISIBLE);
                jiaxueDrawable.reset(); //开始播放
                break;
            case R.id.home_rl_guahua:
                home_activity_guahua.setVisibility(View.VISIBLE);
                guahuaDrawable.reset(); //开始播放
                break;
            case R.id.home_rl_diannao:
                home_activity_diannao.setVisibility(View.VISIBLE);
                diannaoDrawable.reset(); //开始播放
                break;
            case R.id.home_rl_feibiao:
                home_activity_feibiao.setVisibility(View.VISIBLE);
                feibiaoDrawable.reset(); //开始播放
                break;
            case R.id.home_rl_mofaqiu:
                home_activity_mofaqiu.setVisibility(View.VISIBLE);
                mofaqiuDrawable.reset(); //开始播放
                break;
            case R.id.home_rl_taili:
                tailiDrawable.reset(); //开始播放
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
    }
}
