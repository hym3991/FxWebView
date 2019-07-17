package com.hym.fxwebview.preload;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.webkit.WebView;

import java.util.Stack;

/**
 *
 */
public abstract class FxBasePreLoadWebView<T extends WebView>
{
	private final Stack<T> cachedWebViewStack = new Stack<>();
	private static final int CACHED_WEBVIEW_MAX_NUM = 2;
	
	public abstract T createWebView();
	public abstract T getWebViewToUse(Context context);
	
	public T getWebView( Context context)
	{
		T web;
		if ( cachedWebViewStack.size() == 0)
		{
			web = createWebView();
		}
		else
		{
			web = cachedWebViewStack.pop();
		}
		return web;
	}
	
	public void webViewProLoad()
	{
		Looper.myQueue().addIdleHandler( new MessageQueue.IdleHandler()
		{
			@Override
			public boolean queueIdle()
			{
				if (cachedWebViewStack.size() < CACHED_WEBVIEW_MAX_NUM)
				{
					cachedWebViewStack.push(createWebView());
				}
				return false;
			}
		} );
	}
}
