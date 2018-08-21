package com.theworldofluster.example.ziang.tremblenumber;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.StrictMode;

import com.theworldofluster.example.ziang.tremblenumber.utils.PreferenceUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by USR_LJQ on 2015-11-17.
 */
public class MyApplication extends Application {

    public enum SERVICE_TYPE{
        TYPE_USR_DEBUG,TYPE_NUMBER,TYPE_STR,TYPE_OTHER;
    }

    private static Context context;

    private final List<BluetoothGattCharacteristic> characteristics = new ArrayList<>();

    private BluetoothGattCharacteristic characteristic;


    public static SERVICE_TYPE serviceType ;



    public List<BluetoothGattCharacteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<BluetoothGattCharacteristic> characteristics) {
        this.characteristics.clear();
        this.characteristics.addAll(characteristics);
    }


    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public BluetoothGattCharacteristic getCharacteristic() {
        return characteristic;
    }

    public PreferenceUtil preferenceUtil = new PreferenceUtil();
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(false);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        context = getApplicationContext();

        preferenceUtil.init(getApplicationContext(), "Z_jiatianxia");

        //JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }


    }

    public static Context getContext(){
        return context;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


}
