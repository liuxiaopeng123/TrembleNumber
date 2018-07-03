package com.theworldofluster.example.ziang.tremblenumber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthAlertActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthIntegralTableActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsychologicalTestActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

public class MainActivity extends Activity {

    @ViewInject(R.id.main_gv)
    GridView main_gv;

    @ViewInject(R.id.main_jiankangtixing)
    LinearLayout main_jiankangtixing;
    @ViewInject(R.id.main_jiankangbaogao)
    LinearLayout main_jiankangbaogao;
    @ViewInject(R.id.main_jiankangzixun)
    LinearLayout main_jiankangzixun;
    @ViewInject(R.id.main_xinqingriji)
    LinearLayout main_xinqingriji;
    @ViewInject(R.id.main_xinliceshi)
    LinearLayout main_xinliceshi;
    @ViewInject(R.id.main_jiankangfenpk)
    LinearLayout main_jiankangfenpk;

    private static final String[] names = {"健康提醒","健康报告","心情日记","健康PK"};
    private static final int[] icons = {R.mipmap.main_icon_tixing,R.mipmap.main_icon_baogao,R.mipmap.main_icon_riji,R.mipmap.main_icon_pk};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this); //注入view和事件
        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#ffffff"));

        if(PreferenceUtil.getString("isLogin","").equals("")){
            startActivity(new Intent(this, LoginActivity.class));
        }
//        else{
//            startActivity(new Intent(this, PersonalActivity.class));
//        }

        Log.e("Ziang",PreferenceUtil.getString("userId",""));

        initData();
    }

    public void initData() {
        main_gv.setAdapter(new GridViewAdapter());
        getList("");

    }

    @OnClick({R.id.main_jiankangtixing,R.id.main_jiankangbaogao,R.id.main_jiankangzixun,R.id.main_xinqingriji,R.id.main_xinliceshi,R.id.main_jiankangfenpk})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.main_jiankangtixing:
                startActivity(new Intent(MainActivity.this, HealthAlertActivity.class));
                break;
            case R.id.main_jiankangbaogao:
                startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                break;
            case R.id.main_jiankangzixun:
                startActivity(new Intent(MainActivity.this, HealthConsultActivity.class));
                break;
            case R.id.main_xinqingriji:
                ToastUtil.showContent(getApplicationContext(),"心情日记");
                break;
            case R.id.main_xinliceshi:
                startActivity(new Intent(MainActivity.this, PsychologicalTestActivity.class));
                break;
            case R.id.main_jiankangfenpk:
                startActivity(new Intent(MainActivity.this, HealthIntegralTableActivity.class));
                break;
            default:
                break;
        }
    }

    //获取列表
    public void getList(String name) {
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return 4;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),
                    R.layout.item_main_gv, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            ImageView iv_icon = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            tv_name.setText(names[position]);
            iv_icon.setImageResource(icons[position]);
            return view;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}
