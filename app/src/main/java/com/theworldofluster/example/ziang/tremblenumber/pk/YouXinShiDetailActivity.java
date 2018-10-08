package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.ComfortBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.XinShi;
import com.theworldofluster.example.ziang.tremblenumber.controller.TabController;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab1Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab2Controller;
import com.theworldofluster.example.ziang.tremblenumber.controller.YouXinShiTab3Controller;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;
import com.theworldofluster.example.ziang.tremblenumber.view.CircularImage;
import com.theworldofluster.example.ziang.tremblenumber.view.DataPickerDialog;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.util.ArrayList;
import java.util.List;

public class YouXinShiDetailActivity extends Activity {

    @ViewInject(R.id.activity_youxinshi_back)
    RelativeLayout activity_youxinshi_back;
    @ViewInject(R.id.detail_youxinshi_lv)
    ListView detail_youxinshi_lv;
    @ViewInject(R.id.detail_youxinshi_user_img)
    CircularImage detail_youxinshi_user_img;
    @ViewInject(R.id.detail_youxinshi_user_name)
    TextView detail_youxinshi_user_name;
    @ViewInject(R.id.detail_youxinshi_content)
    TextView detail_youxinshi_content;
    @ViewInject(R.id.detail_youxinshi_hugnumber)
    TextView detail_youxinshi_hugnumber;
    @ViewInject(R.id.detail_youxinshi_anweita)
    TextView detail_youxinshi_anweita;
    @ViewInject(R.id.detail_youxinshi_anweita2)
    TextView detail_youxinshi_anweita2;
    @ViewInject(R.id.detail_youxinshi_have_data)
    LinearLayout detail_youxinshi_have_data;
    @ViewInject(R.id.detail_youxinshi_no_data)
    LinearLayout detail_youxinshi_no_data;

    List<ComfortBean> comfortBeanList=new ArrayList<>();

    MyAdapter adapter = new MyAdapter();

    List<String> data = new ArrayList<>();

    String mindId,mindContext,hugNumber,nickName,headUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_youxinshi_detail);
        ViewUtils.inject(this); //注入view和事件

        mindId=getIntent().getStringExtra("mindId");
        hugNumber=getIntent().getStringExtra("hugNumber");
        mindContext=getIntent().getStringExtra("mindContext");
        nickName=getIntent().getStringExtra("nickName");
        headUrl=getIntent().getStringExtra("headUrl");

        detail_youxinshi_user_name.setText(nickName);
        detail_youxinshi_hugnumber.setText(hugNumber+" 抱抱");
        detail_youxinshi_content.setText(mindContext);

        data.add("除生死外，无关大事。面朝大海，春暖花开");
        data.add("心之所愿，无所不成");
        data.add("既然庸庸碌碌也难逃一死，何不奋起一搏？");
        data.add("万事随缘，随遇而安。");
        data.add("上坡路走起来总会辛苦");
        data.add("任何时候都不算晚，把握今天才是最明智的选择");
        data.add("快意时早回首，拂心处莫放手");

        getList("");
    }

    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("mindId", mindId);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_comfortList + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<ComfortBean>>>(MouthpieceUrl.base_mind_comfortList , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<ComfortBean>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    if (response.data==null){
                        detail_youxinshi_have_data.setVisibility(View.GONE);
                        detail_youxinshi_no_data.setVisibility(View.VISIBLE);
                    }else {
                        comfortBeanList=response.data;
                        if (response.data.size()>0){
                            detail_youxinshi_have_data.setVisibility(View.VISIBLE);
                            detail_youxinshi_no_data.setVisibility(View.GONE);
                        }else {
                            detail_youxinshi_have_data.setVisibility(View.GONE);
                            detail_youxinshi_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                    detail_youxinshi_lv.setAdapter(adapter);
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

    //获取列表
    public void delComfort(String comfortId) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("comfortId", comfortId);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_delComfort + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_mind_delComfort , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    getList("");
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

    //获取列表
    public void anWeiTa(String context) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("mindId", mindId);
        params.addQueryStringParameter("comfortContext", context);
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_comfort + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_mind_comfort , this, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    getList("");
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

    @OnClick({R.id.activity_youxinshi_back,R.id.detail_youxinshi_anweita2,R.id.detail_youxinshi_anweita})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_youxinshi_back:
                finish();
                break;
            case R.id.detail_youxinshi_anweita2:
                showDialog();
                break;
            case R.id.detail_youxinshi_anweita:
                showDialog();
                break;
            default:
                break;
        }
    }

    private final void showDialog() {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);

//        DataPickerDialog dialog = builder.setUnit("单位").setData(data).setSelection(1).setTitle("标题")
        DataPickerDialog dialog = builder.setData(data).setSelection(1)
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(int itemValue) {
                        anWeiTa(data.get(itemValue));
                        Toast.makeText(getApplicationContext(), data.get(itemValue), Toast.LENGTH_SHORT).show();
                    }
                }).create();

        dialog.show();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return comfortBeanList==null?0:comfortBeanList.size();
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_youxinshi_detail, null);
            }
            LinearLayout del_toggle_ll=convertView.findViewById(R.id.del_toggle_ll);
            TextView content=convertView.findViewById(R.id.item_youxinshi_detail_context);
            TextView delCancle=convertView.findViewById(R.id.item_youxinshi_detail_del_cancle);
            TextView del=convertView.findViewById(R.id.item_youxinshi_detail_del);
            TextView name=convertView.findViewById(R.id.item_youxinshi_detail_name);
            content.setText("“"+comfortBeanList.get(position).getComfortContext()+"”");
            name.setText(comfortBeanList.get(position).getNickName()+"说");
            if (position%9==0){
                content.setBackgroundColor(Color.parseColor("#ACFFEEEE"));
                content.setTextColor(Color.parseColor("#FFD89494"));
                name.setTextColor(Color.parseColor("#FFD89494"));
            }else if (position%9==1){
                content.setBackgroundColor(Color.parseColor("#DAFFEDDE"));
                content.setTextColor(Color.parseColor("#FFC07D50"));
                name.setTextColor(Color.parseColor("#FFC07D50"));
            }else if (position%9==2){
                content.setBackgroundColor(Color.parseColor("#FFFFFEE5"));
                content.setTextColor(Color.parseColor("#FF8C8B54"));
                name.setTextColor(Color.parseColor("#FF8C8B54"));
            }else if (position%9==3){
                content.setBackgroundColor(Color.parseColor("#96D8ECFF"));
                content.setTextColor(Color.parseColor("#FF5D9CC5"));
                name.setTextColor(Color.parseColor("#FF5D9CC5"));
            }else if (position%9==4){
                content.setBackgroundColor(Color.parseColor("#C4E8EEDE"));
                content.setTextColor(Color.parseColor("#FF65833B"));
                name.setTextColor(Color.parseColor("#FF65833B"));
            }else if (position%9==5){
                content.setBackgroundColor(Color.parseColor("#E3EEEEEE"));
                content.setTextColor(Color.parseColor("#FF666666"));
                name.setTextColor(Color.parseColor("#FF666666"));
            }else if (position%9==6){
                content.setBackgroundColor(Color.parseColor("#CEE7F5F0"));
                content.setTextColor(Color.parseColor("#FF538875"));
                name.setTextColor(Color.parseColor("#FF538875"));
            }else if (position%9==7){
                content.setBackgroundColor(Color.parseColor("#FFEBEBFF"));
                content.setTextColor(Color.parseColor("#FF7471B0"));
                name.setTextColor(Color.parseColor("#FF7471B0"));
            }else if (position%9==8){
                content.setBackgroundColor(Color.parseColor("#FFE3EDED"));
                content.setTextColor(Color.parseColor("#FF4F8383"));
                name.setTextColor(Color.parseColor("#FF4F8383"));
            }
            if (comfortBeanList.get(position).isFlag_open()){
                del_toggle_ll.setVisibility(View.VISIBLE);
            }else {
                del_toggle_ll.setVisibility(View.GONE);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString("userId","").equals(comfortBeanList.get(position).getUserId()+"")){
                        comfortBeanList.get(position).setFlag_open(!comfortBeanList.get(position).isFlag_open());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delComfort(comfortBeanList.get(position).getComfortId()+"");
                }
            });
            delCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PreferenceUtil.getString("userId","").equals(comfortBeanList.get(position).getUserId()+"")){
                        comfortBeanList.get(position).setFlag_open(!comfortBeanList.get(position).isFlag_open());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }

}
