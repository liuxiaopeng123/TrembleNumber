package com.theworldofluster.example.ziang.tremblenumber.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.theworldofluster.example.ziang.tremblenumber.R;


/**
 * Created by 庄立宏 on 2016/8/25.
 */
public class CounterRadioButton extends RadioButton {
    int value;
    Bitmap bitmap;
    public CounterRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.newsnumber);

    }

    public CounterRadioButton(Context context) {
        super(context);
    }

    public CounterRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(value<=0)return;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        int width = getWidth();
        int height = getHeight();
        float size = width < height ? width : height;
//        canvas.drawRoundRect(width / 2, 0, width/2+height/3, height / 3,height/6,height/6, paint);
//        canvas.drawArc(width / 2, 0, width/2+height/3, height / 3,0,360, false,paint);
//        Rect src=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,null,new Rect(width / 2, 0, width/2+height*2/5, height *2/ 5),paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(33);
        String strTotal = value > 100 ? "99" : "" + value;
        Rect rect = new Rect();
        paint.getTextBounds(strTotal, 0, strTotal.length(), rect);
        canvas.drawText(strTotal, width/2+height/5 - rect.width() / 2, height / 5
                + rect.height() / 2, paint);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        postInvalidate();
    }

    public void increaseValue() {
        value++;
        postInvalidate();
    }

    public void reduceValue() {
        if (value > 0) {
            value--;
            postInvalidate();
        }
    }
}
