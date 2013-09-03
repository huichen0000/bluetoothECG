package com.cz.wisdomcity.util;

import com.cz.wisdomcity.entity.LoginInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginHelper {
	@SuppressWarnings("unused")
	private Context context;

	private DBOpenHelper openHelper;

	public LoginHelper(Context context) {
		openHelper = new DBOpenHelper(context);
	}

	private SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase database = null;
		database = openHelper.getWritableDatabase();
		return database;
	}

	private SQLiteDatabase getReadableDatabase() {
		return openHelper.getReadableDatabase();
	}

	/**
	 * 
	 * @param baseinfo
	 * @return false代表用户名已存在，true代表注册成功
	 */
	public boolean addLogin(LoginInfo baseinfo) {
		// TODO Auto-generated method stub
		LoginInfo info = getLogin(baseinfo.name);
		if (info != null) {
			return false;
		} else {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues initialValues = new ContentValues();
			initialValues.put("name", baseinfo.name);
			initialValues.put("password", baseinfo.password);
			initialValues.put("email", baseinfo.email);
			initialValues.put("type", baseinfo.type);
			db.insert("login", null, initialValues);
			db.close();
			return true;
		}
	}

	/**
	 * 根据登录名查询是否存在用户
	 * 
	 * @param info
	 * @return
	 */
	public LoginInfo getLogin(String name) {
		LoginInfo loginInfo = null;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(true, "login", new String[] { "password" },
					"name='" + name + "'", null, null, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				loginInfo = new LoginInfo();
				loginInfo.name = name;
				loginInfo.password = cursor.getString(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
			db.close();
		}
		return loginInfo;
	}

	/**
	 * 用户登陆
	 * 
	 * @param info
	 * @return
	 */
	public LoginInfo login(String name, String password, int type) {
		SQLiteDatabase db = null;
		if (name.equals("admin") && getLogin("admin") == null) {
			db = getWritableDatabase();
			db.execSQL(
					"insert into login(name, password,email,type) values(?,?,?,?)",
					new Object[] { "admin", "admin", "admin@qq.com", 1 });
		}else{
			db = getWritableDatabase();
		}
		LoginInfo loginInfo = null;
		Cursor cursor = null;
		try {
			cursor = db.query(true, "login", new String[] { "password" },
					"name='" + name + "' and password ='" + password
							+ "' and type=" + type, null, null, null, null,
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				loginInfo = new LoginInfo();
				loginInfo.name = name;
				loginInfo.password = cursor.getString(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return loginInfo;
	}

	public boolean updatePassword(String name, String password,
			String new_password) {
		LoginInfo info = getLogin(name);
		if (info != null && password.equals(info.password)) {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues initialValues = new ContentValues();
			initialValues.put("password", password);
			try {
				db.update("login", initialValues, "name='" + name + "'", null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			db.close();
			return true;
		} else {
			return false;
		}
	}

	public void updateLogin(LoginInfo info) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues initialValues = new ContentValues();
		initialValues.put("password", info.password);
		try {
			db.update("login", initialValues, "name='" + info.name + "'", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		db.close();
	}

	private class DBOpenHelper extends SQLiteOpenHelper {
		private static final String DBNAME = "sui_login";
		private static final int VERSION = 1;

		public DBOpenHelper(Context context) {
			super(context, DBNAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table IF NOT EXISTS login (_id integer primary key autoincrement, "
					+ "name text ,password text,email text,type integer default 0);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}
}
