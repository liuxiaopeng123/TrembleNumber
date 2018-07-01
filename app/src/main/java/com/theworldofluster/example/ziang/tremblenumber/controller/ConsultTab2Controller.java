package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultListActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemActivity;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class ConsultTab2Controller extends TabController {
    View view;
    GridView gridView1 ;
    GridView gridView2 ;
    private static final String[] names = {"婚姻","两性心理","家庭","心理健康","忧郁","未成年心理","人际关系","职场心理"};
    public ConsultTab2Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.consulttab2_control, null);
        return view;
    }

    @Override
    public void initData() {
        gridView1= (GridView) view.findViewById(R.id.psytab1_gv1);
        gridView1.setAdapter(new GridViewAdapter());
        gridView2= (GridView) view.findViewById(R.id.psytab1_gv2);
        gridView2.setAdapter(new GridView2Adapter());
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HealthConsultListActivity.class);
                intent.putExtra("title",names[position]);
                mContext.startActivity(intent);
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HealthConsultListActivity.class);
                intent.putExtra("title",names[position]);
                mContext.startActivity(intent);
            }
        });
        getList("");

    }

    //获取列表
    public void getList(String name) {
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return 8;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_health_consult, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.item_consult_name);
            tv_name.setText(names[position]);
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

    private class GridView2Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return 8;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_health_consult, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.item_consult_name);
            tv_name.setText(names[position]);
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
