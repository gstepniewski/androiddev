package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		TextView secondText = (TextView) findViewById(R.id.second_text);
		String text = getIntent().getExtras().getString("text");
		secondText.setText(text);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		Button button2 = (Button) findViewById(R.id.button2);
		
		Intent i = new Intent(this, MainActivity.class);
		
		if (v.equals(button2)) {
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
		startActivity(i);
	}

}
