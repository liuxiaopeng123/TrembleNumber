package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.SamePersonBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import java.util.ArrayList;
import java.util.List;

public class HealthSamePersonActivity extends Activity {
    @ViewInject(R.id.activity_health_alert_same_person_back)
    RelativeLayout activity_health_alert_same_person_back;
    @ViewInject(R.id.activity_health_alert_same_person_title)
    TextView activity_health_alert_same_person_title;
    @ViewInject(R.id.activity_health_alert_same_person_lv)
    ListView activity_health_alert_same_person_lv;
    @ViewInject(R.id.same_person_precent)
    TextView same_person_precent;

    List<AleartBean> aleartBeanList = new ArrayList<>();
    MyAdapter adapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_alert_same_person);
        ViewUtils.inject(this); //注入view和事件
        getList(getIntent().getStringExtra("code"));
        init();
    }

    private SamePersonBean samePersonBean = new SamePersonBean();
    private void getList(String code) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("code", code);
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "200");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_symptom + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<SamePersonBean>>(MouthpieceUrl.base_health_alert_symptom , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<SamePersonBean> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    samePersonBean=response.data;
                    if (samePersonBean!=null){
                        same_person_precent.setText(samePersonBean.getPercent()+"");
                        activity_health_alert_same_person_lv.setAdapter(adapter);
                    }
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

    private void init() {
        activity_health_alert_same_person_lv.setAdapter(adapter);
        activity_health_alert_same_person_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    @OnClick({R.id.activity_health_alert_same_person_back})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_health_alert_same_person_back:
                finish();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (samePersonBean.getList()==null){
                return 0;
            }else {
                return samePersonBean.getList().size();
            }
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_same_person_activity, null);
            }
            CircularImage img = convertView.findViewById(R.id.item_same_person_img);
            ImageView imgtrend = convertView.findViewById(R.id.item_same_person_trend);
            TextView username = convertView.findViewById(R.id.item_same_person_name);
            TextView score = convertView.findViewById(R.id.item_same_person_score);
            TextView sign = convertView.findViewById(R.id.item_same_person_sign);
            if (samePersonBean.getList().get(position).getTrend()==1){
                imgtrend.setBackgroundResource(R.drawable.jiantou_up);
                score.setTextColor(Color.parseColor("#FF7F50"));
            }else {
                imgtrend.setBackgroundResource(R.drawable.jiantou_down);
                score.setTextColor(Color.parseColor("#00CED1"));
            }
            if (samePersonBean.getList().get(position).getSex()==0){
                sign.setText("年龄："+samePersonBean.getList().get(position).getAge()+"岁"+ "  男");
            }else {
                sign.setText("年龄："+samePersonBean.getList().get(position).getAge()+"岁"+ "  女");
            }
            score.setText((int)samePersonBean.getList().get(position).getScore()+"分");
            username.setText(samePersonBean.getList().get(position).getNickName());
            Utils.BJSloadImg(getApplicationContext(),MouthpieceUrl.base_loading_img+samePersonBean.getList().get(position).getHeadImagePath(),img);

            return convertView;
        }
    }
}
