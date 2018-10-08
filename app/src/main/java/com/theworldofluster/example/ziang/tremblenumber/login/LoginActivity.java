package com.theworldofluster.example.ziang.tremblenumber.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.theworldofluster.example.ziang.tremblenumber.HomeActivity;
import com.theworldofluster.example.ziang.tremblenumber.MainActivity;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.utils.ZiangUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xiaopeng";
    @ViewInject(R.id.activity_login_password)
    TextView activity_login_password;

    @ViewInject(R.id.activity_login_phonecode)
    TextView activity_login_phonecode;

    @ViewInject(R.id.activity_login_password_v)
    View activity_login_password_v;

    @ViewInject(R.id.activity_login_phonecode_v)
    View activity_login_phonecode_v;

    @ViewInject(R.id.activity_login_isshowpassword)
    ImageView activity_login_isshowpassword;

    @ViewInject(R.id.login_username)
    public EditText login_username;

    @ViewInject(R.id.login_password)
    public EditText login_password;

    @ViewInject(R.id.login_btn)
    TextView login_btn;

    @ViewInject(R.id.activity_login_foundpassword)
    TextView activity_login_foundpassword;

    @ViewInject(R.id.activity_login_getcode)
    TextView activity_login_getcode;

    @ViewInject(R.id.activity_login_register)
    TextView activity_login_register;

    @ViewInject(R.id.login_back)
    RelativeLayout login_back;

    public int screenHeight ;

    public int screenWidth ;

    private int isshow = 0;

    int logintype = 0;

    private TimeCount time;

    HttpDialog dia;

    String userid="";
    String token="";

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    PreferenceUtil.putBool("setTagSuccess",true);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    public static LoginActivity loginactivity;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this); //注入view和事件

        loginactivity = this;

        dia = new HttpDialog(this);
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
//        window.setStatusBarColor(Color.parseColor("#ffffff"));

        initview();

        base_userInfo();

        initWindow();

        if(PreferenceUtil.getString("isLogin","").equals("")){
        }else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    private void initWindow() {

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;

        //窗口高度
        screenHeight = dm.heightPixels;
    }

    private void base_userInfo(){

        Log.i("xiaopeng---","我爱你123");
        dia.show();

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("imsi", ZiangUtils.getIMSI(LoginActivity.this));
        params.addQueryStringParameter("imei",  ZiangUtils.getIMEI(LoginActivity.this));

//        params.addQueryStringParameter("imsi", "460004306794886");
//        params.addQueryStringParameter("imei",  "865901030381860");

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_device, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("xiaopeng---","我爱你123"+responseInfo.result);
                Log.e("xiaopengS---初始化",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        userid = jsonobject.getJSONObject("data").getString("userId");
                        token = jsonobject.getJSONObject("data").getString("token");

                        PreferenceUtil.putString("userId",jsonobject.getJSONObject("data").getString("userId"));
                        PreferenceUtil.putString("token",jsonobject.getJSONObject("data").getString("token"));

                    }else{
                        ToastUtil.showContent(LoginActivity.this,"信息初始化失败，请重试！");
                    }

                } catch (JSONException e) {
                    Log.i("xiaopeng---","我爱你123"+e);
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("xiaopeng---","我爱你123"+msg);
                Log.e("xiaopengF-初始化",msg);
                dia.dismiss();
            }
        });

    }

    private void initview() {

        login_back.setOnClickListener(this);
        activity_login_password.setOnClickListener(this);
        activity_login_phonecode.setOnClickListener(this);
        activity_login_isshowpassword.setOnClickListener(this);
        activity_login_getcode.setOnClickListener(this);
        activity_login_foundpassword.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        activity_login_register.setOnClickListener(this);

        login_username.setHint("手机号");

        login_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(login_username.getText().toString().length()==11&&!login_password.getText().toString().equals("")){
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

                if(login_username.getText().toString().length()==11&&!login_password.getText().toString().equals("")){
                    login_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    login_btn.setClickable(true);
                }else{
                    login_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    login_btn.setClickable(false);
                }

                if(login_password.getText().toString().equals("")){
                    activity_login_isshowpassword.setVisibility(View.INVISIBLE);
                    activity_login_isshowpassword.setImageResource(R.mipmap.noshowpassword);
                    isshow = 0;
                    login_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    login_password.setSelection(login_password.getText().toString().length());//将光标移至文字末尾
                }else{
                    activity_login_isshowpassword.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        time = new TimeCount(60000, 1000);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_back:

                finish();

                break;
            case R.id.activity_login_getcode:


                if(login_username.getText().toString().length()!=11){

                    ToastUtil.showContent(LoginActivity.this,"请按要求输入手机号");

                    return;
                }

                base_code();


                break;
            case R.id.login_btn:

                if(login_username.getText().toString().length()!=11){

                    ToastUtil.showContent(LoginActivity.this,"请按要求输入手机号");

                    return;
                }

                if(login_password.getText().toString().length()<6){

                    ToastUtil.showContent(LoginActivity.this,"请输入正确密码");

                    return;
                }

                if(login_username.getText().toString().equals("")){

                    if(logintype==0){
                        ToastUtil.showContent(LoginActivity.this,"请输入密码");
                    }else{
                        ToastUtil.showContent(LoginActivity.this,"请输入验证码");
                    }

                    return;
                }

                if(logintype==0){
                    base_updatemembertonickname();
                }else{
                    base_updatemembertonickname2();
                }


                break;
            case R.id.activity_login_foundpassword:

                startActivity(new Intent(LoginActivity.this,FoundPasswordActivity.class));

                break;
            case R.id.activity_login_register:

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

                break;
            case R.id.activity_login_password:

                logintype = 0;

                activity_login_password_v.setVisibility(View.VISIBLE);
                activity_login_phonecode_v.setVisibility(View.INVISIBLE);
                activity_login_password.setTextColor(Color.parseColor("#4A4A4A"));
                activity_login_phonecode.setTextColor(Color.parseColor("#9B9B9B"));
                login_password.setHint("登录密码");
                login_password.setText("");
                activity_login_foundpassword.setVisibility(View.VISIBLE);
                activity_login_getcode.setVisibility(View.GONE);


                break;
            case R.id.activity_login_phonecode:

                logintype = 1;

                activity_login_password_v.setVisibility(View.INVISIBLE);
                activity_login_phonecode_v.setVisibility(View.VISIBLE);
                activity_login_password.setTextColor(Color.parseColor("#9B9B9B"));
                activity_login_phonecode.setTextColor(Color.parseColor("#4A4A4A"));
                login_password.setHint("验证码");
                login_password.setText("");
                activity_login_foundpassword.setVisibility(View.GONE);
                activity_login_getcode.setVisibility(View.VISIBLE);

                break;
            case R.id.activity_login_isshowpassword:

               if(isshow==0){

                   activity_login_isshowpassword.setImageResource(R.mipmap.showpassword);
                   isshow = 1;
                   login_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                   login_password.setSelection(login_password.getText().toString().length());//将光标移至文字末尾
               }else{

                   activity_login_isshowpassword.setImageResource(R.mipmap.noshowpassword);
                   isshow = 0;
                   login_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                   login_password.setSelection(login_password.getText().toString().length());//将光标移至文字末尾
               }

                break;

        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            activity_login_getcode.setBackgroundResource(R.drawable.round_gray);
            activity_login_getcode.setClickable(false);
            activity_login_getcode.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            activity_login_getcode.setText("重新获取");
            activity_login_getcode.setClickable(true);
            activity_login_getcode.setBackgroundResource(R.drawable.round_dian);

        }
    }

    private void base_code(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token",token);
        params.addQueryStringParameter("userId",userid);
        params.addQueryStringParameter("phone",login_username.getText().toString());
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.e("xiaopeng",userid);
        Log.e("xiaopeng",login_username.getText().toString());

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_code, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("xiaopengS---获取验证码",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        time.start();
                        ToastUtil.showContent(LoginActivity.this,"验证码获取成功！");

                    }else{

                        ToastUtil.showContent(LoginActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("xiaopengF-获取验证码",msg);
                dia.dismiss();
            }
        });
    }


    private void base_updatemembertonickname(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token",token);
        params.addQueryStringParameter("userId",userid);
        params.addQueryStringParameter("phone",login_username.getText().toString());

        if(logintype==0){
            params.addQueryStringParameter("password",login_password.getText().toString());
        }else{
            params.addQueryStringParameter("verify",login_password.getText().toString());
        }
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_updatemembertonickname, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("xiaopengS---登录",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        PreferenceUtil.putString("token",jsonobject.getJSONObject("data").getString("token"));
                        PreferenceUtil.putString("userId",jsonobject.getJSONObject("data").getString("userId"));
                        PreferenceUtil.putString("isLogin","yes");
                        PreferenceUtil.putString("finishRegister","yes");
//                        PreferenceUtil.putString("finishNickNameYinDao","yes");
//                        if (!PreferenceUtil.getBool("setTagSuccess",false)){
//
//                        }
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, PreferenceUtil.getString("userId","")));
                        finish();

                    }else{

                        ToastUtil.showContent(LoginActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("xiaopengF-登录",msg);
                dia.dismiss();
            }
        });
    }

    private void base_updatemembertonickname2(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token",token);
//        params.addQueryStringParameter("userId",userid);
        params.addQueryStringParameter("phone",login_username.getText().toString());
        params.addQueryStringParameter("verify",login_password.getText().toString());

        HttpUtils http = new HttpUtils();
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_login_by_vertify + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_login_by_vertify, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("xiaopengS---登录",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        PreferenceUtil.putString("token",jsonobject.getJSONObject("data").getString("token"));
                        PreferenceUtil.putString("userId",jsonobject.getJSONObject("data").getString("userId"));
                        PreferenceUtil.putString("isLogin","yes");
                        PreferenceUtil.putString("finishRegister","yes");
//                        PreferenceUtil.putString("finishNickNameYinDao","yes");
//                        if (!PreferenceUtil.getBool("setTagSuccess",false)){
//                            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, PreferenceUtil.getString("userId","")));
//                        }
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, PreferenceUtil.getString("userId","")));

                        finish();

                    }else{

                        ToastUtil.showContent(LoginActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("xiaopengF-登录",msg);
                dia.dismiss();
            }
        });
    }
}
