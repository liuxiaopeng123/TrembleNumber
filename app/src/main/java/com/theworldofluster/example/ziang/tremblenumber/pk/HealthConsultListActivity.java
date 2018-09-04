package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.theworldofluster.example.ziang.tremblenumber.bean.ArticleBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.List;

public class HealthConsultListActivity extends Activity {
    @ViewInject(R.id.activity_psy_test_item_back)
    RelativeLayout activity_psy_test_item_back;
    @ViewInject(R.id.activity_psy_test_item_title)
    TextView activity_psy_test_item_title;
    @ViewInject(R.id.activity_psy_test_item_lv)
    ListView activity_psy_test_item_lv;

    MyAdapter adapter = new MyAdapter();

    List<ArticleBean> articleBeanList=null;

    String cateCode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consult_list);
        ViewUtils.inject(this); //注入view和事件
        init();
    }
    private void init() {
        activity_psy_test_item_title.setText(getIntent().getStringExtra("title"));
        cateCode=getIntent().getStringExtra("cateCode");
        getList();


        activity_psy_test_item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HealthConsultListActivity.this,HealthConsultDetailActivity.class);
                intent.putExtra("articleCode",articleBeanList.get(position).getArticleCode()+"");
                intent.putExtra("articleTitle",articleBeanList.get(position).getArticleTitle()+"");
                intent.putExtra("articleContext",articleBeanList.get(position).getArticleContext()+"");
                intent.putExtra("collected",articleBeanList.get(position).isCollected());
                intent.putExtra("secondCategoryCode",articleBeanList.get(position).getSecondCategoryCode());
                startActivity(intent);
            }
        });
    }

    //获取列表
    public void getList() {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("cateCode", cateCode);
        params.addQueryStringParameter("firstCateCode", "");
        params.addQueryStringParameter("pageIndex", "1");
        params.addQueryStringParameter("pageSize", "10");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_consult_articles + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ArticleBean>>>(MouthpieceUrl.base_health_consult_articles , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ArticleBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    articleBeanList=response.data;
                    activity_psy_test_item_lv.setAdapter(adapter);
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_consult_tab3, null);
            }
            TextView title = convertView.findViewById(R.id.item_article_title);
            title.setText(articleBeanList.get(position).getArticleTitle());
            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }
}
