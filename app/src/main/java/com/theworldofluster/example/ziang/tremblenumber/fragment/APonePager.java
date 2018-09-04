package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.activity.FoldlinediagramActivity;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;

import java.util.ArrayList;


/**
 * Created by Ziang on 2018/3/29.
 */

public class APonePager extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

    View view;

    WanNengBean report;

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
        pager_p_one_date.setText(DateUtil.getMonday2()+"至"+DateUtil.getLastWeekSunday2());
        mPieChart = (PieChart) view.findViewById(R.id.chart1);

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
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);


        // 添加一个选择监听器
        mPieChart.setOnChartValueSelectedListener(this);

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if (report!=null){
            entries.add(new PieEntry(report.getPsychologyScore(), "心理分"));
            entries.add(new PieEntry(report.getPhysiologyScore(), "生理分"));
        }else {
            entries.add(new PieEntry(50, "心理分"));
            entries.add(new PieEntry(50, "生理分"));
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

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "三年级一班");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
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
             s= new SpannableString(report.getScore()+"分");
        }else {
            s = new SpannableString("0分");
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
        if (((PersonalActivity)getContext()).reportWeek!=null){
            report=((PersonalActivity)getContext()).reportWeek;
            initData();
        }
    }
}
