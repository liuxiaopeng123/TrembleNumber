package com.theworldofluster.example.ziang.tremblenumber.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

/**
 *
 * 等级中心
 *
 */

public class RankCenterActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_rank_center_upgrades)
    public TextView activity_rank_center_upgrades;
    @ViewInject(R.id.home_more_progressbar)
    public ProgressBar home_more_progressbar;
    @ViewInject(R.id.information_back)
    public RelativeLayout information_back;
    @ViewInject(R.id.rank_center_growth)
    public TextView rank_center_growth;
    @ViewInject(R.id.rank_center_img)
    public ImageView rank_center_img;
    @ViewInject(R.id.rank_center_username)
    public TextView rank_center_username;
    @ViewInject(R.id.rank_center_lev1)
    private TextView rank_center_lev1;
    @ViewInject(R.id.rank_center_lev2)
    private TextView rank_center_lev2;
    @ViewInject(R.id.rank_center_lev3)
    private TextView rank_center_lev3;


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
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_level + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<LevelBean>>(MouthpieceUrl.base_level ,this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<LevelBean> response, String result) {
                if (response.code==200){
                    levelBean=response.data;
                    updateView();
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
                updateView();
            }
        };
    }


    private void updateView() {
        if (levelBean==null){
            return;
        }
        switch (levelBean.getLevelInfoVo().getLevel()){
            case 0:

                break;
            case 1:
                Drawable drawable = getResources().getDrawable(R.mipmap.v1);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
                rank_center_lev1.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev1.setCompoundDrawables(null,drawable,null,null);
                break;
            case 2:
                Drawable drawable5 = getResources().getDrawable(R.mipmap.v1);
                drawable5.setBounds(0, 0, drawable5.getIntrinsicWidth(), (int) (drawable5.getMinimumHeight()));
                rank_center_lev1.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev1.setCompoundDrawables(null,drawable5,null,null);
                Drawable drawable2 = getResources().getDrawable(R.mipmap.v2);
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), (int) (drawable2.getMinimumHeight()));
                rank_center_lev2.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev2.setCompoundDrawables(null,drawable2,null,null);
                break;
            case 3:
                Drawable drawable1 = getResources().getDrawable(R.mipmap.v1);
                drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), (int) (drawable1.getMinimumHeight()));
                rank_center_lev1.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev1.setCompoundDrawables(null,drawable1,null,null);
                Drawable drawab = getResources().getDrawable(R.mipmap.v2);
                drawab.setBounds(0, 0, drawab.getIntrinsicWidth(), (int) (drawab.getMinimumHeight()));
                rank_center_lev2.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev2.setCompoundDrawables(null,drawab,null,null);
                Drawable drawable3 = getResources().getDrawable(R.mipmap.v3);
                drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), (int) (drawable3.getMinimumHeight()));
                rank_center_lev3.setTextColor(getResources().getColor(R.color.black_overlay));
                rank_center_lev3.setCompoundDrawables(null,drawable3,null,null);
                break;
        }
        Utils.BJSloadImg(this,PreferenceUtil.getString("userheadUrl",""),rank_center_img);
        rank_center_username.setText(PreferenceUtil.getString("userNickName",""));
        rank_center_growth.setText(levelBean.getGrowthValue()+"");
        if (levelBean.getLevelInfoVo().getGrowthMax()>=levelBean.getGrowthValue()){
            if (levelBean.getGrowthValue()!=-1&&levelBean.getGrowthValue()!=-1){
                home_more_progressbar.setMax(levelBean.getLevelInfoVo().getGrowthMax());
                home_more_progressbar.setProgress(levelBean.getGrowthValue());
            }
        }

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
