package com.example.test;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        
        LinearLayout smsList = (LinearLayout) findViewById(R.id.sms_list);
        
        Uri smsUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(smsUri, null, null, null, null);
        
        while (cursor.moveToNext()) {
        	String message = cursor.getString(cursor.getColumnIndex("body"));
        	String number = cursor.getString(cursor.getColumnIndex("address"));
        	
        	TextView tv = new TextView(this);
        	tv.setText(number + " : " + message);
        	smsList.addView(tv);
        	
        	// Deleting SMS
//            String id = cursor.getString(0);
//            getContentResolver().delete("content://sms/" + id, null, null);
        }
        
        cursor.close();
    }
	
}
