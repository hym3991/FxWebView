package com.hym.fxwebview.base;

import android.os.Bundle;
import android.view.ViewGroup;

import com.hym.fxwebview.BR;

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
		setContentView( getLayoutRes() );
		ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
		ViewDataBinding viewDataBinding = DataBindingUtil.inflate( getLayoutInflater(), getLayoutRes() , content,false);
		if( viewDataBinding != null )
		{
			BaseViewModel baseViewModel = initViewModel(setViewModel());
			viewDataBinding.setVariable( BR.viewModel,baseViewModel );
			baseViewModel.onViewBind();
		}
	}
	
	public abstract int getLayoutRes();
	
	public abstract T setViewModel();
	
	private BaseViewModel initViewModel(T z)
	{
		return ViewModelProviders.of( this ).get( z.getClass() );
	}
}
