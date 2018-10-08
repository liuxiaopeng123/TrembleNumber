package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.CeShiFenXi;
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

public class PsyTab4Controller extends TabController implements View.OnClickListener{
    View view;
    GridView gridView2 ;
    GridViewAdapter adapter;
    List<CeShiFenXi> psyBeanList=new ArrayList<>();
    TextView psy_tab4_item1;
    TextView psy_tab4_item2;
    TextView psy_tab4_item3;
    TextView psy_tab4_item4;
    TextView psy_tab4_item5;
    TextView psy_tab4_item6;
    TextView psy_tab4_item7;
    List<CeShiFenXi> psyBeanListBeiFen=new ArrayList<>();
    private int flag_selected = -1,flag_selected2=-1;
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
        psy_tab4_item1=view.findViewById(R.id.psy_tab4_item1);
        psy_tab4_item2=view.findViewById(R.id.psy_tab4_item2);
        psy_tab4_item3=view.findViewById(R.id.psy_tab4_item3);
        psy_tab4_item4=view.findViewById(R.id.psy_tab4_item4);
        psy_tab4_item5=view.findViewById(R.id.psy_tab4_item5);
        psy_tab4_item6=view.findViewById(R.id.psy_tab4_item6);
        psy_tab4_item7=view.findViewById(R.id.psy_tab4_item7);
        psy_tab4_item1.setOnClickListener(this);
        psy_tab4_item2.setOnClickListener(this);
        psy_tab4_item3.setOnClickListener(this);
        psy_tab4_item4.setOnClickListener(this);
        psy_tab4_item5.setOnClickListener(this);
        psy_tab4_item6.setOnClickListener(this);
        psy_tab4_item7.setOnClickListener(this);
        gridView2= (GridView) view.findViewById(R.id.psytab1_gv2);
        adapter = new GridViewAdapter();

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPKAboutDialog(position);
            }
        });
        getList("");

    }


    Dialog dialog;
    private void showPKAboutDialog(final int position) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_psytab4, null);
        LinearLayout dialog_psy_bg_ll=view.findViewById(R.id.dialog_psy_bg_ll);
        ImageView cancle = view.findViewById(R.id.dialog_psy_tab4_cancle);
        TextView title = view.findViewById(R.id.dialog_psy_tab4_title);
        TextView content = view.findViewById(R.id.dialog_psy_tab4_content);
        if (position%9==0){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#ACFFEEEE"));
            title.setTextColor(Color.parseColor("#FFD89494"));
            content.setTextColor(Color.parseColor("#FFD89494"));
        }else if (position%9==1){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#DAFFEDDE"));
            title.setTextColor(Color.parseColor("#FFC07D50"));
            content.setTextColor(Color.parseColor("#FFC07D50"));
        }else if (position%9==2){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#FFFFFEE5"));
            title.setTextColor(Color.parseColor("#FF8C8B54"));
            content.setTextColor(Color.parseColor("#FF8C8B54"));
        }else if (position%9==3){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#96D8ECFF"));
            title.setTextColor(Color.parseColor("#FF5D9CC5"));
            content.setTextColor(Color.parseColor("#FF5D9CC5"));
        }else if (position%9==4){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#C4E8EEDE"));
            title.setTextColor(Color.parseColor("#FF65833B"));
            content.setTextColor(Color.parseColor("#FF65833B"));
        }else if (position%9==5){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#E3EEEEEE"));
            title.setTextColor(Color.parseColor("#FF666666"));
            content.setTextColor(Color.parseColor("#FF666666"));
        }else if (position%9==6){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#CEE7F5F0"));
            title.setTextColor(Color.parseColor("#FF538875"));
            content.setTextColor(Color.parseColor("#FF538875"));
        }else if (position%9==7){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#FFEBEBFF"));
            title.setTextColor(Color.parseColor("#FF7471B0"));
            content.setTextColor(Color.parseColor("#FF7471B0"));
        }else if (position%9==8){
            dialog_psy_bg_ll.setBackgroundColor(Color.parseColor("#FFE3EDED"));
            title.setTextColor(Color.parseColor("#FF4F8383"));
            content.setTextColor(Color.parseColor("#FF4F8383"));
        }
        title.setText(psyBeanList.get(position).getTitleName());
        content.setText(psyBeanList.get(position).getTitleDesc());
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
        new HttpGet<GsonObjModel<List<CeShiFenXi>>>(MouthpieceUrl.base_psy_test_analysis , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<CeShiFenXi>> response, String result) {
                if (response.code==200){
                    psyBeanList=response.data;
                    psyBeanListBeiFen=response.data;
                    gridView2.setAdapter(adapter);
                    xianshibuxianshi();
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


    //显示不显示
    private void xianshibuxianshi() {
        for (int i = 0 ;i<psyBeanListBeiFen.size();i++){

            if (psyBeanListBeiFen.get(i).getCategoryCode()==10003||psyBeanListBeiFen.get(i).getCategoryCode()==10010){
                psy_tab4_item1.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10004||psyBeanListBeiFen.get(i).getCategoryCode()==10011){
                psy_tab4_item2.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10005||psyBeanListBeiFen.get(i).getCategoryCode()==10012){
                psy_tab4_item3.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10006||psyBeanListBeiFen.get(i).getCategoryCode()==10013){
                psy_tab4_item4.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10007||psyBeanListBeiFen.get(i).getCategoryCode()==10014){
                psy_tab4_item5.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10008||psyBeanListBeiFen.get(i).getCategoryCode()==10015){
                psy_tab4_item6.setVisibility(View.VISIBLE);
            }
            if (psyBeanListBeiFen.get(i).getCategoryCode()==10009||psyBeanListBeiFen.get(i).getCategoryCode()==10016){
                psy_tab4_item7.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.psy_tab4_item1:
                flag_selected=10003;
                flag_selected2=10010;
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item2:
                flag_selected=10004;
                flag_selected2=10011;
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item3:
                flag_selected=10005;
                flag_selected2=10012;
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item4:
                flag_selected=10006;
                flag_selected2=10013;
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item5:
                flag_selected=10007;
                flag_selected2=10014;
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item6:
                flag_selected=10008;
                flag_selected2=10015;
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
            case R.id.psy_tab4_item7:
                flag_selected=10009;
                flag_selected2=10016;
                psy_tab4_item7.setBackgroundResource(R.mipmap.yuanbeijing);
                psy_tab4_item2.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item3.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item4.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item5.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item6.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                psy_tab4_item1.setBackgroundResource(R.mipmap.yuanbeijingweixuanzhong);
                break;
        }
        shaiXuan();
    }

    private void shaiXuan() {
        psyBeanList = new ArrayList<>();
        for (int i = 0;i<psyBeanListBeiFen.size();i++){
            if (flag_selected == psyBeanListBeiFen.get(i).getCategoryCode()||flag_selected2==psyBeanListBeiFen.get(i).getCategoryCode()){
                psyBeanList.add(psyBeanListBeiFen.get(i));
                Log.i("xiaopeng------",""+psyBeanListBeiFen.get(i).getCategoryCode()+psyBeanListBeiFen.get(i).getTitleName());
            }
        }
        adapter.notifyDataSetChanged();
    }

    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回的条目
            if (psyBeanList==null){
                return 0;
            }else {
                if (flag_selected==-1){
                    return psyBeanList.size();
                }else {
                    int count=0;
                    for (int i = 0 ; i<psyBeanList.size();i++){
                        if (psyBeanList.get(i).getCategoryCode()==flag_selected||psyBeanList.get(i).getCategoryCode()==flag_selected2){
                            count++;
                        }
                    }
                    return count;
                }
            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView= View.inflate(mContext,
                        R.layout.item_psytab_gb2, null);
            }
            LinearLayout bg = convertView.findViewById(R.id.item_psytab_gb_bg);
            TextView tv_name = (TextView) convertView
                    .findViewById(R.id.tv_homeitem_name);
            tv_name.setText(psyBeanList.get(position).getTitleName()+"");
            TextView tv_num = (TextView) convertView
                    .findViewById(R.id.tv_homeitem_num);
            tv_num.setVisibility(View.GONE);
            tv_num.setText(psyBeanList.get(position).getNum()+"%");
            if (position%9==0){
                bg.setBackgroundColor(Color.parseColor("#ACFFEEEE"));
                tv_num.setTextColor(Color.parseColor("#FFD89494"));
                tv_name.setTextColor(Color.parseColor("#FFD89494"));
            }else if (position%9==1){
                bg.setBackgroundColor(Color.parseColor("#DAFFEDDE"));
                tv_num.setTextColor(Color.parseColor("#FFC07D50"));
                tv_name.setTextColor(Color.parseColor("#FFC07D50"));
            }else if (position%9==2){
                bg.setBackgroundColor(Color.parseColor("#FFFFFEE5"));
                tv_num.setTextColor(Color.parseColor("#FF8C8B54"));
                tv_name.setTextColor(Color.parseColor("#FF8C8B54"));
            }else if (position%9==3){
                bg.setBackgroundColor(Color.parseColor("#96D8ECFF"));
                tv_num.setTextColor(Color.parseColor("#FF5D9CC5"));
                tv_name.setTextColor(Color.parseColor("#FF5D9CC5"));
            }else if (position%9==4){
                bg.setBackgroundColor(Color.parseColor("#C4E8EEDE"));
                tv_num.setTextColor(Color.parseColor("#FF65833B"));
                tv_name.setTextColor(Color.parseColor("#FF65833B"));
            }else if (position%9==5){
                bg.setBackgroundColor(Color.parseColor("#E3EEEEEE"));
                tv_num.setTextColor(Color.parseColor("#FF666666"));
                tv_name.setTextColor(Color.parseColor("#FF666666"));
            }else if (position%9==6){
                bg.setBackgroundColor(Color.parseColor("#CEE7F5F0"));
                tv_num.setTextColor(Color.parseColor("#FF538875"));
                tv_name.setTextColor(Color.parseColor("#FF538875"));
            }else if (position%9==7){
                bg.setBackgroundColor(Color.parseColor("#FFEBEBFF"));
                tv_num.setTextColor(Color.parseColor("#FF7471B0"));
                tv_name.setTextColor(Color.parseColor("#FF7471B0"));
            }else if (position%9==8){
                bg.setBackgroundColor(Color.parseColor("#FFE3EDED"));
                tv_num.setTextColor(Color.parseColor("#FF4F8383"));
                tv_name.setTextColor(Color.parseColor("#FF4F8383"));
            }
            return convertView;
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
