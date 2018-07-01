package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.theworldofluster.example.ziang.tremblenumber.R;

/**
 * @date 2016/12/21
 */

public abstract class TabController {
    protected View rootView;
    protected Context mContext;

    protected FrameLayout contentFragment;

    protected TabController(Context context) {
        this.mContext = context;
        rootView = initView(context);
    }

    //初始化视图
    protected View initView(Context context) {
        View view = View.inflate(mContext, R.layout.base_tab, null);
        contentFragment = (FrameLayout) view.findViewById(R.id.tab_container_content);
        contentFragment.addView(initContentView(context));
        return view;
    }

    //不同的内容的方法
    protected abstract View initContentView(Context context);

    public View getRootView() {
        return rootView;
    }

    public Context getContext() {
        return mContext;
    }

    //初始化数据
    public void initData() {

    }

}
