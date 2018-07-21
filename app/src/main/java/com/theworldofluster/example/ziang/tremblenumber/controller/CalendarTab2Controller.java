package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CalendarView;
import com.theworldofluster.example.ziang.tremblenumber.view.ChartView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class CalendarTab2Controller extends TabController {
    View view;

    private ImageButton calendarLeft;
    private TextView calendarText;
    private ImageButton calendarRight;
    private GridView pager_month_gv;
    private GridView pager_day_gv;
    private LinearLayout date_title;
    private TextView smail_year;

    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();

    private String year;
    private String mouth;
    private String[] mouthstr = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};

    private String day;



    MyAdapter adapter = new MyAdapter();
    public CalendarTab2Controller(Context context) {
        super(context);
    }

    @Override
    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.pager_month, null);
        return view;
    }

    @Override
    public void initData() {

        year = Utils.getyear();
        mouth = Utils.getmouth();
        day = Utils.getday();

        calendarLeft = (ImageButton)view.findViewById(R.id.calendarLeft);
        calendarText = (TextView)view.findViewById(R.id.calendarText);
        calendarRight = (ImageButton)view.findViewById(R.id.calendarRight);
        pager_month_gv = (GridView) view.findViewById(R.id.pager_month_gv);
        pager_day_gv = (GridView) view.findViewById(R.id.pager_day_gv);
        date_title = (LinearLayout) view.findViewById(R.id.date_title);
        smail_year = (TextView) view.findViewById(R.id.smail_year);

        calendarText.setText(year);

        pager_month_gv.setAdapter(new DateAdapter());

        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
            }
        });

        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 60);
        }

        ChartView chartView = (ChartView) view.findViewById(R.id.chartview);
        chartView.setValue(value, xValue, yValue);


        pager_month_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                pager_month_gv.setVisibility(View.GONE);
                date_title.setVisibility(View.VISIBLE);
                smail_year.setText(year);
                calendarText.setText(mouthstr[i]);

                if(mouth.equals(i+"")||mouth.equals("0"+i)){
                    getWeek(year+"-"+(i+1)+"-"+"01",i,0);
                }else{
                    getWeek(year+"-"+(i+1)+"-"+"01",i,1);
                }
            }
        });

        smail_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager_month_gv.setVisibility(View.VISIBLE);
                date_title.setVisibility(View.GONE);
                smail_year.setText("");
                calendarText.setText(year);
            }
        });

    }

    private String getWeek(String pTime,int i,int type) {

        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
            pager_day_gv.setAdapter(new DayAdapter(6+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
            pager_day_gv.setAdapter(new DayAdapter(Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
            pager_day_gv.setAdapter(new DayAdapter(1+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
            pager_day_gv.setAdapter(new DayAdapter(2+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
            pager_day_gv.setAdapter(new DayAdapter(3+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
            pager_day_gv.setAdapter(new DayAdapter(4+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
            pager_day_gv.setAdapter(new DayAdapter(5+Utils.getDaysOfMonth(year+"-"+(i+1)+"-"+"01"),Week,type));

        }

        Log.e("Ziang",Week);

        return Week;
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
        params.addQueryStringParameter("type", "1");
        params.addQueryStringParameter("readed", "0");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_alert_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<PsyTestBean>>(MouthpieceUrl.base_health_alert_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<PsyTestBean> response, String result) {
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
            return 6;
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
            return convertView;
        }
    }

    class DateAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = View.inflate(mContext, R.layout.date_item, null);

            TextView date_item_mouth = view.findViewById(R.id.date_item_mouth);

            if(mouth.equals(i+"")||mouth.equals("0"+i)){
                date_item_mouth.setBackgroundResource(R.drawable.button_stoke_white);
            }

            date_item_mouth.setText(i+1+"");

            return view;

        }
    }

    class DayAdapter extends BaseAdapter{

        int daynum;
        int ty;
        String daysofmonth;


        public DayAdapter(int i, String week, int type) {

            daynum = i;
            daysofmonth = week;
            ty = type;
        }

        @Override
        public int getCount() {
            return daynum;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = View.inflate(mContext, R.layout.day_item, null);
            final TextView date_item_mouth = view.findViewById(R.id.date_item_mouth);

            if(daysofmonth.equals("二")){
                if(i==0){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i+"");
                }
            }

            if(daysofmonth.equals("三")){
                if(i==0||i==1){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i-1+"");
                }
            }

            if(daysofmonth.equals("四")){
                if(i==0||i==1||i==2){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i-2+"");
                }
            }

            if(daysofmonth.equals("五")){
                if(i==0||i==1||i==2||i==3){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i-3+"");
                }
            }

            if(daysofmonth.equals("六")){
                if(i==0||i==1||i==2||i==3||i==4){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i-4+"");
                }
            }

            if(daysofmonth.equals("天")){
                if(i==0||i==1||i==2||i==3||i==4||i==5){
                    date_item_mouth.setText("");
                }else {
                    date_item_mouth.setText(i-5+"");
                }
            }

            if(daysofmonth.equals("一")){
                date_item_mouth.setText(i+1+"");
            }

            if(ty==0){
                if(day.equals(i+"")||day.equals("0"+i)){
                    date_item_mouth.setBackgroundResource(R.drawable.button_stoke_white);
                }
            }

            date_item_mouth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!date_item_mouth.getText().toString().equals("")){

                        date_item_mouth.setBackgroundResource(R.drawable.corners_white);
                        date_item_mouth.setTextColor(Color.parseColor("#28d3bd"));

                    }

                }
            });

            return view;

        }
    }

}
