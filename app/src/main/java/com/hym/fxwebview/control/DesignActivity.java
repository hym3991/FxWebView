package com.hym.fxwebview.control;

import com.hym.fxwebview.R;
import com.hym.fxwebview.base.BaseActivity;

/**
 * Created by Neo on 2019/7/29.
 * Description :
 */
public class DesignActivity extends BaseActivity<DesignVM>
{
	@Override
	public int getLayoutRes()
	{
		return R.layout.activity_design;
	}
	
	@Override
	public DesignVM setViewModel()
	{
		return new DesignVM();
	}
}
