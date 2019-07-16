package com.hym.fxwebview.control.web;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Toast;

import com.hym.fxwebview.R;
import com.hym.fxwebview.base.BaseWebActivity;
import com.hym.fxwebview.config.Const;
import com.hym.fxwebview.sonic.SonicSessionClientImpl;
import com.hym.fxwebview.utils.DateUtils;
import com.hym.fxwebview.widget.X5WebView;
import com.tencent.sonic.sdk.SonicSession;

import androidx.annotation.Nullable;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class DefaultWebActivity extends BaseWebActivity<DefaultWebActivityVM>
{
	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setTitle( "普通网页加载" );
	}
	
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_web_def;
	}
	
	@Override
	public DefaultWebActivityVM setViewModel()
	{
		return new DefaultWebActivityVM();
	}
	
	@Override
	public int getRddWebViewId()
	{
		return R.id.web_root;
	}
	
	@Override
	public void initCustomData()
	{
	
	}
	
	@Override
	public void doHandleMessage( Message msg )
	{
	
	}
	
	@Override
	public String getOpenUrl()
	{
		return Const.OPEN_URL;
	}
	
	String startTime = "";
	
	@Override
	public void doOnPageStarted( String url )
	{
		startTime = DateUtils.getCurDateSecond();
		Log.d(TAG,"doOnPageStarted:"+ startTime );
	}
	
	@Override
	public boolean doOnshouldOverrideUrlLoading( String url )
	{
		return false;
	}
	
	@Override
	public void doOnPageFinished(
			String url ,
			WebView view )
	{
		Log.d(TAG,"doOnPageFinished:"+ DateUtils.getCurDateSecond() );
		if( !TextUtils.isEmpty( startTime ) )
		{
			String result = "加载总耗时:"+ DateUtils.getDistanceSecond(startTime);
			Log.d(TAG, result);
			Toast.makeText( this,result,Toast.LENGTH_LONG ).show();
		}
	}
	
	@Override
	public void doOnReceiverError()
	{
	
	}
	
	@Override
	public WebResourceResponse doShouldInterceptRequest( String url )
	{
		
		return null;
	}
	
	@Override
	public X5WebView getX5WebView()
	{
		return null;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public SonicSession getSonicSession()
	{
		return null;
	}
	
	@Override
	public SonicSessionClientImpl getSonicSessionClientImpl()
	{
		return null;
	}
}
