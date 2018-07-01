package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemActivity;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class PsyTab1Controller extends TabController {
    View view;
    GridView gridView1 ;
    GridView gridView2 ;
    private static final String[] names = {"情绪","人格性格","家庭","亲密关系","婚姻","成长"};
    private static final int[] icons = {R.mipmap.zhuanye1,R.mipmap.zhuanye2,R.mipmap.zhuanye3,R.mipmap.zhuanye4,R.mipmap.zhuanye5,R.mipmap.zhuanye6};
    private static final int[] icons2 = {R.mipmap.yule1,R.mipmap.yule2,R.mipmap.yule3,R.mipmap.yule4,R.mipmap.yule5,R.mipmap.yule6};
    public PsyTab1Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.psytab1_control, null);
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
                Intent intent = new Intent(mContext, PsyTestItemActivity.class);
                intent.putExtra("title",names[position]);
                mContext.startActivity(intent);
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PsyTestItemActivity.class);
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
            return 2;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_fragment_gb, null);
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

    private class GridView2Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return 2;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_fragment_gb, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            ImageView iv_icon = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            tv_name.setText(names[position]);
            iv_icon.setImageResource(icons2[position]);
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
