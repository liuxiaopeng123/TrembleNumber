package com.theworldofluster.example.ziang.tremblenumber.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by liupeng on 2018/9/4.
 */

public class MyTextView extends TextView{
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) getMaxLineHeight(this.getText().toString())
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    private float getMaxLineHeight(String str) {
        float screenW = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout)this.getParent()).getPaddingLeft();
        float paddingReft = ((LinearLayout)this.getParent()).getPaddingRight();
        //这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil( (this.getPaint().measureText(str)/(screenW-paddingLeft-paddingReft)));
        float height = (this.getPaint().getFontMetrics().descent-this.getPaint().getFontMetrics().ascent)*line;
        return height;
    }
}
