package com.theworldofluster.example.ziang.tremblenumber.personal;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.adapter.MyFragmentPagerAdapter;
import com.theworldofluster.example.ziang.tremblenumber.bean.AleartBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.ExtrasBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.OptData;
import com.theworldofluster.example.ziang.tremblenumber.bean.RankBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APonePager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APthreePager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.APtwoPager;
import com.theworldofluster.example.ziang.tremblenumber.jpushdemo.ExampleUtil;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.services.PostDataService;
import com.theworldofluster.example.ziang.tremblenumber.user.MyActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.DateUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

//2018-07-21
public class PersonalActivity extends AppCompatActivity {

    @ViewInject(R.id.view_pager)
    ViewPager mViewPager;

    @ViewInject(R.id.view_pager_box)
    RelativeLayout mViewPagerBox;

    @ViewInject(R.id.activity_personal_bg)
    LinearLayout activity_personal_bg;

    @ViewInject(R.id.activity_personal_userdata)
    CircularImage activity_personal_userdata;

    @ViewInject(R.id.yuan_a)
    ImageView yuan_a;

    @ViewInject(R.id.yuan_b)
    ImageView yuan_b;

    @ViewInject(R.id.yuan_c)
    ImageView yuan_c;

    @ViewInject(R.id.activity_persion_pk)
    TextView activity_persion_pk;

    @ViewInject(R.id.activity_personal_back)
    RelativeLayout activity_personal_back;

    float strength;

    float X_lateral;
    float Y_longitudinal;
    float Z_vertical;

    private SensorManager mSensroMgr;

    private ArrayList<Fragment> fragmentsList;

    private static final String TAG = "sensor";

    Sensor sensor;

    public float stepTotalCount = 0;
    public float mDetector = 0;
    float distance,energy;
    String month;
    String today;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //所需要申请的权限数组
    private static final String[] permissionsArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS };

    //还需申请的权限列表
    private List<String> permissionsList = new ArrayList<String>();
    //申请权限后的返回码
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_personal);
        ViewUtils.inject(this); //注入view和事件

        JPushInterface.init(getApplicationContext());

        Log.e("JPush", ExampleUtil.getImei(getApplicationContext(), "123"));

        checkRequiredPermission(this);

        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色·
        window.setStatusBarColor(Color.parseColor("#00000000"));

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        mViewPager.setOffscreenPageLimit(3);

        fragmentsList = new ArrayList<Fragment>();

        fragmentsList.add(new APonePager());
        fragmentsList.add(new APtwoPager());
        fragmentsList.add(new APthreePager());

        mViewPager.setAdapter(new MyFragmentPagerAdapter(PersonalActivity.this.getSupportFragmentManager(), fragmentsList));

        mViewPagerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mViewPager.setCurrentItem(1);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==0){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_02);

                    yuan_a.setImageResource(R.mipmap.yuan_a);
                    yuan_b.setImageResource(R.mipmap.yuan_b);
                    yuan_c.setImageResource(R.mipmap.yuan_b);

                }else if(position==1){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_01);

                    yuan_a.setImageResource(R.mipmap.yuan_b);
                    yuan_b.setImageResource(R.mipmap.yuan_a);
                    yuan_c.setImageResource(R.mipmap.yuan_b);

                }else if(position==2){
                    activity_personal_bg.setBackgroundResource(R.mipmap.activity_personal_03);

                    yuan_a.setImageResource(R.mipmap.yuan_b);
                    yuan_b.setImageResource(R.mipmap.yuan_b);
                    yuan_c.setImageResource(R.mipmap.yuan_a);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        activity_personal_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity_personal_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rankBeanMySelf!=null){
                    switch (rankBeanMySelf.getRanking()){

                    }
                }

//                startActivity(new Intent(PersonalActivity.this,MyActivity.class));
            }
        });




        init();
//        postData();
        getReportWeek();
        getHealthGraph();
        getPKUserStauts();
        Intent intent = new Intent(this, PostDataService.class);
        startService(intent);
        Log.i("xiaopeng---","启动了一个服务");

        Utils.BJSloadImg(this,PreferenceUtil.getString("userheadUrl",""),activity_personal_userdata);
        getRankSelf("1");
    }


    RankBean rankBeanMySelf;
    private void getRankSelf(String type) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("period", DateUtil.getSunday());
        Log.i("xiaopeng", "url----1:" + MouthpieceUrl.base_pk_recored_rank_self + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<RankBean>>(MouthpieceUrl.base_pk_recored_rank_self , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<RankBean> response, String result) {
                if (response.code==200){
                    rankBeanMySelf=response.data;
                }

                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result1-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    Dialog dialog;
    private void showPKZhongDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_pk_result_jinxingzhong, null);
        ImageView close =view.findViewById(R.id.dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView jinxingzhong = view.findViewById(R.id.dialog_pk_result_pkzhong);
        jinxingzhong.setText("正在与用户"+pkInfoBean.getPkSourceUserNickName()+"PK中");
        TextView date =view.findViewById(R.id.dialog_pk_result_date);
        date.setText(DateUtil.getXiaZhouMonday()+"8:00可查看PK结果");
        dialog.setContentView(view);
        dialog.show();
    }

    //胜利
    private void showPKShengLiDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_pk_result_shengli, null);
        Button button = view.findViewById(R.id.dialog_pk_result_shengli_button);
        TextView username1 = view.findViewById(R.id.dialog_pk_result_shengli_username1);
        TextView jifen1 = view.findViewById(R.id.dialog_pk_result_shengli_jifen1);
        TextView jieguo1 = view.findViewById(R.id.dialog_pk_result_shengli_jieguo1);
        TextView username2 = view.findViewById(R.id.dialog_pk_result_shengli_username2);
        TextView jifen2 = view.findViewById(R.id.dialog_pk_result_shengli_jifen2);
        TextView jieguo2 = view.findViewById(R.id.dialog_pk_result_shengli_jieguo2);
        username1.setText(pkInfoBean.getPkSourceUserNickName()+"");
        username2.setText(pkInfoBean.getPkTargetUserNickName()+"");
        switch (extrasBean.getStatus()){
            case 1://胜利
                button.setText("再来一局");
                jifen1.setText("+4积分");
                jieguo1.setText("获胜奖励");
                jifen2.setText("-4积分");
                jifen2.setText("失败惩罚");
                break;
            case -1://失败
                button.setText("我要变强");
                jifen1.setText("-4积分");
                jieguo1.setText("失败惩罚");
                jifen2.setText("+4积分");
                jifen2.setText("获胜奖励");
                break;
            case 0://平局
                break;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalActivity.this,HealthIntegralTableActivity.class));
            }
        });

        ImageView close =view.findViewById(R.id.dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private ExtrasBean extrasBean ;
    private void getPKUserStauts() {
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----5:" + MouthpieceUrl.base_pk_user + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<ExtrasBean>>(MouthpieceUrl.base_pk_user , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<ExtrasBean> response, String result) {
                if (response.code==200){
                    extrasBean=response.data;
                    if (extrasBean!=null){
                        switch (extrasBean.getStatus()){
                            case -1:
                                activity_persion_pk.setVisibility(View.INVISIBLE);
                                activity_persion_pk.setText("PK失败");
                                if (extrasBean.getPkId()>0){
                                    getPKInfoStauts();
                                }
                                break;
                            case 0:
                                activity_persion_pk.setVisibility(View.INVISIBLE);
                                activity_persion_pk.setText("PK平局");
                                if (extrasBean.getPkId()>0){
                                    getPKInfoStauts();
                                }
                                break;
                            case 1:
                                activity_persion_pk.setVisibility(View.INVISIBLE);
                                activity_persion_pk.setText("PK胜利");
                                if (extrasBean.getPkId()>0){
                                    getPKInfoStauts();
                                }
                                break;
                            case 2:
                                activity_persion_pk.setVisibility(View.INVISIBLE);
                                activity_persion_pk.setText("PK进行中");
                                if (extrasBean.getPkId()>0){
                                    getPKInfoStauts();
                                }
                                break;
                            case 9:

                                activity_persion_pk.setVisibility(View.GONE);
                                activity_persion_pk.setText("无PK");
                                break;
                        }

                    }

                }
                Log.i("xiaopeng-----5","result5-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----5","result5-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private WanNengBean pkInfoBean;
    private void getPKInfoStauts() {
        com.lidroid.xutils.http.RequestParams params = new com.lidroid.xutils.http.RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("pkId", extrasBean.getPkId()+"");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----6:" + MouthpieceUrl.base_pk_info + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_pk_info , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    pkInfoBean=response.data;

                    activity_persion_pk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pkInfoBean!=null){
                                switch (pkInfoBean.getPkStatus()){
                                    case 0:
                                        activity_persion_pk.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        activity_persion_pk.setVisibility(View.VISIBLE);
                                        showPKZhongDialog();
                                        break;
                                    case 2:
                                        activity_persion_pk.setVisibility(View.GONE);
                                        break;
                                    case 3:
                                        activity_persion_pk.setVisibility(View.GONE);
                                        break;
                                    case 4:
                                        activity_persion_pk.setVisibility(View.VISIBLE);
                                        showPKShengLiDialog();
                                        break;
                                }

                            }
//                startActivity(new Intent(PersonalActivity.this,HealthIntegralTableActivity.class));
                        }
                    });
                }
                Log.i("xiaopeng-----6","result6-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----6","result6-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    public WanNengBean reportWeek;
    private void getReportWeek() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("startDate", "2018-07-23");
        params.addQueryStringParameter("endDate","2018-07-29");
//        params.addQueryStringParameter("startDate", DateUtil.getMonday());
//        params.addQueryStringParameter("endDate",DateUtil.getLastWeekSunday());
        Log.i("xiaopeng", "url----1:" + MouthpieceUrl.base_health_report_week + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_health_report_week , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                if (response.code==200){
                    reportWeek=response.data;
                }
                Log.i("xiaopeng-----1","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----1","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    private void getHealthGraph() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("startDate", "2018-07-23");
        params.addQueryStringParameter("endDate","2018-07-29");
        Log.i("xiaopeng", "url----2:" + MouthpieceUrl.base_health_report_graph + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<AleartBean>>>(MouthpieceUrl.base_health_report_graph , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<AleartBean>> response, String result) {
                if (response.code==200){
                }
                Log.i("xiaopeng-----2","result-----"+result);
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

    //监控
    private void init() {
//        getForegroundApp();
    }

//    private List<OptData> getForegroundApp() {
//        List<OptData> optList=new ArrayList<>();
//        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
//            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
//            long ts = System.currentTimeMillis();
//            //第一个参数： 按照时间间隔来查询  第二个：开始时间 第三个：截止时间
//            //通过给定的开始与结束时间  INTERVAL_BEST是按照最合适的时间间隔类型
//            //还可以有：INTERVAL_DAILY  WEEKLY MONTHLY YEARLY
//            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts-30*60*1000, ts);
//            //返回结果中的UsageStats的官方解释是：包含特定时间范围内应用程序包的使用统计资料。
//            if (queryUsageStats == null || queryUsageStats.isEmpty()) {
//                Log.i("xiaopeng", "getForegroundApp: 空"  );
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//                return null;
//            }
//            PackageManager pm = getPackageManager();
//            for (UsageStats usageStats : queryUsageStats) {
//                if (usageStats.getLastTimeUsed()!=0) {
//                    try {
//                        ApplicationInfo info = pm.getApplicationInfo(usageStats.getPackageName(),PackageManager.GET_META_DATA);
//                        Log.i("xiaopeng22222", "应用包名:"+info.loadLabel(pm).toString()  +"--------最后使用时间："+sf.format(new Date(usageStats.getLastTimeUsed()))+"--------最后时间戳："+sf.format(new Date(usageStats.getLastTimeStamp()))+""+"--------第一次打开时间："+"--------使用时间："+usageStats.getTotalTimeInForeground()+"秒");
//                        OptData optData = new OptData();
//                        OptData optData2 = new OptData();
//                        if (usageStats.getTotalTimeInForeground()>0&&usageStats.getFirstTimeStamp()!=usageStats.getLastTimeStamp()){
//                            optData.setOptDetail("打开"+info.loadLabel(pm).toString());
//                            optData.setOptTime(""+sf.format(new Date(usageStats.getLastTimeStamp()-usageStats.getTotalTimeInForeground())));
//                            optData2.setOptDetail("关闭"+info.loadLabel(pm).toString());
//                            optData2.setOptTime(""+sf.format(new Date(usageStats.getLastTimeStamp())));
//                            optList.add(optData);
//                            optList.add(optData2);
//                        }
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//            Gson gson = new Gson();
//            Log.i("xiaopeng-----optlist",gson.toJson(optList));
//            return optList;
//        }else {
//            return null;
//        }
//
//    }

//    private void postData() {
//        Location location = LocationUtils.getInstance(this).showLocation();
//        RequestParams params = new RequestParams(MouthpieceUrl.base_monitor_dataGrab);
//        params.setAsJsonContent(true);
//        params.addHeader("Content-Type", "application/json");
//        params.addHeader("token",PreferenceUtil.getString("token",""));
//        params.addHeader("userId", PreferenceUtil.getString("userId",""));
//        RequestBean requestBean = new RequestBean();
//        requestBean.setUserId(PreferenceUtil.getString("userId",""));
//        requestBean.setDirectionX(1);
//        requestBean.setDirectionY(1);
//        requestBean.setDirectionZ(1);
//        requestBean.setLight(1);
//        requestBean.setWalk(100);
//        requestBean.setSpeed(10);
//        requestBean.setTime(sf.format(new Date(System.currentTimeMillis())));
//        requestBean.setStartTime(sf.format(new Date(System.currentTimeMillis()-1800000)));
//        requestBean.setLatitude(location.getLatitude());
//        requestBean.setLongitude(location.getLongitude());
//        requestBean.setOptList(getForegroundApp());
//        Gson gson = new Gson();
//        params.setBodyContent(gson.toJson(requestBean));
//        Log.i("xiaopeng", "url----bodyContent:" + params.getBodyContent());
//        Log.i("xiaopeng", "url----22:" + MouthpieceUrl.base_monitor_dataGrab + "?" + params.getBodyParams().toString());
//        Log.i("xiaopeng", "url----11:" + MouthpieceUrl.base_monitor_dataGrab + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //解析result
//                Log.i("xiaopeng-----1","result-----"+result);
//            }
//            //请求异常后的回调方法
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.i("xiaopeng-----2","result-----"+ex.toString());
//            }
//            //主动调用取消请求的回调方法
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Log.i("xiaopeng-----3","result-----"+cex);
//            }
//            @Override
//            public void onFinished() {
//                Log.i("xiaopeng-----4","result-----finish");
//            }
//        });
//    }


    private void checkRequiredPermission(final Activity activity){
        for (String permission : permissionsArray) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        mSensroMgr.registerListener(this, mSensroMgr.getDefaultSensor(Sensor.TYPE_LIGHT),
//                SensorManager.SENSOR_DELAY_NORMAL);
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
//            float light_strength = event.values[0];
//
//            strength =  light_strength;
//
////            Log.e("Ziang","光线强度"+"---"+strength);
//        }
//
//        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//            if (event.values[0] == 1.0) {
//                mDetector++;
//            }
//            Log.i("xiaopeng-------", "历史总步数: " + event.values[0] + "步");
//        }
//        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
//            if (event.values[0] == 1.0) {
//                mDetector++;
//
//                PreferenceUtil.putFloat("mDetector", mDetector);
//                PreferenceUtil.putString("month", month);
//                PreferenceUtil.putString("today", today);
//                Log.i("xiaopeng-------", "一共走了" + mDetector + "步");
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        //当传感器精度改变时回调该方法，一般无需处理
//    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensroMgr.unregisterListener(this);
    }


}
