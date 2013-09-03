package com.cz.wisdomcity.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

public class TestSettingActivity extends BaseActivity implements
		OnClickListener {

	private Spinner sp_jgtime; // 动态测量时间
	private Button btn_save;
	private String[] items = { "5min", "10min", "20min", "30min", "60min" };
	private ArrayAdapter<String> adapter;
	private int jgtime_type = 1;
	private boolean isSingle = true;

	private RadioButton rb_single; // 单次测量
	private RadioButton rb_move; // 动态测量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testsetting);
		init();
		setUserTitle();
	}

	private void init() {
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		jgtime_type = Integer.valueOf(getSharedPreferences("jgtime_type")
				.trim()) - 1;
		sp_jgtime = (Spinner) findViewById(R.id.spinner_testsetting_jgtime);
		rb_single = (RadioButton) findViewById(R.id.rb_testsetting_one);
		rb_move = (RadioButton) findViewById(R.id.rb_testsetting_move);
		rb_single.setOnClickListener(this);
		rb_move.setOnClickListener(this);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_jgtime.setAdapter(adapter);
		sp_jgtime.setSelection(jgtime_type);
		sp_jgtime.setOnItemSelectedListener(new SpinnerSelectedListener(1));

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			initSharedPreferences("jgtime_type", jgtime_type + " ");
			showToast("保存成功");
			if (isSingle) {
				sendMessage(MSG_SINGLE_TEST);
			} else {
				sendMessage(MSG_MOVE_TEST);
			}
			break;
		case R.id.rb_testsetting_one:
			isSingle = true;
			break;
		case R.id.rb_testsetting_move:
			isSingle = false;
			break;

		}
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {
		private int type;

		public SpinnerSelectedListener(int type) {
			this.type = type;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (type == 0) {
			} else {
				jgtime_type = arg2 + 1;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
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
