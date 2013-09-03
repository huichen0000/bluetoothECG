package com.cz.wisdomcity.ui;

import com.cz.wisdomcity.ui.R;

import android.os.Bundle;
import android.view.KeyEvent;

public class HelpInfoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helpinfo);
	}
	
	/**
	 * 实现两次点击返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getToTabHost(3);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
