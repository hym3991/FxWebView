package com.hym.fxwebview.utils;

import android.util.Log;

/**
 * Created by Neo on 2019/7/16.
 * Description :
 */
public class FLog
{
	private static String TAG = "<<FXWEB>>";
	public static void d(String msg)
	{
		Log.d( TAG,msg );
	}
	
	public static void e(String msg)
	{
		Log.e( TAG,msg );
	}
	
	public static void v(String msg)
	{
		Log.v( TAG,msg );
	}
}
