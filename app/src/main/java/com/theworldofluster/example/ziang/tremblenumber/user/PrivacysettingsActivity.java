package com.theworldofluster.example.ziang.tremblenumber.user;

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
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.SettingBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 隐私设置
 *
 */

public class PrivacysettingsActivity extends AppCompatActivity {

    ImageView yinsi_im1,yinsi_im2,yinsi_im3,yinsi_im4;

    boolean flag_1,flag_2,flag_3,flag_4,flag_onOff;
    String type1,type2,type3,type4,type;
    private List<SettingBean> settingBeans = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_privacysettings);
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

        initView();

        getListSetting();

    }

    private void initView() {
        yinsi_im1=findViewById(R.id.yinsi_im1);
        yinsi_im2=findViewById(R.id.yinsi_im2);
        yinsi_im3=findViewById(R.id.yinsi_im3);
        yinsi_im4=findViewById(R.id.yinsi_im4);
    }

    private void updateView() {
        for (int i=0;i<settingBeans.size();i++){
            switch (settingBeans.get(i).getSettingType()){
                case "STRANGEVIEW":
                    type1=settingBeans.get(i).getId()+"";
                    flag_1=settingBeans.get(i).isOnOff();
                    if (settingBeans.get(i).isOnOff()){
                        yinsi_im1.setBackgroundResource(R.mipmap.open_mure);
                    }else {
                        yinsi_im1.setBackgroundResource(R.mipmap.close_mure);
                    }
                    break;
                case "FRIENDVIEW":
                    type2=settingBeans.get(i).getId()+"";
                    flag_2=settingBeans.get(i).isOnOff();
                    if (settingBeans.get(i).isOnOff()){
                        yinsi_im2.setBackgroundResource(R.mipmap.open_mure);
                    }else {
                        yinsi_im2.setBackgroundResource(R.mipmap.close_mure);
                    }
                    break;
                case "STRANGEREPORT":
                    type3=settingBeans.get(i).getId()+"";
                    flag_3=settingBeans.get(i).isOnOff();
                    if (settingBeans.get(i).isOnOff()){
                        yinsi_im3.setBackgroundResource(R.mipmap.open_mure);
                    }else {
                        yinsi_im3.setBackgroundResource(R.mipmap.close_mure);
                    }
                    break;
                case "FRIENDREPORT":
                    type4=settingBeans.get(i).getId()+"";
                    flag_4=settingBeans.get(i).isOnOff();
                    if (settingBeans.get(i).isOnOff()){
                        yinsi_im4.setBackgroundResource(R.mipmap.open_mure);
                    }else {
                        yinsi_im4.setBackgroundResource(R.mipmap.close_mure);
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.information_back,R.id.yinsi_im1,R.id.yinsi_im2,R.id.yinsi_im3,R.id.yinsi_im4})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.information_back:
                finish();
                break;
            case R.id.yinsi_im1:
                type="10";
                updateSetting(flag_1,type);
                break;
            case R.id.yinsi_im2:
                type="11";
                updateSetting(flag_2,type);
                break;
            case R.id.yinsi_im3:
                type="12";
                updateSetting(flag_3,type);
                break;
            case R.id.yinsi_im4:
                type="13";
                updateSetting(flag_4,type);
                break;
            default:
                break;
        }
    }

    //获取设置
    private void getListSetting() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_list_user_setting + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<SettingBean>>>(MouthpieceUrl.base_list_user_setting , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<SettingBean>> response, String result) {
                if (response.code==200){
                    settingBeans=response.data;
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
            }
        };
    }

    //更新设置
    private void updateSetting(boolean flag,String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("onOff", !flag+"");
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_update_user_setting + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<SettingBean>>(MouthpieceUrl.base_update_user_setting , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<SettingBean> response, String result) {
                if (response.code==200){
                    getListSetting();
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
}
