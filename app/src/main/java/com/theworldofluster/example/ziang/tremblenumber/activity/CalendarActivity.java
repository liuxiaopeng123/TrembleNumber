package com.theworldofluster.example.ziang.tremblenumber.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.controller.CalendarTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.CalendarTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.TabController;
import com.theworldofluster.example.ziang.tremblenumber.pk.YouXinShiActivity;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    List<TabController> list;
    public int ChoiceWhere = 0;

    @ViewInject(R.id.calendar_view_pager)
    NoAnimViewpager calendar_view_pager;
    @ViewInject(R.id.activity_calendar_today)
    TextView activity_calendar_today;
    @ViewInject(R.id.activity_calendar_month)
    TextView activity_calendar_month;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calendar);
        ViewUtils.inject(this); //注入view和事件


        Window window = getWindow();
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
//        window.setStatusBarColor(Color.parseColor("#28d3bd"));
        init();
    }


    private void init() {
        list = new ArrayList<>();
        list.add(new CalendarTab1Controller(this));
        list.add(new CalendarTab2Controller(this));

        calendar_view_pager.setAdapter(new MessageAdapter());
        calendar_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        activity_calendar_today.setTextColor(Color.BLACK);
                        activity_calendar_month.setTextColor(Color.WHITE);
                        activity_calendar_today.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                        activity_calendar_month.setBackgroundResource(R.drawable.button_shape_half_white_cricle_stoke);
                        ChoiceWhere = 0;
                        break;
                    case 1:
                        activity_calendar_today.setTextColor(Color.WHITE);
                        activity_calendar_month.setTextColor(Color.BLACK);
                        activity_calendar_today.setBackgroundResource(R.drawable.button_shape_half_white_cricle_stoke);
                        activity_calendar_month.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                        ChoiceWhere = 1;
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.activity_calendar_today,R.id.activity_calendar_month,R.id.activity_calendar_back,R.id.activity_calendar_xinshi})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_calendar_xinshi:
                startActivity(new Intent(CalendarActivity.this, YouXinShiActivity.class));
                break;
            case R.id.activity_calendar_back:
                finish();
                break;
            case R.id.activity_calendar_today:
                activity_calendar_today.setTextColor(Color.BLACK);
                activity_calendar_month.setTextColor(Color.WHITE);
                activity_calendar_today.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                activity_calendar_month.setBackgroundResource(R.drawable.button_shape_half_white_cricle_stoke);
                ChoiceWhere = 0;
                calendar_view_pager.setCurrentItem(0);
                break;
            case R.id.activity_calendar_month:
                activity_calendar_today.setTextColor(Color.WHITE);
                activity_calendar_month.setTextColor(Color.BLACK);
                activity_calendar_today.setBackgroundResource(R.drawable.button_shape_half_white_cricle_stoke);
                activity_calendar_month.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                ChoiceWhere = 1;
                calendar_view_pager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }



    class MessageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabController controller = list.get(position);
            // 获得数据
            View view = controller.getRootView();
            container.addView(view);
            // 加载数据
            controller.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            CalendarTab1Controller controller = (CalendarTab1Controller) list.get(0);
//            return false;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
