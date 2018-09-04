package com.theworldofluster.example.ziang.tremblenumber.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.ChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoldlinediagramActivity extends AppCompatActivity {

    TextView activity_graph_jiankangfen,activity_graph_quanwnagpingjunfen;

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


        activity_graph_jiankangfen = findViewById(R.id.activity_graph_jiankangfen);
        activity_graph_quanwnagpingjunfen =findViewById(R.id.activity_graph_quanwangpingjunfen);
        getHealthGraph();

    }

    private void getHealthGraph() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("startDate", "2018-07-01");
        params.addQueryStringParameter("endDate","2018-07-31");
        Log.i("xiaopeng", "url----2:" + MouthpieceUrl.base_health_report_graph + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<WanNengBean>>>(MouthpieceUrl.base_health_report_graph , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<WanNengBean>> response, String result) {
                Log.i("xiaopeng-----2","result-----"+result);
                if (response.code==200){

                    updateGraph(response.data);
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


    private void updateGraph(List<WanNengBean> datas) {
        if (datas!=null){
            activity_graph_jiankangfen.setText(datas.get(0).getPhysiologyScore()+"");
            activity_graph_quanwnagpingjunfen.setText(datas.get(0).getAvgScore()+"");

            for (int i =0;i<datas.size();i++){
                xValue.add(datas.get(i).getPeriodBegin().substring(5,10)+"~"+datas.get(i).getPeriodEnd().substring(5,10));
                value.put(datas.get(i).getPeriodBegin().substring(5,10)+"~"+datas.get(i).getPeriodEnd().substring(5,10),datas.get(i).getAvgScore());
            }
        }
//
//        for (int i = 0; i < 12; i++) {
//            xValue.add((i + 1) + "月");
//            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
//        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 200);
        }

        ChartView chartView = (ChartView) findViewById(R.id.chartview_graph);
        chartView.setValue(value, xValue, yValue);
    }


}
