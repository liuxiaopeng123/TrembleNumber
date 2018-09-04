package com.theworldofluster.example.ziang.tremblenumber.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by liupeng on 2018/9/4.
 */

public class AccountTxtView extends android.support.v7.widget.AppCompatEditText {
    private final char CUT = ' ';
    public AccountTxtView(Context context) {
        super(context);
    }
    public AccountTxtView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AccountTxtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (text == null || text.length() == 0)
            return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {//添加分割符
            if (i != 3 && i != 8 && text.charAt(i) == CUT) {
                continue;
            } else {
                sb.append(text.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9)
                        && sb.charAt(sb.length() - 1) != CUT) {
                    sb.insert(sb.length() - 1, CUT);
                }
            }
        }
        //防止多次设置值
        if (!sb.toString().equals(text.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == CUT) {
                if (lengthBefore == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (lengthBefore == 1) {
                    index--;
                }
            }
            setText(sb.toString());
            setSelection(index);
        }else{//删除时候判断
            String line = text.subSequence(text.length() - 1, text.length()).toString();
            if (line.equals(String.valueOf(CUT))) {//如果删除碰到‘-'符号，则默认去除
                sb.deleteCharAt(text.subSequence(0, text.length() - 1).length());
                setText(sb.toString());
                setSelection(sb.length());
            }
        }
    }
    public String getPhone() {
        String result = null;
        String val = getText().toString();
        if (val == null || val.isEmpty())
            return "";
        result = val.replace(String.valueOf(CUT), "");
        return result;
    }
}