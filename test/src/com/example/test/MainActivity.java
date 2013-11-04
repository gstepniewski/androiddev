package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
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
		
		String hello = getResources().getString(R.string.hello_world);
		String bye = getResources().getString(R.string.bye_world);
		
		if (hello.equals(tv.getText())) {
			tv.setText(bye);
		} else {
			tv.setText(hello);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		TextView tv = (TextView) findViewById(R.id.textview);
		String text = tv.getText().toString();
		
		outState.putString("textviewtext", text);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		TextView tv = (TextView) findViewById(R.id.textview);
		String text = savedInstanceState.getString("textviewtext");
		
		tv.setText(text);
	}
    
}
