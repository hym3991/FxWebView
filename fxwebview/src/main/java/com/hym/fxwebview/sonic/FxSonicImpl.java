package com.hym.fxwebview.sonic;

import com.tencent.sonic.sdk.SonicSessionClient;

/**
 *
 */
public interface FxSonicImpl<T>
{
	public T create();
	public  String getFxUserAgent();
	public  String getFxCurrentUserAccount();
	public  void fxShowToast(CharSequence text, int duration);
	public  void fxNotifyError( SonicSessionClient client, String url, int errorCode);
	public  boolean isFxSonicUrl( String url);
}
