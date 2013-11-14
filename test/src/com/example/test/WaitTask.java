package com.example.test;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class WaitTask extends AsyncTask<Void, Integer, Void> {
	
	private final Context mContext;
	private final TextView mTextView;
	
	public WaitTask(Context context, TextView textView) {
		mContext = context;
		mTextView = textView;
	}

	@Override
	protected void onPreExecute() {
		mTextView.setText("Waiting");
		
		Toast startToast = Toast.makeText(mContext, "Please Wait", Toast.LENGTH_LONG);
		startToast.show();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		for (int i = 0 ; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return null;
			}
			
			publishProgress(i);
		}
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		mTextView.setText("Waiting " + values[0]);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		mTextView.setText("Finished");
		
		Builder builder = new Builder(mContext);
		builder.setTitle("Complete");
		builder.setMessage("Waiting is finished");
		builder.setNeutralButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

}
