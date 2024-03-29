package com.hym.fxwebview.install;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.hym.fxwebview.preload.FxPreLoadWebView;
import com.hym.fxwebview.utils.FxDateUtils;
import com.hym.fxwebview.utils.FLog;
import com.tencent.smtt.sdk.QbSdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Neo on 2019/7/16.
 * Description :
 */
public class FxWebViewInstaller extends ContentProvider
{
	
	@Override
	public boolean onCreate()
	{
		Context context = getContext();
		String START = FxDateUtils.getCurDateSecond();
		String TOTAL = START;
		FLog.d( "/*FxWebViewInstaller begin success START:"+START );
		FxPreLoadWebView.getInstance().setContext( context ).webViewProLoad();
		START = FxDateUtils.getCurDateSecond();
		FLog.d( "**FxPreLoadWebView install success use:"+ FxDateUtils.getDistance( START ) +" 毫秒" );
		
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback()
		{
			@Override
			public void onViewInitFinished(boolean arg0)
			{
				FLog.d( "X5 onViewInitFinished is " + arg0);
			}
			
			@Override
			public void onCoreInitFinished()
			{
				FLog.d( "X5 onCoreInitFinished");
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getContext(),  cb);
		if(!QbSdk.isTbsCoreInited())
		{
			// preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
			QbSdk.preInit(getContext(), null);// 设置X5初始化完成的回调接口
		}
		START = FxDateUtils.getCurDateSecond();
		FLog.d( "**X5 install success use:"+ FxDateUtils.getDistance( START ) +" 毫秒" );
		FLog.d( "\\*FxWebView Installer end success use:"+ FxDateUtils.getDistance( TOTAL ) +" 毫秒");
		return true;
	}
	
	@Nullable
	@Override
	public Cursor query(
			@NonNull Uri uri ,
			@Nullable String[] projection ,
			@Nullable String selection ,
			@Nullable String[] selectionArgs ,
			@Nullable String sortOrder )
	{
		return null;
	}
	
	@Nullable
	@Override
	public String getType( @NonNull Uri uri )
	{
		return null;
	}
	
	@Nullable
	@Override
	public Uri insert(
			@NonNull Uri uri ,
			@Nullable ContentValues values )
	{
		return null;
	}
	
	@Override
	public int delete(
			@NonNull Uri uri ,
			@Nullable String selection ,
			@Nullable String[] selectionArgs )
	{
		return 0;
	}
	
	@Override
	public int update(
			@NonNull Uri uri ,
			@Nullable ContentValues values ,
			@Nullable String selection ,
			@Nullable String[] selectionArgs )
	{
		return 0;
	}
}
