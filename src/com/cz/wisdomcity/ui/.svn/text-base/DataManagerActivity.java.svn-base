package com.cz.wisdomcity.ui;

import com.cz.wisdomcity.ui.R;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DataManagerActivity extends BaseActivity implements
		OnClickListener {

	private ListView listview_data;
	private Button btn_clear_data;
	private Button btn_back;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datamanager);
		setUserTitle();
		listview_data = (ListView) findViewById(R.id.listview_datamanager);
		btn_clear_data = (Button) findViewById(R.id.btn_clear_data);
		btn_back = (Button) findViewById(R.id.btn_back);

		btn_clear_data.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		DataManagerAdapter adapter = new DataManagerAdapter();
		listview_data.setAdapter(adapter);
		context = this;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_clear_data:

			break;
		case R.id.btn_back:
			getToTabHost(3);
			break;
		default:
			break;
		}
	}

	private class DataManagerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 20;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(DataManagerActivity.this)
						.inflate(R.layout.listview_item_datamanager, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_test = (TextView) convertView
						.findViewById(R.id.tv_test);
				viewHolder.tv_type = (TextView) convertView
						.findViewById(R.id.tv_type);
				viewHolder.tv_date = (TextView) convertView
						.findViewById(R.id.tv_date);
				viewHolder.tv_xinlv = (TextView) convertView
						.findViewById(R.id.tv_xinlv);
				viewHolder.tv_QT = (TextView) convertView
						.findViewById(R.id.tv_QT);
				viewHolder.tv_PR = (TextView) convertView
						.findViewById(R.id.tv_PR);
				viewHolder.tv_data = (TextView) convertView
						.findViewById(R.id.tv_data);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_test.setBackgroundResource(R.drawable.tv_bg_on);
			viewHolder.tv_test.setText("test" + (position + 1));
			return convertView;
		}

	}

	private class ViewHolder {
		TextView tv_test;
		TextView tv_type;
		TextView tv_date;
		TextView tv_xinlv;
		TextView tv_QT;
		TextView tv_PR;
		TextView tv_data;
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
