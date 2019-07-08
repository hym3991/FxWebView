package com.hym.fxwebview.control;

import com.hym.fxwebview.base.BaseViewModel;

import androidx.databinding.ObservableField;

/**
 * Created by Neo on 2019/7/8.
 * Description :
 */
public class MainActivityVM extends BaseViewModel
{
	public ObservableField<String> title = new ObservableField<>();
	
	@Override
	public void onViewBind()
	{
		super.onViewBind();
		title.set( "使用看看吧~~" );
	}
}
