package com.example.test;

import android.os.AsyncTask;
import android.widget.TextView;

public class WaitTask extends AsyncTask<Void, Integer, Void> {
	
	private final TextView mTextView;
	
	public WaitTask(TextView textView) {
		mTextView = textView;
	}

	@Override
	protected void onPreExecute() {
		mTextView.setText("Waiting");
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
	}

}
