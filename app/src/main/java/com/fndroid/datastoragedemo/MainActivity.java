package com.fndroid.datastoragedemo;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	public static final String TABLENAME = "student";

	private MySQLiteOpenHelper mMySQLiteOpenHelper;
	private SQLiteDatabase mDatabase;
	private EditText name, age;
	private CheckBox autoDownload, askBeforeExit;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		name = (EditText) findViewById(R.id.et_name);
		age = (EditText) findViewById(R.id.et_age);
		autoDownload = (CheckBox) findViewById(R.id.cb_autoDownload);
		askBeforeExit = (CheckBox) findViewById(R.id.cb_askBeforeExit);
		sp = this.getSharedPreferences("settings", MODE_PRIVATE);
		autoDownload.setChecked(sp.getBoolean("autoDownload", false));
		askBeforeExit.setChecked(sp.getBoolean("askBeforeExit", false));
	}

	public void openDatabase(View view) {
		mMySQLiteOpenHelper = new MySQLiteOpenHelper(this, "student.db", null, 1);
		mDatabase = mMySQLiteOpenHelper.getWritableDatabase();
	}

	public void upgradeDatabase(View view) {
		mMySQLiteOpenHelper = new MySQLiteOpenHelper(this, "student.db", null, 2);
		mDatabase = mMySQLiteOpenHelper.getWritableDatabase();
	}

	public void insertData(View view) {
		String sname = name.getText().toString();
		String sage = age.getText().toString();
		ContentValues values = new ContentValues();
		values.put("name", sname);
		values.put("age", sage);
		long id = mDatabase.insert(TABLENAME, null, values);
		Toast.makeText(MainActivity.this, "插入成功，新列的id为：" + id, Toast.LENGTH_SHORT).show();
	}

	public void deleteData(View view) {
		String sname = name.getText().toString();
		int lines = mDatabase.delete(TABLENAME, "name = ?", new String[]{sname});
		Toast.makeText(MainActivity.this, "删除成功，影响行数为：" + lines, Toast.LENGTH_SHORT).show();
	}

	public void alterData(View view) {
		String sname = name.getText().toString();
		String sage = age.getText().toString();
		ContentValues values = new ContentValues();
		values.put("name", sname);
		values.put("age", sage);
		int lines = mDatabase.update(TABLENAME, values, "name = ?", new String[]{sname});
		Toast.makeText(MainActivity.this, "修改成功，影响行数为：" + lines, Toast.LENGTH_SHORT).show();
	}

	public void selectData(View view) {
		Cursor cursor = mDatabase.query(TABLENAME, null, null, null, null, null, "id");
		int id_index = cursor.getColumnIndex("id");
		int name_index = cursor.getColumnIndex("name");
		int age_index = cursor.getColumnIndex("age");
		Log.d(TAG, "id|name|age");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d(TAG, cursor.getInt(id_index) + "|" + cursor.getString(name_index) + "|" + cursor
					.getInt(age_index));
			cursor.moveToNext();
		}
	}

	public void writeToSP(View view) {
		SharedPreferences sp = this.getSharedPreferences("setting", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("DBVersion", 1);
		editor.apply();
	}

	public void readFromSP(View view) {
		SharedPreferences sp = this.getSharedPreferences("setting", MODE_PRIVATE);
		int dbversion = sp.getInt("DBVersion", -1);
		Log.d(TAG, "readFromSP: SharedPreferences中的DBVersion值为：" + dbversion);
	}

	public void autoDownload(View view){
		SharedPreferences.Editor editor = sp.edit();
		autoDownload.setChecked(autoDownload.isChecked());
		editor.putBoolean("autoDownload", autoDownload.isChecked());
		editor.apply();
	}

	public void askBeforeExit(View view){
		SharedPreferences.Editor editor = sp.edit();
		askBeforeExit.setChecked(askBeforeExit.isChecked());
		editor.putBoolean("askBeforeExit", askBeforeExit.isChecked());
		editor.apply();
	}

}
