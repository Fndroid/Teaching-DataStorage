package com.fndroid.datastoragedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private static final String TAG = "MySQLiteOpenHelper";
	public static final String CREATE_STUDENT = "create table student(id integer primary key " +
			"autoincrement, name text, age integer)";

	public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
	                          int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STUDENT);
		Log.d(TAG, "onCreate: 数据库创建成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade: 数据库更新成功，旧版本号：" + oldVersion + "，新版本号为" + newVersion);
	}


}
