package com.cz.wisdomcity.ui;

import com.cz.wisdomcity.util.LoginHelper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {
	private EditText et_password;
	private EditText et_password_new;
	private EditText et_password_config;
	private Button btn_changePassword;
	private LoginHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		setTitle("修改密码");
		helper = new LoginHelper(this);
		initView();
	}

	private void initView() {
		et_password = (EditText) findViewById(R.id.et_password);
		et_password_new = (EditText) findViewById(R.id.et_password_new);
		et_password_config = (EditText) findViewById(R.id.et_password_config);
		btn_changePassword = (Button) findViewById(R.id.btn_change_password);
		btn_changePassword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_change_password:
			checkChangePassword();
		default:
			break;
		}
	}

	private void checkChangePassword() {
		// TODO Auto-generated method stub
		String password = et_password.getText().toString().trim();
		String password_new = et_password_new.getText().toString().trim();
		String password_config = et_password_config.getText().toString().trim();

		if (password == null || password.equals("")) {
			showToast("请输入原始密码！");
			return;
		}
		if (password_new == null || password_new.equals("")) {
			showToast("请输入新密码！");
			return;
		}
		if (password_config == null || password_config.equals("")) {
			showToast("请输入新密码！");
			return;
		}
		if (!password_new.equals(password_config)) {
			showToast("两次密码输入不一致！");
			return;
		}
		if (!password_new.equals(password_config)) {
			showToast("新密码不能与原始密码一样！");
			return;
		}
		if(helper.updatePassword(getWisdomApplication().userName, password,password_new)){
			showToast("密码修改成功！");
			finish();
		}else{
			showToast("原始密码错误！");
		}

	}
}
