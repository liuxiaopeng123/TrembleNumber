package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyResultBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemDetailActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class PsyTab3Controller extends TabController {
    View view;
    ListView psytab3_lv;

    List<PsyResultBean> psyResultBeanList = null;

    MyAdapter adapter = new MyAdapter();
    public PsyTab3Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.psytab3_control, null);
        return view;
    }

    @Override
    public void initData() {
        psytab3_lv=view.findViewById(R.id.psytab3_lv);

        psytab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPKAboutDialog(position);
            }
        });

        getList("");

    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_completed + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<PsyResultBean>>>(MouthpieceUrl.base_psy_test_completed , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<PsyResultBean>> response, String result) {
                if (response.code==200){
                    psyResultBeanList =response.data;
                    psytab3_lv.setAdapter(adapter);
                }
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
            }
        };
    }


    Dialog dialog;
    private void showPKAboutDialog(final int position) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_psy_test_result, null);
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PsyTestItemDetailActivity.class);
                intent.putExtra("title",psyResultBeanList.get(position).getSetTitle()+"");
                intent.putExtra("setCode",psyResultBeanList.get(position).getSetCode()+"");
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
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
                convertView = View.inflate(mContext, R.layout.item_psytab3, null);
            }
            ImageView imageView =convertView.findViewById(R.id.item_psytab3_img);
            if (position%2==0){
                imageView.setBackgroundResource(R.mipmap.psytest_bg1);
            }else {
                imageView.setBackgroundResource(R.mipmap.psytest_bg2);
            }
            TextView textView = convertView.findViewById(R.id.item_psy_test_name);
            textView.setText(psyResultBeanList.get(position).getSetTitle());
            return convertView;
        }
    }
}
