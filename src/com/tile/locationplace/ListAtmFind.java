/**
 * TÃ¬m ATM trong danh sÃ¡ch.
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
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tile.locationplace.adapter.AdapterATM_Near;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.menu.ActionItem;
import com.tile.locationplace.menu.QuickAction;
import com.tile.locationplace.menu.QuickAction.OnActionItemClickListener;
import com.tile.locationplace.network.DirectionsJSONParser;

public class ListAtmFind extends Activity {

	/** Má»™t danh sÃ¡ch chá»©a cÃ¡c Ä‘iá»ƒm trong database. */
	List<MyATM> myAtmDB;
	/** Má»™t danh sÃ¡ch táº¡m */
	List<MyATM> myAtmTam;
	/** Má»™t Adapter */
	AdapterATM_Near adapter;
	/** Má»™t biáº¿n chá»©a km */
	float km;
	/** Má»™t ListView */
	ListView lv;
	/** Menu */
	QuickAction mQ;
	/* Progress Dialog. */
	ProgressDialog progressD;

	TextView tvName, tvaddress;
	EditText edInfo;
	Button btnOk;
	RatingBar rtB;
	ImageView im;

	/* Kiá»ƒm tra ratingBar. */
	boolean i = false;
	Intent atm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_atm_find);
		atm = getIntent();

		String phuong = atm.getStringExtra("phuong");
		String duong = atm.getStringExtra("duong");
		String nameAtm = atm.getStringExtra("atmname");

		
		/* Khá»Ÿi táº¡o cÃ¡c biáº¿n. */
		myAtmDB = getListATM(MainLocationPlace.arrayAtm, nameAtm, phuong, duong);
		myAtmTam = new ArrayList<MyATM>();
		Log.d("size", myAtmDB.size()+"");
		/* Khá»Ÿi táº¡o menu */
		mQ = new QuickAction(this);
		clickListView();
		/* Hiá»ƒn thá»‹ GropressDialog */
		showDialog(0);
		/* Láº¥y khoáº£ng cÃ¡ch tá»« vá»‹ trÃ­ ng dÃ¹ng Ä‘áº¿n má»™t Ä‘á»‹a Ä‘iá»ƒm. */
		new DownloadTask().execute();
		/* Hide MenuBar. */
		menuBarHide();
		lv = (ListView) findViewById(R.id.lvatmfind);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/* Láº¥y má»™t Atm khi ngÆ°á»�i dÃ¹ng click vÃ o List. */
				MainLocationPlace._atm = myAtmTam.get(arg2);
				mQ.show(arg1); // show Menu.

			}
		});

	}

	/** HÃ m tráº£ vá»� danh sÃ¡ch ATM tÃ¬m Ä‘Æ°á»£c. */
	private ArrayList<MyATM> getListATM(ArrayList<MyATM> listAtm,
			String nameID, String phuong, String duong) {
		ArrayList<MyATM> list = new ArrayList<MyATM>();
		for (MyATM mAtm : listAtm) {
			if (!phuong.equals("0")) {
				if (mAtm.get_ward().contains(phuong)
						&& mAtm.get_Nameid().equalsIgnoreCase((String) nameID.subSequence(0, nameID.length()))) {
					if (!duong.equals("")) {
						if (mAtm.get_address().equalsIgnoreCase((String) duong.subSequence(0, duong.length())))
							list.add(mAtm);
					} else if (duong.equals("")) {
						list.add(mAtm);
					}
				}
//				Log.d("tim ATM", );
			}else if(phuong.equals("0")) {
				if(mAtm.get_Nameid().equalsIgnoreCase(nameID)){
					if (!duong.equals("")) {
						if (mAtm.get_address().equalsIgnoreCase((String) duong.subSequence(0, duong.length())))
							list.add(mAtm);
					} else if (duong.equals("")) {
						list.add(mAtm);
					}
				}
			}

		}
		Log.d("danhsach", list.size()+"");
		return list;
	}

	/** áº¨n MenuBar. */
	private void menuBarHide() {

		ActionBar actionB = getActionBar();
		// actionB.setCustomView(R.layout.menu_timkiem);
		// actionB.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
		// | ActionBar.DISPLAY_SHOW_HOME);
		// actionB.setDisplayShowTitleEnabled(false);
		// actionB.setDisplayShowHomeEnabled(false);
		actionB.hide();

	}

	/* Menu cá»§a ListView. */
	private void clickListView() {

		ActionItem addActionMap = new ActionItem();
		addActionMap.setIcon(getResources().getDrawable(R.drawable.navigation));
		addActionMap.setTitle("TÃ¬m Ä�Æ°á»�ng");

		ActionItem addActionShow = new ActionItem();
		addActionShow.setIcon(getResources().getDrawable(R.drawable.info));
		addActionShow.setTitle("ThÃ´ng tin");

		ActionItem addActionLike = new ActionItem();
		addActionLike.setIcon(getResources()
				.getDrawable(R.drawable.rating_good));
		addActionLike.setTitle("YÃªu ThÃ­ch");

		mQ.addActionItem(addActionMap);
		mQ.addActionItem(addActionShow);
		mQ.addActionItem(addActionLike);

		mQ.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {

				if (pos == 0) {
					/* Váº½ Ä�Æ°á»�ng Ä‘i Ä‘áº¿n Ä‘iá»ƒm Ä‘Æ°á»£c chá»�n. */
					Intent timDuong = new Intent(ListAtmFind.this,
							AtmGoogleMap.class);
					startActivity(timDuong);
				} else if (pos == 1) {
					/* Hiá»ƒn thá»‹ thÃ´ng tin Ä‘á»‹a Ä‘iá»ƒm. Show one Dialog. */
					Location();
				} else if (pos == 2) {
					/* YÃªu thÃ­ch Ä‘á»‹a Ä‘iá»ƒm Ä‘Æ°á»£c chá»�n. */

					List<MyATM> list = MainLocationPlace.dbLove.getAllATM();
					if(list.size() > 0){
					for(MyATM atm : list)
						if(MainLocationPlace._atm.get_id() != atm.get_id())
							MainLocationPlace.dbLove.addATM(MainLocationPlace._atm);
					}else if (list.size() == 0){
						MainLocationPlace.dbLove.addATM(MainLocationPlace._atm);
					}
				}

			}
		});

	}

	/** Location Dialog. */
	private void Location() {

		// custom dialog
		final Dialog dialog = new Dialog(ListAtmFind.this);
		dialog.setContentView(R.layout.locationinfo);
		dialog.setTitle("ATM");

		tvaddress = (TextView) dialog.findViewById(R.id.tvaddressMarker);
		tvName = (TextView) dialog.findViewById(R.id.tvNameMarker);
		edInfo = (EditText) dialog.findViewById(R.id.edInfoMarker);
		btnOk = (Button) dialog.findViewById(R.id.btnTimDuongMarker);
		rtB = (RatingBar) dialog.findViewById(R.id.rtbMarker);
		im = (ImageView) dialog.findViewById(R.id.imLocation);

		/* Láº¥y dá»¯ liá»‡u tá»« má»™t ATM Ä‘á»• vÃ o */
		tvaddress.setText(MainLocationPlace._atm.get_address());
		tvName.setText(MainLocationPlace._atm.get_name());
		edInfo.setText(MainLocationPlace._atm.get_info());
		im.setImageResource(R.drawable.atm);

		rtB.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// if (fromUser)

				if (rating == 1) {
					i = true;
				} else {
					i = false;
				}

			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (i) {
					MainLocationPlace.dbLove.addATM(MainLocationPlace._atm);
				}
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	/** ProgressDialog. */
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case 0:
			progressD = new ProgressDialog(this);
			progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressD.setMessage("Ä�ang tÃ¬m.....");
			return progressD;
		}

		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_atm_find, menu);
		return true;
	}

	/* HÃ m láº¥y parse khoáº£ng cÃ¡ch tá»« chuá»—i String tráº£ vá»�. */
	private float partKM(String str) {

		km = 0;
		str.trim();
		int lenght = str.length();
		String km1 = str.substring(0, (lenght - 3));
		try {
			km = Float.parseFloat(km1);
		} catch (Exception e) {
			Log.d("Loi lay khoang cach", e.getMessage());
		}
		Log.d("parseKM", km + "");
		return km;
	}

	/* Google Service. */
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

	/** Class láº¥y khoáº£ng cÃ¡ch. */
	private class DownloadTask extends AsyncTask<Void, Void, List<MyATM>> {

		String data = "";

		// Downloading data in non-ui thread
		@Override
		protected List<MyATM> doInBackground(Void... url) {

			if (myAtmDB.size() > 0) {
				
				for (MyATM a : myAtmDB) {

					double lat = 0, lng = 0;
					/* _______________ Láº¥y Ä‘á»™ dÃ i Ä‘Æ°á»�n Ä‘i. __________________ */

					LatLng pointLocal = new LatLng(
							MainLocationPlace._getMyLocation.getLatitude(),
							MainLocationPlace._getMyLocation.getLongitude());
					try {

						lat = Double.parseDouble(a.get_lat());
						lng = Double.parseDouble(a.get_lng());

					} catch (Exception e) {
						Log.d("lay toa do", e.getMessage());
					}
					LatLng dests = new LatLng(lat, lng);

					// Getting URL to the Google Directions API
					String urls = getDirectionsUrl(pointLocal, dests);

					try {
						// Fetching the data from web service
						data = downloadUrl(urls);
						Log.d("data", data);
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
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					/** Láº¥y danh sÃ¡ch vÃ  phÃ¢n tÃ­ch ra. */
					ArrayList<LatLng> points = null;
					PolylineOptions lineOptions = null;
					// MarkerOptions markerOptions = new MarkerOptions();
					String distance = "";
					String duration = "";

					if (routes.size() < 1) {
						// Toast.makeText(getBaseContext(), "No Points",
						// Toast.LENGTH_SHORT).show();
						Log.d("size", "routes.size() < 1");
						return myAtmTam;
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
						lineOptions.width(2);
						lineOptions.color(Color.RED);

					}
					// Log.d("KM", distance);

					/* ____________________ END ____________________ */
					/* Láº¥y kiá»ƒu Int cá»§a khoáº£ng cÃ¡ch. */
					partKM(distance);
					if (km < (float) 2.0) {
						a.setKm(km);
						myAtmTam.add(a);
//						Log.d("KM", km+"");
					}

				}
			}

			/* Sáº¯p xáº¿p theo thá»© tá»± tÄƒng dáº§n. */
			for (int i = 0; i < myAtmTam.size(); i++) {
				for (int j = 1; j < myAtmTam.size(); j++) {
					if (myAtmTam.get(i).getKm() > myAtmTam.get(j).getKm()) {
						MyATM tam = myAtmTam.get(i);
						myAtmTam.set(i, myAtmTam.get(j));
						myAtmTam.set(j, tam);
						Log.d("ID", myAtmTam.get(j).get_id() + "");
					}

				}
				Log.d("KM", myAtmTam.get(i).getKm() + "");
			}

			return myAtmTam;

		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(List<MyATM> result) {
			super.onPostExecute(result);

//			Log.d("result", result.size()+"");
			if (result.size() < 0) {
				Toast.makeText(ListAtmFind.this, "KhÃ´ng cÃ³ dá»¯ liá»‡u!",
						Toast.LENGTH_SHORT).show();
			}
			/** Táº¡o adapter. */
			lv.setAdapter(new AdapterATM_Near(getApplicationContext(),
					R.layout.activity_find_atm, result));
			
			dismissDialog(0);

		}
	}

}
