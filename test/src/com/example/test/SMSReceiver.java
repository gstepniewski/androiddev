package com.example.test;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle b = intent.getExtras();
		
		System.out.println("Sms received");
		
		if (b == null) {
			return;
		}
		
		Object[] smsExtra = (Object[]) b.get("pdus");
		
		ContentResolver cr = context.getContentResolver();
		
		if (smsExtra.length > 0) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])smsExtra[0]);
			
			String sms = smsMessage.getOriginatingAddress() + ": " + smsMessage.getMessageBody();

			Intent i = new Intent(context, SecondActivity.class);
			i.putExtra("text", sms);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}

}
