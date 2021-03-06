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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

/**
 *
 * 帮助反馈
 *
 */

public class HelpFeedbackActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_help_feedback);
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
    }

    @OnClick({R.id.information_back,R.id.pager_agmenmain_notice,R.id.pager_agmenmain_seting,R.id.pager_agmenmain_help_feedback})
    private void Onclick(View v){
        Intent intent = new Intent(this,UseActivity.class);
        switch (v.getId()){

            case R.id.information_back:
                finish();
                break;
            case R.id.pager_agmenmain_notice:
                intent.putExtra("position","1");
                startActivity(intent);
                break;
            case R.id.pager_agmenmain_seting:
                intent.putExtra("position","2");
                startActivity(intent);
                break;
            case R.id.pager_agmenmain_help_feedback:
                intent.putExtra("position","3");
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
