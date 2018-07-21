package com.theworldofluster.example.ziang.tremblenumber.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.adapter.MyFragmentPagerAdapter;
import com.theworldofluster.example.ziang.tremblenumber.fragment.AMainPager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APonePager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APthreePager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APtwoPager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.AUserPager;
import com.theworldofluster.example.ziang.tremblenumber.jpushdemo.ExampleUtil;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

//2018-07-21
public class PersonalActivity extends AppCompatActivity implements SensorEventListener {

    @ViewInject(R.id.view_pager)
    ViewPager mViewPager;

    @ViewInject(R.id.view_pager_box)
    RelativeLayout mViewPagerBox;

    @ViewInject(R.id.activity_personal_bg)
    LinearLayout activity_personal_bg;

    @ViewInject(R.id.activity_personal_userdata)
    CircularImage activity_personal_userdata;

    @ViewInject(R.id.yuan_a)
    ImageView yuan_a;

    @ViewInject(R.id.yuan_b)
    ImageView yuan_b;

    @ViewInject(R.id.yuan_c)
    ImageView yuan_c;

    @ViewInject(R.id.activity_persion_pk)
    TextView activity_persion_pk;

    float strength;

    float X_lateral;
    float Y_longitudinal;
    float Z_vertical;

    private SensorManager mSensroMgr;

    private ArrayList<Fragment> fragmentsList;

    private static final String TAG = "sensor";

    Sensor sensor;

    //所需要申请的权限数组
    private static final String[] permissionsArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS };

    //还需申请的权限列表
    private List<String> permissionsList = new ArrayList<String>();
    //申请权限后的返回码
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_personal);
        ViewUtils.inject(this); //注入view和事件

        JPushInterface.init(getApplicationContext());

        Log.e("JPush", ExampleUtil.getImei(getApplicationContext(), "123"));

        checkRequiredPermission(this);

        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色·
        window.setStatusBarColor(Color.parseColor("#00000000"));

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        mViewPager.setOffscreenPageLimit(3);

        fragmentsList = new ArrayList<Fragment>();

        fragmentsList.add(new APonePager());
        fragmentsList.add(new APtwoPager());
        fragmentsList.add(new APthreePager());

        mViewPager.setAdapter(new MyFragmentPagerAdapter(PersonalActivity.this.getSupportFragmentManager(), fragmentsList));

        mViewPagerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mViewPager.setCurrentItem(1);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==0){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_02);

                    yuan_a.setImageResource(R.mipmap.yuan_a);
                    yuan_b.setImageResource(R.mipmap.yuan_b);
                    yuan_c.setImageResource(R.mipmap.yuan_b);

                }else if(position==1){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_01);

                    yuan_a.setImageResource(R.mipmap.yuan_b);
                    yuan_b.setImageResource(R.mipmap.yuan_a);
                    yuan_c.setImageResource(R.mipmap.yuan_b);

                }else if(position==2){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_03);

                    yuan_a.setImageResource(R.mipmap.yuan_b);
                    yuan_b.setImageResource(R.mipmap.yuan_b);
                    yuan_c.setImageResource(R.mipmap.yuan_a);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        activity_personal_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalActivity.this,MyActivity.class));
            }
        });

        activity_persion_pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalActivity.this,HealthIntegralTableActivity.class));
            }
        });


        mSensroMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //选取加速度感应器
        sensor = mSensroMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener lsn = new SensorEventListener() {
            public void onSensorChanged(SensorEvent e) {
                X_lateral = e.values[SensorManager.DATA_X];
                Y_longitudinal = e.values[SensorManager.DATA_Y];
                Z_vertical = e.values[SensorManager.DATA_Z];

//                Log.e("ZiangX",X_lateral+"");
//                Log.e("ZiangY",Y_longitudinal+"");
//                Log.e("ZiangZ",Z_vertical+"");
            }
            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        // 注册listener，第三个参数是检测的精确度
        mSensroMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void checkRequiredPermission(final Activity activity){
        for (String permission : permissionsArray) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensroMgr.registerListener(this, mSensroMgr.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light_strength = event.values[0];

            strength =  light_strength;

//            Log.e("Ziang","光线强度"+"---"+strength);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，一般无需处理
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensroMgr.unregisterListener(this);
    }


}
