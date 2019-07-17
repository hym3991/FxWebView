package com.hym.fxwebview.sonic;

import android.webkit.WebView;

import com.hym.fxwebview.FxWebView;
import com.hym.fxwebview.FxWebViewSetting;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

/**
 * Created by Neo on 2019/7/11.
 * Description :
 */
public class FxSonicSetting
{
	public void setSonicRunTime(FxSonicRuntimeImpl runTime)
	{
		if (!SonicEngine.isGetInstanceAllowed())
		{
			SonicEngine.createInstance(runTime, new SonicConfig.Builder().build());
		}
	}
	private SonicSession sonicSession;
	private FxSonicSessionClientImpl sonicSessionClient = null;
	
	public WebView loadUrlWithSonic(String url,WebView webView,
			FxWebView.CallBack callBack )
	{
		FxWebViewSetting.webViewDefauleSetting( webView ,callBack,sonicSession);
		SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
		sessionConfigBuilder.setSupportLocalServer(true);
		sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
		if (null != sonicSession) {
			sonicSession.bindClient(sonicSessionClient = new FxSonicSessionClientImpl());
		}
		sonicSessionClient.bindWebView(webView);
		sonicSessionClient.clientReady();
		
		return webView;
	}
	
	public boolean preLoadUrl( String url )
	{
		SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
		sessionConfigBuilder.setSupportLocalServer(true);
		
		// preload session
		return SonicEngine.getInstance().preCreateSession( url , sessionConfigBuilder.build());
	}
}
