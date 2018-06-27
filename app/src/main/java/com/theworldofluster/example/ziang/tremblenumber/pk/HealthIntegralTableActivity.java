package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.view.NoScrollListView;

public class HealthIntegralTableActivity extends Activity {

    Dialog dialog;

    boolean flag_button_click=false;

    MyAdapter adapter =null;

    @ViewInject(R.id.health_integral_table_back)
    RelativeLayout health_integral_table_back;
    @ViewInject(R.id.pk_rules)
    RelativeLayout pk_rules;
    @ViewInject(R.id.pk_record)
    TextView pk_record;
    @ViewInject(R.id.activity_health_lv)
    NoScrollListView activity_health_lv;
    @ViewInject(R.id.health_integral_table_invite_pk)
    Button health_integral_table_invite_pk;
    @ViewInject(R.id.activity_pk_record_type_select)
    TextView activity_pk_record_type_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_integral_table);
        ViewUtils.inject(this); //注入view和事件

        initPopupWindow();
        initView();
        initData();
    }


    private void initView() {
        health_integral_table_back.setFocusable(true);
        health_integral_table_back.setFocusableInTouchMode(true);
        health_integral_table_back.requestFocus();
        adapter = new MyAdapter();
        activity_health_lv.setAdapter(adapter);

        activity_health_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                Log.i("xiaopeng","--------"+scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("xiaopeng","--------"+firstVisibleItem+"---"+visibleItemCount);
            }
        });
    }

    private void initData() {

    }


    @OnClick({R.id.health_integral_table_back,R.id.pk_rules,R.id.pk_record,R.id.health_integral_table_invite_pk,R.id.activity_pk_record_type_select})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.health_integral_table_back:
                finish();
                break;
            case R.id.pk_rules:
                showPKAboutDialog();
                break;
            case R.id.pk_record:
                startActivity(new Intent(HealthIntegralTableActivity.this,PKRecordActivity.class));
                break;
            case R.id.activity_pk_record_type_select:
                showPopWindow();
                break;
            case R.id.health_integral_table_invite_pk:

                if (flag_button_click){
                    showConfirmDialog();
                }else {
                    flag_button_click=true;
                    health_integral_table_invite_pk.setText("邀请PK");
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                break;
        }
    }

    PopupWindow popupWindow;
    private void initPopupWindow() {
        View contentView;
        //要在布局中显示的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.bottom_popup_window_health, null, false);
        //实例化PopupWindow并设置宽高
        TextView cancle = contentView.findViewById(R.id.cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView item_1 = contentView.findViewById(R.id.item_1);
        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity_pk_record_type_select.setText("好友排名 ");
            }
        });
        TextView item_2 = contentView.findViewById(R.id.item_2);
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity_pk_record_type_select.setText("地区排名 ");
            }
        });
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
        View rootview = LayoutInflater.from(HealthIntegralTableActivity.this).inflate(R.layout.activity_health_integral_table, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private void showPKAboutDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_about_pk, null);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showConfirmDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk, null);
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showFinishDialog();
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

    private void showLoadingDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk_loading, null);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showFinishDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        View view = View.inflate(this, R.layout.dialog_invite_pk_finish, null);
        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 30;
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_health_integral_table, null);
            }
            CheckBox item_health_check_box = convertView.findViewById(R.id.item_health_check_box);
            if (flag_button_click){
                item_health_check_box.setVisibility(View.VISIBLE);
            }else {
                item_health_check_box.setVisibility(View.GONE);
            }
            TextView pk_record_num = convertView.findViewById(R.id.pk_record_num);
            pk_record_num.setText(""+(position+1));
            return convertView;
        }
    }


}
