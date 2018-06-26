package com.theworldofluster.example.ziang.tremblenumber.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_setting_about)
    public LinearLayout activity_setting_about;

    @ViewInject(R.id.activity_setting_securityandprivacy)
    public LinearLayout activity_setting_securityandprivacy;

    @ViewInject(R.id.pager_agmenmain_newnews)
    public LinearLayout pager_agmenmain_newnews;

    @ViewInject(R.id.activity_setting_back)
    public RelativeLayout activity_setting_back;

    @ViewInject(R.id.activity_setting_exit)
    public TextView activity_setting_exit;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#00000000"));

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        activity_setting_about.setOnClickListener(this);
        activity_setting_securityandprivacy.setOnClickListener(this);
        pager_agmenmain_newnews.setOnClickListener(this);
        activity_setting_back.setOnClickListener(this);
        activity_setting_exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_setting_about:

                startActivity(new Intent(SettingActivity.this,AboutActivity.class));

                break;
            case R.id.activity_setting_securityandprivacy:

                startActivity(new Intent(SettingActivity.this,SecurityandprivacyActivity.class));

                break;
            case R.id.pager_agmenmain_newnews:

                startActivity(new Intent(SettingActivity.this,NewnewsActivity.class));

                break;
            case R.id.activity_setting_back:

               finish();

                break;
            case R.id.activity_setting_exit:

                PreferenceUtil.putString("isLogin","");

                startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                finish();

                break;
        }
    }
}
