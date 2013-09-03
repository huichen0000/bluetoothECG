package com.cz.wisdomcity.ui;

import com.cz.wisdomcity.ui.R;
import com.cz.wisdomcity.util.ControlView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemSelectedListener;

public class SystemSettingActivity extends BaseActivity implements
		OnClickListener {

	// private Spinner sp_portrange;
	// private Spinner sp_com;
	private Button btn_save_settings;
	private Button btn_clear_data;
	private Button btn_clear_user;
	private Button btn_change_pwd;
	private Button btn_about;
	private Button btn_update;

	// private String[] items1 = { "4800bps", "9600bps", "14400bps", "19200bps",
	// "38400bps" };
	// private String[] items2 = { "COM1", "COM2", "COM3", "COM4", "COM5" };

	// private ArrayAdapter<String> adapter_port;
	// private ArrayAdapter<String> adapter_com;

	private int port_type = 1;
	private int com_type = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systemsetting);
		setUserTitle();
		init();

	}

	private void init() {

		port_type = Integer.valueOf(getSharedPreferences("port").trim()) - 1;
		com_type = Integer.valueOf(getSharedPreferences("com").trim()) - 1;

		// btn_save_settings = (Button) findViewById(R.id.btn_save_settings);
		btn_clear_data = (Button) findViewById(R.id.btn_clear_data);
		btn_clear_user = (Button) findViewById(R.id.btn_clear_user);
		btn_change_pwd = (Button) findViewById(R.id.btn_change_pwd);
		btn_about = (Button) findViewById(R.id.btn_about);
		btn_update = (Button) findViewById(R.id.btn_update);

		// btn_save_settings.setOnClickListener(this);
		btn_clear_data.setOnClickListener(this);
		btn_clear_user.setOnClickListener(this);
		btn_change_pwd.setOnClickListener(this);
		btn_about.setOnClickListener(this);
		btn_update.setOnClickListener(this);

		// sp_portrange = (Spinner)
		// findViewById(R.id.spinner_systemsetting_port);
		// sp_com = (Spinner) findViewById(R.id.spinner_systemsetting_com);

		// adapter_port = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, items1);
		// adapter_com = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, items2);
		// 设置下拉列表的风格
		// adapter_port
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// adapter_com
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// sp_portrange.setAdapter(adapter_port);
		// sp_com.setAdapter(adapter_com);

		// sp_portrange.setSelection(port_type);
		// sp_com.setSelection(com_type);
		//
		// sp_portrange.setOnItemSelectedListener(new
		// SpinnerSelectedListener(0));
		// sp_com.setOnItemSelectedListener(new SpinnerSelectedListener(1));

	}

	class SpinnerSelectedListener implements OnItemSelectedListener {
		private int type;

		public SpinnerSelectedListener(int type) {
			this.type = type;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// type = arg2 + 1;
			if (type == 0) {
				port_type = arg2 + 1;
			} else {
				com_type = arg2 + 1;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// case R.id.btn_save_settings:
		// initSharedPreferences("port", port_type + " ");
		// initSharedPreferences("com", com_type + " ");
		// showToast("保存成功");
		// break;
		case R.id.btn_clear_data:
			break;
		case R.id.btn_clear_user:
			initSharedPreferences("UserName", "");
			initSharedPreferences("password", "");
			sendMessage(MSG_STOP);// 先停止
			ControlView.isStop = true;
			ControlView.flag = false;
			showToast("用户已注销，请重新登录");
			Intent intent = new Intent(SystemSettingActivity.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_change_pwd:
			Intent intent2 = new Intent(SystemSettingActivity.this,
					ChangePasswordActivity.class);
			startActivity(intent2);
			break;
		case R.id.btn_about:
			showToast("智慧城市" + getVersionName() + "版本");
			break;

		case R.id.btn_update:
			showToast("暂无更新");
			break;

		default:
			break;
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
