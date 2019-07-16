package com.hym.fxwebview.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * Created by Neo on 2019/7/8.
 * Description :
 */
public class BaseViewModel<A extends BaseActivity,T extends ViewDataBinding> extends ViewModel
{
	private T viewDataBinding;
	private A activity;
	
	public T getViewDataBinding()
	{
		return viewDataBinding;
	}
	
	public void setViewDataBinding( T viewDataBinding )
	{
		this.viewDataBinding = viewDataBinding;
	}
	
	public A getActivity()
	{
		return activity;
	}
	
	public void setActivity( A activity )
	{
		this.activity = activity;
	}
	
	public void onViewBind()
	{
	
	}
}
