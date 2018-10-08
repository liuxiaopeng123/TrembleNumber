package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.ArticleBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthConsultDetailActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class ConsultTab3Controller extends TabController {
    View view;
    ListView consulttab3_lv;
    TextView consult_type_1;
    TextView consult_type_2;

    List<ArticleBean> articleBeanList=new ArrayList<>();
    private int pageNum = 1;
    private boolean haveMore = false;

    public String select="2";
    MyAdapter adapter = new MyAdapter();
    public ConsultTab3Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.consulttab3_control, null);
        return view;
    }

    @Override
    public void initData() {
        consult_type_1 =view.findViewById(R.id.consult_type_1);
        consult_type_2 =view.findViewById(R.id.consult_type_2);
        consult_type_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select="2";
                haveMore=true;
                pageNum=1;
                consult_type_1.setTextColor(Color.WHITE);
                consult_type_1.setBackgroundResource(R.drawable.button_shape_half_white_blue_2);
                consult_type_2.setTextColor(Color.GRAY);
                consult_type_2.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                getList("2");

            }
        });
        consult_type_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select="1";
                haveMore=true;
                pageNum=1;
                consult_type_2.setTextColor(Color.WHITE);
                consult_type_2.setBackgroundResource(R.drawable.button_shape_half_white_blue_2);
                consult_type_1.setTextColor(Color.GRAY);
                consult_type_1.setBackgroundResource(R.drawable.button_shape_half_white_cricle);
                getList("1");
            }
        });
        consulttab3_lv=view.findViewById(R.id.consulttab3_lv);
        consulttab3_lv.setAdapter(adapter);
        consulttab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,HealthConsultDetailActivity.class);
                intent.putExtra("articleCode",articleBeanList.get(position).getArticleCode()+"");
                intent.putExtra("articleTitle",articleBeanList.get(position).getArticleTitle()+"");
                intent.putExtra("articleContext",articleBeanList.get(position).getArticleContext()+"");
                intent.putExtra("collected",articleBeanList.get(position).isCollected());
                intent.putExtra("secondCategoryCode",articleBeanList.get(position).getSecondCategoryCode());
                ((HealthConsultActivity)(mContext)).startActivityForResult(intent,0);

            }
        });
        consulttab3_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    //用户抬起手指
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    //滑动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (consulttab3_lv.getLastVisiblePosition() == articleBeanList.size() - 1) {
                            if (haveMore) {
                                pageNum++;
                                getList(select);
                            }
                        }
                        break;
                    //滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            }
        });

        getList(select);

    }

    //获取列表
    public void getList(String typename) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("cateCode", "");
        params.addQueryStringParameter("firstCateCode", typename);
        params.addQueryStringParameter("pageIndex", ""+pageNum);
        params.addQueryStringParameter("pageSize", "200");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_consult_articles + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ArticleBean>>>(MouthpieceUrl.base_health_consult_articles , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ArticleBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    articleBeanList=response.data;
//                    if (response.data!=null){
//                        if (response.data.size() < 10) {
//                            haveMore = false;
//                        } else {
//                            haveMore = true;
//                        }
//                        if (pageNum==1){
//                            articleBeanList=new ArrayList<>();
//                            articleBeanList.addAll(response.data);
//                        }else {
//                            articleBeanList.addAll(response.data);
//                        }
//
//                    }else {
//                        haveMore = false;
//                    }


                    adapter.notifyDataSetChanged();
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return articleBeanList==null?0:articleBeanList.size();
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
                convertView = View.inflate(mContext, R.layout.item_consult_tab3, null);
            }
            TextView title = convertView.findViewById(R.id.item_article_title);
            title.setText(articleBeanList.get(position).getArticleTitle());
            return convertView;
        }
    }
}
