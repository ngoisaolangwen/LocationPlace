package com.tile.locationplace;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tile.locationplace.database.Databases;
import com.tile.locationplace.database.DatabasesLove;
import com.tile.locationplace.database.JSONParser;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.database.MyFood;
import com.tile.locationplace.network.CheckInternet;
import com.tile.locationplace.network.GetLocations;

public class MainLocationPlace extends Activity {
	
	
	

	/* _____________ Khai báo các biến _____________ */
	GridView gv;
	String[] ten = { "ATM", "Quán Ăn", "Yêu Thích", "Thông Tin" };
	int[] hinh = { R.drawable.atm, R.drawable.restaurants,
			R.drawable.favorites, R.drawable.info };

	/** Biến chứa danh sách ATM lấy từ server. */
	static ArrayList<MyATM> arrayAtm;
	/** Biến chứa danh sách Quán Ăn(Food) lấy từ server. */
	static ArrayList<MyFood> arrayFood;
	/** Biến dùng để lấy tọa độ của người dùng tại vị trí đang đứng */
	public static GetLocations _getMyLocation;
	public String _myLat, _myLng;
	/* Tạo 2 databases client. */
	public static Databases dbMy;
	public static DatabasesLove dbLove;
	/* Biến static của ATM và Food. */
	public static MyATM _atm;
	public static MyFood _food;
	/** Cần có một ProgressDialog. */
	ProgressDialog pDialog;
	/** Class kiểm tra Internet. */
	CheckInternet internet;
	
	/** Kết nối server */
	JSONParser jParser = new JSONParser();
	/** ATM */
	private String url_all_ATM = "http://dat0493.byethost5.com/MainLocationPlaceconnect/get_all_atms.php";
	private final String ATM_TAG_SUCCESS = "success";
	private final String ATM_TAG_ATM = "atm";
	private final String ATM_TAG_ID = "_id";
	private final String ATM_TAG_LAT = "lat";
	private final String ATM_TAG_LNG = "lng";
	private final String ATM_TAG_NAMEID = "nameid";
	private final String ATM_TAG_NAME = "name";
	private final String ATM_TAG_ADDRESS = "address";
	private final String ATM_TAG_WARD = "ward";
	private final String ATM_TAG_DISTRICT = "district";
	private final String ATM_TAG_CITY = "city";
	private final String ATM_TAG_INFO = "info";
	private final String ATM_TAG_FAVORITES = "favorites";
	private final String ATM_TAG_IMAGE = "image";
	JSONArray jArrayATM = null;

	/** FOOD */
	private String url_all_food = "http://dat0493.byethost5.com/MainLocationPlaceconnect/get_all_foods.php";
	private final String FOOD_TAG_SUCCESS = "success";
	private final String FOOD_TAG_FOOD = "food";
	private final String FOOD_TAG_ID = "_id";
	private final String FOOD_TAG_LAT = "lat";
	private final String FOOD_TAG_LNG = "lng";
	private final String FOOD_TAG_NAME = "name";
	private final String FOOD_TAG_ADDRESS = "address";
	private final String FOOD_TAG_WARD = "ward";
	private final String FOOD_TAG_DISTRICT = "district";
	private final String FOOD_TAG_CITY = "city";
	private final String FOOD_TAG_OPEN = "open";
	private final String FOOD_TAG_CLOSE = "close";
	private final String FOOD_TAG_INFO = "info";
	private final String FOOD_TAG_PRICE = "price";
	private final String FOOD_TAG_FAVORITES = "favorites";
	private final String FOOD_TAG_IMAGE = "image";
	JSONArray jArrayFood = null;

	
	/*---------------------------- END -------------------------- */
	
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_location_place);
		
		
		/*
		 * arrayAtm = ? arrayFood = ?
		 */
		/* Khởi tạo biến. */
		arrayAtm = new ArrayList<MyATM>();
		arrayFood = new ArrayList<MyFood>();
		arrayAtm.clear();
		arrayFood.clear();
		
		/* Tạo mới 2 databases của tôi và yêu thích. */
		dbMy = new Databases(this);
		dbLove = new DatabasesLove(this);
		/* Lấy vị trí người dùng. */
		findLocation();
		
		/* GridView. */
		gv = (GridView) findViewById(R.id.gridView1);
		gv.setAdapter(new myadapter(this));
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					/* Gọi một Activity ATM. */
					Intent atm = new Intent(MainLocationPlace.this,AtmGridView.class);
					startActivity(atm);
					break;
				case 1:
					/* Gọi Activity Quán Ăn. */
					Intent food = new Intent(MainLocationPlace.this,FoodList.class);
					startActivity(food);
					break;
				case 2:
					/* Gọi Activity yêu thích. */
					Intent like = new Intent(MainLocationPlace.this,LocationsLike.class);
					startActivity(like);
					break;
				case 3:
					/* Thông tin của ứng dụng. */
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainLocationPlace.this);
		            alert.setTitle("Thông tin ứng dụng"); //Set Alert dialog title here
		            alert.setMessage("\nỨng Dụng Tìm Kiếm Địa Điểm."+				
		            		"\n\tVersion 1.0.0"	+
		            		"\nNgười phát triển:"+			
		            			"\n\tHuỳnh Văn Đạt Em."+			
		            			"\n\tNguyễn Ngói."+
		            			"\nEmail: NguyenNgoi9190@gmail.com."); //Message here
		 
		            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) { 
		            	dialog.cancel();
		 
		              }
		            });
		            alert.show();
					
					break;
				}
			}
		});
		
		/** KIểm tra người dùng có kết nối Internet chưa? */
		internet = new CheckInternet(this);
		if(internet.isOnline()){ // nếu có kết nối.
			new LoadAllATM().execute(); // ATM.
			new LoadAllFood().execute(); // Quán Ăn.
		}else{ // Nếu ko.
			internet.showSettingAlert();
		}
		
		
		
		
		
	}
	
	
	/** Lấy vị trí người dùng. Tung độ, hoành độ. */
	private void findLocation() {

		/* Định vị người dùng */
		_getMyLocation = new GetLocations(this);
		if (_getMyLocation.canGetLocation()) {
			/* Nếu máy đã bật các chức năng định vị như GPS, Network */

			if (_getMyLocation.getLatitude() != 0) {

				_myLat = Double.toString(_getMyLocation.getLatitude());
				_myLng = Double.toString(_getMyLocation.getLongitude());
				Log.d("Dinh VI", "Da lay duoc dinh vi");
			}else{
				Toast.makeText(MainLocationPlace.this, "Chưa lấy được định vị!"
						, Toast.LENGTH_LONG).show();
			}

		} else { // Yêu cầu mở chức năng nếu người dùng chưa mở
			_getMyLocation.showSettingAlert();
		}

	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_location_place, menu);
		return true;
	}
	
	
	// ///////////class đại dien cho 1 ô //////
		public static class View_Mot_O {
			public ImageView imageview;
			public TextView textview;
		}

		public class myadapter extends BaseAdapter {
			Context context;

			public myadapter(Context c) {
				context = c;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return hinh.length;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return hinh[position];
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				View_Mot_O mot_o;
				LayoutInflater layoutinflater = ((Activity) context)
						.getLayoutInflater();

				if (arg1 == null) {
					mot_o = new View_Mot_O();
					arg1 = layoutinflater.inflate(R.layout.gridview_mot_o, null);
					mot_o.textview = (TextView) arg1.findViewById(R.id.textView1);
					mot_o.imageview = (ImageView) arg1
							.findViewById(R.id.imageView1);
					arg1.setTag(mot_o);
				} else
					mot_o = (View_Mot_O) arg1.getTag();

				mot_o.imageview.setImageResource(hinh[arg0]);
				mot_o.textview.setText(ten[arg0]);

				return arg1;
			}

		}
		
		
		
		/*|********************|||________|||****************** |
		 *|						ĐẠT EM - SERVER					|
		 *|____________________________________________________ |
		 */
		
		/** ______________ Lấy ATM và Food từ Server. _______________ */

		class LoadAllATM extends AsyncTask<String, String, String> {
			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				/* Hiển thị dialog. */
				pDialog = new ProgressDialog(MainLocationPlace.this);
				pDialog.setMessage("Đang lấy dữ liệu từ server.....");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
				
				
			}

			/**
			 * getting All ATM from url
			 * */
			@Override
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_all_ATM, "GET",
						params);
				// Check your log cat for JSON reponse
//				Log.d("All ATM: ", json.toString());
				try {
					int success = json.getInt(ATM_TAG_SUCCESS);
					if (success == 1) {
						jArrayATM = json.getJSONArray(ATM_TAG_ATM);
						for (int i = 0; i < jArrayATM.length(); i++) {
							JSONObject a = jArrayATM.getJSONObject(i);
							MyATM atm = new MyATM();
							atm.set_id(Integer.parseInt(a.getString(ATM_TAG_ID)));
							atm.set_lat(a.getString(ATM_TAG_LAT));
							atm.set_lng(a.getString(ATM_TAG_LNG));
							atm.set_Nameid(a.getString(ATM_TAG_NAMEID));
							atm.set_name(a.getString(ATM_TAG_NAME));
							atm.set_address(a.getString(ATM_TAG_ADDRESS));
							atm.set_ward(a.getString(ATM_TAG_WARD));
							atm.set_district(a.getString(ATM_TAG_DISTRICT));
							atm.set_city(a.getString(ATM_TAG_CITY));
							atm.set_info(a.getString(ATM_TAG_INFO));
							atm.set_favorites(Boolean.parseBoolean(a
									.getString(ATM_TAG_FAVORITES)));
							atm.set_Image(a.getString(ATM_TAG_IMAGE).getBytes());
//							Log.d("ATM", Integer.toString(atm.get_id()));
							arrayAtm.add(atm);
						}
					}
				} catch (JSONException e) {
					// TODO: handle exception
					Log.e("ClientConnect", e.getMessage());
					e.printStackTrace();
				}
				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				// super.onPostExecute(result);
				
				// Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT)
				// .show();
			}

		}

		class LoadAllFood extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
//				pDialog = new ProgressDialog(MainLocationPlace.this);
//				pDialog.setMessage("Loading Food. Please wait...");
//				pDialog.setIndeterminate(false);
//				pDialog.setCancelable(false);
//				pDialog.show();
			}

			/**
			 * getting All Food from url
			 * */
			@Override
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_all_food, "GET",
						params);
				// Check your log cat for JSON reponse
//				Log.d("All Food: ", json.toString());
				try {
					int success = json.getInt(FOOD_TAG_SUCCESS);
					if (success == 1) {
						jArrayFood = json.getJSONArray(FOOD_TAG_FOOD);
						for (int i = 0; i < jArrayFood.length(); i++) {
							JSONObject a = jArrayFood.getJSONObject(i);
							MyFood food = new MyFood();
							food.set_id(Integer.parseInt(a.getString(FOOD_TAG_ID)));
							food.set_lat(a.getString(FOOD_TAG_LAT));
							food.set_lng(a.getString(FOOD_TAG_LNG));
							food.set_name(a.getString(FOOD_TAG_NAME));
							food.set_address(a.getString(FOOD_TAG_ADDRESS));
							food.set_ward(a.getString(FOOD_TAG_WARD));
							food.set_district(a.getString(FOOD_TAG_DISTRICT));
							food.set_city(a.getString(FOOD_TAG_CITY));
							food.set_open(a.getString(FOOD_TAG_OPEN));
							food.set_close(a.getString(FOOD_TAG_CLOSE));
							food.set_info(a.getString(FOOD_TAG_INFO));
							food.set_price(Integer.parseInt(a
									.getString(FOOD_TAG_PRICE)));
							food.set_favorites(Boolean.parseBoolean(a
									.getString(FOOD_TAG_FAVORITES)));
							food.set_Image(a.getString(FOOD_TAG_IMAGE).getBytes());
//							Log.d("FOOD", Integer.toString(food.get_id()));
							arrayFood.add(food);
						}
					}
				} catch (JSONException e) {
					// TODO: handle exception
					Log.e("ClientConnect", e.getMessage());
					e.printStackTrace();
				}
				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				// super.onPostExecute(result);
				pDialog.dismiss();
//				Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT)
//						.show();
			}

		}

	
	
	
	
	
	

}
