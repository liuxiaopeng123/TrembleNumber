package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

public class PsyTestItemResultActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_result_back)
    RelativeLayout activity_psy_test_item_result_back;
    @ViewInject(R.id.activity_psy_test_result_title)
    TextView activity_psy_test_result_title;
    @ViewInject(R.id.activity_psy_test_result_content)
    TextView activity_psy_test_result_content;
    @ViewInject(R.id.activity_psy_test_result_finalcode)
    TextView activity_psy_test_result_finalcode;
    @ViewInject(R.id.activity_psy_test_result_percent)
    TextView activity_psy_test_result_percent;
    @ViewInject(R.id.activity_psy_test_item_result_btn)
    Button activity_psy_test_item_result_btn;
    @ViewInject(R.id.activity_psy_test_item_result_other)
    TextView activity_psy_test_item_result_other;

    String descContext,descTitle,finalScore,percent,setCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psy_test_item_result);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        descContext=getIntent().getStringExtra("descContext");
        descTitle=getIntent().getStringExtra("descTitle");
        finalScore=getIntent().getStringExtra("finalScore");
        percent=getIntent().getStringExtra("percent");
        setCode=getIntent().getStringExtra("setCode");

        activity_psy_test_result_title.setText(descTitle);
        activity_psy_test_result_content.setText("最终测试得分:"+finalScore);
        activity_psy_test_result_content.setText(descContext);
        activity_psy_test_result_finalcode.setText("最终测试得分:"+finalScore);
        activity_psy_test_result_percent.setText("您的测试结果排名位于"+percent+"%左右");
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
                Intent intent = new Intent(PsyTestItemResultActivity.this,PsyTestItemOtherResultActivity.class);
                intent.putExtra("setCode",setCode);
                startActivity(intent);
                break;
        }
    }

}
