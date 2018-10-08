package com.theworldofluster.example.ziang.tremblenumber.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.ReportBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil22;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.ChartView;
import com.theworldofluster.example.ziang.tremblenumber.view.SelectWeekPopupWindow;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FoldlinediagramActivity extends AppCompatActivity {
    private int nowY;
    private int nowM;
    private int nowD;

    private int startY;
    private int startM;
    private int startD;

    private int endY;
    private int endM;
    private int endD;
    public static String selDate;
    private SelectWeekPopupWindow leftPopupWindow ;
    private RelativeLayout information_back;

    ChartView chartView;

    private boolean flag_first_come=true;
    TextView activity_graph_jiankangfen,activity_graph_quanwnagpingjunfen,button_time_select,activity_graph_shentifen,activity_graph_xinlifen;

    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_foldlinediagram);
        ViewUtils.inject(this); //注入view和事件

        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#00000000"));

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        chartView = (ChartView) findViewById(R.id.chartview_graph);
        information_back=findViewById(R.id.information_back);
        information_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_time_select=findViewById(R.id.button_time_select);
        activity_graph_shentifen=findViewById(R.id.activity_graph_shentifen);
        activity_graph_xinlifen = findViewById(R.id.activity_graph_xinlifen);
        activity_graph_jiankangfen = findViewById(R.id.activity_graph_jiankangfen);
        activity_graph_quanwnagpingjunfen =findViewById(R.id.activity_graph_quanwangpingjunfen);
        button_time_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataView();
            }
        });
        setDateRange();
        getHealthGraph();
        timer.schedule(task,1000,400);
    }

    public void showDataView() {
        initPopupWindow();
        if (startY == 0) {

            leftPopupWindow.show(nowY, nowM, nowD, button_time_select, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
        } else {

            leftPopupWindow.show(startY, startM, startD, button_time_select, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
        }

    }

    private void initPopupWindow()

    {

        if (leftPopupWindow == null) {
            leftPopupWindow = new SelectWeekPopupWindow(this, onLeftSelectItemClick, nowY, nowM, nowD);
            leftPopupWindow.setZXStyle(false);
        }

    }

    SelectWeekPopupWindow.OnSelectItemClickListencer onLeftSelectItemClick = new SelectWeekPopupWindow.OnSelectItemClickListencer() {
        @Override
        public void onSelectItemClick(View view, int position) {
            startY = leftPopupWindow.yearData;
            startM = leftPopupWindow.monthData;
            startD = leftPopupWindow.dayData;
            setDateRange();
            getHealthGraph();

        }
    };

    private void setDateRange() {
        nowY = DateUtil22.getYear();
        nowM = DateUtil22.getMonth();
        nowD = DateUtil22.getDay();
        int[] is;
        try {
            if (startY == 0) is = DateUtil22.getWeekDay(nowY, nowM, nowD);
            else
                is = DateUtil22.getWeekDay(startY, startM, startD);
            endY = is[3];
            endM = is[4];
            endD = is[5];
            startY = is[0];
            startM = is[1];
            startD = is[2];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (flag_first_come){
            if (startY == endY)
                button_time_select.setText(startY + "年" + startY + "月" );
            else
                button_time_select.setText(startY + "年" +startY + "月" );
            if ("0".equals(DateUtil.getMonday().substring(5,6))){
                startM=Integer.parseInt(DateUtil.getMonday().substring(6,7));
            }else {
                startM=Integer.parseInt(DateUtil.getMonday().substring(5,7));
            }
            if ("0".equals(DateUtil.getMonday().substring(8,9))){
                startD=Integer.parseInt(DateUtil.getMonday().substring(9,10));
            }else {
                startD=Integer.parseInt(DateUtil.getMonday().substring(8,10));
            }
            flag_first_come=false;
        }
        if (startY == endY)
            button_time_select.setText(startY + "年" + startM + "月");
        else
            button_time_select.setText(startY + "年" + startM + "月");
        selDate = DateUtil22.getYYYY_MM_DD(startY, startM, startD);
    }
    List<ReportBean> datas;
    private void getHealthGraph() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        if (startM==1||startM==3||startM==5||startM==7||startM==8){
            params.addQueryStringParameter("endDate",startY+"-0"+startM +"-31");
            params.addQueryStringParameter("startDate",startY+"-0"+startM +"-01");
        }else if (startM==10||startM==12){
            params.addQueryStringParameter("startDate",startY+"-"+startM +"-01");
            params.addQueryStringParameter("endDate",startY+"-"+startM +"-31");
        }else if (startM==4||startM==6||startM==9){
            params.addQueryStringParameter("startDate",startY+"-0"+startM +"-01");
            params.addQueryStringParameter("endDate",startY+"-0"+startM +"-30");
        }else if (startM==11){
            params.addQueryStringParameter("startDate",startY+"-"+startM +"-01");
            params.addQueryStringParameter("endDate",startY+"-"+startM +"-30");
        }else {
            params.addQueryStringParameter("startDate",startY+"-02-01");
            params.addQueryStringParameter("endDate",startY+"-02-28");
        }
        Log.i("xiaopeng", "url----2:" + MouthpieceUrl.base_health_report_graph + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ReportBean>>>(MouthpieceUrl.base_health_report_graph , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ReportBean>> response, String result) {
                Log.i("xiaopeng-----2","result-----"+result);
                if (response.code==200){
                    datas=response.data;
                    updateGraph();
//                    updaTextScore();
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


    private void updateGraph() {
        xValue = new ArrayList<>();
        yValue = new ArrayList<>();
        value = new HashMap<>();
        if (datas!=null){
            if (datas.size()>0){
                activity_graph_shentifen.setText(((int)datas.get(datas.size()-1).getPhysiologyScore())+"");
                activity_graph_xinlifen.setText(((int)datas.get(datas.size()-1).getPsychologyScore())+"");
                activity_graph_jiankangfen.setText(((int)datas.get(datas.size()-1).getScore())+"");
                activity_graph_quanwnagpingjunfen.setText(((int)datas.get(datas.size()-1).getAvgScore())+"");

                for (int i =0;i<datas.size();i++){
                    xValue.add(datas.get(i).getPeriodBegin().substring(8,10)+"~"+datas.get(i).getPeriodEnd().substring(8,10));
                    value.put(datas.get(i).getPeriodBegin().substring(8,10)+"~"+datas.get(i).getPeriodEnd().substring(8,10), (int) datas.get(i).getScore());
                }
            }else {
                activity_graph_shentifen.setText("—");
                activity_graph_xinlifen.setText("—");
                activity_graph_jiankangfen.setText("—");
                activity_graph_quanwnagpingjunfen.setText("—");
                xValue = new ArrayList<>();
                yValue = new ArrayList<>();
                value = new HashMap<>();
            }
        }else {
            activity_graph_shentifen.setText("—");
            activity_graph_xinlifen.setText("—");
            activity_graph_jiankangfen.setText("—");
            activity_graph_quanwnagpingjunfen.setText("—");
            xValue = new ArrayList<>();
            yValue = new ArrayList<>();
            value = new HashMap<>();
        }
//
//        for (int i = 0; i < 12; i++) {
//            xValue.add((i + 1) + "月");
//            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
//        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 200);
        }
        chartView.setSelectIndex(-1);
        chartView.setValue(value, xValue, yValue);
    }

    private void updaTextScore() {
        if (datas!=null){
            if (datas.size()>0){
                if (chartView.getSelectIndex()>0){
                    activity_graph_shentifen.setText(((int)datas.get(selectPosition).getPhysiologyScore())+"");
                    activity_graph_xinlifen.setText(((int)datas.get(selectPosition).getPsychologyScore())+"");
                    activity_graph_jiankangfen.setText(((int)datas.get(selectPosition).getScore())+"");
                    activity_graph_quanwnagpingjunfen.setText(((int)datas.get(selectPosition).getAvgScore())+"");
                }else {
                    activity_graph_shentifen.setText(((int)datas.get(0).getPhysiologyScore())+"");
                    activity_graph_xinlifen.setText(((int)datas.get(0).getPsychologyScore())+"");
                    activity_graph_jiankangfen.setText(((int)datas.get(0).getScore())+"");
                    activity_graph_quanwnagpingjunfen.setText(((int)datas.get(0).getAvgScore())+"");
                }
            }else {
                activity_graph_shentifen.setText("—");
                activity_graph_xinlifen.setText("—");
                activity_graph_jiankangfen.setText("—");
                activity_graph_quanwnagpingjunfen.setText("—");
            }
        }else {
            activity_graph_shentifen.setText("—");
            activity_graph_xinlifen.setText("—");
            activity_graph_jiankangfen.setText("—");
            activity_graph_quanwnagpingjunfen.setText("—");
        }
//
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    updaTextScore();
                    break;
            }
        };
    };

    private int  selectPosition=-1;
    Timer timer  =new Timer();
    TimerTask task = new TimerTask() {
        @Override public void run() {
            if (chartView.getSelectIndex()>0){
                if (chartView.getSelectIndex()-1!=selectPosition){
                    Log.i("xiaopeng----",""+selectPosition  +"----"+chartView.getSelectIndex());
                    selectPosition=chartView.getSelectIndex()-1;
                    handler.sendEmptyMessage(1);
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
