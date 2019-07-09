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
	}
	
	private Context getContext()
	{
		return context;
	}
}
