package com.hym.fxwebview.base;

import android.content.Context;

import com.hym.fxwebview.sonic.FxSonicRuntimeImpl;
import com.tencent.sonic.sdk.SonicSessionClient;

/**
 *
 */
public class Sssd extends FxSonicRuntimeImpl<Sssd>
{
	public Sssd( Context context) {
		super(context);
	}
	
	@Override
	public String getFxUserAgent()
	{
		return "qqweeqeq";
	}
	
	@Override
	public String getFxCurrentUserAccount()
	{
		return "1111";
	}
	
	@Override
	public void fxShowToast( CharSequence text ,
			int duration )
	{
	
	}
	
	@Override
	public void fxNotifyError( SonicSessionClient client ,
			String url ,
			int errorCode )
	{
	
	}
	
	@Override
	public boolean isFxSonicUrl( String url )
	{
		return false;
	}
	
	@Override
	public Sssd create()
	{
		return this;
	}
}
