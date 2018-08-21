package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.RankBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.dialog.HttpDialog;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;
import com.theworldofluster.example.ziang.tremblenumber.view.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

public class HealthIntegralTableActivity extends Activity {

    Dialog dialog;

    boolean flag_button_click=false;

    MyAdapter adapter =null;

    @ViewInject(R.id.rank_top1_img)
    CircularImage rank_top1_img;
    @ViewInject(R.id.rank_top2_img)
    CircularImage rank_top2_img;
    @ViewInject(R.id.rank_top3_img)
    CircularImage rank_top3_img;
    @ViewInject(R.id.rank_top1_name_score)
    TextView rank_top1_name_score;
    @ViewInject(R.id.rank_top2_name_score)
    TextView rank_top2_name_score;
    @ViewInject(R.id.rank_top3_name_score)
    TextView rank_top3_name_score;

    @ViewInject(R.id.health_integral_table_back)
    RelativeLayout health_integral_table_back;
    @ViewInject(R.id.pk_rules)
    RelativeLayout pk_rules;
    @ViewInject(R.id.pk_record)
    TextView pk_record;
    @ViewInject(R.id.activity_health_lv)
    NoScrollListView activity_health_lv;
    @ViewInject(R.id.health_integral_table_invite_pk)
    Button health_integral_table_invite_pk;
    @ViewInject(R.id.activity_pk_record_type_select)
    TextView activity_pk_record_type_select;
    @ViewInject(R.id.health_integral_table_pk_tab)
    TextView health_integral_table_pk_tab;

    @ViewInject(R.id.rank_myself_user_img)
    CircularImage rank_myself_user_img;
    @ViewInject(R.id.rank_myself_nickname)
    TextView rank_myself_nickname;
    @ViewInject(R.id.rank_myself_dabaiduishou)
    TextView rank_myself_dabaiduishou;
    @ViewInject(R.id.rank_myself_ranking)
    TextView rank_myself_ranking;
    @ViewInject(R.id.rank_myself_total_score)
    TextView rank_myself_total_score;

    private String flag_type="1";

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    List<RankBean> rankBeanTop=new ArrayList<>();
    WanNengBean rankBeanTotal = new WanNengBean();
    List<Boolean> status =new ArrayList<>();
    private int flag_count_select_person=0;
    private String commit_pk_ids="";
    List<RankBean> rankBeanList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_integral_table);
        ViewUtils.inject(this); //注入view和事件
        initPopupWindow();
        initView();
        initData();

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            String contentType=null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                contentType=bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.i("xiaopeng-----222","title-"+title+"content-"+content+"contentType-"+contentType);
            }
        }
    }


    private void initView() {
        health_integral_table_back.setFocusable(true);
        health_integral_table_back.setFocusableInTouchMode(true);
        health_integral_table_back.requestFocus();
        adapter = new MyAdapter();

        activity_health_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                Log.i("xiaopeng","--------"+scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("xiaopeng","--------"+firstVisibleItem+"---"+visibleItemCount);
            }
        });

        dia = new HttpDialog(this);

    }

    private void initData() {
        base_useruserinfo();
        getPKStauts();
        getRankSelf(flag_type);
        getRankTop(flag_type);
        getRankTotal(flag_type);
//        getRankList(flag_type);

    }

    HttpDialog dia;
    private void base_useruserinfo(){
        dia.show();
        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId",PreferenceUtil.getString("userId",""));

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, MouthpieceUrl.base_useruserinfo, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("xiaopeng---用户信息",responseInfo.result);
                try {
                    JSONObject jsonobject = new JSONObject(responseInfo.result);
                    if (200==jsonobject.getInt("code")||"SUCCESS".equals(jsonobject.getString("code"))) {
                        Utils.BJSloadImg(getApplicationContext(),jsonobject.getJSONObject("data").getString("headUrl"),rank_myself_user_img);
                        if(jsonobject.getJSONObject("data").getString("nickName").equals("")){
                            rank_myself_nickname.setText("未命名");
                        }else{
                            rank_myself_nickname.setText(jsonobject.getJSONObject("data").getString("nickName"));
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dia.dismiss();
            }
            @Override
            public void onFailure(HttpException error, String msg) {
                Log.e("xiaopeng---用户信息",msg);
                dia.dismiss();
            }
        });
    }

    RankBean rankBeanMySelf=new RankBean();
    private void getRankSelf(String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        Log.i("xiaopeng", "url----1:" + MouthpieceUrl.base_pk_recored_rank_self + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<RankBean>>(MouthpieceUrl.base_pk_recored_rank_self , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<RankBean> response, String result) {
                if (response.code==200){
                    rankBeanMySelf=response.data;
                    rank_myself_total_score.setText(response.data.getTotalScore()+"↑");
                    rank_myself_ranking.setText(""+response.data.getRanking());
                    rank_myself_dabaiduishou.setText("打败了"+response.data.getPercent()+"%的对手");
                }

                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void getRankTop(String type) {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        Log.i("xiaopeng", "url----2:" + MouthpieceUrl.base_pk_recored_rank_top + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<RankBean>>>(MouthpieceUrl.base_pk_recored_rank_top , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<RankBean>> response, String result) {
                if (response.code==200){
                    rankBeanTop =response.data;
                    if (rankBeanTop!=null){
                        initTopData();
                    }

                }
                Log.i("xiaopeng-----","result2-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result222222-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void initTopData() {
        for (int i=0;i<rankBeanTop.size();i++){
            switch (rankBeanTop.get(i).getRanking()){
                case 1:
                    rank_top1_name_score.setText(rankBeanTop.get(i).getNickName()+"|"+rankBeanTop.get(i).getTotalScore());
                    break;
                case 2:
                    rank_top2_name_score.setText(rankBeanTop.get(i).getNickName()+"|"+rankBeanTop.get(i).getTotalScore());
                    break;
                case 3:
                    rank_top3_name_score.setText(rankBeanTop.get(i).getNickName()+"|"+rankBeanTop.get(i).getTotalScore());
                    break;
            }
            Log.i("xiaopeng-----","rank"+rankBeanTop.get(i).getRanking());
        }
    }

    private void getRankTotal(String type) {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("top", ""+false);
        params.addQueryStringParameter("self", ""+true);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        params.addQueryStringParameter("pageIndex","1");
        params.addQueryStringParameter("pageSize", "11");
        Log.i("xiaopeng", "url----3:" + MouthpieceUrl.base_pk_recored_rank_total + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_recored_rank_total , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    rankBeanTotal = response.data;
                    if (rankBeanTotal!=null){
                        if (rankBeanTotal.getListRank()!=null){
                            for (int i=0 ;i<rankBeanTotal.getListRank().size();i++){
                                status.add(false);
                            }
                            activity_health_lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                Log.i("xiaopeng-----","result3-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result3333-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void getRankList(String type) {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        params.addQueryStringParameter("pageIndex","1");
        params.addQueryStringParameter("pageSize", "10");
        Log.i("xiaopeng", "url----4:" + MouthpieceUrl.base_pk_recored_rank_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<PsyTestBean>>(MouthpieceUrl.base_pk_recored_rank_list , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<PsyTestBean> response, String result) {
                Log.i("xiaopeng-----4","result4-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----4","result4-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private int flag_user_pk_status=-1;
    private String desc="";
    private void getPKStauts() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----5:" + MouthpieceUrl.base_pk_status + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_status , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    flag_user_pk_status=response.data.getCode();
                    desc=response.data.getDesc();
                }
                Log.i("xiaopeng-----5","result5-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----5","result5-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    @OnClick({R.id.health_integral_table_pk_tab,R.id.health_integral_table_back,R.id.pk_rules,R.id.pk_record,R.id.health_integral_table_invite_pk,R.id.activity_pk_record_type_select})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.health_integral_table_pk_tab:
//                startActivity(new Intent(HealthIntegralTableActivity.this,PsychologicalTestActivity.class));
                break;
            case R.id.health_integral_table_back:
                finish();
                break;
            case R.id.pk_rules:

                showPKAboutDialog();
                break;
            case R.id.pk_record:
                startActivity(new Intent(HealthIntegralTableActivity.this,PKRecordActivity.class));
                break;
            case R.id.activity_pk_record_type_select:
                showPopWindow();
                break;
            case R.id.health_integral_table_invite_pk:
                if (PreferenceUtil.getBool("isNotFirstPK",false)){
                    switch (flag_user_pk_status){
                        case 100:
                            if (flag_button_click){
                                if(flag_count_select_person<1){
                                    ToastUtil.showContent(this,"请至少选择一名对手");
                                }else {
                                    showConfirmDialog();
                                }
                            }else {
                                flag_button_click=true;
                                health_integral_table_invite_pk.setText("邀请PK");
                                adapter.notifyDataSetChanged();
                            }
                            break;
                        case 101:
                            ToastUtil.showContent(this,desc);
                            break;
                        case 102:
                            ToastUtil.showContent(this,desc);
                            break;
                        case 103:
                            ToastUtil.showContent(this,desc);
                            break;

                    }
                }else {
                    PreferenceUtil.putBool("isNotFirstPK",true);
                    showPKAboutDialog();
                }
                break;

            default:
                break;
        }
    }

    PopupWindow popupWindow;
    private void initPopupWindow() {
        View contentView;
        //要在布局中显示的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.bottom_popup_window_health, null, false);
        //实例化PopupWindow并设置宽高
        TextView cancle = contentView.findViewById(R.id.cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView item_1 = contentView.findViewById(R.id.item_1);
        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity_pk_record_type_select.setText("好友排名 ");
                flag_type="2";
                initData();
            }
        });
        TextView item_2 = contentView.findViewById(R.id.item_2);
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity_pk_record_type_select.setText("全网排名 ");
                flag_type="1";
                initData();
            }
        });
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
    }

    private void showPopWindow() {
        View rootview = LayoutInflater.from(HealthIntegralTableActivity.this).inflate(R.layout.activity_health_integral_table, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private void showPKAboutDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_about_pk, null);
        ImageView close =view.findViewById(R.id.dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView close2 =view.findViewById(R.id.dialog_close2);
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void showConfirmDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk, null);
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pkLauch();
//                showFinishDialog();
            }
        });
        TextView cancle = view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    //发起PK
    private void pkLauch() {
        dia.show();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("targetList", commit_pk_ids.substring(0,commit_pk_ids.length()-1));
        Log.i("xiaopeng", "url----pklauch:" + MouthpieceUrl.base_pk_launch + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_launch , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    showFinishDialog();
                }
                Log.i("xiaopeng-----pklauch","resultpklauch-----"+result);
                dia.dismiss();
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----pklauch","resultpklauch-----"+result);
                dia.dismiss();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
                dia.dismiss();
            }
        };
    }

    private void showLoadingDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk_loading, null);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showFinishDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk_finish, null);
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return rankBeanTotal.getListRank()==null?0:rankBeanTotal.getListRank().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_health_integral_table, null);
            }
            final CheckBox item_health_check_box = convertView.findViewById(R.id.item_health_check_box);
            if (flag_button_click){
                if (rankBeanMySelf.getUserId().equals(rankBeanTotal.getListRank().get(position).getUserId())){
                    item_health_check_box.setVisibility(View.INVISIBLE);
                }else {
                    item_health_check_box.setVisibility(View.VISIBLE);
                }

            }else {
                item_health_check_box.setVisibility(View.GONE);
            }
            item_health_check_box.setChecked(status.get(position));
            item_health_check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag_count_select_person>=5){
                        if (commit_pk_ids.contains(rankBeanTotal.getListRank().get(position).getUserId()+"")){
                            status.set(position,!status.get(position));
                            item_health_check_box.setChecked(status.get(position));
                            adapter.notifyDataSetChanged();
                            flag_count_select_person=0;
                            commit_pk_ids="";
                            for (int i=0 ;i<status.size();i++){
                                if (status.get(i)){
                                    flag_count_select_person++;
                                    commit_pk_ids+=rankBeanTotal.getListRank().get(i).getUserId()+",";
                                }
                            }
                        }else {
                            ToastUtil.showContent(getApplicationContext(),"一次最多可向5位用户发起PK邀请");
                        }

                        adapter.notifyDataSetChanged();
                    }else {
                        status.set(position,!status.get(position));
                        item_health_check_box.setChecked(status.get(position));
                        adapter.notifyDataSetChanged();
                        flag_count_select_person=0;
                        commit_pk_ids="";
                        for (int i=0 ;i<status.size();i++){
                            if (status.get(i)){
                                flag_count_select_person++;
                                commit_pk_ids+=rankBeanTotal.getListRank().get(i).getUserId()+",";
                            }

                        }
                    }

                    Log.i("xiaopeng----",commit_pk_ids+"+++"+"--"+flag_count_select_person);
                }
            });
//            item_health_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){//选择的时候
//                        if(flag_count_select_person>=5){
//                            ToastUtil.showContent(getApplicationContext(),"最多选择5个对手进行PK！");
//                            return;
//                        }else {
//
//                        }
//                    }
//                    flag_count_select_person=0;
//                    commit_pk_ids="";
//                    status.set(position,isChecked);
//                    for (int i=0 ;i<status.size();i++){
//                        if (status.get(i)){
//                            flag_count_select_person++;
//                            commit_pk_ids+=rankBeanTotal.getListRank().get(i).getUserId()+",";
//                        }
//
//                    }
//                    Log.i("xiaopeng----",commit_pk_ids+"+++"+isChecked+position+"--"+flag_count_select_person);
//                }
//            });
            TextView pk_record_num = convertView.findViewById(R.id.pk_record_num);
            TextView pk_record_name =convertView.findViewById(R.id.pk_record_name);
            CircularImage pk_record_img =convertView.findViewById(R.id.pk_record_img);
            TextView pk_record_score =convertView.findViewById(R.id.pk_record_score);
            pk_record_num.setText(""+rankBeanTotal.getListRank().get(position).getRanking());
            pk_record_name.setText(""+rankBeanTotal.getListRank().get(position).getNickName());

            switch (rankBeanTotal.getListRank().get(position).getTrend()){
                case -1:
                    pk_record_score.setText(rankBeanTotal.getListRank().get(position).getTotalScore()+"↓");
                    break;
                case 0:
                    pk_record_score.setText(rankBeanTotal.getListRank().get(position).getTotalScore()+" ");
                    break;
                case 1:
                    pk_record_score.setText(rankBeanTotal.getListRank().get(position).getTotalScore()+"↑");
                    break;
            }
            return convertView;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            String contentType=null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                contentType=bundle.getString(JPushInterface.EXTRA_EXTRA).toString();
                Log.i("xiaopeng-----222","title-"+title+"content-"+content+"contentType-"+contentType);
            }
        }
    }
}
