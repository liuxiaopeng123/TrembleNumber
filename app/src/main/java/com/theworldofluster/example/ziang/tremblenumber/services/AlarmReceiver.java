package com.theworldofluster.example.ziang.tremblenumber.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by liupeng on 2018/8/11.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PostDataService.class);
        context.startService(i);
    }
}
