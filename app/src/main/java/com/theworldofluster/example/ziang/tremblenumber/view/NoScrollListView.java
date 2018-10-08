package com.theworldofluster.example.ziang.tremblenumber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

public class NoScrollListView extends ListView  
{  
    public NoScrollListView(Context context,
                            AttributeSet attrs)
    {  
        super(context, attrs);  
    }

    public void setParent(ScrollView view){
        mParent = view;
    }

    private ScrollView mParent;

    private float mDownY;


    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }


//    //重写该方法 在按下的时候让父容器不处理滑动事件
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setParentScrollAble(false);
//                mDownY = ev.getY();
//                Log.i("xiaopeng---按下",""+ev.getY()+"----"+ev.getRawY());
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //
//                if (isListViewReachTop() && ev.getY() - mDownY > 0) {
//                    setParentScrollAble(true);
//                } else if (isListViewReachBottom() && ev.getY() - mDownY < 0) {
//                    setParentScrollAble(true);
//                }
//                Log.i("xiaopeng---滑动",""+ev.getY()+"----"+ev.getRawY());
//                break;
//            case MotionEvent.ACTION_UP:
//
//            case MotionEvent.ACTION_CANCEL:
//                setParentScrollAble(true);
//                Log.i("xiaopeng---滑动",""+ev.getY()+"----"+ev.getRawY());
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }
//
//
//    /**
//     * @param flag
//     */
//    private void setParentScrollAble(boolean flag) {
//        if (mParent!=null)mParent.requestDisallowInterceptTouchEvent(!flag);
//    }
//
//
//    public boolean isListViewReachTop() {
//        boolean result=false;
//        if(getFirstVisiblePosition()==0){
//            View topChildView = getChildAt(0);
//            if (topChildView != null) {
//                result=topChildView.getTop()==0;
//            }
//        }
//        return result ;
//    }
//
//    public boolean isListViewReachBottom() {
//        boolean result=false;
//        if (getLastVisiblePosition() == (getCount() - 1)) {
//            View bottomChildView = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
//            if (bottomChildView != null) {
//                result= (getHeight() >= bottomChildView.getBottom());
//            }
//        }
//        return  result;
//    }

}

