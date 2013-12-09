package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentList extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_layout_list, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		LinearLayout itemList = (LinearLayout) getActivity().findViewById(R.id.fragment_list_container);
		itemList.removeAllViews();
		
		for (int i = 0; i < 10; i++) {
			TextView tv = new TextView(getActivity());
			tv.setText("Item " + i);
			
			final int index = i;
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Fragment detailFragment = new FragmentDetail();
					
					Bundle b = new Bundle();
					b.putString("arg", index+"");
					detailFragment.setArguments(b);
					
					final FragmentManager fm = getActivity().getFragmentManager();
					final FragmentTransaction ft = fm.beginTransaction();
					
					Configuration c = getActivity().getResources().getConfiguration();
					int orientation = c.orientation;
					
					ft.replace(R.id.detail_container, detailFragment);
					
					if (orientation == Configuration.ORIENTATION_PORTRAIT) {
						ft.addToBackStack(null);
					}
					
					ft.commit();
				}
			});
			
			itemList.addView(tv);
		}
	}
	
}
