package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.LevelBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.EditActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.HelpFeedbackActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyAboutActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.NoticeActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.RankCenterActivity;
import com.theworldofluster.example.ziang.tremblenumber.user.SettingActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
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

    private TextView pager_agmenmain_userdata,my_guanyuyinsibaohu;
    private TextView mian_rankcenter;
    private LinearLayout pager_agmenmain_notice,pager_agmenmain_seting,pager_agmenmain_help_feedback;

    private LinearLayout my_activity;

    private RelativeLayout information_back;

    private ProgressBar pager_level_progressbar;

    private TextView pager_agmenmain_num1,pager_agmenmain_num2,pager_agmenmain_num3,pager_level_growth,pager_level_max_growth;

    private TextView pager_agmenmain_username,pager_agmenmain_sgin;

    private CircularImage pager_agmenmain_headurl;

    private ImageView pager_level_img;

    private String qianming="",nickName;

    View view;

    HttpDialog dia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_agmenmain, container, false);
            initData();

        }


        return view;
    }

    private void initData() {

        dia = new HttpDialog(getActivity());
        dia.getWindow().setDimAmount(0);
        dia.setCanceledOnTouchOutside(false);

        information_back=view.findViewById(R.id.information_back);

        my_guanyuyinsibaohu=view.findViewById(R.id.my_guanyuyinsibaohu);
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

        pager_level_growth=view.findViewById(R.id.pager_level_growth);
        pager_level_max_growth=view.findViewById(R.id.pager_level_max_growth);
        pager_level_progressbar =view.findViewById(R.id.pager_level_progressbar);
        pager_level_img=view.findViewById(R.id.pager_level_img);

        information_back.setOnClickListener(this);
        my_guanyuyinsibaohu.setOnClickListener(this);
        pager_agmenmain_userdata.setOnClickListener(this);
        mian_rankcenter.setOnClickListener(this);
        pager_agmenmain_notice.setOnClickListener(this);
        pager_agmenmain_seting.setOnClickListener(this);
        pager_agmenmain_help_feedback.setOnClickListener(this);
        my_activity.setOnClickListener(this);


        base_useruserinfo();
        getLevel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_guanyuyinsibaohu:
                showDialog();
                break;

            case R.id.information_back:
                getActivity().finish();
                break;

            case R.id.pager_agmenmain_userdata:

                Intent intent2 =new Intent(getActivity(),EditActivity.class);
                intent2.putExtra("qianming",qianming);
                intent2.putExtra("nickName",nickName);
                startActivityForResult(intent2,0,null);

                break;
            case R.id.mian_rankcenter:
                Intent intent = new Intent(getActivity(),RankCenterActivity.class);
                if (levelBean!=null){
                    intent.putExtra("level",""+levelBean.getLevelInfoVo().getLevel());
                }
                startActivity(intent);

                break;
            case R.id.pager_agmenmain_notice:

                startActivity(new Intent(getActivity(),NoticeActivity.class));

                break;
            case R.id.pager_agmenmain_seting:

                startActivityForResult(new Intent(getActivity(),SettingActivity.class),0);

                break;
            case R.id.pager_agmenmain_help_feedback:

                startActivity(new Intent(getActivity(),HelpFeedbackActivity.class));

                break;
            case R.id.my_activity:

//                startActivity(new Intent(getActivity(),MyAboutActivity.class));

                break;

        }
    }

    Dialog dialog;
    private void showDialog() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(getContext(), R.layout.dialog_yinsibaohu, null);
        TextView confirm = view.findViewById(R.id.dialog_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    LevelBean levelBean;
    private void getLevel() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_level + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<LevelBean>>(MouthpieceUrl.base_level , getContext(), params) {
            @Override
            public void onParseSuccess(GsonObjModel<LevelBean> response, String result) {
                if (response.code==200){
                    levelBean=response.data;
                    updateLevelView();

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
                updateLevelView();
            }
        };
    }

    private void updateLevelView() {

        if (levelBean==null){
            return;
        }
        if (levelBean.getLevelInfoVo().getGrowthMax()>=levelBean.getGrowthValue()){
            if (levelBean.getGrowthValue()!=-1&&levelBean.getGrowthValue()!=-1){
                pager_level_progressbar.setMax(levelBean.getLevelInfoVo().getGrowthMax());
                pager_level_progressbar.setProgress(levelBean.getGrowthValue());
            }
        }
        pager_level_growth.setText(levelBean.getGrowthValue()+"");
        if (levelBean.getLevelInfoVo().getLevel()==0){
            pager_level_max_growth.setText(" 成长值");
        }else {
            pager_level_max_growth.setText("/"+levelBean.getLevelInfoVo().getGrowthMax()+" 成长值");
        }
        switch (levelBean.getLevelInfoVo().getLevel()){
            case 0:
//                pager_level_img.setBackgroundResource(R.mipmap.v1);
                break;
            case 1:
                pager_level_img.setBackgroundResource(R.mipmap.v1);
                break;
            case 2:
                pager_level_img.setBackgroundResource(R.mipmap.v2);
                break;
            case 3:
                pager_level_img.setBackgroundResource(R.mipmap.v3);
                break;
        }
    }

    private void base_useruserinfo(){
        dia.show();

        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_useruserinfo, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ZiangS---用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);

                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {

                        pager_agmenmain_num1.setText(jsonobject.getJSONObject("data").getString("fansNum"));
                        pager_agmenmain_num2.setText(jsonobject.getJSONObject("data").getString("focusNum"));
                        pager_agmenmain_num3.setText(jsonobject.getJSONObject("data").getString("friendNum"));

                        PreferenceUtil.putString("userheadUrl",MouthpieceUrl.base_loading_img+jsonobject.getJSONObject("data").getString("headUrl"));
                        Utils.BJSloadImg(getActivity(),PreferenceUtil.getString("userheadUrl",""),pager_agmenmain_headurl);

                        nickName=jsonobject.getJSONObject("data").getString("nickName");
                        PreferenceUtil.putString("userNickName",jsonobject.getJSONObject("data").getString("nickName"));

                        if(jsonobject.getJSONObject("data").getString("nickName").equals("")){
                            pager_agmenmain_username.setText("未命名");
                        }else{
                            pager_agmenmain_username.setText(jsonobject.getJSONObject("data").getString("nickName"));
                        }

                        qianming=jsonobject.getJSONObject("data").getString("signature");
                        if(jsonobject.getJSONObject("data").getString("signature").equals("")){
                            pager_agmenmain_sgin.setText("这位用户很懒，什么都没留下—");
                        }else{
                            pager_agmenmain_sgin.setText(jsonobject.getJSONObject("data").getString("signature"));
                        }

                    }else{

//                        ToastUtil.showContent(getActivity(),jsonobject.getString("message"));

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


    @Override
    public void onResume() {
        super.onResume();
        base_useruserinfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==111){
            getActivity().finish();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }
}
