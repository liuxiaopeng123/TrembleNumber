package com.theworldofluster.example.ziang.tremblenumber.pk;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.theworldofluster.example.ziang.tremblenumber.R;
import com.theworldofluster.example.ziang.tremblenumber.view.NoScrollListView;

public class PKRecordActivity extends Activity {

    @ViewInject(R.id.activity_pk_record_back)
    RelativeLayout activity_pk_record_back;
    @ViewInject(R.id.activity_pk_record_lv)
    NoScrollListView activity_pk_record_lv;
    @ViewInject(R.id.activity_pk_record_type_select)
    TextView activity_pk_record_type_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_record);
        ViewUtils.inject(this); //注入view和事件

        initView();
        initData();
    }


    private void initView() {
        activity_pk_record_back.setFocusable(true);
        activity_pk_record_back.setFocusableInTouchMode(true);
        activity_pk_record_back.requestFocus();
        initPopupWindow();
    }

    private void initData() {
        activity_pk_record_lv.setAdapter(new MyAdapter());
    }


    @OnClick({R.id.activity_pk_record_back,R.id.activity_pk_record_type_select})
    private void Onclick(View v){
        switch (v.getId()){
            case R.id.activity_pk_record_back:
                finish();
                break;
            case R.id.activity_pk_record_type_select:
                showPopWindow();
                break;

            default:
                break;
        }
    }
    PopupWindow popupWindow;
    private void initPopupWindow() {
        View contentView;
        //要在布局中显示的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.bottom_popup_window, null, false);
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
                activity_pk_record_type_select.setText("主动PK ");
            }
        });
        TextView item_2 = contentView.findViewById(R.id.item_2);
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity_pk_record_type_select.setText("被动PK ");
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
        View rootview = LayoutInflater.from(PKRecordActivity.this).inflate(R.layout.activity_pk_record, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_pk_record, null);
            }
            return convertView;
        }
    }

}
