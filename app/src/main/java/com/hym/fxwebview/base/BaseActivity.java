package com.hym.fxwebview.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hym.fxwebview.BR;
import com.hym.fxwebview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Neo on 2019/7/8.
 * Description :
 */
public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity
{
	@Override
	protected void onCreate( @Nullable Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		ViewGroup container = (ViewGroup) LayoutInflater.from(this).inflate( R.layout.activity_basic, null);
		setContentView( container );
		LayoutInflater layoutInflater = LayoutInflater.from(this);
	
		ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, getLayoutRes() , container,true);
		if( viewDataBinding != null )
		{
			T baseViewModel = initViewModel(setViewModel());
			viewDataBinding.setVariable( BR.viewModel,baseViewModel );
			baseViewModel.setViewDataBinding( viewDataBinding );
			baseViewModel.onViewBind();
		}
	}
	
	public abstract int getLayoutRes();
	
	public abstract T setViewModel();
	
	private T initViewModel(T z)
	{
		return (T)ViewModelProviders.of( this ).get( z.getClass() );
	}
}
