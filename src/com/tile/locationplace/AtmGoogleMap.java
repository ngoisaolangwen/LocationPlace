/**
 * Hiá»ƒn thá»‹ google map cho ATM.
 */

package com.tile.locationplace;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.network.DirectionsJSONParser;
import com.tile.locationplace.network.MainMapFragment;

public class AtmGoogleMap extends FragmentActivity {
	
	
	/** Báº£n Ä‘á»“. */
	public GoogleMap _myMap;
	/** DÃ¹ng Ä‘á»ƒ váº½ Ä‘Æ°á»�ng Ä‘i trÃªn báº£n Ä‘á»“. */
	public PolylineOptions polyline;
	MainMapFragment mainMapFragment;
	ProgressDialog progressD;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atm_google_map);
		/* áº¨n MenuBar */
		menuBarHide();
		/* Show Progress Dialog. */
		showDialog(0);
		/* Hiá»ƒn thá»‹ map khi Activity dc gá»�i. */
		onLoadGoogleMap();
		
		/* TÃ¬m Ä‘Æ°á»�ng. */
		new DownloadTask().execute();

		
	}
	
	/** Táº¡o má»™t ProgressDialog. */
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case 0:
			progressD = new ProgressDialog(this);
			progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressD.setMessage("Loading.....");
			return progressD;
		}

		return super.onCreateDialog(id, args);
	}
	
	/** áº¨n MenuBar. */
	private void menuBarHide() {

		ActionBar actionB = getActionBar();
//		actionB.setCustomView(R.layout.menu_timkiem);
//		actionB.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
//				| ActionBar.DISPLAY_SHOW_HOME);
//		actionB.setDisplayShowTitleEnabled(false);
//		actionB.setDisplayShowHomeEnabled(false);
		actionB.hide();
		
	}
	
	/** Hiá»ƒn thá»‹ Google Maps vÃ  Ä‘á»‹nh vá»‹ ngÆ°á»�i dÃ¹ng khi á»©ng dá»¥ng Ä‘Æ°á»£c má»Ÿ */
	private void onLoadGoogleMap() {

		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapAtm);
		_myMap = fm.getMap();
		/* Hiá»ƒn thá»‹ nÃºt Ä‘á»‹nh vá»‹ trÃªn Map vÃ  áº©n nÃºt zoom */
		_myMap.setMyLocationEnabled(true);
		_myMap.getUiSettings().setZoomControlsEnabled(false);
		Log.d("LOI", "");
		
		/*
		 * Custom InfoWindows Marker. Xem Marker vá»›i giao diá»‡n khÃ¡c.
		 */

		_myMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				
				return null;
			}

			@Override
			public View getInfoContents(Marker arg0) {
				

			
				View v = getLayoutInflater().inflate(
						R.layout.item_marker_info, null);

				/* Ã�nh xáº¡ tá»«ng hÃ ng ListView cáº­p nháº­t thÃ´ng tin. */
				TextView tvInfo = (TextView) v
						.findViewById(R.id.tvinfo_item_marker_info);
				TextView tvName = (TextView) v
						.findViewById(R.id.tvname_item_marker_info);
				ImageView im = (ImageView) v
						.findViewById(R.id.imageView1);
				tvName.setText(arg0.getTitle());
				tvInfo.setText(arg0.getSnippet());
				im.setImageResource(R.drawable.atm);

				return v;
			}
		});


		
	}
	
	public void addMarker(MyATM myATM) {
		// Creat markerOption
		MarkerOptions markerOption = new MarkerOptions();
		double _lat = Double.parseDouble(myATM.get_lat());
		double _lng = Double.parseDouble(myATM.get_lng());
		LatLng point = new LatLng(_lat, _lng);
		markerOption.position(point);
		String info = myATM.get_address() + ", PhÆ°á»�ng " + myATM.get_ward()
				+ ", " + myATM.get_district();
		markerOption.snippet(info).title(myATM.get_name());

		// add marker vao Google Map
		_myMap.addMarker(markerOption);
		_myMap.moveCamera(CameraUpdateFactory.newLatLng(point));
		_myMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.atm_google_map, menu);
		return true;
	}
	
	/* __________________________________________________________________ */
	/* ============== CÃ¡c hÃ m dÃ¹ng Ä‘á»ƒ váº½ Ä‘Æ°á»�ng Ä‘i ==================== */
	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}
	
	
	/** HÃ m láº¥y Ä‘Æ°á»�ng Ä‘i tá»« 2 Ä‘iá»ƒm. */
	private class DownloadTask extends AsyncTask<Void, Void, PolylineOptions> {
		

		String data = "";
		// Downloading data in non-ui thread
		@Override
		protected PolylineOptions doInBackground(Void... url) {

	
					double lat = 0 ,lng = 0;
					LatLng pointLocal = new LatLng(
							MainLocationPlace._getMyLocation.getLatitude(),
							MainLocationPlace._getMyLocation.getLongitude());
					try{
					
					lat = Double.parseDouble(MainLocationPlace._atm.get_lat());
					lng = Double.parseDouble(MainLocationPlace._atm.get_lng());
						
					}catch(Exception e){
						Log.d("lay toa do", e.getMessage());
					}
					LatLng dests = new LatLng(lat, lng);

					// Getting URL to the Google Directions API
					String urls = getDirectionsUrl(pointLocal, dests);

					try {
						// Fetching the data from web service
						data = downloadUrl(urls);
					} catch (Exception e) {
						Log.d("Background Task", e.toString());
					}

					/** */
					JSONObject jObject;
					List<List<HashMap<String, String>>> routes = null;

					try {
						jObject = new JSONObject(data);
						DirectionsJSONParser parser = new DirectionsJSONParser();

						// Starts parsing data
						routes = parser.parse(jObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					/** Láº¥y danh sÃ¡ch vÃ  phÃ¢n tÃ­ch ra. */
					ArrayList<LatLng> points = null;
					PolylineOptions lineOptions = null;
					// MarkerOptions markerOptions = new MarkerOptions();
					String distance = "";
					String duration = "";

					if (routes.size() < 1) {
//						Toast.makeText(getBaseContext(), "No Points",
//								Toast.LENGTH_SHORT).show();
						return null;
					}

					// Traversing through all the routes
					for (int i = 0; i < routes.size(); i++) {
						points = new ArrayList<LatLng>();
						lineOptions = new PolylineOptions();

						// Fetching i-th route
						List<HashMap<String, String>> path = routes.get(i);

						// Fetching all the points in i-th route
						for (int j = 0; j < path.size(); j++) {
							HashMap<String, String> point = path.get(j);

							if (j == 0) { // Get distance from the list
								distance = point.get("distance");
								continue;
							} else if (j == 1) { // Get duration from the list
								duration = point.get("duration");
								continue;
							}

							double lats = Double.parseDouble(point.get("lat"));
							double lngs = Double.parseDouble(point.get("lng"));
							LatLng position = new LatLng(lats, lngs);

							points.add(position);
						}

						// Adding all the points in the route to LineOptions
						lineOptions.addAll(points);
						lineOptions.width(5);
						lineOptions.color(Color.RED);

					}
					
			
			return lineOptions;

		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(PolylineOptions result) {
			super.onPostExecute(result);
			/* Add marker vÃ  váº½ Ä‘Æ°á»�ng Ä‘i. */
			addMarker(MainLocationPlace._atm);
			_myMap.addPolyline(result);
			/* Ä�Ã³ng Dialog. */
			dismissDialog(0);

		}
	}

}
