package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.HealthConsultBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultListActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class ConsultTab2Controller extends TabController {
    View view;
    GridView gridView1 ;
    GridView gridView2 ;

    TextView consult_cate_name1;
    TextView consult_cate_name2;
    List<HealthConsultBean> healthConsultBeanList=null;
    public ConsultTab2Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.consulttab2_control, null);
        return view;
    }

    @Override
    public void initData() {
        consult_cate_name1 =view.findViewById(R.id.consult_cate_name1);
        consult_cate_name2 =view.findViewById(R.id.consult_cate_name2);
        gridView1= view.findViewById(R.id.psytab1_gv1);
        gridView2=  view.findViewById(R.id.psytab1_gv2);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HealthConsultListActivity.class);
                intent.putExtra("title",healthConsultBeanList.get(0).getSecondCategoryList().get(position).getCategoryName());
                intent.putExtra("cateCode",healthConsultBeanList.get(0).getSecondCategoryList().get(position).getCategoryCode()+"");
                mContext.startActivity(intent);
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HealthConsultListActivity.class);
                intent.putExtra("title",healthConsultBeanList.get(1).getSecondCategoryList().get(position).getCategoryName());
                intent.putExtra("cateCode",healthConsultBeanList.get(1).getSecondCategoryList().get(position).getCategoryCode()+"");
                mContext.startActivity(intent);
            }
        });
        getList("");

    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token", PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_consult_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<HealthConsultBean>>>(MouthpieceUrl.base_health_consult_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<HealthConsultBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                healthConsultBeanList=response.data;
                if (response.code==200){
                    gridView1.setAdapter(new GridViewAdapter());
                    gridView2.setAdapter(new GridView2Adapter());
                    consult_cate_name1.setText(healthConsultBeanList.get(0).getCategoryName());
                    consult_cate_name2.setText(healthConsultBeanList.get(1).getCategoryName());
                }
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

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return healthConsultBeanList==null?0:healthConsultBeanList.get(0).getSecondCategoryList().size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_health_consult, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.item_consult_name);
            tv_name.setText(healthConsultBeanList.get(0).getSecondCategoryList().get(position).getCategoryName());
            return view;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

    private class GridView2Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return healthConsultBeanList==null?0:healthConsultBeanList.get(1).getSecondCategoryList().size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_health_consult, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.item_consult_name);
            tv_name.setText(healthConsultBeanList.get(1).getSecondCategoryList().get(position).getCategoryName());
            return view;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}
