package com.theworldofluster.example.ziang.tremblenumber.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.LevelBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

/**
 *
 * 等级中心
 *
 */

public class RankCenterActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_rank_center_upgrades)
    public TextView activity_rank_center_upgrades;
    @ViewInject(R.id.information_back)
    public RelativeLayout information_back;

    String level = "0";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rank_center);
        ViewUtils.inject(this); //注入view和事件

        level = getIntent().getStringExtra("level");
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

        activity_rank_center_upgrades.setOnClickListener(this);
        information_back.setOnClickListener(this);

        getLevel();
    }

    LevelBean levelBean;
    private void getLevel() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("level", level);
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_level + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<LevelBean>>(MouthpieceUrl.base_level ,this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<LevelBean> response, String result) {
                if (response.code==200){
                    levelBean=response.data;
                }
                Log.i("xiaopeng-----6","result6-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----6","result6-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_rank_center_upgrades:
                startActivity(new Intent(RankCenterActivity.this,UpgradesActivity.class));
                break;

            case R.id.information_back:
                finish();
                break;
        }
    }
}
