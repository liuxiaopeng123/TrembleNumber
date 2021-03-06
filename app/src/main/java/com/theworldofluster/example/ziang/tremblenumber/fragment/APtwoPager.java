package com.theworldofluster.example.ziang.tremblenumber.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.activity.CalendarActivity;
import com.theworldofluster.example.ziang.tremblenumber.activity.FoldlinediagramActivity;
import com.theworldofluster.example.ziang.tremblenumber.personal.PersonalActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import org.w3c.dom.Text;


/**
 * Created by Ziang on 2018/3/29.
 */

public class APtwoPager extends Fragment implements View.OnClickListener {

    View view;

    TextView pager_p_two_go,pager_xinlifenxi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){

            view = inflater.inflate(R.layout.pager_p_two, container, false);
            initData();

        }


        return view;
    }

    private void initData() {
        pager_p_two_go = view.findViewById(R.id.pager_p_two_go);
        pager_xinlifenxi=view.findViewById(R.id.pager_xinlifenxi);


        pager_p_two_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.pager_p_two_go:

                startActivity(new Intent(getActivity(),CalendarActivity.class));

                break;

        }
    }

    public void update(){
        if (pager_xinlifenxi!=null){
            pager_xinlifenxi.setText(PreferenceUtil.getString("xinlifenxi","暂无心理分析~"));
        }
        Log.i("xiaopeng----resume","显示心理分析");
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
