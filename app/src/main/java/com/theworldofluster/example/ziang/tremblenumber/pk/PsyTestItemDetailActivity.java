package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
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
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyResultBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.Question;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

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

    int index=0,checked_position=-1;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    List<Question> questionList = null;
    MyAdapter adapter =new MyAdapter();
    boolean flag_yijingqueren=false;

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

        getList();
    }
    private void getList() {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("setCode", setCode);
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_question_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<Question>>>(MouthpieceUrl.base_psy_test_question_list , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<Question>> response, String result) {
                if (response.code==200){
                    questionList=response.data;
                    activity_question_lv.setAdapter(adapter);
//                    for (int i=0;i<questionList.size();i++){
//                        if (i==(questionList.size()-1)){
//                            optCodes+=questionList.get(i).getOptionList().get(0).getOptionCode();
//                        }else {
//                            optCodes+=questionList.get(i).getOptionList().get(0).getOptionCode()+",";
//                        }
//                        Log.i("xiaopeng-----2",questionList.get(i).getOptionList().get(0).getOptionCode()+"");
//                    }
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
        checked_position=-1;
        adapter.notifyDataSetChanged();
    }

    //提交测试
    private void commitTest() {
        RequestParams params = new RequestParams();
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("beginDate",beginDate);
        params.addQueryStringParameter("setCode", setCode);
        params.addQueryStringParameter("optCodes", optCodes.substring(0,optCodes.length()-1));
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_result + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<PsyResultBean>>(MouthpieceUrl.base_psy_test_result , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<PsyResultBean> response, String result) {
                Log.i("xiaopeng-----",optCodes+"--result-----"+result);
                if (response.code==200){
                    Intent intent= new Intent(PsyTestItemDetailActivity.this,PsyTestItemResultActivity.class);
                    intent.putExtra("descContext",response.data.getDescContext());
                    intent.putExtra("descTitle",response.data.getDescTitle());
                    intent.putExtra("finalScore",response.data.getFinalScore());
                    intent.putExtra("percent",response.data.getPercent());
                    intent.putExtra("setCode",response.data.getSetCode());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----",optCodes+"--result-----"+result);
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
                showConfirmDialog();
                break;
            case R.id.activity_psy_test_item_detail_btn:
//                if (flag_yijingqueren){
//
//                }
                if (index!=(questionList.size())){
                    for (int i=0;i<questionList.get(index).getOptionList().size();i++){
                        if (status.get(i)==true){
                            checked_position=i;
                        }
                    }
                    if (checked_position==-1){
                        ToastUtil.showContent(getApplicationContext(),"请选择一个你认为最合理的选项~");
                        return;
                    }else {
                        optCodes+=questionList.get(index).getOptionList().get(checked_position).getOptionCode()+",";
                    }
                }

                Log.i("xiaopeng-----",optCodes);

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


    private void showDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_ceshiti_queren, null);
        TextView cancle = view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_yijingqueren=false;
                dialog.dismiss();
            }
        });
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_yijingqueren=true;
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


    List<Boolean> status;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (questionList.get(index)==null){
                return 0;
            }else {
                return questionList==null?0:questionList.get(index).getOptionList().size();
            }


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
                    if (index==questionList.size()){
                        ToastUtil.showContent(getApplicationContext(),"已做完，请提交~");
                        return;
                    }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showConfirmDialog();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    Dialog dialog;
    private void showConfirmDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk, null);
        TextView content = view.findViewById(R.id.content);
        content.setText("确定结束答题？");
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        TextView cancle = view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

}
