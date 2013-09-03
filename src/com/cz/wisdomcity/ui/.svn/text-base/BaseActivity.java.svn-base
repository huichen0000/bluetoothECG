package com.cz.wisdomcity.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// FirstActivity.getInstance().closeMenu();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		flag = false;
	}

	private OutputStream outStream = null;
	protected static String encodeType = "UTF-8";
	protected static byte MSG_SINGLE_TEST = 0x00; // 单次测量
	protected static byte MSG_MOVE_TEST = 0x01; // 动态测量
	protected static byte MSG_START = (byte) 0xFF; // 开始测量
	protected static byte MSG_STOP = (byte) 0xf0; // 停止测量

	
	/**
	 * 发送指令
	 * @param msg
	 */
	public void sendMessage(byte msg) {

		// 控制模块
		try {
			outStream = XdTestActivity.btSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(),
					" Output stream creation failed.", Toast.LENGTH_SHORT);
		}
//		byte[] msgBuffer = null;
//		try {
//			msgBuffer = msg.getBytes(encodeType);// 编码
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//			Log.e("write", "Exception during write encoding GBK ", e1);
//		}
		try {
			outStream.write(msg);
			// setTitle("成功发送指令:" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "发送数据失败",
					Toast.LENGTH_SHORT).show();

		}

	}

	public WisdomCityApplication getWisdomApplication() {
		return (WisdomCityApplication) getApplication();
	}

	
	protected void setUserTitle() {
		TextView tv_user = (TextView) findViewById(R.id.tv_top_username);
		tv_user.setText(getWisdomApplication().userName + "正在使用!");
	}

	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void quit() {
		// 退出时清除所有的notification
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
		int api = Build.VERSION.SDK_INT;
		if (api < 8) {
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage("com.cz.wisdomcity.ui");
		} else {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
		}
	}
	
	public void getToTabHost(int page){
		FirstActivity th2 = FirstActivity.getInstance();
		th2.mTabHost.setCurrentTab(page);
		if(page==3){
			setTitle(getString(R.string.xindiantest));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// 在这里做你想做的事情
			if (!flag) {
				FirstActivity.getInstance().openMenu();
				flag = true;
			} else {
				FirstActivity.getInstance().closeMenu();
				flag = false;
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/************* 实现两次返回退出程序 *********************/
	protected static Boolean isExit = false;
	protected static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	public void twiceQuit() {
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			if (!hasTask) {
				tExit.schedule(task, 2000);
			}
		}
	}

	public String getVersionName() {
		String versionName = "";
		PackageManager pm = getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(getPackageName(), 0);
			versionName = pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}

	protected void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(int res) {
		Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
	}

	// 记住密码
	protected void initSharedPreferences(String key, String value) {
		SharedPreferences sp = getSharedPreferences("RememberPWD", MODE_PRIVATE);
		Editor e = sp.edit();
		e.putString(key, value);
		e.commit();
	}

	protected String getSharedPreferences(String key) {
		SharedPreferences sp = getSharedPreferences("RememberPWD", MODE_PRIVATE);
		return sp.getString(key, "1");
	}
}
