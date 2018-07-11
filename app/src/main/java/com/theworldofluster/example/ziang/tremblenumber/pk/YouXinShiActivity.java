package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.controller.ConsultTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.ConsultTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.ConsultTab3Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.TabController;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab3Controller;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.util.ArrayList;
import java.util.List;

public class YouXinShiActivity extends Activity {

    @ViewInject(R.id.activity_youxinshi_back)
    RelativeLayout activity_youxinshi_back;
    @ViewInject(R.id.activity_youxinshi_shangchuan)
    RelativeLayout activity_youxinshi_shangchuan;
    @ViewInject(R.id.user_view_pager)
    NoAnimViewpager user_view_pager;
    @ViewInject(R.id.youxinshi_tab_1)
    TextView youxinshi_tab_1;
    @ViewInject(R.id.youxinshi_tab_2)
    TextView youxinshi_tab_2;
    @ViewInject(R.id.youxinshi_tab_3)
    TextView youxinshi_tab_3;

    @ViewInject(R.id.youxinshi_line_1)
    TextView youxinshi_line_1;
    @ViewInject(R.id.youxinshi_line_2)
    TextView youxinshi_line_2;
    @ViewInject(R.id.youxinshi_line_3)
    TextView youxinshi_line_3;

    List<TabController> list;
    public int ChoiceWhere = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_youxinshi);
        ViewUtils.inject(this); //注入view和事件

        init();
    }

    private void init() {

        list = new ArrayList<>();
        list.add(new YouXinShiTab1Controller(this));
        list.add(new YouXinShiTab2Controller(this));
        list.add(new YouXinShiTab3Controller(this));

        user_view_pager.setAdapter(new MessageAdapter());
        user_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        youxinshi_tab_1.setTextColor(Color.BLACK);
                        youxinshi_tab_2.setTextColor(Color.GRAY);
                        youxinshi_tab_3.setTextColor(Color.GRAY);
                        youxinshi_line_1.setBackgroundResource(R.color.colorPrimary);
                        youxinshi_line_2.setBackgroundResource(R.color.white);
                        youxinshi_line_3.setBackgroundResource(R.color.white);
                        ChoiceWhere = 0;
                        break;
                    case 1:
                        youxinshi_tab_1.setTextColor(Color.GRAY);
                        youxinshi_tab_2.setTextColor(Color.BLACK);
                        youxinshi_tab_3.setTextColor(Color.GRAY);
                        youxinshi_line_1.setBackgroundResource(R.color.white);
                        youxinshi_line_2.setBackgroundResource(R.color.colorPrimary);
                        youxinshi_line_3.setBackgroundResource(R.color.white);
                        ChoiceWhere = 1;
                        break;
                    case 2:
                        youxinshi_tab_1.setTextColor(Color.GRAY);
                        youxinshi_tab_2.setTextColor(Color.GRAY);
                        youxinshi_tab_3.setTextColor(Color.BLACK);
                        youxinshi_line_1.setBackgroundResource(R.color.white);
                        youxinshi_line_2.setBackgroundResource(R.color.white);
                        youxinshi_line_3.setBackgroundResource(R.color.colorPrimary);
                        ChoiceWhere = 2;
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.activity_youxinshi_back,R.id.activity_youxinshi_shangchuan,R.id.youxinshi_tab_1,R.id.youxinshi_tab_2,R.id.youxinshi_tab_3})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_youxinshi_back:
                finish();
                break;
            case R.id.activity_youxinshi_shangchuan:
                startActivity(new Intent(YouXinShiActivity.this,EditXinShiActivity.class));
                break;
            case R.id.youxinshi_tab_1:
                youxinshi_tab_1.setTextColor(Color.BLACK);
                youxinshi_tab_2.setTextColor(Color.GRAY);
                youxinshi_tab_3.setTextColor(Color.GRAY);
                youxinshi_line_1.setBackgroundResource(R.color.colorPrimary);
                youxinshi_line_2.setBackgroundResource(R.color.white);
                youxinshi_line_3.setBackgroundResource(R.color.white);
                ChoiceWhere = 0;
                user_view_pager.setCurrentItem(0);
                break;
            case R.id.youxinshi_tab_2:
                youxinshi_tab_1.setTextColor(Color.GRAY);
                youxinshi_tab_2.setTextColor(Color.BLACK);
                youxinshi_tab_3.setTextColor(Color.GRAY);
                youxinshi_line_1.setBackgroundResource(R.color.white);
                youxinshi_line_2.setBackgroundResource(R.color.colorPrimary);
                youxinshi_line_3.setBackgroundResource(R.color.white);
                ChoiceWhere = 1;
                user_view_pager.setCurrentItem(1);
                break;
            case R.id.youxinshi_tab_3:
                youxinshi_tab_1.setTextColor(Color.GRAY);
                youxinshi_tab_2.setTextColor(Color.GRAY);
                youxinshi_tab_3.setTextColor(Color.BLACK);
                youxinshi_line_1.setBackgroundResource(R.color.white);
                youxinshi_line_2.setBackgroundResource(R.color.white);
                youxinshi_line_3.setBackgroundResource(R.color.colorPrimary);
                ChoiceWhere = 2;
                user_view_pager.setCurrentItem(2);
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
}
