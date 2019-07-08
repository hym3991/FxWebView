package com.hym.fxwebview.control;

import android.os.Bundle;

import com.hym.fxwebview.R;
import com.hym.fxwebview.base.BaseActivity;

public class MainActivity extends BaseActivity<MainActivityVM>
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
	}
	
	@Override
	public MainActivityVM setViewModel()
	{
		return new MainActivityVM();
	}
	
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_main;
	}
}
