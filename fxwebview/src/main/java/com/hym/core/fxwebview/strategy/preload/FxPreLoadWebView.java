package com.hym.core.fxwebview.strategy.preload;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.view.View;
import android.webkit.WebView;

import com.hym.core.fxwebview.strategy.FxWebViewSetting;

/**
 * Created by Neo on 2019/7/9.
 * Description : 预载webview
 */
public class FxPreLoadWebView extends BasePreLoadWebView<WebView>
{
	private static volatile FxPreLoadWebView preloadWebView = null;
	private Context context;
	
	public static FxPreLoadWebView getInstance()
	{
		return Holder.INSTANCE;
	}
	
	private static class Holder
	{
		private static final FxPreLoadWebView INSTANCE = new FxPreLoadWebView();
	}
	
	@Override
	public WebView getWebViewToUse( Context context )
	{
		WebView webView = getWebView( context );
		MutableContextWrapper contextWrapper = (MutableContextWrapper) webView.getContext();
		contextWrapper.setBaseContext(context);
		return webView;
	}
	
	@Override
	public WebView createWebView()
	{
		WebView webView = new WebView( new MutableContextWrapper( context ) );
		webView.setSaveEnabled(true);
		webView.setScrollBarStyle( View.SCROLLBARS_INSIDE_OVERLAY);
		FxWebViewSetting.webViewDefauleSetting( webView );
		return webView;
	}
}
