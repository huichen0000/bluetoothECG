package com.cz.wisdomcity.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends BaseActivity implements OnClickListener {

	private EditText et_password_name;
	private EditText et_password_email;
	private Button btn_getPassword;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);

		initView();
	}

	private void initView() {
		et_password_name = (EditText) findViewById(R.id.et_password_name);
		et_password_email = (EditText) findViewById(R.id.et_password_email);
		btn_getPassword = (Button) findViewById(R.id.btn_get_password);
		btn_getPassword.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_get_password:
			checkRegister();
		default:
			break;
		}
	}

	private void checkRegister() {
		// TODO Auto-generated method stub
		String userName = et_password_name.getText().toString().trim();
		String email = et_password_email.getText().toString().trim();

		if (userName == null || userName.equals("")) {
			showToast("请输入用户名！");
			return;
		}
		if (email == null || email.equals("")) {
			showToast("请输入邮箱地址！");
			return;
		}
		if (!isEmail()) {
			showToast("邮箱地址不正确！");
			return;
		}
		showToast("密码已发送到你的邮箱！");
		finish();

	}

	/**
	 * 判断是否为邮箱格式
	 * 
	 * @return
	 */
	private boolean isEmail() {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(et_password_email.getText().toString());
		return m.matches();
	}

}
