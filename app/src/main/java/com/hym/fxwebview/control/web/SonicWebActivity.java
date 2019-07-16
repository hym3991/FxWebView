package com.hym.fxwebview.control.web;

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
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class SonicWebActivity extends BaseWebActivity<SonicWebActivityVM>
{
	private SonicSession sonicSession;
	private SonicSessionClientImpl sonicSessionClient = null;
	
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_web_sonic;
	}
	
	@Override
	public SonicWebActivityVM setViewModel()
	{
		return new SonicWebActivityVM();
	}
	
	@Override
	public X5WebView getX5WebView()
	{
		return null;
	}
	
	@Override
	public int getRddWebViewId()
	{
		return R.id.activity_sonic_web_root;
	}
	
	@Override
	public void initCustomData()
	{
		SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
		sessionConfigBuilder.setSupportLocalServer(true);
		sonicSession = SonicEngine.getInstance().createSession(Const.OPEN_URL, sessionConfigBuilder.build());
		if (null != sonicSession) {
			sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
		} else {
			Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
		}
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
		Log.d(TAG,"sonic doOnPageStarted:"+ startTime );
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
		Log.d(TAG,"sonic doOnPageFinished:"+ DateUtils.getCurDateSecond() );
		if( !TextUtils.isEmpty( startTime ) )
		{
			String result = "sonic加载总耗时:"+ DateUtils.getDistanceSecond(startTime);
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
	protected void onDestroy()
	{
		super.onDestroy();
		sonicSession = null;
		sonicSessionClient = null;
	}
	
	@Override
	public SonicSession getSonicSession()
	{
		return sonicSession;
	}
	
	@Override
	public SonicSessionClientImpl getSonicSessionClientImpl()
	{
		return sonicSessionClient;
	}
}
