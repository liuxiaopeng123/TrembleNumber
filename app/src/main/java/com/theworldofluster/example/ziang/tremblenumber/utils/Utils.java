package com.theworldofluster.example.ziang.tremblenumber.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theworldofluster.example.ziang.tremblenumber.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class Utils {

	public static final String savePath = "/sdcard/test/";

	public static final String saveFileName = savePath + "daijiaguania.apk";

	private static String CALENDARS_NAME = "test";
	private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
	private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
	private static String CALENDARS_DISPLAY_NAME = "测试账户";

	private static String CALANDER_URL = "content://com.android.calendar/calendars";
	private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
	private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

	public static int getDaysOfMonth(String  date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String time(long milSecond) {
		Date date = new Date(milSecond);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}


	//电商计算价格
	public static String Calculatingprice(double UnitPrice, List<Double> Specifications, int num) {
		Double str = UnitPrice;

        for(int i = 0;i<Specifications.size();i++){

        	if(Specifications.get(i)!=-999){

				str = str + Specifications.get(i);

			}

			if(i==Specifications.size()-1){

				str = str * num;

				return new DecimalFormat("0.00").format(str);
			}

		}

		return str+"";

	}

	//电商计算单个商品价格加规格
	public static String CalculatingpriceGG(double UnitPrice, List<Double> Specifications) {
		Double str = UnitPrice;

		for(int i = 0;i<Specifications.size();i++){

			if(Specifications.get(i)!=-999){

				str = str + Specifications.get(i);

			}

			if(i==Specifications.size()-1){

				return new DecimalFormat("0.00").format(str);
			}

		}

		return str+"";

	}

//	//分享

	public static void showShare(Context context,String content,String path) {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();

		// title标题，微信、QQ和QQ空间等平台使用
		oks.setTitle(content);
		// titleUrl QQ和QQ空间跳转链接
//		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我的健康周报分");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		oks.setImagePath(path);
//		oks.setImageUrl(path);
		Log.i("xiaopeng----path",path+"");
		// url在微信、微博，Facebook等平台中使用
//		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网使用
//		oks.setComment("我是测试评论文本");
		// 启动分享GUI
		oks.show(context);
	}
//
public static void BJSloadImg(Context context, String url, ImageView imageView){

	if(url.equals("")){
		url = "www.baidu.com";
	}

	Picasso.with(context)
			.load(url)
			//加载过程中的图片显示
			.placeholder(R.mipmap.expert_b)
			//加载失败中的图片显示
			//如果重试3次（下载源代码可以根据需要修改）还是无法成功加载图片，则用错误占位符图片显示。
			.error(R.mipmap.expert_b)
			.into(imageView);

}

    //时间差
	public static String getSJC(String startdata, String enddata) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date d1 = df.parse(enddata);

			Date d2 = df.parse(startdata);
			long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

			if(days>3){

				return startdata.substring(0,15);

			}else if(days<=3&&days!=0){

				return days+"天前";

			}else if(days==0&&hours!=0){

				return hours+"小时前";

			}else if(days==0&&hours==0&&minutes!=0){

				return minutes+"分钟前";

			}else{

				return startdata.substring(0,15);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return startdata.substring(0,15);
	}
	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getCurrentDate2() {
		Date now = new Date();
		now.setTime(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getyear() {
		Date now = new Date();
		now.setTime(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getmouth() {
		Date now = new Date();
		now.setTime(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM");
		String date = dateFormat.format(now);
		return date;
	}

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getday() {
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd");
        String date = dateFormat.format(now);
        return date;
    }

	/*
 * 将时间转换为时间戳
 */
	public static long dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		return ts;
	}

	public static void addCalendarEvent(Context context, String title, String description, long beginTime){
		// 获取日历账户的id
		int calId = checkAndAddCalendarAccount(context);
		if (calId < 0) {
			// 获取账户id失败直接返回，添加日历事件失败
			return;
		}

		ContentValues event = new ContentValues();
		event.put("title", title);
		event.put("description", description);
		// 插入账户的id
		event.put("calendar_id", calId);

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(beginTime);//设置开始时间
		long start = mCalendar.getTime().getTime();
		mCalendar.setTimeInMillis(start + 10*1000);//设置终止时间
		long end = mCalendar.getTime().getTime();

		event.put(CalendarContract.Events.DTSTART, start);
		event.put(CalendarContract.Events.DTEND, end);
		event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
		event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
		//添加事件
		Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
		if (newEvent == null) {
			// 添加日历事件失败直接返回
			return;
		}
		//事件提醒的设定
		ContentValues values = new ContentValues();
		values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
		// 提前10分钟有提醒
		values.put(CalendarContract.Reminders.MINUTES, 10);
		values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
		Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
		if(uri == null) {
			// 添加闹钟提醒失败直接返回
			return;
		}
	}

    //1.检查是否有现有存在的账户。存在则返回账户id，否则返回-1
	public static int checkCalendarAccount(Context context) {
		Cursor userCursor = context.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
		try {
			if (userCursor == null)//查询返回空值
				return -1;
			int count = userCursor.getCount();
			if (count > 0) {//存在现有账户，取第一个账户的id返回
				userCursor.moveToFirst();
				return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
			} else {
				return -1;
			}
		} finally {
			if (userCursor != null) {
				userCursor.close();
			}
		}
	}

	//检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
	public static int checkAndAddCalendarAccount(Context context){
		int oldId = checkCalendarAccount(context);
		if( oldId >= 0 ){
			return oldId;
		}else{
			long addId = addCalendarAccount(context);
			if (addId >= 0) {
				return checkCalendarAccount(context);
			} else {
				return -1;
			}
		}
	}

	public static long addCalendarAccount(Context context) {
		TimeZone timeZone = TimeZone.getDefault();
		ContentValues value = new ContentValues();
		value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

		value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
		value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
		value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
		value.put(CalendarContract.Calendars.VISIBLE, 1);
		value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
		value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
		value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
		value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
		value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
		value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

		Uri calendarUri = Uri.parse(CALANDER_URL);
		calendarUri = calendarUri.buildUpon()
				.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
				.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
				.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
				.build();

		Uri result = context.getContentResolver().insert(calendarUri, value);
		long id = result == null ? -1 : ContentUris.parseId(result);
		return id;
	}

	public static int getGapCount(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}


		public static long DateOfCalculation(String starttime, String endtime) throws ParseException {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1=df.parse(endtime);
			Date d2=df.parse(starttime);

			return (d1.getTime()-d2.getTime())/(60*60*1000*24);
		}

	/***
	 *  获取指定日后 后 dayAddNum 天的 日期
	 *  @param day  日期，格式为String："2013-9-3";
	 *  @param dayAddNum 增加天数 格式为int;
	 *  @return
	 */
	public static String getDateStr(String day, long dayAddNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = null;
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateOk = simpleDateFormat.format(newDate2);
		return dateOk;
	}

	public static double getLiLv(double lilv,int position) {
		switch (position) {
		case 0:
			lilv = lilv * 0.7;
			return lilv;
		case 1:
			lilv = lilv * 0.75;
			return lilv;
		case 2:
			lilv = lilv * 0.8;
			return lilv;
		case 3:
			lilv = lilv * 0.85;
			return lilv;
		case 4:
			lilv = lilv * 0.9;
			return lilv;
		case 5:
			lilv = lilv * 0.95;
			return lilv;
		case 6:
			lilv = lilv * 1;
			return lilv;
		case 7:
			lilv = lilv * 1.1;
			return lilv;
		case 8:
			lilv = lilv * 1.2;
			return lilv;
		case 9:
			lilv = lilv * 1.3;
			return lilv;
		default:
			return lilv;
		}

	}
	public static boolean isNull(String values) {
		if ((values != null) && (!"".equals(values))
				&& (!"null".equals(values))) {
			return false;
		}
		return true;
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
		String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
	}

	//获取当前时间
	public static String getCurDate(String pattern){
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		return sDateFormat.format(new Date());
	}

    //获取系统时间戳
	public long getCurTimeLong(){
		long time= System.currentTimeMillis();
		return time;
	}
   //时间戳转换成字符窜
	public static String getDateToString(long milSecond) {
		Date date = new Date(milSecond);
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日  HH:mm");
		return format.format(date);
	}

	// 字符串的非空
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input) || "null".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	// 判断网络是否连接
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 验证身份证
	public static boolean doAuthentication(String shenfen) {
		Pattern pattern = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher m = pattern.matcher(shenfen);
		return m.matches();
	}

	// 验证只能输入数字和字母
	public static boolean InputFigureLetter(String input) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = pattern.matcher(input);
		return m.matches();
	}

	/**
	 * 字符串转日期(yyyy-MM-dd)
	 * */
	public static Date StrToDate(String str) {
		return StrToDate(str, "yyyy-MM-dd");
	}

	/**
	 * 字符串转日期()
	 * */
	public static Date StrToDate2(String str) {
		return StrToDate(str, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到几天前的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到当前时间之后的几个小时时间
	 *
	 * @param differhour
	 * @return
	 */
	public static String getCurrentHourAfter(int differhour) {
		long currenttime = new Date().getTime();
		Date dat = new Date(currenttime + 1000 * 60 * 60 * differhour);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(dat);
	}

	/**
	 * 得到当前时间之前的几个小时时间
	 *
	 * @param differhour
	 * @return
	 */
	public static String getCurrentHourBefor(int differhour) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.ENGLISH);
		long currenttime = new Date().getTime();
		Date dat = new Date(currenttime - 1000 * 60 * 60 * 2);
		// format.parse(format.format(dat))
		return format.format(dat);
	}

	/**
	 * 字符串转日期
	 * */
	public static Date StrToDate(String str, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换成Java字符串
	 *
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		String str = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			str = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 时间转化
	 *
	 * @param time
	 * @param type type类型
	 * @return
	 */
	public static String DateToStr(Date time, String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(type);
		String date = dateFormat.format(time);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 *
	 * @return
	 */
	public static Integer getCurrentDate_MM() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String date = dateFormat.format(now);
		return isInteger(date);
	}

	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd
	 *
	 * @return
	 */
	public static Integer getCurrentDate_dd() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		String date = dateFormat.format(now);
		return isInteger(date);
	}

	public static String getstringrandom() {
		Random random = new Random();// 定义随机类
		int result = random.nextInt(10000);// 返回[0,10)集合中的整数，注意不包括10
		return result + 1+""; // +1后，[0,10)集合变为[1,11)集合，满足要求
	}

	/**
	 * 获取指定日期的月
	 *
	 * @return
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static Integer getDate_MM(String data) throws ParseException {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat2 = new SimpleDateFormat("MM");
		Date s = dateFormat1.parse(data);
		String date = dateFormat2.format(s);
		return isInteger(date);
	}



	/**
	 * 获取当前时间，格式为 :yyyy-MM-dd HH:mm
	 *
	 * @return
	 */
	public static String getCurrentDate3() {
		Date now = new Date();
		now.setTime(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = dateFormat.format(now);
		return date;
	}

	/**
	 * 获取明天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
	 *
	 * @return
	 */
	public static String getTomorrowDateAtZeroAM() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString.substring(0, dateString.length() - 8) + "00:00:00";
	}

	/**
	 * 获取指定日期的日
	 *
	 * @return
	 * @throws ParseException
	 */
	public static Integer getDate_DD(String data) throws ParseException {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat2 = new SimpleDateFormat("dd");
		Date s = dateFormat1.parse(data);
		String date = dateFormat2.format(s);
		return isInteger(date);
	}

	/**
	 * 比较两个 yyyy-MM-dd 格式的日期字符串时间前后
	 *
	 * @param date1
	 * @param date2
	 * @return true:"date1在date2后" , false:"date1在date2前"
	 */
	public static boolean dateComparator(String date1, String date2) {
		return dateComparator(date1, date2, "yyyy-MM-dd");
	}

	/**
	 * 比较两个 yyyy-MM-dd HH:mm:ss 格式的日期字符串时间前后
	 *
	 * @param date1
	 * @param date2
	 * @return true:"date1在date2前" , false:"date1在date2后"
	 */
	public static boolean dateComparator2(String date1, String date2) {
		return dateComparator(date1, date2, "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean dateComparator(String date1, String date2, String str) {
		DateFormat df = new SimpleDateFormat(str);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return false;
			} else if (dt1.getTime() < dt2.getTime()) {
				return true;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取两个日期的差 yyyy-MM-dd
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDifference1(String date1, String date2) {
		return dateDifference(date1, date2, "yyyy-MM-dd");
	}

	/**
	 * 获取两个日期的差 yyyy-MM-dd HH:mm:ss
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDifference2(String date1, String date2) {
		return dateDifference(date1, date2, "yyyy-MM-dd HH:mm:ss");

	}

	/**
	 * 获取两个日期的差
	 *
	 * @param date1
	 * @param date2
	 * @param str
	 * @return
	 */
	public static long dateDifference(String date1, String date2, String str) {
		DateFormat df = new SimpleDateFormat(str);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			long temp = dt2.getTime() - dt1.getTime();
			long result = temp / (1000 * 60);
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0;
		}
	}

	/**
	 * 得到两个日期的差
	 *
	 * @param fDate
	 * @param oDate
	 * @return 天数
	 */
	public static int daysOfTwo(String fDate, String oDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt1;
		Date dt2;
		try {
			dt1 = df.parse(fDate);
			dt2 = df.parse(oDate);

			Calendar aCalendar = Calendar.getInstance();

			aCalendar.setTime(dt1);

			int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
			int year1 = aCalendar.get(Calendar.YEAR);

			aCalendar.setTime(dt2);

			int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
			int year2 = aCalendar.get(Calendar.YEAR);

			return (day2 - day1) + (year2 - year1) * 365;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 比较两个数的大小
	 *
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean numComparator(String num1, String num2) {
		int int1;
		int int2;
		try {
			int1 = Integer.parseInt(num1.trim());
			int2 = Integer.parseInt(num2.trim());
			return int1 > int2;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param time
	 *            需要获取的日期
	 * @return 当前日期是星期几，(从0开始，周日、周一.....)
	 */
	public static int getWeekOfDate(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt;
		int week = 0;
		try {
			dt = df.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (week < 0)
				week = 0;
		} catch (ParseException e) {
		}
		return week;
	}

	/**
	 * 判断是否为double类型
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDoubleNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) { // 不是数字
			return false;
		}
	}

	/**
	 * 判断是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) { // 不是数字
			return false;
		}
	}

	/**
	 * 关于EditText的判断方法
	 *
	 * @param editText
	 * @param yajin
	 *            限额大小
	 * @param c
	 */
	public static void setPricePoint(final EditText editText,
									 final double yajin, final Context c) {
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
				if (isDoubleNumeric(s.toString())) {
					if (s.toString().contains(".")) {
						if (s.length() - 1 - s.toString().indexOf(".") > 2) {
							s = s.toString().subSequence(0,
									s.toString().indexOf(".") + 3);
							editText.setText(s);
							editText.setSelection(s.length());
						}
					}
					if (s.toString().trim().substring(0).equals(".")) {
						s = "0" + s;
						editText.setText(s);
						editText.setSelection(2);
					}
					if (s.toString().startsWith("0")
							&& s.toString().trim().length() > 1) {
						if (!s.toString().substring(1, 2).equals(".")) {
							editText.setText(Utils.isDouble(s.subSequence(0,
									s.length()).toString())
									+ "");
							// editText.setSelection(s.length()+1);
							return;
						}
					}
				} else {
					return;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals("")) {
					editText.setText("0");
					return;
				}
				if (!isDoubleNumeric(s.toString())) {
					editText.setText("0");
					Toast.makeText(c, "请输入正确价格", Toast.LENGTH_SHORT).show();
					return;
				}
				double strcount = Double.parseDouble(s.toString());
				double count = yajin;
				if (strcount > count) {
					editText.setText("0");
					Toast.makeText(c, "超出限额", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/** 时间秒数转换为时间 */
	public static String getDatesft(Long dates) {
		// long sstime = dates.toString();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getFullDate(Long dates) {
		// long sstime = dates.toString();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getDate(Long dates) {
		// long sstime = dates.toString();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/** 时间秒数转换为时间 */
	public static String getDate2(Long dates) {
		// long sstime = dates.toString();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dates);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String checkInt(String num) {
		return (num == null || !Utils.isNumeric(num)) ? "0" : num;
	}

	public static String checkDouble(String num) {
		return (num == null || "NaN".equals(num) || !Utils.isDoubleNumeric(num)) ? "0"
				: num;
	}

	public static String checkStr(String str) {
		return Utils.isEmpty(str) ? "" : str;
	}

	/** 获取前3天时间 */
	public static String FrontThreeDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3); // 得到前三天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 获取前15天时间 */
	public static String FroutFifteenFDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -15); // 得到前十五天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 获取前30天时间 */
	public static String FroutthirtyDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30); // 得到前三十天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/**
	 * 几分钟以后的时间
	 *
	 * @param after
	 * @return
	 */
	public static String MinueLaterTime(int after) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, after); // 得到前三十天
		Date date = calendar.getTime();
		String dates = DateToStrtimeminute(date);
		return dates;
	}

	/** 日期转换字符串 */
	public static String DateToStrtimeminute(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/** 检查Integer数据 */
	public static Integer isInteger(Integer num) {
		return isInteger(num + "");
	}

	/** 检查Double数据 */
	public static Double isDouble(Double num) {
		return isDouble(num + "");
	}

	/** String检查Integer数据 */
	public static Integer isInteger(String num) {
		return Integer.parseInt(checkInt(num));
	}

	/** String检查Double数据 */
	public static Double isDouble(String num) {
		return Double.parseDouble(checkDouble(num));
	}

	/**
	 * 获取小时和分钟的字符串
	 *
	 * @param mDate orderDate
	 * @return
	 */
	public static String getShorDate(Date mDate) {
		if (mDate == null) {
			return "00:00";
		}
		String hoursStr = "";
		String minutesStr = "";
		int hours = mDate.getHours();
		int minutes = mDate.getMinutes();
		if (hours < 10) {
			hoursStr = "0" + hours;
		} else {
			hoursStr = "" + hours;
		}
		if (minutes < 10) {
			minutesStr = "0" + minutes;
		} else {
			minutesStr = "" + minutes;
		}
		return hoursStr + ":" + minutesStr;
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleMin(double a, double b) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a - b);
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 *
	 * @param x a
	 * @param y b
	 * @return
	 */
	public static double getAddDouble(double x, double y) {
		BigDecimal add1 = new BigDecimal(Double.toString(x));
		BigDecimal add2 = new BigDecimal(y + "");

		return add1.add(add2).doubleValue();
	}

	/**
	 * 得到小数点后两位
	 *
	 * @param x
	 * @return
	 */
	public static double parseDecimalDouble2(double x) {
		BigDecimal bg = new BigDecimal(isDouble(x));
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 得到小数点后两位
	 *
	 * @param str
	 * @return
	 */
	public static double parseDecimalDouble2(String str) {
		BigDecimal bg = new BigDecimal(isDouble(str));
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个double相减 返回保留2位小数的字符串
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleMin(String a, String b) {
		double x = isDouble(a);
		double y = isDouble(b);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(x - y);
	}

	/**
	 * 两个double相加 返回保留2位小数的字符串
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleAdd(double a, double b) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a + b);
	}

	/**
	 * 两个double相加 返回保留2位小数的字符串
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDoubleAdd(String a, String b) {
		double x = isDouble(a);
		double y = isDouble(b);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(x + y);
	}

	/**
	 * double取两位
	 *
	 * @param a
	 * @return
	 */
	public static String formatDoubleReturnString(double a) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(a);
	}

	/**
	 * 判断app是否安装
	 *
	 * @author gaof
	 * @return boolean
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
				if (pn.contains(packageName)) {
					String version = pinfo.get(i).versionName;// 获取版本号
					int versionNum = Utils.isInteger(version.replace(".", "")
							.trim());
				}
			}
		}
		return pName.contains(packageName);
	}

	/**
	 * 得到当前应用版本号
	 *
	 * @author gaof
	 * @return boolean
	 */
	public static String getAppVersion(Context context) {
		String version = "1.0.0";
		final PackageManager packageManager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return version;
		}
		// info.versionCode
		// info.packageName;
		// info.signatures;

		return version;
	}

	/**
	 * 获取银联插件版本号
	 *
	 * @param context
	 * @return 插件版本号
	 */
	public static String getYinlianPluginVersion(Context context) {
		String version = "1.0.0";
		PackageInfo info = null;
		PackageManager packageManager = context.getPackageManager();
		try {
			info = packageManager.getPackageInfo("com.chinaums.mpospluginpad",
					0);
			version = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * double取两位
	 *
	 * @param a
	 * @return
	 */
	public static double formatDoubleReturnDouble(double a) {
		DecimalFormat df = new DecimalFormat("0.00");
		return isDouble(df.format(a));
	}

	/**
	 * 把电话号码替换成带星号的 例如：182****6742 假如不是电话号码的就不进行替换
	 *
	 * @param phone
	 * @return
	 */
	public static String replacePhoneWithAsterisk(String phone) {
		if (phone==null || phone.length()!=11) {
			return "";
		}
		String newphone = phone;
		if (isMobileNO(newphone)) {
			newphone = phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return newphone;
	}

	/**
	 * 获取屏幕分辨率的高度
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		Display mDisplay = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		return mDisplay.getHeight();
	}


	/**
	 * 通过backtype的种类进行确认字段1.还车结算界面,2.违章结算界面,3.维修结算界面,4.取车租金支付,5.取车先付结算界面
	 * 6.预授权列表界面 0：不关联结算单 1：一次结算 2：二次结算 取车 支付 0 还车支付 1 违章预授权列表 2
	 *
	 * @author gaof
	 */
	public static String ConfirmSettlementTimesVaule(String backtype) {
		String SettlementTimes = "";
		switch (Utils.isInteger(backtype)) {
			case 1:
				SettlementTimes = "1";
				break;
			case 2:
				SettlementTimes = "1";
				break;
			case 3:
				SettlementTimes = "1";
				break;
			case 4:
				SettlementTimes = "0";
				break;
			case 5:
				SettlementTimes = "0";
				break;
			case 6:
				SettlementTimes = "2";
				break;
		}
		return SettlementTimes;
	}

	/**
	 * @author gaof
	 *            将银行卡号处理为前四后6的格式
	 */
	public static String ChangeToBankCard(String bankcard) {
		String bankcardS = "";
		bankcardS = bankcard.substring(0, 6) + "*******"
				+ bankcard.substring(bankcard.length() - 4, bankcard.length());
		return bankcardS;
	}

	/**
	 * @author gaof
	 * @param billsMID
	 *            billsTID 商户编号,商户终端号
	 */
	public static boolean billsMidOrTid(String billsMID, String billsTID) {
		if (Utils.isEmpty(billsMID) || Utils.isEmpty(billsTID)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证邮政编码
	 *
	 * @param post
	 *            邮编
	 * @return
	 */
	public static boolean checkPost(String post) {
		if (post.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * double 乘法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 根据月日判断星座
	 *
	 * @return int
	 */
	public static String getConstellation(int m, int d) {

		final String[] constellationArr = { "魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
				"双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

		final int[] constellationEdgeDay = { 20, 18, 20, 20, 20, 21, 22, 22,
				22, 22, 21, 21 };
		int month = m;
		int day = d;
		if (day <= constellationEdgeDay[month - 1]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];

	}

	/**
	 * 调用系统相册
	 */
	public static void openSysPhone(int flag, Context context) {
		Intent intent2 = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) context).startActivityForResult(intent2, flag);
	}

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger1(value) || isDouble1(value);
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger1(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble1(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** 获取设备唯一标识 */
	public static String getDeviceUnique(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param filepath
	 * @return
	 */
	public static byte[] getBase64(String filepath) {
		if (Utils.isEmpty(filepath)) {
			return null;
		}
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(filepath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return android.util.Base64.encode(data, android.util.Base64.DEFAULT);
	}

	public static String getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}

		return age + "";
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 验证邮箱格式是否正确
	 *
	 * @param mail
	 * @return
	 */
	public static boolean isMail(String mail) {
		boolean ismail = false;
		String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		// String check =
		// "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		try {
			Pattern p = Pattern.compile(check);
			Matcher m = p.matcher(mail);
			ismail = m.matches();
		} catch (Exception e) {
			ismail = false;
		}
		return ismail;
	}
	/**
	 * 获取 数据摘要
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source){
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
			// 进制需要 32 个字符
			int k = 0;// 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
				// 进制字符的转换
				byte byte0 = tmp[i];// 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
			}
			s = new String(str);// 换后的结果转换为字符串
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static int getrandom() {
		Random random = new Random();// 定义随机类
		int result = random.nextInt(10000000);// 返回[0,10)集合中的整数，注意不包括10
		return result + 1; // +1后，[0,10)集合变为[1,11)集合，满足要求
	}
}
