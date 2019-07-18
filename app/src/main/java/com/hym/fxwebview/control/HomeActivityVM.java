package com.hym.fxwebview.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.hym.fxwebview.FxWebView;
import com.hym.fxwebview.R;
import com.hym.fxwebview.base.BaseViewModel;
import com.hym.fxwebview.base.Sssd;
import com.hym.fxwebview.config.Const;
import com.hym.fxwebview.control.web.DefaultWebActivity;
import com.hym.fxwebview.control.web.SonicWebActivity;
import com.hym.fxwebview.control.web.X5WebActivity;
import com.hym.fxwebview.databinding.ActivityHomeBinding;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class HomeActivityVM extends BaseViewModel<HomeActivity,ActivityHomeBinding>
{
	public void onClick( View view )
	{
		switch( view.getId() )
		{
			case R.id.home_web_default:
				startActivity( DefaultWebActivity.class );
				break;
			case R.id.home_web_x5:
				startActivity( X5WebActivity.class );
				break;
			case R.id.home_web_sonic:
				doSonic();
				break;
			default:
				break;
		}
	}
	
	private void startActivity(Class c )
	{
		Intent intent = new Intent( getActivity(),c );
		getActivity().startActivity( intent );
	}
	
	private void doSonic()
	{
		FxWebView.getInstance().setSonicRunTime( new Sssd( getActivity().getApplicationContext() ) );
		
		SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
		sessionConfigBuilder.setSupportLocalServer(true);
		
		// preload session
		boolean preloadSuccess = SonicEngine.getInstance().preCreateSession( Const.OPEN_URL , sessionConfigBuilder.build());
		Log.d("FxWebView",preloadSuccess?"sonic预载完成":"sonic预载失败");
		
		if( preloadSuccess )
		{
			startActivity( SonicWebActivity.class );
		}
	}
}
