package com.theworldofluster.example.ziang.tremblenumber.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.adapter.MyFragmentPagerAdapter;
import com.theworldofluster.example.ziang.tremblenumber.fragment.AMainPager;
import com.theworldofluster.example.ziang.tremblenumber.fragment.AUserPager;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.view.CounterRadioButton;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentsList;

    @ViewInject(R.id.vp_content_fragment)
    public NoAnimViewpager vp_content_fragment;

    @ViewInject(R.id.rg_content_fragment)
    public RadioGroup rg_content_fragment;

    @ViewInject(R.id.rb_main_information)
    public CounterRadioButton rb_main_information;

    public int screenHeight ;

    public int screenWidth ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my);
        ViewUtils.inject(this); //注入view和事件

        fragmentsList = new ArrayList<Fragment>();

        Fragment fragment1 = new AMainPager();
        Fragment fragment2 = new AUserPager();

        fragmentsList.add(fragment1);
        fragmentsList.add(fragment2);

        rg_content_fragment.check(R.id.rb_bottom_agmenthome);

        //添加radioGroup的点击监听
        rg_content_fragment.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        vp_content_fragment.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        vp_content_fragment.setNoScroll(true);

        vp_content_fragment.setCurrentItem(0);

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

        initWindow();
    }

    private void initWindow() {

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;

        //窗口高度
        screenHeight = dm.heightPixels;

        rb_main_information.setValue(3);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_bottom_agmenthome://主页

                    vp_content_fragment.setCurrentItem(0);

                    break;
                case R.id.rb_main_information://个人

                    vp_content_fragment.setCurrentItem(1);

                    break;

                default:
                    break;
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
