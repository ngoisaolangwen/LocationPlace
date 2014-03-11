/**
 * Fragment chá»©a danh sÃ¡ch quÃ¡n Äƒn server.
 * VÃ  cÃ¡c tÃ¡c vá»¥ trÃªn má»™t Ä‘á»‹a Ä‘iá»ƒm.
 */

package com.tile.locationplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.tile.locationplace.adapter.AdapterFood_Near;
import com.tile.locationplace.database.MyFood;
import com.tile.locationplace.menu.ActionItem;
import com.tile.locationplace.menu.QuickAction;
import com.tile.locationplace.menu.QuickAction.OnActionItemClickListener;

@SuppressLint("ValidFragment")
public class FoodFragmentS extends Fragment {

	ListView _lv;
	EditText edSearch;
	AdapterFood_Near _adapterFood;
	QuickAction mQS;
	List<MyFood> _foodList; // Danh sÃ¡ch Food
	List<MyFood> _foodListN; // Danh sÃ¡ch Food
	/** Chiá»�u dÃ i chuá»—i text. */
	int textLength = 0;
	/** Má»™t danh sÃ¡ch táº¡m */
	List<MyFood> myFoodTam;
	List<MyFood> myFoodTam2;
	/* Biáº¿n kiá»ƒm tra tÃ¬m. */
	boolean flag = false;
	/** ProgressDialog. */
	ProgressDialog pDialog;

	TextView tvName, tvaddress;
	EditText edInfo;
	Button btnOk;
	RatingBar rtB;
	ImageView im;
	/* Kiểm tra ratingBar. */
	boolean i = false;
	/** Một biến chứa km */
	float km;

	/** mảng chứa ký tự đặc biệt
	 * Dùng để so sách tìm kiếm bằng tiếng việt.*/
	private char[] charA = { 'à', 'á', 'ạ', 'ả', 'ã',// 0-&gt;16
			'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ' };// a,
																			// ă,
																			// â
	private char[] charE = { 'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',// 17-&gt;27
			'è', 'é', 'ẹ', 'ẻ', 'ẽ' };// e
	private char[] charI = { 'ì', 'í', 'ị', 'ỉ', 'ĩ' };// i 28-&gt;32
	private char[] charO = { 'ò', 'ó', 'ọ', 'ỏ', 'õ',// o 33-&gt;49
			'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',// ô
			'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ' };// ơ
	private char[] charU = { 'ù', 'ú', 'ụ', 'ủ', 'ũ',// u 50-&gt;60
			'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ' };// ư
	private char[] charY = { 'ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ' };// y 61-&gt;65
	private char[] charD = { 'đ', ' ' }; // 66-67
	String charact = String.valueOf(charA, 0, charA.length)
			+ String.valueOf(charE, 0, charE.length)
			+ String.valueOf(charI, 0, charI.length)
			+ String.valueOf(charO, 0, charO.length)
			+ String.valueOf(charU, 0, charU.length)
			+ String.valueOf(charY, 0, charY.length)
			+ String.valueOf(charD, 0, charD.length);

	/** káº¿t thÃºc máº£ng Ä‘áº·c biá»‡t */

	public FoodFragmentS(List<MyFood> list) {
		super();
		this._foodList = list;
		Log.d("soodList", _foodList.size() + "");
		this._foodListN = new ArrayList<MyFood>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_food_fragment_s,
				container, false);
		_lv = (ListView) view.findViewById(R.id.lvFoodS);
		edSearch = (EditText) view.findViewById(R.id.edFoodSearch);
		mQS = new QuickAction(getActivity());
		_foodListN.clear();
		new DownloadTask().execute();

		/* Set Adapter. */
		_lv.setAdapter(_adapterFood);

		/* Sá»± kiá»‡n click vÃ o ListView. */
		_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (textLength == 0) {
					flag = false;
				} else if (textLength != 0) {
					flag = true;
				}
				if (flag) {
					MainLocationPlace._food = myFoodTam2.get(arg2);
				} else if (!flag) {
					MainLocationPlace._food = _foodListN.get(arg2);
				}
				mQS.show(arg1);

			}
		});
		myFoodTam = _foodListN;
		myFoodTam2 = new ArrayList<MyFood>();
		/** Sá»± kiá»‡n trÃªn Edittext. */
		edSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

				textLength = edSearch.getText().length();
				String text = edSearch.getText().toString();
				Log.d("size", textLength + " + " + text);
				myFoodTam2.clear();
				// for (int i = 0; i < myFoodTam.size(); i++) {
				// if ((textLength <= myFoodTam.get(i).get_name().length())
				// || (textLength <= myFoodTam.get(i).get_address()
				// .length())
				// || (textLength <= myFoodTam.get(i).get_info()
				// .length())) {
				// // Log.d("do dai", edSearch.getText().toString());
				// if (!myFoodTam.get(i).get_info().equals("")) {
				// if ((text.equalsIgnoreCase((String) myFoodTam
				// .get(i).get_name()
				// .subSequence(0, textLength)))
				// || (text.equalsIgnoreCase((String) myFoodTam
				// .get(i).get_address()
				// .subSequence(0, textLength)))
				// || (text.equalsIgnoreCase((String) myFoodTam
				// .get(i).get_info()
				// .subSequence(0, textLength)))) {
				// // Log.d("so sanh",
				// // myFoodTam.get(i).get_name());
				// /* ThÃªm Má»™t quÃ¡n Äƒn vÃ o danh sÃ¡ch. */
				// myFoodTam2.add(myFoodTam.get(i));
				//
				// }
				// } else if (myFoodTam.get(i).get_info().equals("")) {
				// if ((text.equalsIgnoreCase((String) myFoodTam
				// .get(i).get_name()
				// .subSequence(0, textLength)))
				// || (text.equalsIgnoreCase((String) myFoodTam
				// .get(i).get_address()
				// .subSequence(0, textLength)))) {
				// // Log.d("so sanh",
				// // myFoodTam.get(i).get_name());
				// /* ThÃªm Má»™t quÃ¡n Äƒn vÃ o danh sÃ¡ch. */
				// myFoodTam2.add(myFoodTam.get(i));
				//
				// }
				// }
				//
				// }
				//
				// } // End for.

				// for (MyFood food : myFoodTam) {
				// if (food.get_name().toLowerCase()
				// .contains(text.toLowerCase())) {
				// myFoodTam2.add(food);
				// }
				// }
				for (MyFood food : _foodListN) {
					if (ConvertString(food.get_name()).contains(
							ConvertString(text))) {
						myFoodTam2.add(food);
					}
				}

				_lv.setAdapter(new AdapterFood_Near(getActivity(),
						R.layout.activity_food_fragment_s, myFoodTam2));
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		clickListViewS();
		return view;
	}

	/** Chuyá»ƒn kÃ½ tá»± Ä‘áº·c biá»‡t */
	private char GetAlterChar(char pC) {
		if ((int) pC == 32) {
			return ' ';
		}

		char tam = pC;// Character.toLowerCase(pC);

		int i = 0;
		while (i < charact.length() && charact.charAt(i) != tam) {
			i++;
		}
		if (i < 0 || i > 67)
			return pC;

		if (i == 66) {
			return 'd';
		}
		if (i >= 0 && i <= 16) {
			return 'a';
		}
		if (i >= 17 && i <= 27) {
			return 'e';
		}
		if (i >= 28 && i <= 32) {
			return 'i';
		}
		if (i >= 33 && i <= 49) {
			return 'o';
		}
		if (i >= 50 && i <= 60) {
			return 'u';
		}
		if (i >= 61 && i <= 65) {
			return 'y';
		}
		return pC;
	}

	public String ConvertString(String pStr) {

		String convertString = pStr.toLowerCase();

		// System.out.print(convertString.length());
		Character[] returnString = new Character[convertString.length()];
		for (int i = 0; i < convertString.length(); i++) {
			// System.out.print(returnString[] +"-");
			char temp = convertString.charAt(i);
			// System.out.print(temp + "-");
			if ((int) temp < 97 || temp > 122) {
				char tam1 = this.GetAlterChar(temp);
				// System.out.println(tam1 + "/");
				if ((int) temp != 32)
					convertString = convertString.replace(temp, tam1);
			}
			// returnString[i]=Character.valueOf(temp);
		}
		// System.out.println(convertString);
		return convertString;
	}

	/** Káº¿t thÃºc chuyá»ƒn kÃ½ tá»± Ä‘áº·c biá»‡t */

	/** Sáº¯p xáº¿p Ä‘á»‹a Ä‘iá»ƒm gáº§n xa */
	private Double TinhKhoangCach(double lat1, double lng1, String slat2,
			String slng2) {
		double ketqua = 0;
		double lat2 = Double.parseDouble(slat2);
		double lng2 = Double.parseDouble(slng2);
		double x = Math.abs(lat1 - lat2);
		double y = Math.abs(lng1 - lng2);
		ketqua = Math.sqrt(x * x + y * y);
		return ketqua;
	}

	private ArrayList<MyFood> sapxepganxa(List<MyFood> lists) {
		ArrayList<MyFood> list = new ArrayList<MyFood>();
		list.addAll(lists);
		Collections.sort(list, new Comparator<MyFood>() {

			@Override
			public int compare(MyFood lhs, MyFood rhs) {
				// TODO Auto-generated method stub
				if (TinhKhoangCach(MainLocationPlace._getMyLocation.getLatitude(),
						MainLocationPlace._getMyLocation.getLongitude(), lhs.get_lat(),
						lhs.get_lng()) < TinhKhoangCach(
						MainLocationPlace._getMyLocation.getLatitude(),
						MainLocationPlace._getMyLocation.getLongitude(), rhs.get_lat(),
						rhs.get_lng()))
					return -1;
				else if (TinhKhoangCach(MainLocationPlace._getMyLocation.getLatitude(),
						MainLocationPlace._getMyLocation.getLongitude(), lhs.get_lat(),
						lhs.get_lng()) > TinhKhoangCach(
						MainLocationPlace._getMyLocation.getLatitude(),
						MainLocationPlace._getMyLocation.getLongitude(), rhs.get_lat(),
						rhs.get_lng()))
					return 1;
				else
					return 0;
			}
		});
		return list;
	}

	// private Double pitago(String lat, double lng){
	// double km = 0;
	// double a = Double.parseDouble(lat);
	// double c = a*a;
	// double d = lng*lng;
	// double e = c + d;
	// km = (float) Math.sqrt(e);
	// return km;
	// }
	// private Double pitago(double lat, double lng){
	// double km = 0;
	// double c = Math.pow(lat, 2);
	// double d = Math.pow(lng, 2);
	// double e = c + d;
	// km = (float) Math.sqrt(e);
	// return km;
	// }

	// private ArrayList<MyFood> listKm(List<MyFood> lists){
	// ArrayList<MyFood> list = new ArrayList<MyFood>();
	// for(MyFood food : lists){
	// double b = pitago(food.get_lat(), MainLocationPlace._getMyLocation.getLongitude());
	// Log.d("km", b+"");
	// if(b <= 3.0){
	// Float a = (float)b;
	// food.setKm(a);
	// list.add(food);
	//
	// }
	//
	// }
	//
	// return list;
	// }
	/** Káº¿t thÃºc sáº¯p xáº¿p Ä‘á»‹a Ä‘iá»ƒm gáº§n xa */

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("Resum", "Resum");
	}

	private void clickListViewS() {

		/* TÃ¬m Ä‘Æ°á»�ng Ä‘i. */
		ActionItem addActionMap = new ActionItem();
		addActionMap.setIcon(getResources().getDrawable(R.drawable.navigation));
		addActionMap.setTitle("TÃ¬m Ä�Æ°á»�ng");
		/* ThÃ´ng tin Ä‘á»‹a Ä‘iá»ƒm. */
		ActionItem addActionInfo = new ActionItem();
		addActionInfo.setIcon(getResources().getDrawable(R.drawable.info));
		addActionInfo.setTitle("ThÃ´ng Tin");
		/* YÃªu thÃ­ch. */
		ActionItem addActionLike = new ActionItem();
		addActionLike.setIcon(getResources()
				.getDrawable(R.drawable.rating_good));
		addActionLike.setTitle("YÃªu ThÃ­ch");

		mQS.addActionItem(addActionMap);
		mQS.addActionItem(addActionInfo);
		mQS.addActionItem(addActionLike);

		mQS.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {

				if (pos == 0) {
					/* Direstion two Location. */
					Intent timDuong = new Intent(getActivity(),
							FoodGoogleMap.class);
					timDuong.putExtra("food", true);
					startActivity(timDuong);
				} else if (pos == 1) {
					/* About Location. */
					Location();
				} else if (pos == 2) {
					/* Like. */
					List<MyFood> list = MainLocationPlace.dbLove.getAllfood();
					if (list.size() > 0) {
						for (MyFood food : list) {
							if (MainLocationPlace._food.get_id() != food.get_id())
								MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
						}
					} else if (list.size() == 0) {
						MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
					}

				}

			}
		});

	}

	/** Location Dialog. */
	private void Location() {

		// custom dialog
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.locationinfo);
		dialog.setTitle("QuÃ¡n Ä‚n");

		tvaddress = (TextView) dialog.findViewById(R.id.tvaddressMarker);
		tvName = (TextView) dialog.findViewById(R.id.tvNameMarker);
		edInfo = (EditText) dialog.findViewById(R.id.edInfoMarker);
		btnOk = (Button) dialog.findViewById(R.id.btnTimDuongMarker);
		rtB = (RatingBar) dialog.findViewById(R.id.rtbMarker);
		im = (ImageView) dialog.findViewById(R.id.imLocation);

		/* Láº¥y dá»¯ liá»‡u tá»« má»™t ATM Ä‘á»• vÃ o */
		tvaddress.setText(MainLocationPlace._food.get_address());
		tvName.setText(MainLocationPlace._food.get_name());
		edInfo.setText(MainLocationPlace._food.get_info());
		im.setImageResource(R.drawable.restaurants);

		rtB.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
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
					MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
				}
				dialog.dismiss();
			}
		});
		dialog.show();

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
	private class DownloadTask extends AsyncTask<Void, Void, List<MyFood>> {

		String data = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Ä�ang láº¥y Ä‘á»‹a Ä‘iá»ƒm gáº§n.....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		// Downloading data in non-ui thread
		@Override
		protected List<MyFood> doInBackground(Void... url) {

			// if (_foodList.size() > 0) {
			//
			// for (MyFood a : _foodList) {
			// // Log.d("lat", a.get_lat());
			// // Log.d("lng", a.get_lng());
			// double lat = 0, lng = 0;
			// /* _______________ Láº¥y Ä‘á»™ dÃ i Ä‘Æ°á»�n Ä‘i. __________________ */
			//
			// LatLng pointLocal = new LatLng(
			// MainLocationPlace._getMyLocation.getLatitude(),
			// MainLocationPlace._getMyLocation.getLongitude());
			// try {
			//
			// lat = Double.parseDouble(a.get_lat());
			// lng = Double.parseDouble(a.get_lng());
			//
			// } catch (Exception e) {
			// Log.d("lay toa do", e.getMessage());
			// }
			// LatLng dests = new LatLng(lat, lng);
			//
			// // Getting URL to the Google Directions API
			// String urls = getDirectionsUrl(pointLocal, dests);
			//
			// try {
			// // Fetching the data from web service
			// data = downloadUrl(urls);
			// } catch (Exception e) {
			// Log.d("Background Task", e.toString());
			// }
			//
			// /** */
			// JSONObject jObject;
			// List<List<HashMap<String, String>>> routes = null;
			//
			// try {
			// jObject = new JSONObject(data);
			// DirectionsJSONParser parser = new DirectionsJSONParser();
			//
			// // Starts parsing data
			// routes = parser.parse(jObject);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			//
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// /** Láº¥y danh sÃ¡ch vÃ  phÃ¢n tÃ­ch ra. */
			// ArrayList<LatLng> points = null;
			// PolylineOptions lineOptions = null;
			// // MarkerOptions markerOptions = new MarkerOptions();
			// String distance = "";
			// String duration = "";
			//
			// if (routes.size() < 1) {
			// // Toast.makeText(getBaseContext(), "No Points",
			// // Toast.LENGTH_SHORT).show();
			// return _foodListN;
			// }
			//
			// // Traversing through all the routes
			// for (int i = 0; i < routes.size(); i++) {
			// points = new ArrayList<LatLng>();
			// lineOptions = new PolylineOptions();
			//
			// // Fetching i-th route
			// List<HashMap<String, String>> path = routes.get(i);
			//
			// // Fetching all the points in i-th route
			// for (int j = 0; j < path.size(); j++) {
			// HashMap<String, String> point = path.get(j);
			//
			// if (j == 0) { // Get distance from the list
			// distance = point.get("distance");
			// continue;
			// } else if (j == 1) { // Get duration from the list
			// duration = point.get("duration");
			// continue;
			// }
			//
			// double lats = Double.parseDouble(point.get("lat"));
			// double lngs = Double.parseDouble(point.get("lng"));
			// LatLng position = new LatLng(lats, lngs);
			//
			// points.add(position);
			// }
			//
			// // Adding all the points in the route to LineOptions
			// lineOptions.addAll(points);
			// lineOptions.width(2);
			// lineOptions.color(Color.RED);
			//
			// }
			// // Log.d("KM", distance);
			//
			// /* ____________________ END ____________________ */
			// /* Láº¥y kiá»ƒu Int cá»§a khoáº£ng cÃ¡ch. */
			// partKM(distance);
			// if (km < (float) 2.0) {
			// a.setKm(km);
			// _foodListN.add(a);
			// }
			//
			// }
			// }
			//
			// /* Sáº¯p xáº¿p theo thá»© tá»± tÄƒng dáº§n. */
			// for (int i = 0; i < _foodListN.size() - 1; i++) {
			// for (int j = 1; j < _foodListN.size(); j++) {
			// if (_foodListN.get(i).getKm() > _foodListN.get(j).getKm()) {
			// MyFood tam = _foodListN.get(i);
			// _foodListN.set(i, _foodListN.get(j));
			// _foodListN.set(j, tam);
			// // Log.d("ID", _foodListN.get(j).get_id()+"");
			// }
			//
			// }
			// // Log.d("KM", _foodListN.get(i).getKm()+"");
			// }
			//
			// Log.d("size", _foodListN.size() + "");
			// return _foodListN;

			if (_foodList.size() > 0) {
				_foodListN = sapxepganxa(_foodList);
			}
			Log.d("SAP XEP", _foodList.size() + "");
			Log.d("SAP XEP", _foodListN.size() + "");
			return _foodListN;

		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(List<MyFood> result) {
			super.onPostExecute(result);

			/* Set Adapter. */
			_adapterFood = new AdapterFood_Near(getActivity(),
					R.layout.activity_food_fragment_s, result);
			// ArrayAdapter<MyFood> adapter = new
			// ArrayAdapter<MyFood>(getActivity()
			// , android.R.layout.simple_list_item_1,result);
			/** Táº¡o adapter. */
			_lv.setAdapter(_adapterFood);

			if (result.size() < 0) {
				Toast.makeText(getActivity(), "KhÃ´ng cÃ³ dá»¯ liá»‡u!",
						Toast.LENGTH_LONG).show();
			}
			pDialog.dismiss();

		}
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

}
