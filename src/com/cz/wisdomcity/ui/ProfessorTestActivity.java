package com.cz.wisdomcity.ui;

import com.cz.wisdomcity.ui.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ProfessorTestActivity extends BaseActivity implements OnClickListener{
	
	private Button btn_get_professional_suggestion;
	private EditText et_need_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_professortest);
		setUserTitle();
		btn_get_professional_suggestion= (Button) findViewById(R.id.btn_get_professional_suggestion);
		et_need_info = (EditText) findViewById(R.id.et_need_info);
		btn_get_professional_suggestion.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_get_professional_suggestion:
			showToast("获取专家诊断意见");
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
