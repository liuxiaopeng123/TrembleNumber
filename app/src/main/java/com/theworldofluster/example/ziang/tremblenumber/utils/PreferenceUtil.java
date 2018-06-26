package com.theworldofluster.example.ziang.tremblenumber.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 基类
 */
public class PreferenceUtil {

	private static SharedPreferences sp = null;

	public void init(Context c, String name) {
		sp = c.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public static void putBool(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();

	}

	public static boolean getBool(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);

	}

	public static void putInt(String key, int value) {
		sp.edit().putInt(key, value).commit();

	}

	public static int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public static void putLong(String key, long value) {
		sp.edit().putLong(key, value).commit();
	}

	public static long getLong(String key, long defValue) {
		return sp.getLong(key, defValue);
	}

	public static void putString(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	public static String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public static void putFloat(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	public static Float getFloat(String key, float defValue) {
		return sp.getFloat(key, defValue);
	}

	public static void Clear() {
		sp.edit().clear().commit();
	}

}
