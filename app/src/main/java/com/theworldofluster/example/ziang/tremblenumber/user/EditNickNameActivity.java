package com.theworldofluster.example.ziang.tremblenumber.user;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class EditNickNameActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.activity_edit_back)
    private RelativeLayout activity_edit_back;

    @ViewInject(R.id.activity_proposal_content)
    EditText activity_proposal_content;
    @ViewInject(R.id.edit_activity_zishu)
    TextView edit_activity_zishu;


    HttpDialog dia;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_nick_name);
        ViewUtils.inject(this); //注入view和事件


        dia = new HttpDialog(this);
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

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

        activity_edit_back.setOnClickListener(this);

        activity_proposal_content.setText(PreferenceUtil.getString("userNickName","未命名"));

        activity_proposal_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_activity_zishu.setText((20-activity_proposal_content.getText().toString().length())+"");
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_edit_back:
                if (activity_proposal_content.getText().toString().trim().length()<1){
                    ToastUtil.showContent(this,"昵称为空不能返回");
                    return;
                }
                if (PreferenceUtil.getString("userNickName","").equals(activity_proposal_content.getText().toString().trim())){
                    finish();
                }else {
                    base_edituser();
                }
                break;

        }
    }

    private void base_edituser(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("sex",PreferenceUtil.getString("userSex","0"));
        params.addQueryStringParameter("age",PreferenceUtil.getString("userSex","2"));
        params.addQueryStringParameter("nickName",activity_proposal_content.getText().toString().trim());
        params.addQueryStringParameter("signature",PreferenceUtil.getString("qianming",""));

        HttpUtils http = new HttpUtils();
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_edituser + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_edituser, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---更新用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {
                        PreferenceUtil.putString("userNickName",""+activity_proposal_content.getText().toString().trim());
                        finish();
                    }else{

                        ToastUtil.showContent(EditNickNameActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-更新用户信息",msg);
                dia.dismiss();
            }
        });
    }

}
