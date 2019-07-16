package com.hym.fxwebview;

import android.graphics.Color;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Neo on 2019/7/9.
 * Description :
 */
public class FxWebViewSetting
{
	public static WebView webViewDefauleSetting( WebView webView ,
			WebViewClient webViewClient )
	{
		WebSettings webSettings = webView.getSettings();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1 )
		{
			webSettings.setAllowFileAccess( true ); // 允许访问文件
			webSettings.setAppCacheEnabled( false ); //设置H5的缓存打开,默认关闭
			webSettings.setLoadWithOverviewMode( true );//设置webview自适应屏幕大小
			webSettings.setDomStorageEnabled( true );//设置可以使用localStorage
			webSettings.setBuiltInZoomControls( false );//关闭zoom
			webSettings.setGeolocationEnabled( false );
		}
		webSettings.setDefaultTextEncodingName( "UTF-8" );
		webSettings.setCacheMode( WebSettings.LOAD_NO_CACHE );
		webSettings.setUseWideViewPort( true );//设置webview自适应屏幕大小
		webSettings.setLayoutAlgorithm( WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
		//设置，可能的话使所有列的宽度不超过屏幕宽度
		webSettings.setSupportZoom( false );//关闭zoom按钮
		webSettings.setJavaScriptEnabled( true );
		webView.setBackgroundColor( Color.WHITE );
		webView.setVerticalScrollBarEnabled( false );
		webView.setHorizontalScrollBarEnabled( false );
		webView.setWebViewClient( webViewClient );
		webSettings.setRenderPriority( WebSettings.RenderPriority.HIGH );
		webSettings.setBlockNetworkImage( false );//解决图片不显示
		return webView;
	}
	
	
	public static com.tencent.smtt.sdk.WebSettings webViewX5Setting( com.tencent.smtt.sdk.WebSettings webSettings)
	{
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1 )
		{
			webSettings.setAllowFileAccess( true ); // 允许访问文件
			webSettings.setAppCacheEnabled( false ); //设置H5的缓存打开,默认关闭
			webSettings.setLoadWithOverviewMode( true );//设置webview自适应屏幕大小
			webSettings.setDomStorageEnabled( true );//设置可以使用localStorage
			webSettings.setBuiltInZoomControls( false );//关闭zoom
			webSettings.setGeolocationEnabled( false );
		}
		webSettings.setDefaultTextEncodingName( "UTF-8" );
		webSettings.setCacheMode( WebSettings.LOAD_NO_CACHE );
		webSettings.setUseWideViewPort( true );//设置webview自适应屏幕大小
		webSettings.setLayoutAlgorithm( com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
		//设置，可能的话使所有列的宽度不超过屏幕宽度
		webSettings.setSupportZoom( false );//关闭zoom按钮
		webSettings.setJavaScriptEnabled( true );
		webSettings.setRenderPriority( com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH );
		webSettings.setBlockNetworkImage( false );//解决图片不显示
		return webSettings;
	}
	
	
	public static WebView webViewDefauleSetting( WebView webView)
	{
		WebSettings webSettings = webView.getSettings();
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1 )
		{
			webSettings.setAllowFileAccess( true ); // 允许访问文件
			webSettings.setAppCacheEnabled( false ); //设置H5的缓存打开,默认关闭
			webSettings.setLoadWithOverviewMode( true );//设置webview自适应屏幕大小
			webSettings.setDomStorageEnabled( true );//设置可以使用localStorage
			webSettings.setBuiltInZoomControls( false );//关闭zoom
			webSettings.setGeolocationEnabled( false );
		}
		webSettings.setDefaultTextEncodingName( "UTF-8" );
		webSettings.setCacheMode( WebSettings.LOAD_NO_CACHE );
		webSettings.setUseWideViewPort( true );//设置webview自适应屏幕大小
		webSettings.setLayoutAlgorithm( WebSettings.LayoutAlgorithm.NARROW_COLUMNS );
		//设置，可能的话使所有列的宽度不超过屏幕宽度
		webSettings.setSupportZoom( false );//关闭zoom按钮
		webSettings.setJavaScriptEnabled( true );
		webView.setBackgroundColor( Color.WHITE );
		webView.setVerticalScrollBarEnabled( false );
		webView.setHorizontalScrollBarEnabled( false );
		webSettings.setRenderPriority( WebSettings.RenderPriority.HIGH );
		webSettings.setBlockNetworkImage( false );//解决图片不显示
		return webView;
	}
	
}
