package com.hnshilin.ddwallet.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 */
public class LogUtil {
	public static final String projectName = "ddwallet";
	public static void e(String className, String content) {
		Log.e(className, "time:" + getDateTime() + ";" + "[e]" + ";"
				+ projectName+":\n" + content);
	}

	public static void d(String className, String content) {
		Log.d(className, "time:" + getDateTime() + ";" + "[d]" + ";"
				+ projectName+":\n"  + content);
	}

	public static void i(String className, String content) {
		Log.i(className, "time:" + getDateTime() + ";" + "[i]" + ";"
				+ projectName+":\n"  + content);
	}

	public static void w(String className, String content) {
		Log.w(className, "time:" + getDateTime() + ";" + "[w]" + ";"
				+ projectName+":\n"  + content);
	}

	public static void v(String className, String content) {
		Log.v(className, "time:" + getDateTime() + ";" + "[v]" + ";"
				+ projectName+":\n"  + content);
	}
	public static void a(String content) {
		Log.d("System.out", "time:" + getDateTime() + ";" + "[a]" + ";"
				+ "sharehome:\n" + content);
	}
	
	
	
	public static void e(String className, String content, Throwable e) {
		Log.e(className, "time:" + getDateTime() + ";" + "[e]" + ";"
				+ projectName+":\n"  + content, e);
	}

	public static void d(String className, String content, Throwable e) {
		Log.d(className, "time:" + getDateTime() + ";" + "[d]" + ";"
				+ projectName+":\n"  + content, e);
	}

	public static void i(String className, String content, Throwable e) {
		Log.i(className, "time:" + getDateTime() + ";" + "[i]" + ";"
				+ projectName+":\n"  + content, e);
	}

	public static void w(String className, String content, Throwable e) {
		Log.w(className, "time:" + getDateTime() + ";" + "[w]" + ";"
				+ projectName+":\n"  + content, e);
	}

	public static void v(String className, String content, Throwable e) {
		Log.v(className, "time:" + getDateTime() + ";" + "[v]" + ";"
				+ projectName+":\n"  + content, e);
	}
	public static void a(String content, Throwable e) {
		Log.d("System.out", "time:" + getDateTime() + ";" + "[a]" + ";"
				+ projectName+":\n"  + content, e);
	}
	/*
	 * public static void main(String[] args) { System.out.println("class:" +
	 * "worleLog"); System.out.println("time:" + getDateTime() + ";" + "[d]" +
	 * ";"); System.out.println("content:"+"当前内容"); }
	 */

	/**
	 * 获取系统时间
	 * 
	 * @return String 2016-6-12 10:53:05:888
	 */
	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SS");
		Date date = new Date(System.currentTimeMillis());
		String time = dateFormat.format(date);
		return time;
	}
}
