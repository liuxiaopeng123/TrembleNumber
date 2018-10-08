package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.WanNengBean;
import com.theworldofluster.example.ziang.tremblenumber.bean.XinShi;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.YouXinShiDetailActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpGet;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class YouXinShiTab3Controller extends TabController {
    View view;
    ListView youxinshi_lv;
    private int pageNum = 1;
    private boolean haveMore = false;
    List<XinShi> xinShiList=new ArrayList<>();

    MyAdapter adapter = new MyAdapter();
    public YouXinShiTab3Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.youxinshitab3_control, null);
        return view;
    }

    @Override
    public void initData() {
        youxinshi_lv=view.findViewById(R.id.youxinshi_lv);
        youxinshi_lv.setAdapter(adapter);
        youxinshi_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, YouXinShiDetailActivity.class);
                intent.putExtra("mindId",xinShiList.get(position).getMindId()+"");
                intent.putExtra("hugNumber",xinShiList.get(position).getHugNumber()+"");
                intent.putExtra("mindContext",xinShiList.get(position).getMindContext()+"");
                intent.putExtra("nickName",xinShiList.get(position).getNickName()+"");
                intent.putExtra("headUrl",xinShiList.get(position).getHeadUrl()+"");
                mContext.startActivity(intent);
//                showDialog();
            }
        });

        youxinshi_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    //用户抬起手指
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    //滑动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (youxinshi_lv.getLastVisiblePosition() == xinShiList.size() - 1) {
                            if (haveMore) {
                                pageNum++;
                                getList("");
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

        getList("");

    }

    Dialog dialog;
    private void showDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_alert_health, null);
        ImageView cancle = (ImageView) view.findViewById(R.id.dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView godetail = (TextView) view.findViewById(R.id.dialog_alert_health_godetail);
        godetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
            }
        });
        TextView gosameperson = (TextView) view.findViewById(R.id.dialog_alert_health_gosameperson);
        gosameperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mContext.startActivity(new Intent(mContext,HealthSamePersonActivity.class));
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
        params.addQueryStringParameter("isDecline", "false");
        params.addQueryStringParameter("isMine", "true");
        params.addQueryStringParameter("pageIndex", ""+pageNum);
        params.addQueryStringParameter("pageSize", "10");
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_list + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<List<XinShi>>>(MouthpieceUrl.base_mind_list , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<List<XinShi>> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    if (response.data!=null){
                        if (response.data.size() < 10) {
                            haveMore = false;
                        } else {
                            haveMore = true;
                        }
                        if (pageNum==1){
                            xinShiList=new ArrayList<>();
                            xinShiList.addAll(response.data);
                        }else {
                            xinShiList.addAll(response.data);
                        }

                    }else {
                        haveMore = false;
                    }
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

    private void showConfirmDialog(final int position) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(mContext, R.layout.dialog_invite_pk, null);
        TextView content = view.findViewById(R.id.content);
        content.setText("确定删除这条心情？");
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                DelMind(position);
            }
        });
        TextView cancle = view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    //删除心情
    private void DelMind(final int position) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("mindId", ""+xinShiList.get(position).getMindId());
        params.addQueryStringParameter("Ziang", Utils.getrandom()+"");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mind_del + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpGet<GsonObjModel<WanNengBean>>(MouthpieceUrl.base_mind_del , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<WanNengBean> response, String result) {
                Log.i("xiaopeng-----","result-----"+result);
                if (response.code==200){
                    xinShiList.remove(position);
                    adapter.notifyDataSetChanged();
//                    getList("");
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
            return xinShiList==null?0:xinShiList.size();
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
                convertView = View.inflate(mContext, R.layout.item_youxinshi_tab3, null);
            }
            TextView content=convertView.findViewById(R.id.item_youxinshi_content);
            TextView del=convertView.findViewById(R.id.item_youxinshi_del);
            TextView year=convertView.findViewById(R.id.item_youxinshi_year);
            TextView day=convertView.findViewById(R.id.item_youxinshi_day);
            TextView hour=convertView.findViewById(R.id.item_youxinshi_hour);
            content.setText(xinShiList.get(position).getMindContext());
            year.setText(xinShiList.get(position).getCreateDate().substring(0,4)+"."+xinShiList.get(position).getCreateDate().substring(5,7));
            day.setText(xinShiList.get(position).getCreateDate().substring(8,10));
            hour.setText(xinShiList.get(position).getCreateDate().substring(11,16));
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmDialog(position);
                }
            });
            return convertView;
        }
    }

}
