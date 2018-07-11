package com.theworldofluster.example.ziang.tremblenumber.pk;

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
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

public class EditXinShiActivity extends Activity {
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
        setContentView(R.layout.activity_edit_xinshi);
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
                    ToastUtil.showContent(getApplicationContext(),"心情内容太短");
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
        params.addQueryStringParameter("mindContext", edit_xinshi.getText().toString().trim());
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_add + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_mind_add , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    ToastUtil.showContent(getApplicationContext(),"发表心情成功~");
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
