package com.hym.fxwebview.control;

import com.google.android.material.appbar.AppBarLayout;
import com.hym.fxwebview.base.BaseViewModel;
import com.hym.fxwebview.databinding.ActivityDesignBinding;
import com.hym.fxwebview.widget.AppBarStateChangeListener;

/**
 * Created by Neo on 2019/7/29.
 * Description :
 */
public class DesignVM extends BaseViewModel<DesignActivity, ActivityDesignBinding>
{
	@Override
	public void onViewBind()
	{
		super.onViewBind();
		AppBarLayout toolbarLayout = getViewDataBinding().appBar;
		toolbarLayout.addOnOffsetChangedListener( new AppBarStateChangeListener()
		{
			@Override
			public void onStateChanged(
					AppBarLayout appBarLayout ,
					State state )
			{
				if( state == State.EXPANDED ) {
					
					//展开状态
					//getViewDataBinding().barBack.setAlpha( 1 );
					getViewDataBinding().barTitle.setAlpha( 0 );
				}else if(state == State.COLLAPSED){
					
					//折叠状态
					//getViewDataBinding().barBack.setAlpha( 1 );
					getViewDataBinding().barTitle.setAlpha( 1);
				}else {
					
					//中间状态
					getViewDataBinding().barTitle.setAlpha( 1);
					//getViewDataBinding().barBack.setAlpha( 1 );
				}
			}
		} );
	}
}
