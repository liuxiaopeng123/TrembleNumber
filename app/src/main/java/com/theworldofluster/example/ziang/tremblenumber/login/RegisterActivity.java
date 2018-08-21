package com.theworldofluster.example.ziang.tremblenumber.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.rey.material.app.BottomSheetDialog;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.LoopView;
import com.theworldofluster.example.ziang.tremblenumber.view.OnItemSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.register2_back)
    RelativeLayout register2_back;

    @ViewInject(R.id.register_lla)
    LinearLayout register_lla;

    @ViewInject(R.id.register_llb)
    LinearLayout register_llb;

    @ViewInject(R.id.register_btn)
    TextView register_btn;

    @ViewInject(R.id.activity_register2_getcode)
    TextView activity_register2_getcode;

    @ViewInject(R.id.register_agreement)
    TextView register_agreement;

    @ViewInject(R.id.activity_register2_nameuser)
    EditText activity_register2_nameuser;

    @ViewInject(R.id.activity_register2_password)
    EditText activity_register2_password;

    @ViewInject(R.id.activity_register2_newpassword)
    EditText activity_register2_newpassword;

    @ViewInject(R.id.activity_register2_cb)
    CheckBox activity_register2_cb;

    @ViewInject(R.id.activity_login_password_v)
    View activity_login_password_v;

    @ViewInject(R.id.activity_login_phonecode_v)
    View activity_login_phonecode_v;

    @ViewInject(R.id.rg_content_fragment)
    RadioGroup rg_content_fragment;

    @ViewInject(R.id.register_nickname)
    EditText register_nickname;

    @ViewInject(R.id.register_age)
    TextView register_age;

    HttpDialog dia;

    int register = 0;

    int items = 0;

    private TimeCount time;

    String userphone;
    String password;

    int sex = 0;

    private BottomSheetDialog bottomInterPasswordDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register2);
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

        register2_back.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        register_agreement.setOnClickListener(this);
        register_age.setOnClickListener(this);

        rg_content_fragment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_bottom_agmenthome){
                    sex = 0;
                }else {
                    sex = 1;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register2_back:

                finish();

                break;

            case R.id.register_btn:

                if(register==0){

                    if(activity_register2_nameuser.getText().toString().length()!=11){

                        ToastUtil.showContent(RegisterActivity.this,"请按要求输入手机号");

                        return;
                    }

                    if(activity_register2_password.getText().toString().equals("")){

                        ToastUtil.showContent(RegisterActivity.this,"请输入验证码");

                        return;
                    }

                    if(activity_register2_newpassword.getText().toString().equals("")){

                        ToastUtil.showContent(RegisterActivity.this,"请输入新密码");

                        return;
                    }

                    if(!activity_register2_cb.isChecked()){

                        ToastUtil.showContent(RegisterActivity.this,"请勾选服务协议");

                        return;
                    }

                    base_login_mobilenotecode();

                }else{

                    if(register_age.getText().toString().equals("")){

                        ToastUtil.showContent(RegisterActivity.this,"请选择年龄");

                        return;
                    }


                    if(register_nickname.getText().toString().equals("")){

                        ToastUtil.showContent(RegisterActivity.this,"请输入昵称");

                        return;
                    }


                    base_edituser();
                }


                break;

            case R.id.activity_register2_getcode:

                if(activity_register2_nameuser.getText().toString().length()!=11){

                    ToastUtil.showContent(RegisterActivity.this,"请按要求输入手机号");

                    return;
                }

                base_code();

                break;
            case R.id.register_agreement:

                startActivity(new Intent(RegisterActivity.this,AgreementActivity.class));

                break;
            case R.id.register_age:

                showBottomDialog("请选择年龄");

                break;

        }
    }

    private void base_edituser(){


        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("sex",sex+"");
        params.addQueryStringParameter("age","2");
        params.addQueryStringParameter("nickName",register_nickname.getText().toString());
        params.addQueryStringParameter("signature","");

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_edituser, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---更新用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {


                          LoginActivity.loginactivity.login_username.setText(userphone);
                          LoginActivity.loginactivity.login_password.setText(password);
                          finish();

                    }else{

                        ToastUtil.showContent(RegisterActivity.this,jsonobject.getString("message"));

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


    private void base_login_mobilenotecode(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("phone",activity_register2_nameuser.getText().toString());
        params.addQueryStringParameter("password",activity_register2_newpassword.getText().toString());
        params.addQueryStringParameter("verify",activity_register2_password.getText().toString());

        HttpUtils http = new HttpUtils();
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_login_mobilenotecode + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_login_mobilenotecode, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---注册",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        register_lla.setVisibility(View.GONE);
                        activity_login_password_v.setVisibility(View.INVISIBLE);
                        register_llb.setVisibility(View.VISIBLE);
                        activity_login_phonecode_v.setVisibility(View.VISIBLE);
                        register_btn.setText("完成");
                        register = 2;

                        userphone = activity_register2_nameuser.getText().toString();
                        password = activity_register2_password.getText().toString();
                    }else{

                        ToastUtil.showContent(RegisterActivity.this,jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-注册",msg);
                dia.dismiss();
            }
        });
    }

    private void setLisenner() {
        activity_register2_nameuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(activity_register2_nameuser.getText().toString().length()==11&&!activity_register2_password.getText().toString().equals("")&&!activity_register2_newpassword.getText().toString().equals("")){
                    register_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    register_btn.setClickable(true);
                }else{
                    register_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    register_btn.setClickable(false);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activity_register2_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(activity_register2_nameuser.getText().toString().length()==11&&!activity_register2_password.getText().toString().equals("")&&!activity_register2_newpassword.getText().toString().equals("")){
                    register_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    register_btn.setClickable(true);
                }else{
                    register_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    register_btn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activity_register2_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(activity_register2_nameuser.getText().toString().length()==11&&!activity_register2_password.getText().toString().equals("")&&!activity_register2_newpassword.getText().toString().equals("")){
                    register_btn.setBackgroundResource(R.drawable.button_shape_cricle_dian);

                    register_btn.setClickable(true);
                }else{
                    register_btn.setBackgroundColor(Color.parseColor("#bff1eb"));

                    register_btn.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        time = new TimeCount(60000, 1000);

        activity_register2_getcode.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            activity_register2_getcode.setBackgroundResource(R.drawable.round_gray);
            activity_register2_getcode.setClickable(false);
            activity_register2_getcode.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            activity_register2_getcode.setText("重新获取");
            activity_register2_getcode.setClickable(true);
            activity_register2_getcode.setBackgroundResource(R.drawable.round_dian);

        }
    }

    private void base_code(){
        dia.show();
        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("phone",activity_register2_nameuser.getText().toString());

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_code, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---获取验证码",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        time.start();
                        ToastUtil.showContent(RegisterActivity.this,"验证码获取成功！");

                    }else{

                        ToastUtil.showContent(RegisterActivity.this,jsonobject.getString("message"));

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

    private void showBottomDialog(String title) {

        items = 1;

        View  ad = View.inflate(RegisterActivity.this,R.layout.dialog_select_individual,null);

        TextView dialog_select_title = ad.findViewById(R.id.dialog_select_title);
        final TextView dialog_select_determine = ad.findViewById(R.id.dialog_select_determine);
        TextView dialog_select_close = ad.findViewById(R.id.dialog_select_close);

        dialog_select_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomInterPasswordDialog.dismiss();
            }
        });

        dialog_select_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_age.setText(items+"");

                bottomInterPasswordDialog.dismiss();
            }
        });

        dialog_select_title.setText(title);

        LoopView loopView =  ad.findViewById(R.id.loopView);

        loopView.setNotLoop();//设置是否循环播放
        loopView.setTextSize(20);//设置字体大小

        ArrayList<String> list = new ArrayList();
        for (int i = 1; i < 121; i++) {
            list.add(i+"");
        }

        loopView.setItems(list);
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                items = index + 1;
            }
        });

        bottomInterPasswordDialog = new BottomSheetDialog(RegisterActivity.this);
        bottomInterPasswordDialog
                .heightParam(LoginActivity.loginactivity.screenHeight/3)
                .contentView(ad)
                .inDuration(200)
                .outDuration(200)
                .cancelable(false)
                .show();

    }

}
