package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.MoodStatisticBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.ReportBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.XinShi;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.ChartView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    private TextView pager_month_chakanpingfen;
    private static final String[] haonames = {"喜极而泣","珍爱","兴高采烈","爱","开心","喜欢","心仪","牵挂","满足","好感","感动","思念","祝福","感激"};
    private static final int[] haoicons = {R.mipmap.biaoqing_11_xijierqi,R.mipmap.biaoqing_12_zhenai,R.mipmap.biaoqing_21_xinggaocailie,
            R.mipmap.biaoqing_22_ai,R.mipmap.biaoqing_31_kaixin, R.mipmap.biaoqing_32_xihuan,R.mipmap.biaoqing_33_xinyi,R.mipmap.biaoqing_34_qiangua,
            R.mipmap.biaoqing_41_manzu,R.mipmap.biaoqing_42_haogan, R.mipmap.biaoqing_43_gandong,R.mipmap.biaoqing_44_sinian,R.mipmap.biaoqing_45_zhufu,R.mipmap.biaoqing_46_ganji};
    private static final String[] yibannames = {"朝思暮想","想要","惊讶","垂涎","震惊","紧张","不满","失望"};
    private static final int[] yibanicons = {R.mipmap.biaoqing_51_zhaosimuxiang,R.mipmap.biaoqing_61_xiangyao,R.mipmap.biaoqing_62_jingya,
            R.mipmap.biaoqing_71_chuiyan,R.mipmap.biaoqing_72_zhenjing, R.mipmap.biaoqing_73_jinzhang,R.mipmap.biaoqing_75_shiwang,R.mipmap.biaoqing_34_qiangua,};

    private static final String[] huainames = {"求知若渴","惊呆","生气","难过","胆怯","反感","忧虑","后悔","愤怒","痛苦","恐惧","厌恶","忧心忡忡","自责","暴跳如雷","痛不欲生","吓晕","深恶痛疾","忧思成疾","悔恨"};
    private static final int[] huaiicons = {R.mipmap.biaoqing_81_qiuzhiruoke,R.mipmap.biaoqing_82_jingdai,R.mipmap.biaoqing_83_shengqi,
            R.mipmap.biaoqing_84_nanguo,R.mipmap.biaoqing_85_danqie, R.mipmap.biaoqing_86_fangan,R.mipmap.biaoqing_87_youlv,R.mipmap.biaoqing_88_houhui,R.mipmap.biaoqing_91_fennu,
            R.mipmap.biaoqing_92_tongku,R.mipmap.biaoqing_93_kongju, R.mipmap.biaoqing_94_yanwu,R.mipmap.biaoqing_95_youxinchongchong,R.mipmap.biaoqing_96_zize,R.mipmap.biaoqing_101_baotiaorulei,
            R.mipmap.biaoqing_102_tongbuyusheng,R.mipmap.biaoqing_103_xiayun,R.mipmap.biaoqing_104_shenetongjue,R.mipmap.biaoqing_105_yousi_chengji,R.mipmap.biaoqing_106_huihen};

    private static final int[] haoIconCode={11,12,21,22,31,32,33,34,41,42,43,44,45,46};
    private static final int[] yibanIconCode={51,61,62,71,72,73,74,75};
    private static final int[] huaiIconCode={81,82,83,84,85,86,87,88,91,92,93,94,95,96,101,102,103,104,105,106};

    private LinearLayout pager_month_mood_bottom_ll,pager_month_mood_center_ll;
    private TextView mood_good,mood_soso,mood_bad,mood_day_acount,mood_day_acount_tian;

    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();

    private String year;
    private String mouth;
    private String[] mouthstr = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};

    private String day;

    private MoodStatisticBean moodStatistic,moodStatisticBeiFen;


    private boolean flag_toggle_show_day_acount=false;
    private int flag_which_status_checked=0;
    private int flag_i=-1;

    private boolean flag_mouth_day_is_show=true;

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

        pager_month_chakanpingfen=view.findViewById(R.id.pager_month_chakanpingfen);
        pager_month_mood_bottom_ll=view.findViewById(R.id.pager_month_mood_bottom_ll);
        pager_month_mood_center_ll=view.findViewById(R.id.pager_month_mood_center_ll);

        mood_day_acount_tian=view.findViewById(R.id.pager_month_mood_day_acount_tian);
        mood_day_acount=view.findViewById(R.id.pager_month_mood_day_acount);
        mood_good=view.findViewById(R.id.pager_month_mood_good);
        mood_soso=view.findViewById(R.id.pager_month_mood_soso);
        mood_bad=view.findViewById(R.id.pager_month_mood_bad);

        calendarLeft = (ImageButton)view.findViewById(R.id.calendarLeft);
        calendarText = (TextView)view.findViewById(R.id.calendarText);
        calendarRight = (ImageButton)view.findViewById(R.id.calendarRight);
        pager_month_gv = (GridView) view.findViewById(R.id.pager_month_gv);
        pager_day_gv = (GridView) view.findViewById(R.id.pager_day_gv);
        date_title = (LinearLayout) view.findViewById(R.id.date_title);
        smail_year = (TextView) view.findViewById(R.id.smail_year);


        if ("0".equals(mouth.substring(0,1))){
            calendarText.setText(mouthstr[(Integer.parseInt(mouth.substring(1,2))-1)]);
        }else {
            calendarText.setText(mouthstr[(Integer.parseInt(mouth)-1)]);
        }


        pager_month_gv.setAdapter(new DateAdapter());


        smail_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_mouth_day_is_show=false;
                pager_month_chakanpingfen.setText("查看年评分");
                pager_month_gv.setVisibility(View.VISIBLE);
                date_title.setVisibility(View.GONE);
                smail_year.setText("");
                calendarText.setText(year);
//                getList("1",-1);
            }
        });
        flag_mouth_day_is_show=true;
        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月
                if ("01".equals(mouth)){
                    return;
                }
                if (flag_mouth_day_is_show){
                    if ("0".equals(mouth.substring(0,1))){
                        if ("00".equals(mouth)){
                            mouth="00";
                        }else {
                            mouth="0"+(Integer.parseInt(mouth.substring(1,2))-1);
                        }
                    }else {
                        if ("10".equals(mouth)){
                            mouth="09";
                        }else {
                            mouth=(Integer.parseInt(mouth)-1)+"";
                        }

                    }
                    pager_month_gv.setVisibility(View.GONE);
                    date_title.setVisibility(View.VISIBLE);
                    smail_year.setText(year);
                    int position=-1;
                    for (int i =0;i<mouthstr.length;i++){
                        if (calendarText.getText().toString().equals(mouthstr[i])){
                            position=i;
                            break;
                        }
                    }
                    if (position>0){
                        int rightpos=position-1;
                        calendarText.setText(mouthstr[rightpos]);
                        getList("2",position-1);
//                        getHealthGraph();
//                        getWeek(year+"-"+(rightpos)+"-"+"01",rightpos,1);
                    }
                }else {
                    year=(Integer.parseInt(year)-1)+"";

                    flag_mouth_day_is_show=false;
                    pager_month_chakanpingfen.setText("查看年评分");
                    pager_month_gv.setVisibility(View.VISIBLE);
                    date_title.setVisibility(View.GONE);
                    smail_year.setText("");
                    calendarText.setText(year);
//                    getList("1",-1);
                }
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                if (flag_mouth_day_is_show){
                    if ("12".equals(mouth)){
                        return;
                    }
                    if ("0".equals(mouth.substring(0,1))){
                        if ("09".equals(mouth)){
                            mouth="10";
                        }else {
                            mouth="0"+(Integer.parseInt(mouth.substring(1,2))+1);
                        }

                    }else {
                        mouth=(Integer.parseInt(mouth)+1)+"";
                    }
                    pager_month_gv.setVisibility(View.GONE);
                    date_title.setVisibility(View.VISIBLE);
                    smail_year.setText(year);
                    int position=-1;
                    for (int i =0;i<mouthstr.length;i++){
                        if (calendarText.getText().toString().equals(mouthstr[i])){
                            position=i;
                            break;
                        }
                    }
                    if (position<11){
                        int rightpos=position+1;
                        calendarText.setText(mouthstr[rightpos]);
                        getList("2",position+1);
//                        getHealthGraph();
//                        getWeek(year+"-"+(rightpos+1)+"-"+"01",rightpos,1);
                    }


                }else {
                    year=(Integer.parseInt(year)+1)+"";

                    flag_mouth_day_is_show=false;
                    pager_month_chakanpingfen.setText("查看年评分");
                    pager_month_gv.setVisibility(View.VISIBLE);
                    date_title.setVisibility(View.GONE);
                    smail_year.setText("");
                    calendarText.setText(year);
//                    getList("1",-1);

                }
            }
        });

//        for (int i = 0; i < 12; i++) {
//            xValue.add((i + 1) + "月");
//            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
//        }
//
//        for (int i = 0; i < 6; i++) {
//            yValue.add(i * 60);
//        }
//
//        ChartView chartView = (ChartView) view.findViewById(R.id.chartview);
//        chartView.setValue(value, xValue, yValue);
        pager_month_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mouthDay=(i+1)+"";
                pager_month_chakanpingfen.setText("查看月评分");
                flag_mouth_day_is_show=true;
                if (mouthDay.length()==1){
                    mouth="0"+mouthDay;
                }else {
                    mouth=mouthDay;
                }
                pager_month_gv.setVisibility(View.GONE);
                date_title.setVisibility(View.VISIBLE);
                smail_year.setText(year);
                calendarText.setText(mouthstr[i]);
                getList("2",i);
                if(mouth.equals(mouthDay+"")||mouth.equals("0"+mouthDay)){
                    getWeek(year+"-"+(i+1)+"-"+"01",i,0);
                }else{
                    getWeek(year+"-"+(i+1)+"-"+"01",i,1);
                }
            }
        });




        pager_month_chakanpingfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_mouth_day_is_show){
                    showDialog();
                }else {
                    showDialog();
                }
            }
        });

        mood_good .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_which_status_checked=0;
                mood_good.setTextColor(Color.BLACK);
                mood_good.setBackgroundResource(R.color.powderblue);
                mood_soso.setTextColor(Color.WHITE);
                mood_soso.setBackgroundResource(R.color.colorAccent);
                mood_bad.setTextColor(Color.WHITE);
                mood_bad.setBackgroundResource(R.color.colorAccent);
                updateView();
            }
        });
        mood_soso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_which_status_checked=1;
                mood_good.setTextColor(Color.WHITE);
                mood_good.setBackgroundResource(R.color.colorAccent);
                mood_soso.setTextColor(Color.BLACK);
                mood_soso.setBackgroundResource(R.color.powderblue);
                mood_bad.setTextColor(Color.WHITE);
                mood_bad.setBackgroundResource(R.color.colorAccent);
                updateView();
            }
        });
        mood_bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_which_status_checked=2;
                mood_good.setTextColor(Color.WHITE);
                mood_good.setBackgroundResource(R.color.colorAccent);
                mood_soso.setTextColor(Color.WHITE);
                mood_soso.setBackgroundResource(R.color.colorAccent);
                mood_bad.setTextColor(Color.BLACK);
                mood_bad.setBackgroundResource(R.color.powderblue);
                updateView();
            }
        });

        pager_month_mood_center_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_toggle_show_day_acount){
                    flag_toggle_show_day_acount=!flag_toggle_show_day_acount;
                    pager_month_mood_bottom_ll.setVisibility(View.GONE);
                    mood_day_acount_tian.setText("");
                    mood_day_acount.setText("");
                    updateView();
                }else {
                    flag_toggle_show_day_acount=!flag_toggle_show_day_acount;
                    pager_month_mood_bottom_ll.setVisibility(View.VISIBLE);
                    mood_day_acount_tian.setText(" 天");
                    updateView();
                }

            }
        });

        getList("2",-1);
//        getHealthGraph();

    }

    private void updateView() {

        if (moodStatistic!=null) {
            switch (flag_which_status_checked) {
                case 0:
                    if (flag_toggle_show_day_acount){
                        mood_day_acount.setText(moodStatistic.getGoodDayNumber() + "");
                    }
                    getWeek(year+"-"+mouth+"-"+"01",flag_i,1);
//                    updateGraph(moodStatistic);
                    break;
                case 1:
                    if (flag_toggle_show_day_acount){
                        mood_day_acount.setText(moodStatistic.getSosoDayNumber() + "");
                    }

                    getWeek(year+"-"+mouth+"-"+"01",flag_i,1);
//                    updateGraph(moodStatistic);
                    break;
                case 2:
                    if (flag_toggle_show_day_acount){
                        mood_day_acount.setText(moodStatistic.getBadDayNumber() + "");
                    }
                    getWeek(year+"-"+mouth+"-"+"01",flag_i,1);
//                    updateGraph(moodStatistic);

                    break;
            }
        }
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
        View view = View.inflate(mContext, R.layout.dialog_mood_xinqing, null);
        ImageView cancle = (ImageView) view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void showDayXinQingDialog(String day) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_show_day_xinqing, null);

        TextView daytext = view.findViewById(R.id.dialog_show_day);
        TextView year = view.findViewById(R.id.dialog_show_year_mouth);
        ImageView img1 = view.findViewById(R.id.dialog_show_biaoqing1);
        ImageView img2 = view.findViewById(R.id.dialog_show_biaoqing2);
        ImageView img3 = view.findViewById(R.id.dialog_show_biaoqing3);
        TextView date1=view.findViewById(R.id.dialog_show_date1);
        TextView textView1=view.findViewById(R.id.dialog_show_text1);
        TextView date2=view.findViewById(R.id.dialog_show_date2);
        TextView textView2=view.findViewById(R.id.dialog_show_text2);
        TextView date3=view.findViewById(R.id.dialog_show_date3);
        TextView textView3=view.findViewById(R.id.dialog_show_text3);


        daytext.setText(day.substring(8,10));
        year.setText(day.substring(0,7));

        ImageView cancle = (ImageView) view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        for (int i=0;i<xinShiList.size();i++){
            switch (xinShiList.get(i).getDialyType()){
                case 1:
                    date1.setText(xinShiList.get(i).getUpdateDate().substring(11,16));
                    textView1.setText(xinShiList.get(i).getDialyContext());
                    for (int j=0;j<haoIconCode.length;j++){
                        if (haoIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img1.setImageResource(haoicons[j]);
                        }
                    }
                    for (int j=0;j<huaiIconCode.length;j++){
                        if (huaiIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img1.setImageResource(huaiicons[j]);
                        }
                    }
                    for (int j=0;j<yibanIconCode.length;j++){
                        if (yibanIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img1.setImageResource(yibanicons[j]);
                        }
                    }
                    break;
                case 2:
                    date2.setText(xinShiList.get(i).getUpdateDate().substring(11,16));
                    textView2.setText(xinShiList.get(i).getDialyContext());
                    for (int j=0;j<haoIconCode.length;j++){
                        if (haoIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img2.setImageResource(haoicons[j]);
                        }
                    }
                    for (int j=0;j<huaiIconCode.length;j++){
                        if (huaiIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img2.setImageResource(huaiicons[j]);
                        }
                    }
                    for (int j=0;j<yibanIconCode.length;j++){
                        if (yibanIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img2.setImageResource(yibanicons[j]);
                        }
                    }
                    break;
                case 3:
                    date3.setText(xinShiList.get(i).getUpdateDate().substring(11,16));
                    textView3.setText(xinShiList.get(i).getDialyContext());
                    for (int j=0;j<haoIconCode.length;j++){
                        if (haoIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img3.setImageResource(haoicons[j]);
                        }
                    }
                    for (int j=0;j<huaiIconCode.length;j++){
                        if (huaiIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img3.setImageResource(huaiicons[j]);
                        }
                    }
                    for (int j=0;j<yibanIconCode.length;j++){
                        if (yibanIconCode[j]==xinShiList.get(i).getEmojiId()){
                            img3.setImageResource(yibanicons[j]);
                        }
                    }
                    break;
            }
        }
        dialog.setContentView(view);
        dialog.show();
    }


    private void getHealthGraph() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        if ("01".equals(mouth)||"03".equals(mouth)||"05".equals(mouth)||"07".equals(mouth)||"08".equals(mouth)||"10".equals(mouth)||"12".equals(mouth)){
            params.addQueryStringParameter("endDate",year+"-"+mouth+"-31");
        }else if ("04".equals(mouth)||"06".equals(mouth)||"09".equals(mouth)||"11".equals(mouth)){
            params.addQueryStringParameter("endDate",year+"-"+mouth+"-30");
        }else {
            params.addQueryStringParameter("endDate",year+"-"+mouth+"-28");
        }
        params.addQueryStringParameter("startDate", year+"-"+mouth+"-01");

        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----2:" + MouthpieceUrl.base_health_report_graph + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ReportBean>>>(MouthpieceUrl.base_health_report_graph , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ReportBean>> response, String result) {
                Log.i("xiaopeng-----2","result-----"+result);
                if (response.code==200){
//                    updateGraph(response.data);
                }else {
                    ToastUtil.showContent(mContext,response.message);
                }
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----2","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }


    private void updateGraph(MoodStatisticBean moodStatistic) {

        xValue = new ArrayList<>();
        //y轴坐标对应的数据
        yValue = new ArrayList<>();
        //折线对应的数据
        value = new HashMap<>();
//        Collections.reverse(datas);
        if (moodStatistic!=null){
            if (moodStatistic.getDays()!=null){
                for (int i =0;i<moodStatistic.getDays().size();i++){
                    xValue.add(moodStatistic.getDays().get(i).getDate().substring(8,10));
                    value.put(moodStatistic.getDays().get(i).getDate().substring(8,10), (int) moodStatistic.getDays().get(i).getMoodScore());
                }
            }

        }
//
//        for (int i = 0; i < 12; i++) {
//            xValue.add((i + 1) + "月");
//            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
//        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 20);
        }

        ChartView chartView = (ChartView) view.findViewById(R.id.chartview);
        chartView.setValue(value, xValue, yValue);
    }

    //获取列表
    List<XinShi> xinShiList =new ArrayList<>();
    public void getDayDataList(final String day) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("dialyDay", day);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mood_getbyday + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<XinShi>>>(MouthpieceUrl.base_mood_getbyday , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<XinShi>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                xinShiList=response.data;
                if (response.code==200){
                    showDayXinQingDialog(day);
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


    //获取列表
    public void getList(String yom,final int i) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        params.addQueryStringParameter("yom", yom);//1是年  2//是月
        if ("1".equals(yom)){
            params.addQueryStringParameter("date", year);
        }else {
            params.addQueryStringParameter("date", year+"-"+mouth);
        }

        Log.i("xiaopeng", "url----mood:" + MouthpieceUrl.base_mood_getMoodStatistics + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<MoodStatisticBean>>(MouthpieceUrl.base_mood_getMoodStatistics , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<MoodStatisticBean> response, String result) {
                if (response.code==200){
                    moodStatisticBeiFen=response.data;
                    moodStatistic=response.data;
                    updateGraph(moodStatistic);
                    flag_i=i;
                    getWeek(year+"-"+mouth+"-"+"01",i,1);
                }
                Log.i("xiaopeng-----mood","result-----mood"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----mood","result-----mood"+result);
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

            String mouthDay=(i+1)+"";

            view = View.inflate(mContext, R.layout.date_item, null);

            TextView date_item_mouth = view.findViewById(R.id.date_item_mouth);
            date_item_mouth.setText(i+1+"");

            if(Utils.getmouth().equals(mouthDay)||Utils.getmouth().equals("0"+mouthDay)){
                date_item_mouth.setBackgroundResource(R.drawable.button_stoke_white);
            }
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
        public View getView(final int i, View view, ViewGroup viewGroup) {

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



            if (moodStatistic!=null){
                if (moodStatistic.getDays()!=null){
                    for (int day=0;day<moodStatistic.getDays().size();day++){
//                        Log.i("xiaopeng----",""+moodStatistic.getDays().size());

                        String nowday="";
                        switch (daysofmonth){
                            case "天":
                                nowday = year+"-"+mouth+"-"+((i-5)<10?"0":"")+(i-5);
                                break;
                            case "一":
                                nowday = year+"-"+mouth+"-"+((i+1)<10?"0":"")+(i+1);
                                break;
                            case "二":
                                nowday = year+"-"+mouth+"-"+((i)<10?"0":"")+(i);
                                break;
                            case "三":
                                nowday = year+"-"+mouth+"-"+((i-1)<10?"0":"")+(i-1);
                                break;
                            case "四":
                                nowday = year+"-"+mouth+"-"+((i-2)<10?"0":"")+(i-2);
                                break;
                            case "五":
                                nowday = year+"-"+mouth+"-"+((i-3)<10?"0":"")+(i-3);
                                break;
                            case "六":
                                nowday = year+"-"+mouth+"-"+((i-4)<10?"0":"")+(i-4);
                                break;
                        }
//                        if (moodStatistic.getDays().get(day).getDate().equals(nowday)||moodStatistic.getDays().get(day).getDate().equals(nowday.substring(0,8)+"0"+nowday.substring(8,9))){
                        if (moodStatistic.getDays().get(day).getDate().equals(nowday)){
                            if (moodStatistic.getDays().get(day).getDate().equals(Utils.getCurrentDate())){

                            }else {
                                if (flag_toggle_show_day_acount){
                                    switch (flag_which_status_checked){
                                        case 0:
                                            if (moodStatistic.getDays().get(day).getMoodType()==1){
                                                date_item_mouth.setBackgroundResource(R.drawable.corners_white);
                                                date_item_mouth.setTextColor(Color.parseColor("#28d3bd"));
                                            }
                                            break;
                                        case 1:
                                            if (moodStatistic.getDays().get(day).getMoodType()==2){
                                                date_item_mouth.setBackgroundResource(R.drawable.corners_white);
                                                date_item_mouth.setTextColor(Color.parseColor("#28d3bd"));
                                            }
                                            break;
                                        case 2:
                                            if (moodStatistic.getDays().get(day).getMoodType()==3){
                                                date_item_mouth.setBackgroundResource(R.drawable.corners_white);
                                                date_item_mouth.setTextColor(Color.parseColor("#28d3bd"));
                                            }
                                            break;
                                    }
                                }else {
                                    date_item_mouth.setBackgroundResource(R.drawable.corners_white);
                                    date_item_mouth.setTextColor(Color.parseColor("#28d3bd"));
                                }


                            }
                            final String requestDay=moodStatistic.getDays().get(day).getDate();
                            date_item_mouth.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getDayDataList(requestDay);
                                }
                            });

                        }else {
                        }
                    }
                }else {
                }
            }

            if (date_item_mouth.getText().toString().length()==1){
                if((year+"-"+mouth+"-0"+date_item_mouth.getText().toString()).equals(Utils.getCurrentDate())){
                    date_item_mouth.setBackgroundResource(R.drawable.button_stoke_white);
                }
            }else {
                if((year+"-"+mouth+"-"+date_item_mouth.getText().toString()).equals(Utils.getCurrentDate())){
                    date_item_mouth.setBackgroundResource(R.drawable.button_stoke_white);
                }
            }




            return view;

        }
    }

}
