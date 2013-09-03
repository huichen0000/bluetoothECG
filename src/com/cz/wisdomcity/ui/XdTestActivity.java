package com.cz.wisdomcity.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class XdTestActivity extends BaseActivity {

	private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	private Button btnSearch;
	private ListView lvBTDevices;
	private ArrayAdapter<String> adtDevices;
	private List<String> lstDevices = new ArrayList<String>();
	private BluetoothAdapter btAdapt;
	public static BluetoothSocket btSocket;

	private ProgressBar progressBar;
	private TextView tv_tishi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xdtest);

		// ProgressBar
		progressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
		tv_tishi = (TextView) findViewById(R.id.tv_blue_tishi);

		// Button 设置
		btnSearch = (Button) this.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(listener);

		// ListView及其数据源 适配器
		lvBTDevices = (ListView) this.findViewById(R.id.lvDevices);
		adtDevices = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lstDevices);
		lvBTDevices.setAdapter(adtDevices);
		lvBTDevices.setOnItemClickListener(new ItemClickEvent());

		btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能

		// 注册Receiver来获取蓝牙设备相关的结果
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(searchDevices, intent);

		// 进入时判断蓝牙是否打开
		if (!btAdapt.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, 2);
		}

		// new AcceptThread().start(); //测试用服务端

	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(searchDevices);
		super.onDestroy();
		// android.os.Process.killProcess(android.os.Process.myPid());
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnSearch:// 搜索蓝牙设备，在BroadcastReceiver显示结果
				lstDevices.clear();
				btAdapt.startDiscovery();
				progressBar.setVisibility(View.VISIBLE);
				tv_tishi.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	};

	private BroadcastReceiver searchDevices = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();

			// 显示所有收到的消息及其细节
			for (int i = 0; i < lstName.length; i++) {
				String keyName = lstName[i].toString();
				System.out.println(keyName + String.valueOf(b.get(keyName)));
			}
			// 搜索设备时，取得设备的MAC地址
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String str = device.getName() + "|" + device.getAddress();
				if (lstDevices.indexOf(str) == -1)// 防止重复添加
					lstDevices.add(str); // 获取设备名称和mac地址
				adtDevices.notifyDataSetChanged();
				progressBar.setVisibility(View.GONE);
				tv_tishi.setVisibility(View.GONE);
			}
		}
	};

	class ItemClickEvent implements AdapterView.OnItemClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			btAdapt.cancelDiscovery();
			String str = lstDevices.get(arg2);
			String[] values = str.split("\\|");
			String address = values[1];
			System.out.println("address" + values[1]);

			UUID uuid = UUID.fromString(SPP_UUID);
			BluetoothDevice btDev = btAdapt.getRemoteDevice(address);

			try {
				btSocket = btDev.createRfcommSocketToServiceRecord(uuid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("CHEN", "Low: Connection failed.", e);
			}

			// Toast.makeText(XdTestActivity.this, "连接成功,进入登录界面",
			// Toast.LENGTH_SHORT).show();
			// // 进入登入界面
			// Intent intent = new Intent();
			// intent.setClass(XdTestActivity.this, LoginActivity.class);
			// startActivity(intent);

			try {
				btSocket.connect();
				Toast.makeText(XdTestActivity.this, "连接成功,进入登录界面",
						Toast.LENGTH_SHORT).show();
				// 进入登入界面
				Intent intent = new Intent();
				intent.setClass(XdTestActivity.this, LoginActivity.class);
				startActivity(intent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(XdTestActivity.this, "连接失败,请重新尝试连接...",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	/**
	 * 
	 * 测试用服务端接收连接
	 */
	private class AcceptThread extends Thread {
		// The local server socket
		private final BluetoothServerSocket mmServerSocket;

		public AcceptThread() {
			BluetoothServerSocket tmp = null;

			// Create a new listening server socket
			try {
				tmp = btAdapt
						.listenUsingRfcommWithServiceRecord(
								"CHEN",
								UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			} catch (IOException e) {
				Log.e("CHEN", "listen() failed", e);
			}
			mmServerSocket = tmp;
		}

		@Override
		public void run() {
			setName("AcceptThread");
			BluetoothSocket socket = null;

			// Listen to the server socket if we're not connected
			while (true) {
				try {
					// This is a blocking call and will only return on a
					// successful connection or an exception
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					Log.e("CHEN", "accept() failed", e);
					break;
				}
				// If a connection was accepted
				if (socket != null) {
					btSocket = socket;
				}
			}
		}

		public void cancel() {
			try {
				mmServerSocket.close();
			} catch (IOException e) {
				Log.e("CHEN", "close() of server failed", e);
			}
		}
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
