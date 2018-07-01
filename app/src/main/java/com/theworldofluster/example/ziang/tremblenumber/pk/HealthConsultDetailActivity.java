package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

public class HealthConsultDetailActivity extends Activity {
    @ViewInject(R.id.activity_health_consult_detail_back)
    RelativeLayout activity_health_consult_detail_back;
    @ViewInject(R.id.activity_health_consult_detail_title)
    TextView activity_health_consult_detail_title;
    @ViewInject(R.id.health_consult_like)
    RelativeLayout health_consult_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consult_detail);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
    }

    @OnClick({R.id.activity_health_consult_detail_back})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_health_consult_detail_back:
                finish();
                break;
        }
    }

}
