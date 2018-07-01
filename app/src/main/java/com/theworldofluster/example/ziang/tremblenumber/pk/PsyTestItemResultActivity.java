package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

public class PsyTestItemResultActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_result_back)
    RelativeLayout activity_psy_test_item_result_back;
    @ViewInject(R.id.activity_psy_test_item_result_title)
    TextView activity_psy_test_item_result_title;
    @ViewInject(R.id.activity_psy_test_item_result_btn)
    Button activity_psy_test_item_result_btn;
    @ViewInject(R.id.activity_psy_test_item_result_other)
    TextView activity_psy_test_item_result_other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psy_test_item_result);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        activity_psy_test_item_result_title.setText(getIntent().getStringExtra("title"));
    }

    @OnClick({R.id.activity_psy_test_item_result_back,R.id.activity_psy_test_item_result_btn,R.id.activity_psy_test_item_result_other})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_psy_test_item_result_back:
                finish();
                break;
            case R.id.activity_psy_test_item_result_btn:
                finish();
                break;
            case R.id.activity_psy_test_item_result_other:
                startActivity(new Intent(PsyTestItemResultActivity.this,PsyTestItemOtherResultActivity.class));
                break;
        }
    }


}
