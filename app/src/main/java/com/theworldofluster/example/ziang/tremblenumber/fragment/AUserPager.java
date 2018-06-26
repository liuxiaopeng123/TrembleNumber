package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.theworldofluster.example.ziang.tremblenumber.R;


/**
 * Created by Ziang on 2018/3/29.
 */

public class AUserPager extends Fragment implements View.OnClickListener {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_agmentuser, container, false);
            initData();

        }


        return view;
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
