package com.example.test.test;

import com.example.test.R;
import com.example.test.MainActivity;
import com.example.test.SecondActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	
	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
	}
	
	public void testButton2() {
		ActivityMonitor monitor = getInstrumentation().addMonitor(SecondActivity.class.getName(), null, false);
		
		Button button2 = (Button) mActivity.findViewById(R.id.button2);
		TouchUtils.clickView(this, button2);
		
		SecondActivity sa = (SecondActivity) monitor.waitForActivityWithTimeout(2000);
		assertNotNull(sa);
		
		TextView secondText = (TextView) sa.findViewById(R.id.second_text);
		
		ViewAsserts.assertOnScreen(sa.getWindow().getDecorView(), secondText);
		assertEquals("this is text", secondText.getText());
	}
}
