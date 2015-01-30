package com.example.sms_sampleproj;

import java.util.ArrayList;
import java.util.Date;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	public class MyDBHelp extends SQLiteOpenHelper {

		public MyDBHelp(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table sms_test(time numeric, number text,"
					+ " content text)");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

	SQLiteOpenHelper myDBHelper;
	SQLiteDatabase myDB;

	ListView lv;
	ArrayList<String> list;
	ArrayAdapter adpt;

	Button refBtn;

	
	protected void listRefresh() {
		list = new ArrayList<String>();
		adpt = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

//		Cursor rs = myDB.rawQuery("select * from sms_test "
//				+ "where time=datetime('now', 'localtime') "
//				+ "order by time desc", null);
		
		Cursor rs = myDB.rawQuery("SELECT * FROM sms_test ORDER BY time DESC", null);

		while (rs.moveToNext()) {
			list.add(rs.getString(0) + "/" + rs.getString(1) + "/"
					+ rs.getString(2));
		}
		rs.close();

		setListAdapter(adpt);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myDBHelper = new MyDBHelp(this, "LifeLogTest", null, 1);
		myDB = myDBHelper.getWritableDatabase();

		Button refBtn = (Button) findViewById(R.id.refBtn);

		refBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listRefresh();
			}
		});
		
		listRefresh();

		Date today = new Date();
		String dayStr = (today.getYear()+1900) + "/" + (today.getMonth()+1) + "/" + today.getDate();
		Toast.makeText(getApplicationContext(), dayStr, 1000).show();

	}

}
