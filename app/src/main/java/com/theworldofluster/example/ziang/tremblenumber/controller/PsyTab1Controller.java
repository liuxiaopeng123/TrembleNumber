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
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.PsyTestItemActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class PsyTab1Controller extends TabController {
    View view;
    GridView gridView1 ;
    GridView gridView2 ;

    List<PsyTestBean> psyTestBeanList=null;
    private static final String[] names = {"情绪","人格性格","家庭","亲密关系","婚姻","成长"};
    private static final int[] icons = {R.mipmap.zhuanye1,R.mipmap.zhuanye2,R.mipmap.zhuanye3,R.mipmap.zhuanye7,R.mipmap.zhuanye4,R.mipmap.zhuanye5,R.mipmap.zhuanye6};
    private static final int[] icons2 = {R.mipmap.yule1,R.mipmap.yule2,R.mipmap.yule3,R.mipmap.yule7,R.mipmap.yule4,R.mipmap.yule5,R.mipmap.yule6};
    public PsyTab1Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.psytab1_control, null);
        return view;
    }

    @Override
    public void initData() {
        gridView1= (GridView) view.findViewById(R.id.psytab1_gv1);
        gridView2= (GridView) view.findViewById(R.id.psytab1_gv2);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PsyTestItemActivity.class);
                intent.putExtra("title",psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryName());
                intent.putExtra("cateCode",psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryCode());
                mContext.startActivity(intent);
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PsyTestItemActivity.class);
                intent.putExtra("title",psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryName());
                intent.putExtra("cateCode",psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryCode());
                mContext.startActivity(intent);
            }
        });
        getList("");

    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_psy_test_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<PsyTestBean>>>(MouthpieceUrl.base_psy_test_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<PsyTestBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    psyTestBeanList=response.data;
                    gridView1.setAdapter(new GridViewAdapter());
                    gridView2.setAdapter(new GridView2Adapter());
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

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            return psyTestBeanList.get(0).getSecondCategoryList().size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_fragment_gb, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            ImageView iv_icon = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            tv_name.setText(psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryName());
            iv_icon.setImageResource(icons[position]);
//            Utils.BJSloadImg(mContext,MouthpieceUrl.base_loading_img+psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryPic(),iv_icon);
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
            return psyTestBeanList.get(1).getSecondCategoryList().size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext,
                    R.layout.item_fragment_gb, null);
            TextView tv_name = (TextView) view
                    .findViewById(R.id.tv_homeitem_name);
            ImageView iv_icon = (ImageView) view
                    .findViewById(R.id.iv_homeitem_icon);
            tv_name.setText(psyTestBeanList.get(1).getSecondCategoryList().get(position).getCategoryName());
            iv_icon.setImageResource(icons2[position]);
//            Utils.BJSloadImg(mContext,MouthpieceUrl.base_url+psyTestBeanList.get(0).getSecondCategoryList().get(position).getCategoryPic(),iv_icon);

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
