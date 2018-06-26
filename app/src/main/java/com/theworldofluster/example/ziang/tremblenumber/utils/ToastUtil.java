/**
 * 
 */
package com.theworldofluster.example.ziang.tremblenumber.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author 赵魉
 *
 * 2015年11月19日
 */
public class ToastUtil {
	public static void showContent(Context context, String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
}
