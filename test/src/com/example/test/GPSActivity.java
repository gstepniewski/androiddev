package com.example.test;

import java.io.IOError;
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
	private LocationDecoder mDecoderTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		} catch (IllegalArgumentException e) {
			// Not provider available
		}
		try {
			mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,	this);
		} catch (IllegalArgumentException e) {
			// Not provider available
		}
	}

	@Override
	protected void onPause() {
		mDecoderTask.cancel(true);
		mManager.removeUpdates(this);
		super.onPause();
	}

	@Override
	public void onLocationChanged(Location location) {
		TextView gpsText = (TextView) findViewById(R.id.gps_coordinates);
		TextView addressText = (TextView) findViewById(R.id.gps_address);
		
		String coordinates = String.format(Locale.getDefault(), "%f, %f (%d m)", //
			location.getLongitude(), location.getLatitude(), (int)location.getAccuracy());
		gpsText.setText(coordinates);
		
		if (mDecoderTask != null) {
			mDecoderTask.cancel(true);
		}
		
		mDecoderTask = new LocationDecoder(location, addressText);
		mDecoderTask.execute();
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
		private final TextView mAddressTextView;

		public LocationDecoder(Location location, TextView addressTextView) {
			mLocation = location;
			mAddressTextView = addressTextView;
		}
		
		@Override
		protected String doInBackground(Void... params) {
			Geocoder coder = new Geocoder(GPSActivity.this, Locale.getDefault());
			
			try {
				List<Address> addresses = coder.getFromLocation(
						mLocation.getLatitude(), mLocation.getLongitude(), 1);
				
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					if (address.getMaxAddressLineIndex() > 0) {
						if (address.getAddressLine(0) != null && address.getLocality() != null) {
							return address.getAddressLine(0) + ", " + address.getLocality();
						}
					}
				}
			} catch (IOException e) {
				// Not essential
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				mAddressTextView.setText(result);
			} else {
				mAddressTextView.setText("No address found");
			}
			
			mDecoderTask = null;
		}

	}
}
