package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.ExtrasBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.controller.AlertTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.AlertTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.AlertTab3Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.AlertTab4Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.PsyTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.PsyTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.PsyTab3Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.PsyTab4Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.TabController;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class HealthAlertActivity extends Activity {


    @ViewInject(R.id.activity_health_alert_back)
    RelativeLayout activity_health_alert_back;
    @ViewInject(R.id.user_view_pager)
    NoAnimViewpager user_view_pager;
    @ViewInject(R.id.alert_tab_1)
    TextView alert_tab_1;
    @ViewInject(R.id.alert_tab_2)
    TextView alert_tab_2;
    @ViewInject(R.id.alert_tab_3)
    TextView alert_tab_3;
    @ViewInject(R.id.alert_tab_4)
    TextView alert_tab_4;

    @ViewInject(R.id.alert_line_1)
    TextView alert_line_1;
    @ViewInject(R.id.alert_line_2)
    TextView alert_line_2;
    @ViewInject(R.id.alert_line_3)
    TextView alert_line_3;
    @ViewInject(R.id.alert_line_4)
    TextView alert_line_4;

    List<TabController> list;
    public int ChoiceWhere = 0;

    ExtrasBean extrasBean = new ExtrasBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_alert);
        ViewUtils.inject(this); //注入view和事件

        init();
    }

    private void init() {

        list = new ArrayList<>();
        list.add(new AlertTab1Controller(this));
        list.add(new AlertTab2Controller(this));
        list.add(new AlertTab3Controller(this));
        list.add(new AlertTab4Controller(this));

        user_view_pager.setAdapter(new MessageAdapter());
        user_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        alert_tab_1.setTextColor(Color.BLACK);
                        alert_tab_2.setTextColor(Color.GRAY);
                        alert_tab_3.setTextColor(Color.GRAY);
                        alert_tab_4.setTextColor(Color.GRAY);
                        alert_line_1.setBackgroundResource(R.color.colorPrimary);
                        alert_line_2.setBackgroundResource(R.color.white);
                        alert_line_3.setBackgroundResource(R.color.white);
                        alert_line_4.setBackgroundResource(R.color.white);
                        ChoiceWhere = 0;
                        break;
                    case 1:
                        alert_tab_1.setTextColor(Color.GRAY);
                        alert_tab_2.setTextColor(Color.BLACK);
                        alert_tab_3.setTextColor(Color.GRAY);
                        alert_tab_4.setTextColor(Color.GRAY);
                        alert_line_1.setBackgroundResource(R.color.white);
                        alert_line_2.setBackgroundResource(R.color.colorPrimary);
                        alert_line_3.setBackgroundResource(R.color.white);
                        alert_line_4.setBackgroundResource(R.color.white);
                        ChoiceWhere = 1;
                        break;
                    case 2:
                        alert_tab_1.setTextColor(Color.GRAY);
                        alert_tab_2.setTextColor(Color.GRAY);
                        alert_tab_3.setTextColor(Color.BLACK);
                        alert_tab_4.setTextColor(Color.GRAY);
                        alert_line_1.setBackgroundResource(R.color.white);
                        alert_line_2.setBackgroundResource(R.color.white);
                        alert_line_3.setBackgroundResource(R.color.colorPrimary);
                        alert_line_4.setBackgroundResource(R.color.white);
                        ChoiceWhere = 2;
                        break;
                    case 3:
                        alert_tab_1.setTextColor(Color.GRAY);
                        alert_tab_2.setTextColor(Color.GRAY);
                        alert_tab_3.setTextColor(Color.GRAY);
                        alert_tab_4.setTextColor(Color.BLACK);
                        alert_line_1.setBackgroundResource(R.color.white);
                        alert_line_2.setBackgroundResource(R.color.white);
                        alert_line_3.setBackgroundResource(R.color.white);
                        alert_line_4.setBackgroundResource(R.color.colorPrimary);
                        ChoiceWhere = 3;
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //我已阅读
    public void readed(String read) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("remindId", extrasBean.getRemindId()+"");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_read + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_health_alert_read , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
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

    //确认PK
    public void pkConfirm(String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("pkId", extrasBean.getPkId()+"");
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_pk_confirm + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_confirm , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    dialog.dismiss();
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
    private void showPKDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_pk_invited, null);
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
        TextView username =view.findViewById(R.id.dialog_pk_invited_username);
        username.setText(extrasBean.getPkSourceUserNickName()+"向你发起挑战邀请");
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
        String guoqiriqi = sf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        TextView zhuyi =view.findViewById(R.id.dialog_pk_invited_zhuyi);
        if (extrasBean.getRemindDate().length()>10){
            zhuyi.setText("注：该挑战将于"+guoqiriqi+extrasBean.getRemindDate().substring(11,extrasBean.getRemindDate().length())+"失效，如接收挑战，则立即进入PK状态，"+ DateUtil.getXiaZhouMonday()+"8：00可出PK结果。");
        }else {
            zhuyi.setText("注：如接收挑战，则立即进入PK状态，"+ DateUtil.getXiaZhouMonday()+"8：00可出PK结果。");
        }

        dialog.setContentView(view);
        dialog.show();
    }

    @OnClick({R.id.activity_health_alert_back,R.id.alert_tab_1,R.id.alert_tab_2,R.id.alert_tab_3,R.id.alert_tab_4})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_health_alert_back:
                finish();
                break;
            case R.id.alert_tab_1:
                alert_tab_1.setTextColor(Color.BLACK);
                alert_tab_2.setTextColor(Color.GRAY);
                alert_tab_3.setTextColor(Color.GRAY);
                alert_tab_4.setTextColor(Color.GRAY);
                alert_line_1.setBackgroundResource(R.color.colorPrimary);
                alert_line_2.setBackgroundResource(R.color.white);
                alert_line_3.setBackgroundResource(R.color.white);
                alert_line_4.setBackgroundResource(R.color.white);
                ChoiceWhere = 0;
                user_view_pager.setCurrentItem(0);
                break;
            case R.id.alert_tab_2:
                alert_tab_1.setTextColor(Color.GRAY);
                alert_tab_2.setTextColor(Color.BLACK);
                alert_tab_3.setTextColor(Color.GRAY);
                alert_tab_4.setTextColor(Color.GRAY);
                alert_line_1.setBackgroundResource(R.color.white);
                alert_line_2.setBackgroundResource(R.color.colorPrimary);
                alert_line_3.setBackgroundResource(R.color.white);
                alert_line_4.setBackgroundResource(R.color.white);
                ChoiceWhere = 1;
                user_view_pager.setCurrentItem(1);
                break;
            case R.id.alert_tab_3:
                alert_tab_1.setTextColor(Color.GRAY);
                alert_tab_2.setTextColor(Color.GRAY);
                alert_tab_3.setTextColor(Color.BLACK);
                alert_tab_4.setTextColor(Color.GRAY);
                alert_line_1.setBackgroundResource(R.color.white);
                alert_line_2.setBackgroundResource(R.color.white);
                alert_line_3.setBackgroundResource(R.color.colorPrimary);
                alert_line_4.setBackgroundResource(R.color.white);
                ChoiceWhere = 2;
                user_view_pager.setCurrentItem(2);
                break;
            case R.id.alert_tab_4:
                alert_tab_1.setTextColor(Color.GRAY);
                alert_tab_2.setTextColor(Color.GRAY);
                alert_tab_3.setTextColor(Color.GRAY);
                alert_tab_4.setTextColor(Color.BLACK);
                alert_line_1.setBackgroundResource(R.color.white);
                alert_line_2.setBackgroundResource(R.color.white);
                alert_line_3.setBackgroundResource(R.color.white);
                alert_line_4.setBackgroundResource(R.color.colorPrimary);
                ChoiceWhere = 3;
                user_view_pager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }


    class MessageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabController controller = list.get(position);
            // 获得数据
            View view = controller.getRootView();
            container.addView(view);
            // 加载数据
            controller.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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
                contentType=bundle.getString(JPushInterface.EXTRA_EXTRA);
                Gson gson =new Gson();
                if (contentType!=null){
                    extrasBean=gson.fromJson(contentType,ExtrasBean.class);
                    if ("2".equals(extrasBean.getContentType())){
//                        readed("1");
                        showPKDialog();
                    }
                }
                Log.i("xiaopeng-----222","title-"+title+"content-"+content+"contentType-"+contentType);
            }
        }
    }
}
