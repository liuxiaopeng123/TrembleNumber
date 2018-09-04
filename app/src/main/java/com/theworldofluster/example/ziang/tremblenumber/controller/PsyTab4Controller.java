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
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class PsyTab4Controller extends TabController {
    View view;
    GridView gridView2 ;
    GridViewAdapter adapter;
    List<WanNengBean> psyBeanList=new ArrayList<>();
    private static final String[] names = {"内向型","别人眼中的你","情感依赖","领袖型气质","高自尊","性情中人","左脑优势","高EQ","成功导向"};
    private static final String[] nums = {"45%","36%","11%","20%","5%","11%","5%","11%","5%"};

    public PsyTab4Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.psytab4_control, null);
        return view;
    }

    @Override
    public void initData() {
        TextView psy_tab4_item1=view.findViewById(R.id.psy_tab4_item1);
        psy_tab4_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, HealthConsultActivity.class));
            }
        });
        gridView2= (GridView) view.findViewById(R.id.psytab1_gv2);
        adapter = new GridViewAdapter();

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        showPKAboutDialog();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                }
            }
        });
        getList("");

    }


    Dialog dialog;
    private void showPKAboutDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_psytab4, null);
        dialog.setContentView(view);
        dialog.show();
    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_analysis + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<WanNengBean>>>(MouthpieceUrl.base_psy_test_analysis , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<WanNengBean>> response, String result) {
                if (response.code==200){
                    psyBeanList=response.data;
                    gridView2.setAdapter(adapter);
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

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return psyBeanList==null?0:psyBeanList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_psytab_gb2, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            tv_name.setText(psyBeanList.get(position).getDescTitle()+"");
            TextView tv_num = (TextView) view
                    .findViewById(R.id.tv_homeitem_num);
            tv_num.setText(psyBeanList.get(position).getNum()+"%");
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
