package com.hym.core.fxwebview.strategy;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.webkit.WebView;

import java.util.Stack;

/**
 * Created by Neo on 2019/7/9.
 * Description : 预载webview
 */
public class FxPreLoadWebView
{
	private static volatile FxPreLoadWebView preloadWebView = null;
	private static final Stack<WebView> mCachedWebViewStack = new Stack<>();
	private static final int CACHED_WEBVIEW_MAX_NUM = 2;
	private Context context;
	
	public static FxPreLoadWebView getInstance()
	{
		return Holder.INSTANCE;
	}
	
	private static class Holder
	{
		private static final FxPreLoadWebView INSTANCE = new FxPreLoadWebView();
	}
	
	public void webViewPreLoad()
	{
		Looper.myQueue().addIdleHandler( new MessageQueue.IdleHandler()
		{
			@Override
			public boolean queueIdle()
			{
				if (mCachedWebViewStack.size() < CACHED_WEBVIEW_MAX_NUM)
				{
					mCachedWebViewStack.push(createWebView());
				}
				return false;
			}
		} );
	}
	
	public WebView getWebView(Context context) {
		WebView web;
		if (mCachedWebViewStack == null || mCachedWebViewStack.size() == 0)
		{
			web = createWebView();
		}
		else
		{
			web = mCachedWebViewStack.pop();
		}
		// webView不为空，则开始使用预创建的WebView,并且替换Context
		MutableContextWrapper contextWrapper = (MutableContextWrapper) web.getContext();
		contextWrapper.setBaseContext(context);
		return web;
	}
	
	private WebView createWebView()
	{
		WebView webView = new WebView( new MutableContextWrapper( context ) );
		webView.setSaveEnabled(true);
		webView.setScrollBarStyle( View.SCROLLBARS_INSIDE_OVERLAY);
		FxWebViewSetting.webViewDefauleSetting( webView );
		return webView;
	}
}
