package com.cz.wisdomcity.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

@SuppressWarnings("deprecation")
public class FirstActivity extends TabActivity {

	private static FirstActivity activity;
	public TabHost mTabHost;
	private TabWidget mTabWidget;

	private ImageView btn_xdtest;
	private ImageView btn_datamanager;
	private ImageView btn_professortest;
	private ImageView btn_testsetting;
	private ImageView btn_systemsetting;
	private ImageView btn_helpinfo;
	private LinearLayout layout;
	
	public static FirstActivity getInstance() {
		return activity;
	}

	public void closeMenu() {
		layout.setVisibility(View.GONE);
	}

	public void openMenu() {
		layout.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		activity = this;

		setContentView(R.layout.activity_first);

		mTabHost = this.getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("测试设置").setIndicator("")
				.setContent(new Intent(this, TestSettingActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("数据管理").setIndicator("")
				.setContent(new Intent(this, DataManagerActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("专家诊断").setIndicator("")
				.setContent(new Intent(this, ProfessorTestActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("心电测试").setIndicator("")
				.setContent(new Intent(this, WaveActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("系统设置").setIndicator("")
				.setContent(new Intent(this, SystemSettingActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("帮助信息").setIndicator("")
				.setContent(new Intent(this, HelpInfoActivity.class)));

		mTabWidget = mTabHost.getTabWidget();

		initView();
		
		closeMenu();
		mTabHost.setCurrentTab(3);
		btn_testsetting.setBackgroundResource(R.drawable.gr_on);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < mTabWidget.getChildCount(); i++) {
					View view = mTabWidget.getChildAt(i);

					switch (i) {
					case 0:
						if (mTabHost.getCurrentTab() == i) {
							btn_xdtest.setBackgroundResource(R.drawable.set_on);
						} else {
							btn_xdtest.setBackgroundResource(R.drawable.set);
						}
						break;
					case 1:
						if (mTabHost.getCurrentTab() == i) {
							btn_datamanager.setBackgroundResource(R.drawable.dt_on);
						} else {
							btn_datamanager.setBackgroundResource(R.drawable.dt);
						}
						break;
					case 2:
						if (mTabHost.getCurrentTab() == i) {
							btn_professortest.setBackgroundResource(R.drawable.kj_on);
						} else {
							btn_professortest.setBackgroundResource(R.drawable.kj);
						}
						break;
					case 3:
						if (mTabHost.getCurrentTab() == i) {
							btn_testsetting.setBackgroundResource(R.drawable.gr_on);
						} else {
							btn_testsetting.setBackgroundResource(R.drawable.gr);
						}
						break;
					case 4:
						if (mTabHost.getCurrentTab() == i) {
							btn_systemsetting.setBackgroundResource(R.drawable.set_on);
						} else {
							btn_systemsetting.setBackgroundResource(R.drawable.set);
						}
						break;
					case 5:
						if (mTabHost.getCurrentTab() == i) {
							btn_helpinfo.setBackgroundResource(R.drawable.kj_on);
						} else {
							btn_helpinfo.setBackgroundResource(R.drawable.kj);
						}
						break;
					}
				}
			}
		});

	}

	private void initView() {
		layout = (LinearLayout) findViewById(R.id.bottom_layout);
		btn_xdtest = (ImageView) findViewById(R.id.btn_tabhost_xindiantest);
		btn_datamanager = (ImageView) findViewById(R.id.btn_tabhost_datamanager);
		btn_testsetting = (ImageView) findViewById(R.id.btn_tabhost_testsetting);
		btn_systemsetting = (ImageView) findViewById(R.id.btn_tabhost_systemsetting);
		btn_helpinfo = (ImageView) findViewById(R.id.btn_tabhost_helpinfo);
		btn_professortest = (ImageView) findViewById(R.id.btn_tabhost_professor);

		btn_xdtest.setOnClickListener(listener);
		btn_datamanager.setOnClickListener(listener);
		btn_professortest.setOnClickListener(listener);
		btn_testsetting.setOnClickListener(listener);
		btn_systemsetting.setOnClickListener(listener);
		btn_helpinfo.setOnClickListener(listener);
		setTitle(getString(R.string.testsetting));
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			closeMenu();
			switch (v.getId()) {
			case R.id.btn_tabhost_xindiantest:
				mTabHost.setCurrentTabByTag("测试设置");
				setTitle(getString(R.string.testsetting));
				break;
			case R.id.btn_tabhost_datamanager:
				mTabHost.setCurrentTabByTag("数据管理");
				setTitle(getString(R.string.datamanager));
				break;
			case R.id.btn_tabhost_professor:
				mTabHost.setCurrentTabByTag("专家诊断");
				setTitle(getString(R.string.professortest));
				break;
			case R.id.btn_tabhost_testsetting:
				mTabHost.setCurrentTabByTag("心电测试");
				setTitle(getString(R.string.xindiantest));
				break;
			case R.id.btn_tabhost_systemsetting:
				mTabHost.setCurrentTabByTag("系统设置");
				setTitle(getString(R.string.systemsetting));
				break;
			case R.id.btn_tabhost_helpinfo:
				mTabHost.setCurrentTabByTag("帮助信息");
				setTitle(getString(R.string.helpinfo));
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // TODO Auto-generated method stub
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.activity_first, menu);
	// return super.onCreateOptionsMenu(menu);
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_testsetting:
			mTabHost.setCurrentTabByTag("测试设置");
			setTitle(getString(R.string.testsetting));
			break;
		case R.id.menu_datamanager:
			mTabHost.setCurrentTabByTag("数据管理");
			setTitle(getString(R.string.datamanager));
			break;
		case R.id.menu_professortest:
			mTabHost.setCurrentTabByTag("专家诊断");
			setTitle(getString(R.string.professortest));
			break;
		case R.id.menu_xindiantest:
			mTabHost.setCurrentTabByTag("心电测试");
			setTitle(getString(R.string.xindiantest));
			break;
		case R.id.menu_systemsetting:
			mTabHost.setCurrentTabByTag("系统设置");
			setTitle(getString(R.string.systemsetting));
			break;
		case R.id.menu_helpinfo:
			mTabHost.setCurrentTabByTag("帮助信息");
			setTitle(getString(R.string.helpinfo));
		}
		return super.onOptionsItemSelected(item);
	}

}
