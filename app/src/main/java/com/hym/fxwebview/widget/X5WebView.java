package com.hym.fxwebview.widget;

import android.content.Context;
import android.graphics.Color;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class X5WebView extends WebView
{
	
	public X5WebView( Context context )
	{
		super( context );
		this.setBackgroundColor( Color.WHITE );
		this.setVerticalScrollBarEnabled( false );
		this.setHorizontalScrollBarEnabled( false );
	}
}
