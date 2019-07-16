package com.hym.fxwebview.base;

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
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.hym.fxwebview.FxWebViewSetting;
import com.hym.fxwebview.R;
import com.hym.fxwebview.sonic.SonicSessionClientImpl;
import com.hym.fxwebview.widget.X5WebView;
import com.tencent.sonic.sdk.SonicSession;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public abstract class BaseWebActivity<T extends BaseViewModel> extends BaseActivity<T>
{
	private ConstraintLayout rootView;
	private T vm;
	private WebView webView;
	private X5WebView x5WebView;
	private static int MSG_PAGE_FINISH = 101;
	private Handler handler = new Handler( Looper.getMainLooper() );
	private final Pattern ACCEPTED_URI_SCHEME = Pattern.compile( "(?i)" + // switch on case
			// insensitive matching
			'(' + // begin group for scheme
			"(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)" + ')' + "(.*)" );
	public String TAG = "FxWebView";
	
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_base_web;
	}
	
	@Override
	public T setViewModel()
	{
		return null;
	}
	
	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		initData();
		initCustomData();
		initView();
		loadUrl();
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
					Log.w( TAG , "handle MSG_PAGE_FINISH url:" + finishUrl );
					if( !TextUtils.isEmpty( finishUrl ) )
					{
						doOnPageFinished( finishUrl , webView );
					}
				}
				else
				{
					doHandleMessage( msg );
				}
			}
		};
	}
	
	private void initView()
	{
		rootView = (ConstraintLayout)findViewById( getRddWebViewId()==0?R.id.activity_base_web_root:getRddWebViewId() );
		
		if( getX5WebView() == null )
		{
			//webView = FxPreLoadWebView.getInstance().getWebViewToUse( getApplicationContext() );
			webView = new WebView( getApplicationContext() );
			FxWebViewSetting.webViewDefauleSetting( webView,new BasicWebViewClient() );
			webView.setWebChromeClient( new WebChromeClient() );
			webView.setLayoutParams( getWebViewLayoutParams() );
			rootView.addView( webView );
		}
		else
		{
			x5WebView = getX5WebView();
			FxWebViewSetting.webViewX5Setting( x5WebView.getSettings() );
			x5WebView.setWebViewClient( new BasicX5WebViewClient()  );
			x5WebView.setWebChromeClient( new com.tencent.smtt.sdk.WebChromeClient() );
			x5WebView.setLayoutParams( getWebViewLayoutParams() );
			rootView.addView( x5WebView );
		}
	}
	
	private ConstraintLayout.LayoutParams getWebViewLayoutParams()
	{
		ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT );
		layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
		layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
		return layoutParams;
	}
	
	private void loadUrl()
	{
		if( getSonicSessionClientImpl() != null )
		{
			getSonicSessionClientImpl().bindWebView(webView);
			getSonicSessionClientImpl().clientReady();
		}
		else if( x5WebView!=null )
		{
			x5WebView.loadUrl( getOpenUrl() );
		}else
		{
			webView.loadUrl( getOpenUrl() );
		}
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
			Log.d( TAG , "parseUri URL :" + url );
			intent = Intent.parseUri( url , Intent.URI_INTENT_SCHEME );
		}
		catch( URISyntaxException e )
		{
			Log.e( TAG , "URISyntaxException: " + e.getLocalizedMessage() );
			return true;
		}
		intent.setComponent( null );
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 )
		{
			intent.setSelector( null );
		}
		if( BaseApplication.getContext().getPackageManager().resolveActivity( intent , 0 ) == null )
		{
			Log.d( TAG , "tryHandleByMarket URL :" + url );
			return true;
		}
		return true;
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
	
	private void sendPageFinishMsg(
			com.tencent.smtt.sdk.WebView view ,
			String url )
	{
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putString( "url" , url );
		message.what = MSG_PAGE_FINISH;
		message.setData( bundle );
		handler.sendMessageDelayed( message , 0 );
	}
	
	class BasicWebViewClient extends WebViewClient
	{
		@Override
		public void onLoadResource(
				WebView view ,
				String url )
		{
			super.onLoadResource( view , url );
			//Log.d( "onLoadResource->" + url );
		}
		
		@Override
		public void onPageStarted(
				WebView view ,
				String url ,
				Bitmap favicon )
		{
			Log.d( TAG , "onPageStarted : " + url );
			doOnPageStarted( url );
			super.onPageStarted( view , url , favicon );
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(
				WebView view ,
				String url )
		{
			boolean isWebViewAccept = isAcceptedScheme( url );
			Log.d( TAG , "shouldOverrideUrlLoading : " + url + "  isWebViewAccept " + isWebViewAccept );
			if( isWebViewAccept )
			{
				if( url.toLowerCase().endsWith( ".apk" ) )
				{
					return false;
				}
				if( doOnshouldOverrideUrlLoading( url ) )
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
			if( getSonicSession()!=null )
			{
				getSonicSession().getSessionClient().pageFinish( url );
			}
			//Log.d( TAG + "onPageFinished : " + url );
			removeMsg( MSG_PAGE_FINISH );
			sendPageFinishMsg( view , url );
			//doOnPageFinished( url, view );
			super.onPageFinished( view , url );
		}
		
		public void removeMsg( int msgWhat )
		{
			if( handler == null )
			{
				Log.d( TAG , "sendMsg handle is null" );
				return;
			}
			Log.d( TAG , "removeMsg : " + msgWhat );
			handler.removeMessages( msgWhat );
		}
		
		@Override
		public void onReceivedError(
				WebView view ,
				int errorCode ,
				String description ,
				String failingUrl )
		{
			Log.e( TAG , "onReceivedError failingUrl : " + failingUrl );
			Log.e( TAG , "onReceivedError errorCode : " + errorCode + " description : " + description );
			String errorMsg = "errorCode : " + errorCode + " description : " + description + " " + "failingUrl : " + failingUrl;
			doOnReceiverError();
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
			if( getSonicSession()!=null )
			{
				return (WebResourceResponse) getSonicSession().getSessionClient().requestResource(url);
			}
			return doShouldInterceptRequest( url );
		}
		
		
		@TargetApi(21)
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
			return shouldInterceptRequest(view, request.getUrl().toString());
		}
	}
	
	class BasicX5WebViewClient extends com.tencent.smtt.sdk.WebViewClient
	{
		
		@Override
		public void onLoadResource(
				com.tencent.smtt.sdk.WebView webView ,
				String s )
		{
			super.onLoadResource( webView , s );
		}
		
		@Override
		public void onPageStarted(
				com.tencent.smtt.sdk.WebView webView ,
				String s ,
				Bitmap bitmap )
		{
			Log.d( TAG , "onPageStarted : " + s );
			doOnPageStarted( s );
			super.onPageStarted( webView , s , bitmap );
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(
				com.tencent.smtt.sdk.WebView webView ,
				String url )
		{
			boolean isWebViewAccept = isAcceptedScheme( url );
			Log.d( TAG , "shouldOverrideUrlLoading : " + url + "  isWebViewAccept " + isWebViewAccept );
			if( isWebViewAccept )
			{
				if( url.toLowerCase().endsWith( ".apk" ) )
				{
					return false;
				}
				if( doOnshouldOverrideUrlLoading( url ) )
				{
					return super.shouldOverrideUrlLoading( webView , url );
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
				com.tencent.smtt.sdk.WebView webView ,
				String url )
		{
			removeMsg( MSG_PAGE_FINISH );
			sendPageFinishMsg( webView , url );
			super.onPageFinished( webView , url );
		}
		
		public void removeMsg( int msgWhat )
		{
			if( handler == null )
			{
				Log.d( TAG , "sendMsg handle is null" );
				return;
			}
			Log.d( TAG , "removeMsg : " + msgWhat );
			handler.removeMessages( msgWhat );
		}
		
		@Override
		public void onReceivedError(
				com.tencent.smtt.sdk.WebView view ,
				int errorCode ,
				String description ,
				String failingUrl )
		{
			Log.e( TAG , "onReceivedError failingUrl : " + failingUrl );
			Log.e( TAG , "onReceivedError errorCode : " + errorCode + " description : " + description );
			String errorMsg = "errorCode : " + errorCode + " description : " + description + " " + "failingUrl : " + failingUrl;
			doOnReceiverError();
			super.onReceivedError( view, errorCode, description, failingUrl );
		}
		
		@Override
		public void onReceivedSslError(
				com.tencent.smtt.sdk.WebView webView ,
				com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler ,
				com.tencent.smtt.export.external.interfaces.SslError sslError )
		{
			sslErrorHandler.proceed();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		
		if (x5WebView != null)
			x5WebView.destroy();
		if( webView!= null )
			webView.destroy();
		if (null != getSonicSession()) {
			getSonicSession().destroy();
		}
		super.onDestroy();
	}
	
	public abstract SonicSession getSonicSession();
	public abstract SonicSessionClientImpl getSonicSessionClientImpl();
	public abstract X5WebView getX5WebView();
	public abstract int getRddWebViewId();
	public abstract void initCustomData();
	public abstract void doHandleMessage( Message msg );
	public abstract String getOpenUrl();
	public abstract void doOnPageStarted( String url );
	public abstract boolean doOnshouldOverrideUrlLoading( String url );
	public abstract void doOnPageFinished( String url , WebView view );
	public abstract void doOnReceiverError();
	public abstract WebResourceResponse doShouldInterceptRequest( String url );
}
