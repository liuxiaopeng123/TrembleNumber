package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.activity.FoldlinediagramActivity;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.ReportBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil22;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.SelectWeekPopupWindow;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by Ziang on 2018/3/29.
 */

public class APonePager extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

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

    private boolean flag_first_come=true;

//    private TextView pager_p_one_date;
    private SelectWeekPopupWindow leftPopupWindow ;

    View view;

    ReportBean report;

    private PieChart mPieChart;

    private TextView pager_p_one_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_p_one, container, false);
            initData();

        }


        return view;
    }

    private void initData() {

        pager_p_one_date=view.findViewById(R.id.pager_p_one_date);
        pager_p_one_date.setText(DateUtil.getMonday2()+"-"+DateUtil.getLastWeekSunday2());
        pager_p_one_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataView();
//                startActivity(new Intent(getActivity(),WeekSelectActivity.class));
            }
        });
        setDateRange();
        mPieChart = (PieChart) view.findViewById(R.id.chart1);
        getReportWeek(DateUtil.getMonday(),DateUtil.getLastWeekSunday());

    }

    public void getReportWeek(String s,String e) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        if ("".equals(s)){
            if (startY == endY){
                params.addQueryStringParameter("startDate", startY + "-" + (startM < 10 ? "0" + startM : startM )+ "-" + (startD < 10 ? "0" + startD : startD ));
                params.addQueryStringParameter("endDate",endY + "-"+ ( endM< 10 ? "0" + endM : endM )+ "-" + (endD < 10 ? "0" + endD : endD ));
            }else {
                params.addQueryStringParameter("startDate", startY + "-" + (startM < 10 ? "0" + startM : startM )+ "-" + (startD < 10 ? "0" + startD : startD ));
                params.addQueryStringParameter("endDate",endY + "-"+ ( endM< 10 ? "0" + endM : endM )+ "-" + (endD < 10 ? "0" + endD : endD ));
            }
        }else {
            params.addQueryStringParameter("startDate", s);
            params.addQueryStringParameter("endDate",e);

        }


        Log.i("xiaopeng", "url----1:" + MouthpieceUrl.base_health_report_week + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<ReportBean>>(MouthpieceUrl.base_health_report_week , getActivity(), params) {
            @Override
            public void onParseSuccess(GsonObjModel<ReportBean> response, String result) {
                if (response.code==200){
                    report=response.data;
                    updataBiaoTu();
                }
                Log.i("xiaopeng-----1","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----1","result-----"+result);
                updataBiaoTu();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
                updataBiaoTu();
            }
        };
    }

    private void updataBiaoTu() {
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //绘制中间文字
        mPieChart.setCenterText(generateCenterSpannableText());
        mPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(true);


        // 添加一个选择监听器
        mPieChart.setOnChartValueSelectedListener(this);
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if (report!=null){
            PreferenceUtil.putString("xinlifenxi",""+report.getPsychologyAnalysis());
            PreferenceUtil.putString("shentifenxi",""+report.getPhysiologyAnalysis());
            entries.add(new PieEntry((float) report.getPsychologyScore(), "心理分"));
            entries.add(new PieEntry((float) report.getPhysiologyScore(), "生理分"));
        }else {
            PreferenceUtil.putString("xinlifenxi","暂无心理分析~");
            PreferenceUtil.putString("shentifenxi","暂无身体分析~");
            entries.add(new PieEntry(0, "心理分"));
            entries.add(new PieEntry(0, "生理分"));
        }
        //设置数据
        setData(entries);

        //默认动画
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }


    public void showDataView() {
        initPopupWindow();
        if (startY == 0) {

            leftPopupWindow.show(nowY, nowM, nowD, pager_p_one_date, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
        } else {

            leftPopupWindow.show(startY, startM, startD, pager_p_one_date, new PopupWindow.OnDismissListener() {
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
            leftPopupWindow = new SelectWeekPopupWindow(getActivity(), onLeftSelectItemClick, nowY, nowM, nowD);
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
            getReportWeek("","");

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
                pager_p_one_date.setText(startY + "年" + DateUtil.getMonday().substring(5,7) + "月" + DateUtil.getMonday().substring(8,10) + "日-" + DateUtil.getLastWeekSunday().substring(5,7) + "月" + DateUtil.getLastWeekSunday().substring(8,10) + "日");
            else
                pager_p_one_date.setText(startY + "年" + DateUtil.getMonday().substring(5,7) + "月" + DateUtil.getMonday().substring(8,10) + "日-" +endY + "年" + DateUtil.getLastWeekSunday().substring(5,7) + "月" + DateUtil.getLastWeekSunday().substring(8,10) + "日");
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
        }else {
            if (startY == endY)
                pager_p_one_date.setText(startY + "年" + startM + "月" + startD + "日-" + endM + "月" + endD + "日");
            else
                pager_p_one_date.setText(startY + "年" + startM + "月" + startD+ "日-" +endY + "年" + endM + "月" + endD + "日");
        }

        selDate = DateUtil22.getYYYY_MM_DD(startY, startM, startD);
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "三年级一班");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);

        // 撤销所有的亮点
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    //绘制中心文字
    private SpannableString generateCenterSpannableText() {
        SpannableString s;
        if (report!=null){
             s= new SpannableString((int)report.getScore()+"分");
        }else {
            s = new SpannableString("暂无数据");
        }

        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#28d3bd")), 0, s.length(), 0);
        //s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length()-17, s.length(), 0);
        return s;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        startActivity(new Intent(getActivity(),FoldlinediagramActivity.class));
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
