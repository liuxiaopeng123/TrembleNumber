package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestTiJi;
import com.theworldofluster.example.ziang.tremblenumber.bean.Question;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PsyTestItemDetailActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_detail_back)
    RelativeLayout activity_psy_test_item_detail_back;

    @ViewInject(R.id.activity_psy_test_item_detail_title)
    TextView activity_psy_test_item_detail_title;
    @ViewInject(R.id.activity_psy_test_item_detail_btn)
    TextView activity_psy_test_item_detail_btn;
    @ViewInject(R.id.activity_psy_test_question_title)
    TextView activity_psy_test_question_title;
    @ViewInject(R.id.activity_question_lv)
    ListView activity_question_lv;
    String setCode="",title="测试题",optCodes="",beginDate="";

    int index=0;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    List<Question> questionList = null;
    MyAdapter adapter =new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psy_test_item_detail);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        title= getIntent().getStringExtra("title");
        activity_psy_test_item_detail_title.setText(title);
        setCode= getIntent().getStringExtra("setCode");
        beginDate=sf.format(new Date(System.currentTimeMillis()));
        activity_question_lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RadioButton radioButton = view.findViewById(R.id.item_question_check_box);
                radioButton.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        activity_question_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioButton radioButton = view.findViewById(R.id.item_question_check_box);
                radioButton.setChecked(true);
            }
        });
        getList();
    }
    private void getList() {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("setCode", setCode);
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_question_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<Question>>>(MouthpieceUrl.base_psy_test_question_list , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<Question>> response, String result) {
                if (response.code==200){
                    questionList=response.data;
                    activity_question_lv.setAdapter(adapter);
                    for (int i=0;i<questionList.size();i++){
                        if (i==(questionList.size()-1)){
                            optCodes+=questionList.get(i).getOptionList().get(0).getOptionCode();
                        }else {
                            optCodes+=questionList.get(i).getOptionList().get(0).getOptionCode()+",";
                        }
                        Log.i("xiaopeng-----2",questionList.get(i).getOptionList().get(0).getOptionCode()+"");
                    }
                    initQuestion();
                }
                Log.i("xiaopeng-----",questionList.size()+"---"+beginDate+optCodes+"--result-----"+result);

            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    //更新题目页面
    private void initQuestion() {
        activity_psy_test_question_title.setText(questionList.get(index).getQuestionTitle());
        status = new ArrayList<>();
        status.add(false);
        status.add(false);
        status.add(false);
        status.add(false);
        status.add(false);
        status.add(false);
        adapter.notifyDataSetChanged();
    }

    //提交测试
    private void commitTest() {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("beginDate",beginDate);
        params.addQueryStringParameter("setCode", setCode);
        params.addQueryStringParameter("optCodes", optCodes);
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_result + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<Question>>>(MouthpieceUrl.base_psy_test_result , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<Question>> response, String result) {
                Log.i("xiaopeng-----",optCodes+"--result-----"+result);
                if (response.code==200){
                    startActivity(new Intent(PsyTestItemDetailActivity.this,PsyTestItemResultActivity.class));
                }
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    @OnClick({R.id.activity_psy_test_item_detail_back,R.id.activity_psy_test_item_detail_btn})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_psy_test_item_detail_back:
                finish();
                break;
            case R.id.activity_psy_test_item_detail_btn:
                if (index==(questionList.size())){
                    commitTest();
                }else if (index==(questionList.size()-1)){
                    index++;
                    activity_psy_test_item_detail_btn.setText("完成");
                }else {
                    index++;
                    initQuestion();
                }
                break;
        }
    }


    List<Boolean> status;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return questionList.get(index).getOptionList().size();
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_psy_question2, null);
            }
            final RadioButton radioButton = convertView.findViewById(R.id.item_question_check_box);
            TextView options = convertView.findViewById(R.id.item_question_select);
            options.setText(questionList.get(index).getOptionList().get(position).getOptionTitle());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<questionList.get(index).getOptionList().size();i++){
                        status.set(i,false);
                    }
                    status.set(position, true);
                    notifyDataSetChanged();
                }
            });

            if (status.get((Integer) position) == null || status.get((Integer) position) == false) {
                radioButton.setChecked(false);
            } else {
                radioButton.setChecked(true);
            }

            return convertView;
        }
    }

}
