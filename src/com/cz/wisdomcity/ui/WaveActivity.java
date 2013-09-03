package com.cz.wisdomcity.ui;

import java.io.IOException;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.cz.wisdomcity.util.ControlView;

/**
 * 波形画面
 * @author Dean
 *
 */
public class WaveActivity extends BaseActivity {

	public static boolean isRecording = false;// 线程控制标记

	private SurfaceView sfv;
	private ControlView clv; //控制绘图
	private Button btn_play;
	private Button btn_pause;

	private boolean isShown = true;
	
	private PowerManager.WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 当前activity强制设置为横屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// 定义全局的 mWakeLock，通过对它的控制来开关屏幕
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(
				PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); // 参数请参阅文章最后
		
		setContentView(R.layout.activity_wave);
		setUserTitle();
		sfv = (SurfaceView) findViewById(R.id.sfv);
		btn_play = (Button) findViewById(R.id.btn_play);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int screenWidth = localDisplayMetrics.widthPixels;
		int screenHeight = localDisplayMetrics.heightPixels;
		try {
			if (XdTestActivity.btSocket.getInputStream() != null) {
				clv = new ControlView(this, sfv, screenWidth-120,
						screenHeight - 10,
						XdTestActivity.btSocket.getInputStream());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btn_play.setOnClickListener(listener);
		btn_pause.setOnClickListener(listener);

	}

	/**
	 * 触屏监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isShown) {
			btn_play.setVisibility(View.GONE);
			btn_pause.setVisibility(View.GONE);
			isShown = false;
		} else {
			if (!ControlView.isStop) {
				btn_pause.setVisibility(View.VISIBLE);
			} else {
				btn_play.setVisibility(View.VISIBLE);
			}
			isShown = true;
		}
		return super.onTouchEvent(event);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_play:
				sendMessage(MSG_START);
				clv.start();
				btn_play.setVisibility(View.GONE);
				btn_pause.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_pause:
				sendMessage(MSG_STOP);
				btn_play.setVisibility(View.VISIBLE);
				btn_pause.setVisibility(View.GONE);
				clv.stop();
				break;
			default:
				break;
			}
		}
	};

	public void fresh() {
		controlHandler.sendEmptyMessage(0);
	}

	private Handler controlHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			btn_play.setVisibility(View.VISIBLE);
			btn_pause.setVisibility(View.GONE);
		}

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mWakeLock.acquire();
		setTitle(getString(R.string.xindiantest));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mWakeLock.release();
		super.onPause();
		clv.stop();
		btn_play.setVisibility(View.VISIBLE);
		btn_pause.setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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