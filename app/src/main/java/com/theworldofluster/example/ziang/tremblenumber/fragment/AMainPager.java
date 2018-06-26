package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.EditActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.HelpFeedbackActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyAboutActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.NoticeActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.RankCenterActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.SettingActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Ziang on 2018/3/29.
 */

public class AMainPager extends Fragment implements View.OnClickListener {

    private TextView pager_agmenmain_userdata;
    private TextView mian_rankcenter;
    private LinearLayout pager_agmenmain_notice,pager_agmenmain_seting,pager_agmenmain_help_feedback;

    private LinearLayout my_activity;

    private TextView pager_agmenmain_num1,pager_agmenmain_num2,pager_agmenmain_num3;

    private TextView pager_agmenmain_username,pager_agmenmain_sgin;

    private CircularImage pager_agmenmain_headurl;

    View view;

    HttpDialog dia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_agmenmain, container, false);
            initData();

            base_useruserinfo();
        }


        return view;
    }

    private void initData() {

        dia = new HttpDialog(getActivity());
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

        pager_agmenmain_userdata = view.findViewById(R.id.pager_agmenmain_userdata);
        mian_rankcenter = view.findViewById(R.id.mian_rankcenter);
        pager_agmenmain_notice = view.findViewById(R.id.pager_agmenmain_notice);
        pager_agmenmain_seting = view.findViewById(R.id.pager_agmenmain_seting);
        pager_agmenmain_help_feedback = view.findViewById(R.id.pager_agmenmain_help_feedback);
        my_activity = view.findViewById(R.id.my_activity);
        pager_agmenmain_num1 = view.findViewById(R.id.pager_agmenmain_num1);
        pager_agmenmain_num2 = view.findViewById(R.id.pager_agmenmain_num2);
        pager_agmenmain_num3 = view.findViewById(R.id.pager_agmenmain_num3);
        pager_agmenmain_headurl = view.findViewById(R.id.pager_agmenmain_headurl);
        pager_agmenmain_username = view.findViewById(R.id.pager_agmenmain_username);
        pager_agmenmain_sgin = view.findViewById(R.id.pager_agmenmain_sgin);

        pager_agmenmain_userdata.setOnClickListener(this);
        mian_rankcenter.setOnClickListener(this);
        pager_agmenmain_notice.setOnClickListener(this);
        pager_agmenmain_seting.setOnClickListener(this);
        pager_agmenmain_help_feedback.setOnClickListener(this);
        my_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.pager_agmenmain_userdata:

                startActivity(new Intent(getActivity(),EditActivity.class));

                break;
            case R.id.mian_rankcenter:

                startActivity(new Intent(getActivity(),RankCenterActivity.class));

                break;
            case R.id.pager_agmenmain_notice:

                startActivity(new Intent(getActivity(),NoticeActivity.class));

                break;
            case R.id.pager_agmenmain_seting:

                startActivity(new Intent(getActivity(),SettingActivity.class));

                break;
            case R.id.pager_agmenmain_help_feedback:

                startActivity(new Intent(getActivity(),HelpFeedbackActivity.class));

                break;
            case R.id.my_activity:

                startActivity(new Intent(getActivity(),MyAboutActivity.class));

                break;

        }
    }

    private void base_useruserinfo(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_useruserinfo, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if ("SUCCESS".equals(jsonobject.getString("code"))) {

                        pager_agmenmain_num1.setText(jsonobject.getJSONObject("data").getString("fansNum"));
                        pager_agmenmain_num2.setText(jsonobject.getJSONObject("data").getString("focusNum"));
                        pager_agmenmain_num3.setText(jsonobject.getJSONObject("data").getString("friendNum"));

                        Utils.BJSloadImg(getActivity(),jsonobject.getJSONObject("data").getString("headUrl"),pager_agmenmain_headurl);

                        if(jsonobject.getJSONObject("data").getString("nickName").equals("")){
                            pager_agmenmain_username.setText("未命名");
                        }else{
                            pager_agmenmain_username.setText(jsonobject.getJSONObject("data").getString("nickName"));
                        }

                        if(jsonobject.getJSONObject("data").getString("signature").equals("")){
                            pager_agmenmain_sgin.setText("这位用户很懒，什么都没留下—");
                        }else{
                            pager_agmenmain_sgin.setText(jsonobject.getJSONObject("data").getString("signature"));
                        }

                    }else{

                        ToastUtil.showContent(getActivity(),jsonobject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("ZiangF-用户信息",msg);
                dia.dismiss();
            }
        });
    }

}
