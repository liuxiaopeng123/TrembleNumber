package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;

public class HealthConsultDetailActivity extends Activity {
    @ViewInject(R.id.activity_health_consult_detail_back)
    RelativeLayout activity_health_consult_detail_back;
    @ViewInject(R.id.activity_health_consult_detail_bar_title)
    TextView activity_health_consult_detail_bar_title;
    @ViewInject(R.id.activity_health_consult_detail_title)
    TextView activity_health_consult_detail_title;
    @ViewInject(R.id.activity_health_consult_detail_content)
    TextView activity_health_consult_detail_content;
    @ViewInject(R.id.health_consult_like)
    RelativeLayout health_consult_like;
    @ViewInject(R.id.health_consult_like_img)
    ImageView health_consult_like_img;

    boolean collected=false;

    String articleCode,articleTitle,articleContext,secondCategoryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consult_detail);
        ViewUtils.inject(this); //注入view和事件
        init();
    }

    private void init() {
        articleCode=getIntent().getStringExtra("articleCode");
        articleTitle=getIntent().getStringExtra("articleTitle");
        articleContext=getIntent().getStringExtra("articleContext");
        secondCategoryCode=getIntent().getStringExtra("secondCategoryCode");
        collected=getIntent().getBooleanExtra("collected",false);

        activity_health_consult_detail_bar_title.setText("文章详情");
        activity_health_consult_detail_title.setText(articleTitle);
        activity_health_consult_detail_content.setText(articleContext);

        initCollected();

    }

    private void initCollected() {

        if (collected){
            health_consult_like_img.setBackgroundResource(R.mipmap.like_on);
        }else {
            health_consult_like_img.setBackgroundResource(R.mipmap.like_off);
        }
    }

    @OnClick({R.id.activity_health_consult_detail_back,R.id.health_consult_like})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_health_consult_detail_back:
                finish();
                break;
            case R.id.health_consult_like:
                like(collected);
                break;
        }
    }

    private void like(final boolean collecte) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token", PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("articleCode", articleCode);
        params.addQueryStringParameter("isCancel", collecte+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_health_consult_collect + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_health_consult_collect , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    if (response.data.getCode()==200){
                        collected=false;
                    }
                    if (response.data.getCode()==100){
                        collected=true;
                    }
                    initCollected();
                    ToastUtil.showContent(getApplicationContext(),response.data.getDesc());
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

}
