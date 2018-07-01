package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.theworldofluster.example.ziang.tremblenumber.R;


/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class ProductsController extends TabController {
    View view;
    ListView fragmentMessageTab;
    MyAdapter adapter = new MyAdapter();
    public String et_text="";
    public ProductsController(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.products_control, null);
        return view;
    }

    @Override
    public void initData() {
        fragmentMessageTab=view.findViewById(R.id.fragment_product_tab);
        getProductList("");
    }

    public void getProductList(String name) {
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
                convertView = View.inflate(mContext, R.layout.item_product, null);
            }

            return convertView;
        }
    }
}
