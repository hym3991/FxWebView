package com.hym.core.fxwebview.strategy;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Neo on 2019/7/9.
 * Description :
 */
public class FxWebViewSetting
{
	public static void webViewDefauleSetting( WebView webView )
	{
		WebSettings settings = webView.getSettings();
		settings.setDomStorageEnabled(true);
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		settings.setDatabaseEnabled(true);
		settings.setAppCacheEnabled(true);
		settings.setSavePassword(false);
		settings.setSaveFormData(false);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		settings.setAllowContentAccess(true);
		settings.setAllowFileAccess(true);
		settings.setAllowUniversalAccessFromFileURLs(true);
		settings.setBuiltInZoomControls(false);
	}
}
