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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class AlertTab1Controller extends TabController {
    View view;
    ListView psytab3_lv;

    MyAdapter adapter = new MyAdapter();
    List<AleartBean> aleartBeanList = new ArrayList<>();
    public AlertTab1Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.alerttab1_control, null);
        return view;
    }

    @Override
    public void initData() {
        psytab3_lv=view.findViewById(R.id.psytab3_lv);
        psytab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog();
            }
        });

        getList("");

    }

    Dialog dialog;
    private void showDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_alert_health, null);
        ImageView cancle = (ImageView) view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView godetail = (TextView) view.findViewById(R.id.dialog_alert_health_godetail);
        godetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
            }
        });
        TextView gosameperson = (TextView) view.findViewById(R.id.dialog_alert_health_gosameperson);
        gosameperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }


    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", "0");
        params.addQueryStringParameter("readed", "1");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<AleartBean>>>(MouthpieceUrl.base_health_alert_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<AleartBean>> response, String result) {
                if (response.code==200){
                    aleartBeanList=response.data;
                    psytab3_lv.setAdapter(adapter);
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
