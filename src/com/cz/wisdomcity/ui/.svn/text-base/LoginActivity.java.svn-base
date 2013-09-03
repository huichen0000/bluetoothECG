package com.cz.wisdomcity.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cz.wisdomcity.entity.LoginInfo;
import com.cz.wisdomcity.util.LoginHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private boolean flag = true; // 记录是否在登陆页面
	private ViewStub loginStub;
	private ViewStub registerStub;
	private Button btn_login;
	private Button btn_register;
	private TextView tv_forget_password;

	private EditText et_login_name;
	private EditText et_login_password;
	private EditText et_register_name;
	private EditText et_register_email;
	private EditText et_register_password;
	private EditText et_register_passwordconfig;
	private Spinner spinner_user_type_login;
	private Spinner spinner_user_type_register;
	private CheckBox cb_remember;

	private static final String[] user_type = { "管理员", "普通用户", "匿名用户" };

	private LoginHelper helper;
	private ArrayAdapter<String> adapter;
	private int type = 1; // 默认1（管理员），2(普通),3(匿名)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		helper = new LoginHelper(this);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub

		setTitle(getString(R.string.denglu2));

		loginStub = (ViewStub) findViewById(R.id.login_stub);
		registerStub = (ViewStub) findViewById(R.id.register_stub);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);

		loginStub.inflate();
		registerStub.inflate();

		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		spinner_user_type_login = (Spinner) findViewById(R.id.spinner_user_type_login);
		spinner_user_type_register = (Spinner) findViewById(R.id.spinner_user_type_register);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, user_type);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner_user_type_login.setAdapter(adapter);
		spinner_user_type_register.setAdapter(adapter);

		// 添加事件Spinner事件监听
		spinner_user_type_login
				.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinner_user_type_register
				.setOnItemSelectedListener(new SpinnerSelectedListener());

		et_register_name = (EditText) findViewById(R.id.et_register_name);
		et_register_email = (EditText) findViewById(R.id.et_register_email);
		et_register_password = (EditText) findViewById(R.id.et_register_password);
		et_register_passwordconfig = (EditText) findViewById(R.id.et_register_passwordconfig);

		getSharedPreferences(); // 设置默认登录名
		registerStub.setVisibility(View.GONE);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_forget_password.setOnClickListener(this);

	}

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			type = arg2 + 1;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public void initLoginView() {
		loginStub.setVisibility(View.VISIBLE);
		registerStub.setVisibility(View.GONE);
		setTitle(getString(R.string.denglu2));
		btn_login.setText(R.string.denglu);
	}

	public void initRegisterView() {
		loginStub.setVisibility(View.GONE);
		registerStub.setVisibility(View.VISIBLE);
		setTitle(getString(R.string.zhuce2));
		btn_login.setText(R.string.cancle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			if (!flag) { // 在注册页面点击登陆
				type = 1;
				initLoginView();
				flag = true;
			} else {
				// Intent intent = new Intent(this, FirstActivity.class);
				// startActivity(intent);
				// finish();
				checkLogin();
			}
			break;
		case R.id.btn_register:
			if (flag) { // 在登陆页面点击注册
				type = 1;
				initRegisterView();
				flag = false;
			} else {
				checkRegister();
			}
			break;
		case R.id.tv_forget_password:
			Intent intent = new Intent(LoginActivity.this,
					PasswordActivity.class);
			startActivity(intent);
		default:
			break;
		}
	}

	private void checkRegister() {
		// TODO Auto-generated method stub
		String userName = et_register_name.getText().toString().trim();
		String email = et_register_email.getText().toString().trim();
		String password = et_register_password.getText().toString().trim();
		String passwordconfig = et_register_passwordconfig.getText().toString()
				.trim();

		if (userName == null || userName.equals("")) {
			showToast("请输入用户名！");
			return;
		}
		if (email == null || email.equals("")) {
			showToast("请输入邮箱地址！");
			return;
		}
		if (!isEmail(email)) {
			showToast("邮箱地址不正确！");
			return;
		}
		if (password == null || userName.equals("")) {
			showToast("请输入用户名！");
			return;
		}
		if (passwordconfig == null || email.equals("")) {
			showToast("请输入密码！");
			return;
		}
		if (!password.equals(passwordconfig)) {
			showToast("两次密码输入不一致 ！");
			return;
		}
		LoginInfo info = new LoginInfo(userName, password, email, type);
		boolean flag = helper.addLogin(info);
		if (flag) {
			showToast("注册成功");
			initLoginView();
			et_login_name.setText(userName);
		} else {
			showToast("用户名已存在，请重新输入用户名");
		}

	}

	private void checkLogin() {
		// TODO Auto-generated method stub
		String userName = et_login_name.getText().toString().trim();
		String password = et_login_password.getText().toString().trim();

		if (type != 3) {
			if (userName == null || userName.equals("")) {
				showToast("请输入用户名！");
				return;
			}
			if (password == null || password.equals("")) {
				showToast("请输入密码！");
				return;
			}
			if (helper.login(userName, password, type) != null) {
				initSharedPreferences(userName, password, type);
				getWisdomApplication().userName = userName;
				showToast("登陆成功");
				Intent intent = new Intent(this, FirstActivity.class);
				startActivity(intent);
				finish();
			} else {
				showToast("登陆失败");
			}
		} else {
			getWisdomApplication().userName = "匿名用户";
			showToast("登陆成功");
			Intent intent = new Intent(this, FirstActivity.class);
			startActivity(intent);
			finish();
		}
	}

	// 记住密码
	private void initSharedPreferences(String userName, String password,
			int type) {
		String saveName = "";
		if (cb_remember.isChecked() && userName != null && password != null) {
			saveName = userName;
		}
		SharedPreferences sp = getSharedPreferences("RememberPWD", MODE_PRIVATE);
		Editor e = sp.edit();
		e.putString("UserName", saveName);
		e.putString("password", password);
		e.putInt("type", type - 1);
		e.commit();
	}

	private void getSharedPreferences() {
		SharedPreferences sp = getSharedPreferences("RememberPWD", MODE_PRIVATE);
		String name = sp.getString("UserName", "");
		String password = sp.getString("password", "");
		int type = sp.getInt("type", -1);
		et_login_name.setText(name);
		et_login_password.setText(password);
		spinner_user_type_login.setSelection(type);
	}

	/**
	 * 判断是否为邮箱格式
	 * 
	 * @return
	 */
	private boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();

	}
	
	/**
	 * 实现两次点击返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				twiceQuit();
			} else {
				finish();
				quit();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
