package com.hym.fxwebview.sonic;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hym.fxwebview.FxWebView;
import com.hym.fxwebview.utils.FLog;
import com.tencent.sonic.sdk.SonicSession;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neo on 2019/7/17.
 * Description :
 */
public class FxSonicWebClient extends WebViewClient
{
	private static int MSG_PAGE_FINISH = 101;
	private FxWebView.CallBack callBack;
	private SonicSession sonicSession;
	private Handler handler = new Handler( Looper.getMainLooper() );
	private final Pattern ACCEPTED_URI_SCHEME = Pattern.compile( "(?i)" + // switch on case
			// insensitive matching
			'(' + // begin group for scheme
			"(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)" + ')' + "(.*)" );
	
	public FxSonicWebClient( FxWebView.CallBack callBack ,
			SonicSession sonicSession )
	{
		this.callBack = callBack;
		this.sonicSession = sonicSession;
		initData();
	}
	
	@Override
	public void onLoadResource(
			WebView view ,
			String url )
	{
		super.onLoadResource( view , url );
	}
	
	@Override
	public void onPageStarted(
			WebView view ,
			String url ,
			Bitmap favicon )
	{
		FLog.d(  "onPageStarted : " + url );
		callBack.loadStart( url );
		super.onPageStarted( view , url , favicon );
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(
			WebView view ,
			String url )
	{
		boolean isWebViewAccept = isAcceptedScheme( url );
		FLog.d( "shouldOverrideUrlLoading : " + url + "  isWebViewAccept " + isWebViewAccept );
		if( isWebViewAccept )
		{
			if( url.toLowerCase().endsWith( ".apk" ) )
			{
				return false;
			}
			if( callBack.shouldOverrideUrlLoading( url ) )
			{
				return super.shouldOverrideUrlLoading( view , url );
			}
			return true;
		}
		else
		{
			return otherWebViewOperation( url );
		}
	}
	
	@Override
	public void onPageFinished(
			WebView view ,
			String url )
	{
		if( sonicSession!=null )
		{
			sonicSession.getSessionClient().pageFinish( url );
		}
		//Log.d( TAG + "onPageFinished : " + url );
		removeMsg( MSG_PAGE_FINISH );
		sendPageFinishMsg( view , url );
		super.onPageFinished( view , url );
	}
	
	public void removeMsg( int msgWhat )
	{
		if( handler == null )
		{
			FLog.d( "sendMsg handle is null" );
			return;
		}
		FLog.d(  "removeMsg : " + msgWhat );
		handler.removeMessages( msgWhat );
	}
	
	@Override
	public void onReceivedError(
			WebView view ,
			int errorCode ,
			String description ,
			String failingUrl )
	{
		FLog.e( "onReceivedError failingUrl : " + failingUrl );
		FLog.e(  "onReceivedError errorCode : " + errorCode + " description : " + description );
		String errorMsg = "errorCode : " + errorCode + " description : " + description + " " + "failingUrl : " + failingUrl;
		callBack.loadError(failingUrl,errorCode);
		super.onReceivedError( view , errorCode , description , failingUrl );
	}
	
	@Override
	public void onReceivedSslError(
			WebView view ,
			SslErrorHandler handler ,
			SslError error )
	{
		handler.proceed();
	}
	
	@Override
	public WebResourceResponse shouldInterceptRequest(
			WebView webView ,
			String url )
	{
		if( sonicSession!=null )
		{
			return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
		}
		return null;
	}
	
	
	@TargetApi(21)
	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
		return shouldInterceptRequest(view, request.getUrl().toString());
	}
	
	
	/**
	 * 该url是否属于浏览器能处理的内部协议
	 */
	private boolean isAcceptedScheme( String url )
	{
		//正则中忽略了大小写，保险起见，还是转成小写
		String lowerCaseUrl = url.toLowerCase();
		Matcher acceptedUrlSchemeMatcher = ACCEPTED_URI_SCHEME.matcher( lowerCaseUrl );
		if( acceptedUrlSchemeMatcher.matches() )
		{
			return true;
		}
		return false;
	}
	
	@TargetApi( Build.VERSION_CODES.DONUT )
	private boolean otherWebViewOperation(
			String url )
	{
		Intent intent;
		try
		{
			FLog.d( "parseUri URL :" + url );
			intent = Intent.parseUri( url , Intent.URI_INTENT_SCHEME );
		}
		catch( URISyntaxException e )
		{
			FLog.e( "URISyntaxException: " + e.getLocalizedMessage() );
			return true;
		}
		intent.setComponent( null );
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 )
		{
			intent.setSelector( null );
		}
		return true;
	}

	private void initData()
	{
		handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				if( msg.what == MSG_PAGE_FINISH )
				{
					Bundle bundle = msg.getData();
					String finishUrl = bundle.getString( "url" );
					FLog.v(  "handle MSG_PAGE_FINISH url:" + finishUrl );
					if( !TextUtils.isEmpty( finishUrl ) )
					{
						callBack.loadFinish( finishUrl );
					}
				}
			}
		};
	}
	
	private void sendPageFinishMsg(
			WebView view ,
			String url )
	{
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putString( "url" , url );
		message.what = MSG_PAGE_FINISH;
		message.setData( bundle );
		handler.sendMessageDelayed( message , 0 );
	}
}
