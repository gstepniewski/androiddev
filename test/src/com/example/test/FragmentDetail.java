package com.example.test;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentDetail extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_layout_detail, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle b = getArguments();
		String data = b.getString("arg");
		
		LinearLayout detailList = (LinearLayout) getActivity().findViewById(R.id.fragment_detail_container);
		
		for (int i = 0; i < 10; i++) {
			TextView tv = new TextView(getActivity());
			tv.setText("Item " + data);
			detailList.addView(tv);
		}
	}
	
	
}
