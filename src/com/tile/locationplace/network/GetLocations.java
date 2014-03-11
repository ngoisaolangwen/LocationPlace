/**
 * \\\___________|-|-|________________///
 * --------------------------------------
 * Class kiá»ƒm tra GPS or Network vÃ  Ä‘á»‹nh vá»‹ ngÆ°á»�i dÃ¹ng
 * ThÃ´ng bÃ¡o náº¿u Ä‘á»‹nh vá»‹ khÃ´ng Ä‘Æ°á»£c.
 * Láº¥y tá»�a Ä‘á»™ ngÆ°á»�i dÃ¹ng.
 */



package com.tile.locationplace.network;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GetLocations extends Service implements LocationListener {
	
	
	/** Táº¡o má»™t Context. */
	private final Context _mContext;

	// flag for GPS status
	private boolean _isGpsEnable = false;
	// flag for Network status
	private boolean _isNetworkEnable = false;

	boolean _canGetLocation = false;

	Location _location;// location
	double _latitude;// latitude
	double _longitude;// longitude

	// update change in 5 meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;

	// The minimum time between updates
	private static final long MIN_TIME_BW_UPDATES = 100 * 60 * 1;

	protected LocationManager _locationManagers;

	
	public GetLocations(Context context){
		this._mContext = context;
		getLocation();
	}
	
	
	/** Láº¥y vá»‹ trÃ­ cá»§a ngÆ°á»�i dÃ¹ng */
	private Location getLocation() {

		try {
			_locationManagers = (LocationManager) _mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			_isGpsEnable = _locationManagers
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting Network status
			_isNetworkEnable = _locationManagers
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!_isGpsEnable && !_isNetworkEnable) {
				// No network provider is enable
			} else {
				this._canGetLocation = true;
				if (_isNetworkEnable) {
					_locationManagers.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (_locationManagers != null) {
						_location = _locationManagers
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (_location != null) {
							_latitude = _location.getLatitude();
							_longitude = _location.getLongitude();
						}
					}
				}// end _isNetworkEnable == true

				/* getting lat/long using GPS Services */

				if (_isGpsEnable) {
					if (_location == null) {
						_locationManagers.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enable", "GPS Enable");
						if (_locationManagers != null) {
							_location = _locationManagers
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (_location != null) {
								_latitude = _location.getLatitude();
								_longitude = _location.getLongitude();
							}
						}
					}

				}// end _isGpsEnable == true
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return _location;

	}
	
	
	/**
	 * 
	 * Stop using GPS Listener Calling this function will stop using GPS in my
	 * app
	 * 
	 */
	public void stopUsingGps() {
		if (_locationManagers != null) {
			_locationManagers.removeUpdates(GetLocations.this);
		}
	}

	/**
	 * Function to get latitude
	 */
	public double getLatitude() {
		if (_location != null) {
			_latitude = _location.getLatitude();
		}
		return _latitude;
	}

	/**
	 * Function to get longitude
	 */
	public double getLongitude() {
		if (_location != null) {
			_longitude = _location.getLongitude();
		}
		return _longitude;
	}

	/**
	 * Function to check GPS/Wifi enable
	 * 
	 * @return boolean
	 */
	public boolean canGetLocation() {
		return this._canGetLocation;
	}

	/**
	 * Function to show setting alert dialog Setting GPS/Wifi
	 */
	public void showSettingAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(_mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to setting menu?");

		// OK Button
		alertDialog.setPositiveButton("Setting",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						/* Gá»�i má»™t Activity chá»©a cÃ i Ä‘áº·t láº¥y vá»‹ trÃ­. */
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						_mContext.startActivity(intent);
					}

				});

		// Cancel Button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}
	
	
	
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		getLocation();
	}

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
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

}
