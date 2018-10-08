package com.theworldofluster.example.ziang.tremblenumber;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.login.LoginActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎界面导航页
 */
public class SplashActivity extends Activity {

    List<ImageView> imageViews = new ArrayList<>();
    @ViewInject(R.id.activity_splash_start_bt)
    Button activitySplashStartBt;
    @ViewInject(R.id.activity_splash_tiaoguo)
    TextView activity_splash_tiaoguo;

    private ViewPager welcomViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ViewUtils.inject(this); //注入view和事件


        welcomViewPager = (ViewPager) findViewById(R.id.viewpager_splash_activity);
        initData();
        welcomViewPager.setAdapter(new MyViewPagerAdapter());
        welcomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        activitySplashStartBt.setVisibility(View.GONE);
                        break;
                    case 1:
                        activitySplashStartBt.setVisibility(View.GONE);
                        break;
                    case 2:
                        activitySplashStartBt.setVisibility(View.GONE);
                        break;
                    case 3:
                        activitySplashStartBt.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    /**
     * 初始化导航页图面资源
     */
    private void initData() {
        ImageView iv1 = new ImageView(this);
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv1.setImageResource(R.drawable.splash_img1);
        imageViews.add(iv1);
        ImageView iv2 = new ImageView(this);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setImageResource(R.drawable.splash_img2);
        imageViews.add(iv2);
        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setImageResource(R.drawable.splash_img3);
        imageViews.add(iv3);
        ImageView iv4 = new ImageView(this);
        iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv4.setImageResource(R.drawable.splash_img4);
        imageViews.add(iv4);
    }

    @OnClick({R.id.activity_splash_start_bt,R.id.activity_splash_tiaoguo})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.activity_splash_start_bt:
                PreferenceUtil.putBool("userIsFirstLogin",true);
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                finish();
                break;
            case R.id.activity_splash_tiaoguo:
                PreferenceUtil.putBool("userIsFirstLogin",true);
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                finish();
                break;
        }

    }


    public class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
