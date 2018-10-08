package com.theworldofluster.example.ziang.tremblenumber.login;

import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.theworldofluster.example.ziang.tremblenumber.utils.PwdCheckUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class FoundPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.register_getcode)
    TextView register_getcode;

    @ViewInject(R.id.login_btn)
    TextView login_btn;

    private TimeCount time;

    @ViewInject(R.id.login_username)
    EditText login_username;

    @ViewInject(R.id.login_password)
    EditText login_password;

    @ViewInject(R.id.login_newpassword)
    EditText login_newpassword;

    @ViewInject(R.id.login_back)
    RelativeLayout login_back;

    HttpDialog dia;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this); //注入view和事件

        dia = new HttpDialog(this);
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#ffffff"));

        setLisenner();

    }

    private void setLisenner() {
        login_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(login_username.getText().toString().length()==11&&login_password.getText().toString().length()==6&&login_newpassword.getText().toString().length()>=6){
                    login_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    login_btn.setClickable(true);
                }else{
                    login_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    login_btn.setClickable(false);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(login_username.getText().toString().length()==11&&login_password.getText().toString().length()==6&&login_newpassword.getText().toString().length()>=6){
                    login_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    login_btn.setClickable(true);
                }else{
                    login_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    login_btn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(login_username.getText().toString().length()==11&&login_password.getText().toString().length()==6&&login_newpassword.getText().toString().length()>=6){
                    login_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    login_btn.setClickable(true);
                }else{
                    login_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    login_btn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        time = new TimeCount(60000, 1000);

        register_getcode.setOnClickListener(this);
        login_back.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.register_getcode:

                if(login_username.getText().toString().length()!=11){

                    ToastUtil.showContent(FoundPasswordActivity.this,"请按要求输入手机号");

                    return;
                }

                base_code();

                break;
            case R.id.login_back:

                finish();

                break;
            case R.id.login_btn:

                if(login_username.getText().toString().length()!=11){

                    ToastUtil.showContent(FoundPasswordActivity.this,"请按要求输入手机号");

                    return;
                }

                if(login_password.getText().toString().length()!=6){

                    ToastUtil.showContent(FoundPasswordActivity.this,"请输入正确验证码");

                    return;
                }

                if(!PwdCheckUtil.isLetterDigit(login_newpassword.getText().toString())){

                    ToastUtil.showContent(FoundPasswordActivity.this,"请按要求输入密码");

                    return;
                }
                if(login_newpassword.getText().toString().contains("_")||login_newpassword.getText().toString().contains("-")){

                    ToastUtil.showContent(FoundPasswordActivity.this,"密码不能包含“-”或“_”");

                    return;
                }

                base_perfectmemberinformation();

                break;
        }

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            register_getcode.setBackgroundResource(R.drawable.round_gray);
            register_getcode.setClickable(false);
            register_getcode.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            register_getcode.setText("重新获取");
            register_getcode.setClickable(true);
            register_getcode.setBackgroundResource(R.drawable.round_dian);

        }
    }

    private void base_code(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("phone",login_username.getText().toString());
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_code, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---获取验证码",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        time.start();
                        ToastUtil.showContent(FoundPasswordActivity.this,"验证码获取成功！");

                    }else{
//                        ToastUtil.showContent(FoundPasswordActivity.this,"验证码错误");
                        ToastUtil.showContent(FoundPasswordActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-获取验证码",msg);
                dia.dismiss();
            }
        });
    }

    private void base_perfectmemberinformation(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("verify",login_password.getText().toString());
        params.addQueryStringParameter("newPwd",login_newpassword.getText().toString());
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_perfectmemberinformation, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---修改密码",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        ToastUtil.showContent(FoundPasswordActivity.this,"修改密码成功！");
                        LoginActivity.loginactivity.login_username.setText(login_username.getText().toString());

                        finish();

                    }else{

                        ToastUtil.showContent(FoundPasswordActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-修改密码",msg);
                dia.dismiss();
            }
        });
    }
}
