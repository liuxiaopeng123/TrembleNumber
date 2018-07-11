package com.theworldofluster.example.ziang.tremblenumber.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.rey.material.app.BottomSheetDialog;
import com.theworldofluster.example.ziang.tremblenumber.MouthpieceUrl;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.bean.GsonObjModel;
import com.theworldofluster.example.ziang.tremblenumber.bean.PsyTestBean;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthAlertActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.HealthSamePersonActivity;
import com.theworldofluster.example.ziang.tremblenumber.pk.PKRecordActivity;
import com.theworldofluster.example.ziang.tremblenumber.utils.HttpPost;
import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;
import com.theworldofluster.example.ziang.tremblenumber.utils.ToastUtil;
import com.theworldofluster.example.ziang.tremblenumber.view.ButtomDialogView;
import com.theworldofluster.example.ziang.tremblenumber.view.MyEditText;
import com.theworldofluster.example.ziang.tremblenumber.view.NoAnimViewpager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaopeng
 * @date 2016/12/23
 */

public class CalendarTab1Controller extends TabController implements View.OnClickListener{
    View view;
    ListView psytab3_lv;

    private int selected_icon=-1;
    ImageView pager_today_img1;
    TextView pager_today_xinqing_tv1;

    LinearLayout bottom_pop_biaoqing_glass;

    List<View> list =new ArrayList<>();

    private static final String[] haonames = {"喜极而泣","珍爱","兴高采烈","爱","开心","喜欢","心仪","牵挂","满足","好感","感动","思念","祝福","感激"};
    private static final int[] haoicons = {R.mipmap.biaoqing_11_xijierqi,R.mipmap.biaoqing_12_zhenai,R.mipmap.biaoqing_21_xinggaocailie,
            R.mipmap.biaoqing_22_ai,R.mipmap.biaoqing_31_kaixin, R.mipmap.biaoqing_32_xihuan,R.mipmap.biaoqing_33_xinyi,R.mipmap.biaoqing_34_qiangua,
            R.mipmap.biaoqing_41_manzu,R.mipmap.biaoqing_42_haogan, R.mipmap.biaoqing_43_gandong,R.mipmap.biaoqing_44_sinian,R.mipmap.biaoqing_45_zhufu,R.mipmap.biaoqing_46_ganji};
    private static final String[] yibannames = {"朝思暮想","想要","惊讶","垂涎","震惊","紧张","不满","失望"};
    private static final int[] yibanicons = {R.mipmap.biaoqing_51_zhaosimuxiang,R.mipmap.biaoqing_61_xiangyao,R.mipmap.biaoqing_62_jingya,
            R.mipmap.biaoqing_71_chuiyan,R.mipmap.biaoqing_72_zhenjing, R.mipmap.biaoqing_73_jinzhang,R.mipmap.biaoqing_75_shiwang,R.mipmap.biaoqing_34_qiangua,};

    private static final String[] huainames = {"求知若渴","惊呆","生气","难过","胆怯","反感","忧虑","后悔","愤怒","痛苦","恐惧","厌恶","忧心忡忡","自责","暴跳如雷","痛不欲生","吓晕","深恶痛疾","忧思成疾","悔恨"};
    private static final int[] huaiicons = {R.mipmap.biaoqing_81_qiuzhiruoke,R.mipmap.biaoqing_82_jingdai,R.mipmap.biaoqing_83_shengqi,
            R.mipmap.biaoqing_84_nanguo,R.mipmap.biaoqing_85_danqie, R.mipmap.biaoqing_86_fangan,R.mipmap.biaoqing_87_youlv,R.mipmap.biaoqing_88_houhui,R.mipmap.biaoqing_91_fennu,
            R.mipmap.biaoqing_92_tongku,R.mipmap.biaoqing_93_kongju, R.mipmap.biaoqing_94_yanwu,R.mipmap.biaoqing_95_youxinchongchong,R.mipmap.biaoqing_96_zize,R.mipmap.biaoqing_101_baotiaorulei,
            R.mipmap.biaoqing_102_tongbuyusheng,R.mipmap.biaoqing_103_xiayun,R.mipmap.biaoqing_104_shenetongjue,R.mipmap.biaoqing_105_yousi_chengji,R.mipmap.biaoqing_106_huihen};

    private static final int[] haoIconCode={11,12,21,22,31,32,33,34,41,42,43,44,45,46};
    private static final int[] yibanIconCode={51,61,62,71,72,73,74,75};
    private static final int[] huaiIconCode={81,82,83,84,85,86,87,88,91,92,93,94,95,96,101,102,103,104,105,106};

    private int commitIconCode=-1;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM E");
    SimpleDateFormat sf2 = new SimpleDateFormat("dd");
    SimpleDateFormat sf3 = new SimpleDateFormat("yyyy-MM-dd");

    MyAdapter adapter = new MyAdapter();

    ViewPagerAdapter viewPagerAdapter;
    public CalendarTab1Controller(Context context) {
        super(context);
    }

    @Override

    protected View initContentView(Context context) {
        mContext = context;
        view = View.inflate(context, R.layout.pager_today, null);
        return view;
    }

    @Override
    public void initData() {
        getList("");
        TextView day = view.findViewById(R.id.pager_today_day);
        TextView year = view.findViewById(R.id.pager_today_year);
        day.setText(sf2.format(new Date(System.currentTimeMillis())));
        year.setText(sf.format(new Date(System.currentTimeMillis())));
        initView();
        initPopupWindow();
    }

    private void initView() {
        pager_today_img1=view.findViewById(R.id.pager_today_img1);
        TextView pager_today_commit1 = view.findViewById(R.id.pager_today_commit1);
        pager_today_commit1.setOnClickListener(this);
        pager_today_xinqing_tv1 =view.findViewById(R.id.pager_today_xinqing_tv1);
        pager_today_xinqing_tv1.setOnClickListener(this);
        initBiaoQing();
    }

    //初始化表情
    private void initBiaoQing() {
        View view11 =View.inflate(mContext, R.layout.view_page_grid_view,null);
        LinearLayout view_page_ll1 = view11.findViewById(R.id.view_page_ll);
        View view22 =View.inflate(mContext,R.layout.view_page_grid_view,null);
        LinearLayout view_page_ll2 = view22.findViewById(R.id.view_page_ll);
        View view33 =View.inflate(mContext,R.layout.view_page_grid_view,null);
        LinearLayout view_page_ll3 = view33.findViewById(R.id.view_page_ll);
        for (int i=0;i<haonames.length;i++){
            View view1=View.inflate(mContext,R.layout.item_biaoqing_icon,null);
            ImageView img = view1.findViewById(R.id.iv_biaoqing_icon_img);
            TextView text = view1.findViewById(R.id.iv_biaoqing_icon_name);
            img.setImageResource(haoicons[i]);
            text.setText(haonames[i]);
            view_page_ll1.addView(view1);
        }
        for (int i=0;i<yibannames.length;i++){
            View view2=View.inflate(mContext,R.layout.item_biaoqing_icon,null);
            ImageView img = view2.findViewById(R.id.iv_biaoqing_icon_img);
            TextView text = view2.findViewById(R.id.iv_biaoqing_icon_name);
            img.setImageResource(yibanicons[i]);
            text.setText(yibannames[i]);
            view_page_ll2.addView(view2);
        }
        for (int i=0;i<huainames.length;i++){
            View view3=View.inflate(mContext,R.layout.item_biaoqing_icon,null);
            ImageView img = view3.findViewById(R.id.iv_biaoqing_icon_img);
            TextView text = view3.findViewById(R.id.iv_biaoqing_icon_name);
            img.setImageResource(huaiicons[i]);
            text.setText(huainames[i]);
            view_page_ll3.addView(view3);
        }
        for (int i = 0;i<view_page_ll1.getChildCount();i++){
            final int position =i;
            view_page_ll1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_icon=haoicons[position];
                    pager_today_img1.setImageResource(haoicons[position]);
                    commitIconCode=haoIconCode[position];
//                    if (popupWindow.isShowing()){
//                        popupWindow.dismiss();
//                    }
                }
            });
        }
        for (int i = 0;i<view_page_ll2.getChildCount();i++){
            final int position =i;
            view_page_ll2.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_icon=yibanicons[position];
                    pager_today_img1.setImageResource(yibanicons[position]);
                    commitIconCode=yibanIconCode[position];
//                    if (popupWindow.isShowing()){
//                        popupWindow.dismiss();
//                    }
                }
            });
        }
        for (int i = 0;i<view_page_ll3.getChildCount();i++){
            final int position =i;
            view_page_ll3.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_icon=huaiicons[position];
                    pager_today_img1.setImageResource(huaiicons[position]);
                    commitIconCode=huaiIconCode[position];
//                    if (popupWindow.isShowing()){
//                        popupWindow.dismiss();
//                    }
                }
            });
        }
        list.add(view11);
        list.add(view22);
        list.add(view33);
        viewPagerAdapter =new ViewPagerAdapter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pager_today_commit1:
                showPopWindow();
                break;
            case R.id.pager_today_xinqing_tv1:
                showEditDialog();
                break;
            case R.id.bottom_pop_biaoqing_glass:
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
        }
    }

    PopupWindow popupWindow;
    private void initPopupWindow() {
        View contentView;
        //要在布局中显示的布局
        contentView = LayoutInflater.from(mContext).inflate(R.layout.bottom_pop_biaoqing, null, false);

        bottom_pop_biaoqing_glass=contentView.findViewById(R.id.bottom_pop_biaoqing_glass);
        bottom_pop_biaoqing_glass.setOnClickListener(this);

        final NoAnimViewpager pop_view_pager= contentView.findViewById(R.id.pop_view_pager);
        pop_view_pager.setAdapter(viewPagerAdapter);
        pop_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        RadioGroup radioGroup = contentView.findViewById(R.id.pop_rg);
        final RadioButton pop_rb1=contentView.findViewById(R.id.pop_rb1);
        final RadioButton pop_rb2=contentView.findViewById(R.id.pop_rb2);
        final RadioButton pop_rb3=contentView.findViewById(R.id.pop_rb3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId()==pop_rb1.getId()){
                    pop_view_pager.setCurrentItem(0);
                }
                if (group.getCheckedRadioButtonId()==pop_rb2.getId()){
                    pop_view_pager.setCurrentItem(1);

                }
                if (group.getCheckedRadioButtonId()==pop_rb3.getId()){
                    pop_view_pager.setCurrentItem(2);

                }
            }
        });

        TextView pop_commit = contentView.findViewById(R.id.pop_commit);
        pop_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
    }

    private void showPopWindow() {
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.pager_today, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
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

    private boolean flag_tongbuyouxinshi=true;
    ButtomDialogView buttomDialogView;
    private void showEditDialog() {
        View view = View.inflate(mContext, R.layout.bottom_pop_edit, null);
        buttomDialogView = new ButtomDialogView(mContext,view,true,true);
        final TextView pop_cancle=view.findViewById(R.id.bootom_pop_edit_cancle);
        final ImageView checked_img =view.findViewById(R.id.pop_edit_checked_img);
        final EditText editText = view.findViewById(R.id.pop_edit_et);
        checked_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_tongbuyouxinshi=!flag_tongbuyouxinshi;
                if (flag_tongbuyouxinshi==false){
                    checked_img.setImageResource(R.mipmap.like_off);
                }else {
                    checked_img.setImageResource(R.mipmap.like_on);
                }

            }
        });
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode== EditorInfo.IME_ACTION_DONE||(event!=null&&event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
                    if (TextUtils.isEmpty(editText.getText().toString().trim())){
                        buttomDialogView.dismiss();
                    }else {
                        pager_today_xinqing_tv1.setText(editText.getText().toString().trim());
                        buttomDialogView.dismiss();
                        commitxinqing(editText.getText().toString().trim());
                    }
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pop_cancle.setText(editText.getText().toString().length()+" / 50");

            }
        });
        buttomDialogView.show();
    }

    //提交心情
    private void commitxinqing(String dialyContext) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("type", "1");
        params.addQueryStringParameter("dialyDay", sf3.format(new Date(System.currentTimeMillis())));
        params.addQueryStringParameter("emojiId", commitIconCode+"");
        params.addQueryStringParameter("dialyContext", dialyContext);
        params.addQueryStringParameter("syncToMind", flag_tongbuyouxinshi?"1":"0");
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mood_save + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<PsyTestBean>>(MouthpieceUrl.base_mood_save , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<PsyTestBean> response, String result) {
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


    //获取列表
    public void getList(String name) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("userId", PreferenceUtil.getString("userId",""));
        params.addHeader("token",PreferenceUtil.getString("token",""));
        params.addQueryStringParameter("dialyDay", sf3.format(new Date(System.currentTimeMillis())));
        Log.i("xiaopeng", "url----:" + MouthpieceUrl.base_mood_getbyday + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<PsyTestBean>>(MouthpieceUrl.base_mood_getbyday , mContext, params) {
            @Override
            public void onParseSuccess(GsonObjModel<PsyTestBean> response, String result) {
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


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 6;
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
                convertView = View.inflate(mContext, R.layout.item_alert_tab1, null);
            }
            return convertView;
        }
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 获得数据
            View view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
