package com.theworldofluster.example.ziang.tremblenumber.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.theworldofluster.example.ziang.tremblenumber.R;


/**
 * Created by Ziang on 2017/5/19.
 */
public class HttpDialog extends Dialog {


    public HttpDialog(Context context) {
        super(context);

        LayoutInflater factory = LayoutInflater.from(context);
        View grabView = factory.inflate(R.layout.htp_dialog, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setContentView(grabView);

    }
}
