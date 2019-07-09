package com.hym.fxwebview.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * Created by Neo on 2019/7/8.
 * Description :
 */
public class BaseViewModel extends ViewModel
{
	private ViewDataBinding viewDataBinding;
	
	public ViewDataBinding getViewDataBinding()
	{
		return viewDataBinding;
	}
	
	public void setViewDataBinding( ViewDataBinding viewDataBinding )
	{
		this.viewDataBinding = viewDataBinding;
	}
	
	public void onViewBind()
	{
	
	}
}
