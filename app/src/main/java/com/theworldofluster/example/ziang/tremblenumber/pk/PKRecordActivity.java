package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PKBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.StatisticBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class PKRecordActivity extends Activity {

    @ViewInject(R.id.activity_pk_record_back)
    RelativeLayout activity_pk_record_back;
    @ViewInject(R.id.activity_pk_record_lv)
    NoScrollListView activity_pk_record_lv;
    @ViewInject(R.id.activity_pk_record_type_select)
    TextView activity_pk_record_type_select;

    @ViewInject(R.id.pk_record_status_cb_1)
    CheckBox pk_record_status_cb_1;
    @ViewInject(R.id.pk_record_status_cb_2)
    CheckBox pk_record_status_cb_2;
    @ViewInject(R.id.pk_record_status_cb_3)
    CheckBox pk_record_status_cb_3;
    @ViewInject(R.id.pk_record_result_cb_1)
    CheckBox pk_record_result_cb_1;
    @ViewInject(R.id.pk_record_result_cb_2)
    CheckBox pk_record_result_cb_2;
    @ViewInject(R.id.pk_record_result_cb_3)
    CheckBox pk_record_result_cb_3;

    @ViewInject(R.id.pk_record_acount)
    TextView pk_record_acount;
    @ViewInject(R.id.pk_record_acount_sheng)
    TextView pk_record_acount_sheng;
    @ViewInject(R.id.pk_record_acount_ping)
    TextView pk_record_acount_ping;
    @ViewInject(R.id.pk_record_acount_bai)
    TextView pk_record_acount_bai;
    @ViewInject(R.id.pk_record_acount_jieshou)
    TextView pk_record_acount_jieshou;
    @ViewInject(R.id.pk_record_acount_jujue)
    TextView pk_record_acount_jujue;
    @ViewInject(R.id.pk_record_acount_shibai)
    TextView pk_record_acount_shibai;

    MyAdapter adapter =new MyAdapter();

    List<Boolean> flag_status=new ArrayList<>();
    List<Boolean> flag_result=new ArrayList<>();

    List<PKBean> pkBeanList = new ArrayList<>();

    StatisticBean statisticBean =new StatisticBean();

    private String flag_status_check="1";
    private String flag_result_check="1";
    private String flag_active="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_record);
        ViewUtils.inject(this); //注入view和事件
        initView();
        initData();
    }


    private void initView() {
        flag_status.add(true);
        flag_status.add(false);
        flag_status.add(false);

        flag_result.add(true);
        flag_result.add(false);
        flag_result.add(false);
        activity_pk_record_back.setFocusable(true);
        activity_pk_record_back.setFocusableInTouchMode(true);
        activity_pk_record_back.requestFocus();
        initPopupWindow();
        initFlagView();
    }

    private void initData() {
        getStatistics();
    }

    //获取列表
    public void getList(String active,String status,String result) {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("active", active);
        params.addQueryStringParameter("status", status);
        params.addQueryStringParameter("result", result);
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "12");
        Log.i("xiaopeng", "url----111:" + MouthpieceUrl.base_pk_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<PKBean>>>(MouthpieceUrl.base_pk_list , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<PKBean>> response, String result) {
                if (response.code==200){
                    pkBeanList=response.data;
                    activity_pk_record_lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                Log.i("xiaopeng-----","result-----111"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result-----111"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    //获取列表
    public void getStatistics() {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        Log.i("xiaopeng", "url----222:" + MouthpieceUrl.base_pk_statistic + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<StatisticBean>>(MouthpieceUrl.base_pk_statistic , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<StatisticBean> response, String result) {
                if (response.code==200){
                    statisticBean=response.data;
                    initStatisticView();
                }
                Log.i("xiaopeng-----","result-----222"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result222-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    //更新统计数据
    private void initStatisticView() {
        switch (flag_active){
            case "0":
                pk_record_acount.setText(""+statisticBean.getTotalPKNumber());

                pk_record_acount_jieshou.setText(""+statisticBean.getTotalAcceptPKNumber()+"次");
                pk_record_acount_jujue.setText(""+statisticBean.getTotalRejuctPKNumber()+"次");
                pk_record_acount_shibai.setText(""+statisticBean.getTotalExpiredPKNumber()+"次");
                pk_record_acount_sheng.setText(""+statisticBean.getTotalWinNumber()+"次");
                pk_record_acount_ping.setText(""+statisticBean.getTotalTieNumber()+"次");
                pk_record_acount_bai.setText(""+statisticBean.getTotalLoseNumber()+"次");
                break;
            case "1":
                pk_record_acount.setText(""+statisticBean.getPositivePKNumber());

                pk_record_acount_jieshou.setText(""+statisticBean.getPositiveAcceptPKNumber()+"次");
                pk_record_acount_jujue.setText(""+statisticBean.getPositiveRejuctPKNumber()+"次");
                pk_record_acount_shibai.setText(""+statisticBean.getPositiveExpiredPKNumber()+"次");
                pk_record_acount_sheng.setText(""+statisticBean.getPositiveWinNumber()+"次");
                pk_record_acount_ping.setText(""+statisticBean.getPositiveTieNumber()+"次");
                pk_record_acount_bai.setText(""+statisticBean.getPositiveLoseNumber()+"次");
                break;
            case "-1":
                pk_record_acount.setText(""+statisticBean.getNegativePKNumber());

                pk_record_acount_jieshou.setText(""+statisticBean.getNegativeAcceptPKNumber()+"次");
                pk_record_acount_jujue.setText(""+statisticBean.getNegativeRejuctPKNumber()+"次");
                pk_record_acount_shibai.setText(""+statisticBean.getNegativeExpiredPKNumber()+"次");
                pk_record_acount_sheng.setText(""+statisticBean.getNegativeWinNumber()+"次");
                pk_record_acount_ping.setText(""+statisticBean.getNegativeTieNumber()+"次");
                pk_record_acount_bai.setText(""+statisticBean.getNegativeLoseNumber()+"次");
                break;
        }


    }


    @OnClick({R.id.activity_pk_record_back,R.id.activity_pk_record_type_select,R.id.pk_record_status_cb_1,R.id.pk_record_status_cb_2,R.id.pk_record_status_cb_3,R.id.pk_record_result_cb_1,R.id.pk_record_result_cb_2,R.id.pk_record_result_cb_3})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_pk_record_back:
                finish();
                break;
            case R.id.activity_pk_record_type_select:
                showPopWindow();
                break;
            case R.id.pk_record_status_cb_1:
                flag_status.set(0,true);
                flag_status.set(1,false);
                flag_status.set(2,false);
                flag_status_check="1";
                initFlagView();
                break;
            case R.id.pk_record_status_cb_2:
                flag_status.set(0,false);
                flag_status.set(1,true);
                flag_status.set(2,false);
                flag_status_check="-1";
                initFlagView();
                break;
            case R.id.pk_record_status_cb_3:
                flag_status.set(0,false);
                flag_status.set(1,false);
                flag_status.set(2,true);
                flag_status_check="2";
                initFlagView();
                break;
            case R.id.pk_record_result_cb_1:
                flag_result.set(0,true);
                flag_result.set(1,false);
                flag_result.set(2,false);
                flag_result_check="1";
                initFlagView();
                break;
            case R.id.pk_record_result_cb_2:
                flag_result.set(0,false);
                flag_result.set(1,true);
                flag_result.set(2,false);
                flag_result_check="0";
                initFlagView();
                break;
            case R.id.pk_record_result_cb_3:
                flag_result.set(0,false);
                flag_result.set(1,false);
                flag_result.set(2,true);
                flag_result_check="-1";
                initFlagView();
                break;

            default:
                break;
        }
    }

    //更新点解界面
    private void initFlagView() {
        pk_record_status_cb_1.setChecked(flag_status.get(0));
        pk_record_status_cb_2.setChecked(flag_status.get(1));
        pk_record_status_cb_3.setChecked(flag_status.get(2));
        pk_record_result_cb_1.setChecked(flag_result.get(0));
        pk_record_result_cb_2.setChecked(flag_result.get(1));
        pk_record_result_cb_3.setChecked(flag_result.get(2));

        initStatisticView();
        getList(flag_active,flag_status_check,flag_result_check);
    }

    PopupWindow popupWindow;
    private void initPopupWindow() {
        View contentView;
        //要在布局中显示的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.bottom_popup_window, null, false);
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
                activity_pk_record_type_select.setText("主动PK ");
                flag_active="1";
                getList(flag_active,flag_status_check,flag_result_check);
                initStatisticView();
            }
        });
        TextView item_2 = contentView.findViewById(R.id.item_2);
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                flag_active="-1";
                getList(flag_active,flag_status_check,flag_result_check);
                activity_pk_record_type_select.setText("被动PK ");
                initStatisticView();
            }
        });
        TextView item_3 = contentView.findViewById(R.id.item_3);
        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                flag_active="0";
                getList(flag_active,flag_status_check,flag_result_check);
                activity_pk_record_type_select.setText("全部PK ");
                initStatisticView();
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
        View rootview = LayoutInflater.from(PKRecordActivity.this).inflate(R.layout.activity_pk_record, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pkBeanList==null?0:pkBeanList.size();
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_pk_record, null);
            }
            TextView username = convertView.findViewById(R.id.item_pk_record_username);
            TextView date = convertView.findViewById(R.id.item_pk_record_date);
            TextView acount = convertView.findViewById(R.id.item_pk_record_acount);
            TextView chengzhangzhi = convertView.findViewById(R.id.item_pk_record_chengzhangzhi);
            TextView result = convertView.findViewById(R.id.item_pk_record_result);
            if ("1".equals(flag_active)){
                username.setText("向"+pkBeanList.get(position).getTargetUserNickName()+"挑战");
                date.setText(""+pkBeanList.get(position).getInvitedDate().substring(0,10));
                chengzhangzhi.setText("+30(假)");
                acount.setText("100次(假)");
                switch (pkBeanList.get(position).getConfirmed()){
                    case 1:
                        result.setText("胜");
                        break;
                    case 0:
                        result.setText("平");
                        break;
                    case -1:
                        result.setText("败");
                        break;
                    case 2:
                        result.setText("失败");
                        break;
                }
            }

            return convertView;
        }
    }

}
