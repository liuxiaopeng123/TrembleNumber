package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.activity.JurisdictionActivity;
import com.theworldofluster.example.ziang.tremblenumber.bean.OptData;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.services.PostDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Ziang on 2018/3/29.
 */

public class APthreePager extends Fragment implements View.OnClickListener {

    View view;

    private TextView pager_p_three_go,pager_shentifenxi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_p_three, container, false);
            initData();

        }


        return view;
    }

    private void initData() {
        pager_shentifenxi=view.findViewById(R.id.pager_shentifenxi);

        pager_p_three_go = view.findViewById(R.id.pager_p_three_go);
        if (isOpenPermisson()){
            pager_p_three_go.setVisibility(View.GONE);
        }else {
            pager_p_three_go.setVisibility(View.VISIBLE);
        }
        pager_p_three_go.setOnClickListener(this);
    }

    private boolean isOpenPermisson() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager usageStatsManager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
            long ts = System.currentTimeMillis();
            //第一个参数： 按照时间间隔来查询  第二个：开始时间 第三个：截止时间
            //通过给定的开始与结束时间  INTERVAL_BEST是按照最合适的时间间隔类型
            //还可以有：INTERVAL_DAILY  WEEKLY MONTHLY YEARLY
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts-30*60*1000, ts);
            //返回结果中的UsageStats的官方解释是：包含特定时间范围内应用程序包的使用统计资料。
            if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                Log.i("xiaopeng", "getForegroundApp: 空"  );
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.pager_p_three_go:
                if (isOpenPermisson()){

                }else {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }


                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOpenPermisson()){
            pager_p_three_go.setVisibility(View.GONE);
        }else {
            pager_p_three_go.setVisibility(View.VISIBLE);
        }
        if (((PersonalActivity)getContext()).reportWeek!=null){
            pager_shentifenxi.setText(((PersonalActivity)getContext()).reportWeek.getPsychologyDesc());
        }
    }
}
