package com.hym.fxwebview.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Neo on 2019/7/8.
 * Description :
 */
public class BaseApplication extends Application
{
	
	private static Context context;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		context = getApplicationContext();
		//String START = FxDateUtils.getCurDateSecond();
		//Log.d("FxWebView","首次创建开始时间:"+ START );
		//FxPreLoadWebView.getInstance().setContext( getApplicationContext() ).createWebView();
		//START = FxDateUtils.getCurDateSecond();
		//Log.d("FxWebView","首次创建创建开始结束:"+ START);
		//Log.d("FxWebView","首次创建总耗时:"+ FxDateUtils.getDistance( START ) +" 毫秒");
		
		
		//Log.d("FxWebView","再次创建开始时间:"+ START );
		//FxPreLoadWebView.getInstance().setContext( getApplicationContext() ).createWebView();
		//START = FxDateUtils.getCurDateSecond();
		//Log.d("FxWebView","再次创建创建开始结束:"+ START);
		//Log.d("FxWebView","再次创建总耗时:"+ FxDateUtils.getDistance( START ) +" 毫秒");
		
	}
	
	public static Context getContext()
	{
		return context;
	}
}
