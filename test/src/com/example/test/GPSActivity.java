package com.example.test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class GPSActivity extends Activity implements LocationListener {

	private LocationManager mManager = null;
	private LocationDecoder mDecoder = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		try {
			mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		} catch (IllegalArgumentException e) {
			// Ignore
		}

		try {
			mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,	this);
		} catch (IllegalArgumentException e) {
			// Ignore
		}
	}
	
	@Override
	protected void onPause() {
		mManager.removeUpdates(this);
		mDecoder.cancel(true);
		
		super.onPause();
	}

	@Override
	public void onLocationChanged(Location location) {
		TextView gpsText = (TextView) findViewById(R.id.gps_coordinates);
		String coordinates = String.format(Locale.getDefault(), "%f, %f (%dm)", //
				location.getLongitude(), location.getLatitude(), (int) location.getAccuracy());
		gpsText.setText(coordinates);
		
		TextView addressText = (TextView) findViewById(R.id.gps_address);
		
		if (mDecoder != null) {
			mDecoder.cancel(true);
		}
		
		mDecoder = new LocationDecoder(location, addressText);
		mDecoder.execute();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	private class LocationDecoder extends AsyncTask<Void, Void, String> {
		
		private final Location mLocation;
		private final TextView mTextView;
		
		public LocationDecoder(Location location, TextView textView) {
			mLocation = location;
			mTextView = textView;
		}
		
		@Override
		protected void onPreExecute() {
			mTextView.setText("Getting address");
		}
		
		@Override
		protected String doInBackground(Void... params) {
			Geocoder coder = new Geocoder(GPSActivity.this, Locale.getDefault());
			
			try {
				List<Address> addresses = coder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
				
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					if (address.getMaxAddressLineIndex() > 0) {
						if (address.getAddressLine(0) != null && address.getLocality() != null) {
							return address.getAddressLine(0) + ", " + address.getLocality();
						}
					}
				}
			} catch (IOException e) {
				// Ignore
			}
			
			return "No address";
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				mTextView.setText(result);
			}
			
			mDecoder = null;
		}
	}
	

}
