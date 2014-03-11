/**
 * TÃ¬m kiáº¿m quÃ¡n Äƒn.
 */

package com.tile.locationplace;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.tile.locationplace.adapter.AdapterFood;
import com.tile.locationplace.database.JSONParser;
import com.tile.locationplace.database.MyFood;
import com.tile.locationplace.menu.ActionItem;
import com.tile.locationplace.menu.QuickAction;
import com.tile.locationplace.menu.QuickAction.OnActionItemClickListener;

public class FoodListFind extends Activity {
	
	
	ListView _lv;
	AdapterFood _adapterFood;
	QuickAction mQC;
	List<MyFood> _foodList; // Danh sÃ¡ch Food
	
	TextView tvName, tvaddress;
	EditText edInfo;
	Button btnOk;
	RatingBar rtB;
	ImageView im;

	/* Kiá»ƒm tra ratingBar. */
	boolean i = false;
	/* Progress Dialog. */
	ProgressDialog progressD;
	Intent intent;
	String phuong,duong,tenquan,thongtinchung;
	
	
	/** FOOD */
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/* Show ProgressDialog. */
		showDialog(0);
		/* Láº¥y thÃ´ng tin gá»­i tá»« Acticity FindFood. */
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		phuong = bundle.getString("phuong");
		duong = bundle.getString("duong");
		tenquan = bundle.getString("name");
		thongtinchung = bundle.getString("info");
		
		mQC = new QuickAction(this);
		
		clickListView();
		
		_foodList = new ArrayList<MyFood>();
		 _adapterFood = new AdapterFood(FoodListFind.this, R.layout.activity_main, _foodList);
		_lv = (ListView)findViewById(R.id.lvfoodlistfind);
		_lv.setAdapter(_adapterFood);
		_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MainLocationPlace._food = _foodList.get(arg2);
				mQC.show(arg1);
			}
		});
		
		/* TÃ¬m quÃ¡n Äƒn. */
		new LoadFindFood().execute();
		
	}
	
	private JSONObject findFood(String name, String phuong, String duong, String thongtinchung){
		/* Ä�Æ°á»�ng dáº«n lÃªn server. */
		String url = "http://dat0493.byethost5.com/MainLocationPlaceconnect/get_find_food.php";
		/* Táº¡o cÃ¡c tham sá»‘ truyá»�n vÃ o. */
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name", name));
		list.add(new BasicNameValuePair("ward", phuong));
		list.add(new BasicNameValuePair("address", duong));
		list.add(new BasicNameValuePair("info", thongtinchung));
		JSONParser json = new JSONParser();
		JSONObject jobject = json.makeHttpRequest(url, "GET", list);
		Log.d("info", name+phuong+duong+thongtinchung);
//		Log.d("jsonO", jobject.toString());
		return jobject;
		
	}
	
	// Find food from arrayList.
//		public List<MyFood> findFood(String phuong, String duong, String tenquan,
//				String thongtinchung) {
//
//			List<MyFood> foodList = new ArrayList<MyFood>();
//			String sql = "";
//			String findfood = "";
//
//			String sqlWard = _WARD + " LIKE " + "'%" + phuong + "%'";
//			String sqlInfo = _INFO + " LIKE " + "'%" + thongtinchung + "%'";
//			String sqlName = _NAME + " LIKE " + "'%" + tenquan + "%'";
//			String sqlAddress = _ADDRESS + " LIKE " + "'%" + duong + "%'";
//			/* Kiá»ƒm tra náº¿u má»™t trong cÃ¡c thÃ´ng tin truyá»�n vÃ o khÃ¡c null thÃ¬ tÃ¬m */
//			if (!duong.equals("")) {
//				findfood += sqlAddress;
//			}
//			if (!tenquan.equals("")) {
//				if (!findfood.equals("")) {
//					findfood += " AND " + sqlName;
//				} else {
//					findfood += sqlName;
//				}
//			}
//			if (!thongtinchung.equals("")) {
//				if (!findfood.equals("")) {
//					findfood += " AND " + sqlInfo;
//				} else {
//					findfood += sqlInfo;
//				}
//
//			}
//
//			if (!findfood.equals("")) {
//				if (!phuong.equals("0")) {
//					sql = "SELECT * FROM " + DATABASE_TABLE_FOOD + " WHERE "
//							+ findfood + " AND " + sqlWard;
//				} else {
//					sql = "SELECT * FROM " + DATABASE_TABLE_FOOD + " WHERE "
//							+ findfood;
//				}
//			}
//			if (findfood.equals("")) {
//				if (!phuong.equals("0")) {
//					sql = "SELECT * FROM " + DATABASE_TABLE_FOOD + " WHERE "
//							+ sqlWard;
//				} else {
//					sql = "SELECT * FROM " + DATABASE_TABLE_FOOD;
//				}
//			}
//
//			Cursor cursor = db.rawQuery(sql, null);
//			if (cursor.moveToFirst()) {
//				do {
//
//					MyFood food = getFood(cursor);
//					foodList.add(food);
//
//				} while (cursor.moveToNext());
//
//			}
//			return foodList;
//		}
//	
	
//	private ArrayList<MyFood> findFood(String phuong, String duong, String tenquan,String thongtinchung){
//		
//		ArrayList<MyFood> foodList = new ArrayList<MyFood>();
//		
//		for(MyFood food : MainLocationPlace.arrayFood){
//			
//			if(phuong.equals("0")){
//				
//				if(duong.equals("")){
//					
//				}
//				
//			}else if(!phuong.equals("0")){
//				if(!duong.equals("")){
//					if(!tenquan.equals("")){
//						if(!thongtinchung.equals("")){
//							if(food.get_name().contains(tenquan) && food.get_address().contains(duong)
//									&& food.get_ward().contains(phuong) && food.get_info().contains(thongtinchung)){
//								/* ThÃªm nhá»¯ng quÃ¡n Äƒn so sÃ¡nh Ä‘Ãºng. */
//								foodList.add(food);
//							}
//						}else if(thongtinchung.equals("")){
//							
//						}
//					}else if(tenquan.equals("")){
//						
//					}
//				}else if(duong.equals("")){
//					
//				}
//				
//				
//			}
//			
//			
////			if(!duong.equals(""))
////			if(food.get_name().contains(tenquan) || food.get_address().contains(duong)
////					|| food.get_ward().contains(phuong) || food.get_info().contains(thongtinchung)){
////				/* ThÃªm nhá»¯ng quÃ¡n Äƒn so sÃ¡nh Ä‘Ãºng. */
////				foodList.add(food);
////			}
//			
//			
//		}
//		
//		
//		return foodList;
//	}
	
	private void clickListView() {

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
		addActionLike.setIcon(getResources().getDrawable(R.drawable.rating_good));
		addActionLike.setTitle("YÃªu ThÃ­ch");


		mQC.addActionItem(addActionMap);
		mQC.addActionItem(addActionInfo);
		mQC.addActionItem(addActionLike);

		mQC.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {

				if (pos == 0) {
					/* TÃ¬m Ä‘Æ°á»�ng. */
					Intent timDuong = new Intent(FoodListFind.this,
							FoodGoogleMap.class);
					timDuong.putExtra("food", true);
					startActivity(timDuong);
				} else if (pos == 1) {
					/* ThÃ´ng tin. */
					Location();
				} else if (pos == 2) {
					/* ThÃ­ch. */
					List<MyFood> list = MainLocationPlace.dbLove.getAllfood();
					if(list.size() > 0){
					for(MyFood food : list){
						if(MainLocationPlace._food.get_id() != food.get_id())
							MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
						}
					}else{
						MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
					}
					
				}

			}
		});

	}
	
	
	/** Location Dialog. */
	private void Location() {

		// custom dialog
		final Dialog dialog = new Dialog(FoodListFind.this);
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
					MainLocationPlace.dbLove.addFood(MainLocationPlace._food);
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
		getMenuInflater().inflate(R.menu.food_list_find, menu);
		return true;
	}
	
	class LoadFindFood extends AsyncTask<Void, Void, Void> {

		/**
		 * getting Find Food from url
		 * @return 
		 * */
		@Override
		protected Void doInBackground(Void... args) {
			// getting JSON string from URL
			JSONObject json = findFood(tenquan, phuong, duong, thongtinchung);
			// Check your log cat for JSON reponse
//			Log.d("Food: ", json.toString());
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
//						Log.d("FOOD", Integer.toString(food.get_id()));
						_foodList.add(food);
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
		protected void onPostExecute(Void result) {
			 super.onPostExecute(result);
			 if(_foodList.size() <= 0){
				 Toast.makeText(FoodListFind.this,
						 "KhÃ´ng cÃ³ Ä‘á»‹a Ä‘iá»ƒm nÃ y!", Toast.LENGTH_SHORT).show();
			 }
			 _adapterFood = new AdapterFood(FoodListFind.this, R.layout.activity_main, _foodList);
			 _lv.setAdapter(_adapterFood);
			progressD.dismiss();

		}

	}

}
