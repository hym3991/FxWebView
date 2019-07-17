package com.hym.fxwebview;

import android.content.Context;
import android.webkit.WebView;

import com.hym.fxwebview.preload.FxPreLoadWebView;
import com.hym.fxwebview.sonic.FxSonicRuntimeImpl;
import com.hym.fxwebview.sonic.FxSonicSetting;

/**
 * Created by Neo on 2019/7/9.
 * Description :
 */
public class FxWebView
{
	//TODO: 研究使用content provide 进行初始化的操作
	//TODO: 研究使用支付宝的测试APP 进行内存溢出的查找
	
	private FxSonicSetting sonicSetting = null;
	
	private static class Holder
	{
		private static FxWebView INSTANCE = new FxWebView();
	}
	
	public static FxWebView getInstance()
	{
		return Holder.INSTANCE;
	}
	
	public FxWebView setSonicRunTime( FxSonicRuntimeImpl runTime )
	{
		if( sonicSetting == null )
		{
			sonicSetting = new FxSonicSetting();
		}
		sonicSetting.setSonicRunTime( runTime );
		return Holder.INSTANCE;
	}
	
	public WebView loadUrlWithSonic( Context context ,String url,CallBack callBack)
	{
		WebView webView = FxPreLoadWebView.getInstance().getWebViewToUse( context );
		return sonicSetting.loadUrlWithSonic(url,webView,callBack);
	}
	
	public boolean preLoadUrl(String url)
	{
		return sonicSetting.preLoadUrl(url);
	}
	
	public WebView getDefaultWebView(Context context)
	{
		return FxPreLoadWebView.getInstance().getWebViewToUse( context );
	}
	
	public interface CallBack
	{
		public void loadStart(String url);
		public void loadFinish(String url);
		public void loadError(String url,int code);
		public boolean shouldOverrideUrlLoading(String url);
	}
}
