package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
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
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultListActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class AlertTab1Controller extends TabController {
    View view;
    ListView psytab3_lv;
    private int pageNum = 1;
    private boolean haveMore = false;
    private int position_click=-1;
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
        psytab3_lv.setAdapter(adapter);
        psytab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (aleartBeanList.get(position).getIsReaded()==0){readed(""+aleartBeanList.get(position).getRemindId(), position);}
                if (aleartBeanList.get(position).getRemindType()==4){
                    mContext.startActivity(new Intent(mContext, PersonalActivity.class));
                }else if (aleartBeanList.get(position).getRemindType()==3){
                    position_click=position;
                    showPKDialog();
                }else {
                    showDialog(aleartBeanList.get(position).getHealthInfoCateCode(),aleartBeanList.get(position).getRemindTitle()
                            , aleartBeanList.get(position).getRemindContext(),aleartBeanList.get(position).getRemindDate());
                }

            }
        });

        psytab3_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    //用户抬起手指
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    //滑动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (psytab3_lv.getLastVisiblePosition() == aleartBeanList.size() - 1) {
                            if (haveMore) {
                                pageNum++;
                                getList("");
                            }
                        }
                        break;
                    //滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            }
        });

        getList("");

    }

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
                pkConfirm("-1");
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
                    getList("");
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

    //我已阅读
    public void readed(String remindId, final int position) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("remindIds", remindId);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_read + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_health_alert_read , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    aleartBeanList.get(position).setIsReaded(1);
                    adapter.notifyDataSetChanged();
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

    Dialog dialog;
    private void showDialog(final String healthInfoCateCode, String remindTitle, String remindContext, String remindDate) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_alert_health, null);
        TextView title = view.findViewById(R.id.remind_title);
        TextView date = view.findViewById(R.id.remind_date);
        TextView context = view.findViewById(R.id.remind_context);
        title.setText(remindTitle);
        date.setText(remindDate.substring(5,7)+"月"+remindDate.substring(8,10)+"日"+remindDate.substring(11,16));
        context.setText(remindContext);
        ImageView cancle = (ImageView) view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView godetail = (TextView) view.findViewById(R.id.dialog_alert_health_godetail);
        if (healthInfoCateCode.split(",").length>1){
            godetail.setText(""+healthInfoCateCode.split(",")[1]);
        }else {
            godetail.setText("");
        }
        godetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, HealthConsultListActivity.class);
                if (healthInfoCateCode.split(",").length>1){
                    intent.putExtra("title",""+healthInfoCateCode.split(",")[1]);
                }else {
                    intent.putExtra("title","");
                }
                intent.putExtra("cateCode",""+healthInfoCateCode.split(",")[0]);
                mContext.startActivity(intent);
            }
        });
        TextView gosameperson = (TextView) view.findViewById(R.id.dialog_alert_health_gosameperson);
        gosameperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext,HealthSamePersonActivity.class);
                intent.putExtra("code",""+healthInfoCateCode.split(",")[0]);
                mContext.startActivity(intent);
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
        params.addQueryStringParameter("readed", "2");
        params.addQueryStringParameter("pageIndex", ""+pageNum);
        params.addQueryStringParameter("pageSize", "10");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<AleartBean>>>(MouthpieceUrl.base_health_alert_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<AleartBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    if (response.data!=null){
                        if (response.data.size() < 10) {
                            haveMore = false;
                        } else {
                            haveMore = true;
                        }
                        if (pageNum==1){
                            aleartBeanList=new ArrayList<>();
                            aleartBeanList.addAll(response.data);
                        }else {
                            aleartBeanList.addAll(response.data);
                        }

                    }else {
                        haveMore = false;
                    }
                    adapter.notifyDataSetChanged();
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
            if (aleartBeanList.get(position).getIsReaded()==0){
                Drawable img = convertView.getResources().getDrawable(R.mipmap.xiaohongdian);
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                title.setCompoundDrawables(img, null, null, null); //设置左图标
            }else {
//                Drawable img = convertView.getResources().getDrawable(R.mipmap.xiaohongdian);
//                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
//                title.setCompoundDrawables(img, null, null, null); //设置左图标
                title.setCompoundDrawables(null, null, null, null); //设置左图标
            }
            return convertView;
        }
    }
}
