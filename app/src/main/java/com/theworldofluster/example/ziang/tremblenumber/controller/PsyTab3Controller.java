package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.theworldofluster.example.ziang.tremblenumber.R;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class PsyTab3Controller extends TabController {
    View view;
    ListView psytab3_lv;

    MyAdapter adapter = new MyAdapter();
    public PsyTab3Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.psytab3_control, null);
        return view;
    }

    @Override
    public void initData() {
        psytab3_lv=view.findViewById(R.id.psytab3_lv);
        psytab3_lv.setAdapter(adapter);
        getList("");

    }

    //获取列表
    public void getList(String name) {
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_psytab3, null);
            }
            return convertView;
        }
    }
}
