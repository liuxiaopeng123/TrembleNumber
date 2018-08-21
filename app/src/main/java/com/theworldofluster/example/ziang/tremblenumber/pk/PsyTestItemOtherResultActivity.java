package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyResultBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.List;

public class PsyTestItemOtherResultActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_back)
    RelativeLayout activity_psy_test_item_back;
    @ViewInject(R.id.activity_psy_test_item_title)
    TextView activity_psy_test_item_title;
    @ViewInject(R.id.activity_psy_test_item_lv)
    ListView activity_psy_test_item_lv;

    List<PsyResultBean> psyResultBeanList=null;
    MyAdapter adapter = new MyAdapter();
    String setCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psy_test_item_other_result);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        setCode=getIntent().getStringExtra("setCode");
        getList();

        activity_psy_test_item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPKAboutDialog(position);
            }
        });
    }

    private void getList() {
        RequestParams params = new RequestParams();
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addQueryStringParameter("setCode", setCode);
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_more_result + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<PsyResultBean>>>(MouthpieceUrl.base_psy_test_more_result , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<PsyResultBean>> response, String result) {
                if (response.code==200){
                    psyResultBeanList=response.data;
                    activity_psy_test_item_lv.setAdapter(adapter);

                }
                Log.i("xiaopeng-----","--result-----"+result);

            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","--result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }

    Dialog dialog;
    private void showPKAboutDialog(final int position) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_psy_test_result, null);
        TextView title = view.findViewById(R.id.dialog_psy_test_result_title);
        TextView content = view.findViewById(R.id.dialog_psy_test_result_content);
        title.setText(psyResultBeanList.get(position).getDescTitle());
        content.setText(psyResultBeanList.get(position).getDescContext());
        ImageView cancle = view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView confirm = view.findViewById(R.id.dialog_confirm);
        confirm.setVisibility(View.INVISIBLE);
        dialog.setContentView(view);
        dialog.show();
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
            return psyResultBeanList==null?0:psyResultBeanList.size();
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_other_consult, null);
            }
            TextView title = convertView.findViewById(R.id.item_other_result_title);
            TextView content = convertView.findViewById(R.id.item_other_result_content);
            title.setText(psyResultBeanList.get(position).getDescTitle());
            content.setText(psyResultBeanList.get(position).getDescContext());
            return convertView;
        }
    }
}
