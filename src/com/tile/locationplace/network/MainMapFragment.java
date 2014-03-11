

package com.tile.locationplace.network;


import android.annotation.TargetApi;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.database.MyFood;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainMapFragment extends MapFragment {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Marker placeMarker(MyFood food) {

		try {
			double lat = Double.parseDouble(food.get_lat());
			double lng = Double.parseDouble(food.get_lng());
			LatLng point = new LatLng(lat, lng);

			Marker m = getMap().addMarker(
					new MarkerOptions().position(point).title(food.get_name()));
			return m;
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Lỗi MainMapFragment",
					Toast.LENGTH_LONG).show();
		}
		return null;

	}

	public Marker placeMarker(MyATM atm) {

		try {
			double lat = Double.parseDouble(atm.get_lat());
			double lng = Double.parseDouble(atm.get_lng());
			LatLng point = new LatLng(lat, lng);

			Marker m = getMap().addMarker(
					new MarkerOptions().position(point).title(atm.get_name()));
			return m;
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Lỗi MainMapFragment",
					Toast.LENGTH_LONG).show();
		}
		return null;

	}

}
