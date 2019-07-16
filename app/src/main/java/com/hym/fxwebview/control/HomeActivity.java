package com.hym.fxwebview.control;

import com.hym.fxwebview.R;
import com.hym.fxwebview.base.BaseActivity;

/**
 * Created by Neo on 2019/7/13.
 * Description :
 */
public class HomeActivity extends BaseActivity<HomeActivityVM>
{
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_home;
	}
	
	@Override
	public HomeActivityVM setViewModel()
	{
		return new HomeActivityVM();
	}
}
