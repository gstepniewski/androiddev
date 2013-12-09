package com.example.test;

import android.net.Uri;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(mActivityClickListener);
		
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(mCallClickListener);
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(mWaitClickListener);
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(mInputClickListener);
		
		Button button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(mGPSClickListener);
		
		TextView button7 = (TextView) findViewById(R.id.button7);
		button7.setOnClickListener(mSQLiteClickListener);
		
		TextView button8 = (TextView) findViewById(R.id.button8);
		button8.setOnClickListener(mNotiClickListener);
		
		TextView button9 = (TextView) findViewById(R.id.button9);
		button9.setOnClickListener(mSMSClickListener);
		
		TextView button10 = (TextView) findViewById(R.id.button10);
		button10.setOnClickListener(mFragmentClickListener);

		TextView tv = (TextView) findViewById(R.id.textview);
		SharedPreferences sp = getSharedPreferences("mysharedprefences", MODE_PRIVATE);
		int count = sp.getInt("count", 0);
		tv.setText("Count: " + count);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		TextView tv = (TextView) findViewById(R.id.textview);
		
		SharedPreferences sp = getSharedPreferences("mysharedprefences", MODE_PRIVATE);
		int count = sp.getInt("count", 0);
		
		tv.setText("Count: " + count++);
		
		Editor editor = sp.edit();
		editor.putInt("count", count);
		editor.commit();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		TextView tv = (TextView) findViewById(R.id.textview);
		String text = tv.getText().toString();
		
		outState.putString("textviewtext", text);
	}
	
	private OnClickListener mActivityClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			intent.putExtra("text", "this is text");
			startActivity(intent);
		}
	};
	
	private OnClickListener mCallClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:12345"));
			startActivity(intent);
		}
	};
	
	private OnClickListener mWaitClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			WaitTask wt = new WaitTask(MainActivity.this, (TextView) findViewById(R.id.textview));
			wt.execute();
		}
	};
	
	private OnClickListener mInputClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final Dialog dialog = new Dialog(MainActivity.this);
			dialog.setContentView(R.layout.input_dialog);
			
			final EditText input = (EditText) dialog.findViewById(R.id.input_text);
			
			Button inputOk = (Button) dialog.findViewById(R.id.input_ok);
			inputOk.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String toast = "Input: " + input.getText().toString();
					Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}
			});
			
			dialog.show();
		}
	};
	
	private OnClickListener mGPSClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, GPSActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener mSQLiteClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, SQLiteActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener mNotiClickListener = new OnClickListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			
			String title = "Tytul";
			String content = "content content content content content content content content content";
			String ticker = "ticker ticker ticker ticker ticker ticker ticker ticker ticker";
			
			Notification noti = new Notification(R.drawable.ic_launcher, ticker, System.currentTimeMillis());
			PendingIntent npi = PendingIntent.getService(MainActivity.this, 0, new Intent(), 0);
			
			noti.setLatestEventInfo(MainActivity.this, title, content, npi);
			
			nm.notify(0, noti);
		}
	};
	
	private OnClickListener mSMSClickListener = new OnClickListener()  {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, SMSActivity.class);
			startActivity(i);
		}
	};
	
	private OnClickListener mFragmentClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, FragmentActivity.class);
			startActivity(i);
		}
	};
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		TextView tv = (TextView) findViewById(R.id.textview);
		String text = savedInstanceState.getString("textviewtext");
		
		tv.setText(text);
	}
    
}
