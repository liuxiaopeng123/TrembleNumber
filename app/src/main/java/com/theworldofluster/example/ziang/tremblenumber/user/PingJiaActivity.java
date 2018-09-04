package com.theworldofluster.example.ziang.tremblenumber.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

public class PingJiaActivity extends Activity {
    @ViewInject(R.id.activity_edit_xinshi_back)
    RelativeLayout activity_edit_xinshi_back;
    @ViewInject(R.id.activity_edit_xinshi_title)
    TextView activity_edit_xinshi_title;
    @ViewInject(R.id.activity_edit_xinshi_fabu)
    RelativeLayout activity_edit_xinshi_fabu;
    @ViewInject(R.id.edit_xinshi)
    EditText edit_xinshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pingjia);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
    }

    @OnClick({R.id.activity_edit_xinshi_back,R.id.activity_edit_xinshi_fabu})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_edit_xinshi_back:
                finish();
                break;
            case R.id.activity_edit_xinshi_fabu:

                if (edit_xinshi.getText().toString().trim().length()<6){
                    ToastUtil.showContent(getApplicationContext(),"请多填写一些内容");
                }else {
                    commit();
                }

                break;
        }
    }

    private void commit() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("comment", edit_xinshi.getText().toString().trim());
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_user_comment + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_user_comment , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    ToastUtil.showContent(getApplicationContext(),"评价成功");
                    finish();
                }else {
                    ToastUtil.showContent(getApplicationContext(),response.message);
                }
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

}
