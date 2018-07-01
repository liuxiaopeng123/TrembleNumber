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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

public class HealthConsultListActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_back)
    RelativeLayout activity_psy_test_item_back;
    @ViewInject(R.id.activity_psy_test_item_title)
    TextView activity_psy_test_item_title;
    @ViewInject(R.id.activity_psy_test_item_lv)
    ListView activity_psy_test_item_lv;

    MyAdapter adapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consult_list);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        activity_psy_test_item_title.setText(getIntent().getStringExtra("title"));
        activity_psy_test_item_lv.setAdapter(adapter);

        activity_psy_test_item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(HealthConsultListActivity.this,HealthConsultDetailActivity.class));
            }
        });
    }


    @OnClick({R.id.activity_psy_test_item_back})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_psy_test_item_back:
                finish();
                break;
        }
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_consult_tab3, null);
            }
            return convertView;
        }
    }
}