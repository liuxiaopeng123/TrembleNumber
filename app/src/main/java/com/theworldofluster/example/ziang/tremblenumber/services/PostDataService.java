package com.theworldofluster.example.ziang.tremblenumber.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.bean.OptData;
import com.theworldofluster.example.ziang.tremblenumber.bean.RequestBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.LocationUtils;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.service.AlarmReceiver;

/**
 * Created by liupeng on 2018/7/30.
 */

public class PostDataService extends Service implements SensorEventListener {

    float strength;

    float X_lateral;
    float Y_longitudinal;
    float Z_vertical;

    double longitude;
    double latitude;


    private SensorManager mSensroMgr;

    Sensor sensor;
    public float stepTotalCount = 0;
    public float mDetector = 0;
    String month;
    String today;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int Time = 1000*60*30;//周期时间
    private int anHour =8*60*60*1000;// 这是8小时的毫秒数 为了少消耗流量和电量，8小时自动更新一次
    private Timer timer = new Timer();



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensroMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //步数传感器
        Sensor mStepCount = mSensroMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //单次有效计步
        Sensor mStepDetector = mSensroMgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        mSensroMgr.registerListener(this, mStepCount, SensorManager.SENSOR_DELAY_FASTEST);
        mSensroMgr.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);

        //选取加速度感应器
        sensor = mSensroMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener lsn = new SensorEventListener() {
            public void onSensorChanged(SensorEvent e) {
                X_lateral = e.values[SensorManager.DATA_X];
                Y_longitudinal = e.values[SensorManager.DATA_Y];
                Z_vertical = e.values[SensorManager.DATA_Z];
            }
            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        // 注册listener，第三个参数是检测的精确度
        mSensroMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);

        /**
         * 方式二：采用timer及TimerTask结合的方法
         */
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (isOpenPermisson()){
////                    Looper.prepare();
//                    postData();
////                    Looper.loop();
////
//                }
//                Log.i("xiaopeng----","小鹏"+new Date().
//                        toString());
//            }
//        };
//        timer.schedule(timerTask,
//                1000,//延迟1秒执行
//                Time);//周期时间


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("LongRunningService", "xiaopeng------executed at " + new Date().
//                        toString());
//            }
//        }).start();
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int anHour = 30 * 1000; // 这是一小时的毫秒数
//        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
//        Intent i = new Intent(this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isOpenPermisson()){
                    postData();
//
                }
                Log.i("xiaopeng----","小鹏"+new Date().
                        toString());
            }
        };
        timer.schedule(timerTask,
                1000,//延迟1秒执行
                Time);//周期时间

        return super.onStartCommand(intent, flags, startId);
    }


    private void postData() {
        RequestParams params = new RequestParams(MouthpieceUrl.base_monitor_dataGrab);
        params.setAsJsonContent(true);
        params.addHeader("Content-Type", "application/json");
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addHeader("userId", PreferenceUtil.getString("userId",""));
        RequestBean requestBean = new RequestBean();
        requestBean.setUserId(PreferenceUtil.getString("userId",""));
        requestBean.setDirectionX(X_lateral);
        requestBean.setDirectionY(Y_longitudinal);
        requestBean.setDirectionZ(Z_vertical);
        requestBean.setLight(strength);
        requestBean.setWalk((int)mDetector);
        requestBean.setSpeed(10);
        requestBean.setTime(sf.format(new Date(System.currentTimeMillis())));
        requestBean.setStartTime(sf.format(new Date(System.currentTimeMillis()-1800000)));
        requestBean.setLongitude(Double.parseDouble(PreferenceUtil.getString("MyLongitude","0.0")));
        requestBean.setLatitude(Double.parseDouble(PreferenceUtil.getString("MyLatitude","0.0")));
        requestBean.setOptList(getForegroundApp());
        Gson gson = new Gson();
        params.setBodyContent(gson.toJson(requestBean));
        Log.i("xiaopeng", "url----bodyContent:" + params.getBodyContent());
        Log.i("xiaopeng", "url----11:" + MouthpieceUrl.base_monitor_dataGrab + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i("xiaopeng-----1","result-----"+result);
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("xiaopeng-----2","result-----"+ex.toString());
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("xiaopeng-----3","result-----"+cex);
            }
            @Override
            public void onFinished() {
                Log.i("xiaopeng-----4","result-----finish");
            }
        });
    }


    private boolean isOpenPermisson() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
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


    private List<OptData> getForegroundApp() {
        List<OptData> optList=new ArrayList<>();
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            long ts = System.currentTimeMillis();
            //第一个参数： 按照时间间隔来查询  第二个：开始时间 第三个：截止时间
            //通过给定的开始与结束时间  INTERVAL_BEST是按照最合适的时间间隔类型
            //还可以有：INTERVAL_DAILY  WEEKLY MONTHLY YEARLY
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts-30*60*1000, ts);
            //返回结果中的UsageStats的官方解释是：包含特定时间范围内应用程序包的使用统计资料。
            if (queryUsageStats == null || queryUsageStats.isEmpty()) {
                Log.i("xiaopeng", "getForegroundApp: 空"  );
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                return null;
            }
            PackageManager pm = getPackageManager();
            for (UsageStats usageStats : queryUsageStats) {
                if (usageStats.getLastTimeUsed()!=0) {
                    try {
                        ApplicationInfo info = pm.getApplicationInfo(usageStats.getPackageName(),PackageManager.GET_META_DATA);
                        OptData optData = new OptData();
                        OptData optData2 = new OptData();
                        if (usageStats.getTotalTimeInForeground()>0&&usageStats.getFirstTimeStamp()!=usageStats.getLastTimeStamp()){
                            optData.setOptDetail("打开"+info.loadLabel(pm).toString());
                            optData.setOptTime(""+sf.format(new Date(usageStats.getLastTimeStamp()-usageStats.getTotalTimeInForeground())));
                            optData2.setOptDetail("关闭"+info.loadLabel(pm).toString());
                            optData2.setOptTime(""+sf.format(new Date(usageStats.getLastTimeStamp())));
                            optList.add(optData);
                            optList.add(optData2);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            Gson gson = new Gson();
            Log.i("xiaopeng-----optlist",gson.toJson(optList));
            return optList;
        }else {
            return null;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light_strength = event.values[0];

            strength =  light_strength;

//            Log.e("Ziang","光线强度"+"---"+strength);
        }

        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (event.values[0] == 1.0) {
                mDetector++;
            }
            Log.i("xiaopeng-------", "历史总步数: " + event.values[0] + "步");
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0) {
                mDetector++;

                PreferenceUtil.putFloat("mDetector", mDetector);
                PreferenceUtil.putString("month", month);
                PreferenceUtil.putString("today", today);
                Log.i("xiaopeng-------", "一共走了" + mDetector + "步");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，一般无需处理
    }


}
