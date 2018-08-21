package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.ExtrasBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class AlertTab4Controller extends TabController {
    View view;
    ListView psytab3_lv;

    private int position_click=-1;

    MyAdapter adapter = new MyAdapter();
    List<ExtrasBean> aleartBeanList = new ArrayList<>();
//    ExtrasBean extrasBean = new ExtrasBean();
    public AlertTab4Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.alerttab4_control, null);
        return view;
    }

    @Override
    public void initData() {
        psytab3_lv=view.findViewById(R.id.psytab3_lv);
        psytab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_click=position;
                showPKDialog();
            }
        });

        getList("");

    }

//    Dialog dialog;
//    private void showDialog() {
//        dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//        View view = View.inflate(mContext, R.layout.dialog_alert_health, null);
//        ImageView cancle = (ImageView) view.findViewById(R.id.dialog_cancle);
//        cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        TextView godetail = (TextView) view.findViewById(R.id.dialog_alert_health_godetail);
//        godetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
//            }
//        });
//        TextView gosameperson = (TextView) view.findViewById(R.id.dialog_alert_health_gosameperson);
//        gosameperson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
//            }
//        });
//
//        dialog.setContentView(view);
//        dialog.show();
//    }


    Dialog dialog;
    private void showPKDialog(){
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_pk_invited, null);
        ImageView close =view.findViewById(R.id.dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button jieshou=view.findViewById(R.id.dialog_pk_invited_jieshou);
        jieshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkConfirm("1");
            }
        });
        Button jujue=view.findViewById(R.id.dialog_pk_invited_jujue);
        jujue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkConfirm("2");
            }
        });
        TextView status = view.findViewById(R.id.dialog_pk_invited_status);
        switch (aleartBeanList.get(position_click).getPkStatus()){
            case 0:
                status.setVisibility(View.GONE);
                jujue.setVisibility(View.VISIBLE);
                jieshou.setVisibility(View.VISIBLE);
                break;
            case 1:
                status.setVisibility(View.VISIBLE);
                status.setText("PK进行中");
                jujue.setVisibility(View.GONE);
                jieshou.setVisibility(View.GONE);
                break;
            case 2:
                status.setVisibility(View.VISIBLE);
                status.setText("拒绝PK");
                jujue.setVisibility(View.GONE);
                jieshou.setVisibility(View.GONE);
                break;
            case 3:
                status.setVisibility(View.VISIBLE);
                status.setText("PK邀请已过期");
                jujue.setVisibility(View.GONE);
                jieshou.setVisibility(View.GONE);
                break;
            case 4:
                status.setVisibility(View.VISIBLE);
                status.setText("PK已完成");
                jujue.setVisibility(View.GONE);
                jieshou.setVisibility(View.GONE);
                break;
        }
        TextView username =view.findViewById(R.id.dialog_pk_invited_username);
        username.setText(aleartBeanList.get(position_click).getPkSourceUserNickName()+"向你发起挑战邀请");
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
        String guoqiriqi = sf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        TextView zhuyi =view.findViewById(R.id.dialog_pk_invited_zhuyi);
        zhuyi.setText("注：该挑战将于"+guoqiriqi+aleartBeanList.get(position_click).getRemindDate().substring(11,aleartBeanList.get(position_click).getRemindDate().length())+"失效，如接受挑战，则立即进入PK状态，"+ DateUtil.getXiaZhouMonday()+"8：00可出PK结果。");

        dialog.setContentView(view);
        dialog.show();
    }

    //确认PK
    public void pkConfirm(String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("pkId", aleartBeanList.get(position_click).getPkId()+"");
        params.addQueryStringParameter("type", type);
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_pk_confirm + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_confirm , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    dialog.dismiss();
                }
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", "3");
        params.addQueryStringParameter("readed", "0");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ExtrasBean>>>(MouthpieceUrl.base_health_alert_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ExtrasBean>> response, String result) {
                if (response.code==200){
                    aleartBeanList=response.data;
                    psytab3_lv.setAdapter(adapter);
                }
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return aleartBeanList==null?0:aleartBeanList.size();
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
                convertView = View.inflate(mContext, R.layout.item_alert_tab1, null);
            }
            TextView title =convertView.findViewById(R.id.item_alert_title);
            TextView date =convertView.findViewById(R.id.item_alert_date);
            title.setText(aleartBeanList.get(position).getRemindTitle());
            date.setText(aleartBeanList.get(position).getRemindDate().substring(5,7)+"月"+aleartBeanList.get(position).getRemindDate().substring(8,10)+"日");
            return convertView;
        }
    }
}
