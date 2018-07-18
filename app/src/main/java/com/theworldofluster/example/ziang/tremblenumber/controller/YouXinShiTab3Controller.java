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
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.XinShi;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class YouXinShiTab3Controller extends TabController {
    View view;
    ListView youxinshi_lv;

    List<XinShi> xinShiList=new ArrayList<>();

    MyAdapter adapter = new MyAdapter();
    public YouXinShiTab3Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.youxinshitab3_control, null);
        return view;
    }

    @Override
    public void initData() {
        youxinshi_lv=view.findViewById(R.id.youxinshi_lv);
        youxinshi_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        params.addQueryStringParameter("isDecline", "false");
        params.addQueryStringParameter("isMine", "true");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<XinShi>>>(MouthpieceUrl.base_mind_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<XinShi>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                xinShiList=response.data;
                if (response.code==200){
                    youxinshi_lv.setAdapter(adapter);
                }
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
            return xinShiList==null?0:xinShiList.size();
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
                convertView = View.inflate(mContext, R.layout.item_youxinshi_tab3, null);
            }
//            TextView content=convertView.findViewById(R.id.item_youxinshi_content);
//            TextView username=convertView.findViewById(R.id.item_youxinshi_user_name);
//            TextView hugnum=convertView.findViewById(R.id.item_youxinshi_hugnumber);
//            hugnum.setText(xinShiList.get(position).getHugNumber()+" 抱抱");
//            username.setText(xinShiList.get(position).getUserId()+"");
//            content.setText(xinShiList.get(position).getMindContext());
            return convertView;
        }
    }
}
