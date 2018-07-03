package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;

public class HealthSamePersonActivity extends Activity {
    @ViewInject(R.id.activity_health_alert_same_person_back)
    RelativeLayout activity_health_alert_same_person_back;
    @ViewInject(R.id.activity_health_alert_same_person_title)
    TextView activity_health_alert_same_person_title;
    @ViewInject(R.id.activity_health_alert_same_person_lv)
    ListView activity_health_alert_same_person_lv;

    MyAdapter adapter = new MyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_alert_same_person);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        activity_health_alert_same_person_lv.setAdapter(adapter);

        activity_health_alert_same_person_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    @OnClick({R.id.activity_health_alert_same_person_back})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_health_alert_same_person_back:
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_same_person_activity, null);
            }
            return convertView;
        }
    }
}
