/**
 * Fragment chá»©a danh sÃ¡ch QuÃ¡n Äƒn client.
 * VÃ  cÃ¡c tÃ¡c vá»¥ trÃªn 1 Ä‘á»‹a Ä‘iá»ƒm.
 */

package com.tile.locationplace;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.tile.locationplace.adapter.AdapterFood;
import com.tile.locationplace.database.JSONParser;
import com.tile.locationplace.database.MyFood;
import com.tile.locationplace.menu.ActionItem;
import com.tile.locationplace.menu.QuickAction;
import com.tile.locationplace.menu.QuickAction.OnActionItemClickListener;

@SuppressLint("ValidFragment")
public class FoodFragment extends Fragment {

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
	
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	int success;
	private String url_create_food = "http://dat0493.byethost5.com/MainLocationPlaceshare_connect/create_food.php";
	private final String TAG_SUCCESS = "success";

	public FoodFragment(List<MyFood> list) {
		super();
		this._foodList = list;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_food_fragment,
				container, false);
		_lv = (ListView) view.findViewById(R.id.lvFoodF);
		mQC = new QuickAction(getActivity());

		/* Set Adapter. */
		_adapterFood = new AdapterFood(getActivity(),R.layout.activity_food_fragment, _foodList);
		/* Set Adapter. */
		_lv.setAdapter(_adapterFood);

		/* Sá»± kiá»‡n click vÃ o ListView. */
		_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MainLocationPlace._food = _foodList.get(arg2);
				mQC.show(arg1);

			}
		});

		clickListViewC();
		return view;
	}

	
	private void clickListViewC() {

		/* TÃ¬m Ä‘Æ°á»�ng Ä‘i. */
		ActionItem addActionMap = new ActionItem();
		addActionMap.setIcon(getResources().getDrawable(R.drawable.navigation));
		addActionMap.setTitle("TÃ¬m Ä�Æ°á»�ng");
		/* ThÃ´ng tin Ä‘á»‹a Ä‘iá»ƒm. */
		ActionItem addActionInfo = new ActionItem();
		addActionInfo.setIcon(getResources().getDrawable(R.drawable.info));
		addActionInfo.setTitle("ThÃ´ng Tin");
		/* YÃªu thÃ­ch. */
		ActionItem addActionDelete = new ActionItem();
		addActionDelete.setIcon(getResources().getDrawable(R.drawable.delete));
		addActionDelete.setTitle("XÃ³a");
		/* Chia Sáº». */
		ActionItem addActionChiaSe = new ActionItem();
		addActionChiaSe.setIcon(getResources().getDrawable(R.drawable.tick));
		addActionChiaSe.setTitle("Chia Sáº»");

		mQC.addActionItem(addActionMap);
		mQC.addActionItem(addActionInfo);
		mQC.addActionItem(addActionDelete);
		mQC.addActionItem(addActionChiaSe);

		mQC.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {

				if (pos == 0) {
					/* Direstion. */
					Intent timDuong = new Intent(getActivity(),
							FoodGoogleMap.class);
					timDuong.putExtra("food", true);
					startActivity(timDuong);
				} else if (pos == 1) {
					/* About. */
					Location();
				} else if (pos == 2) {
					/* Delete. */
					MainLocationPlace.dbMy.deletefood(MainLocationPlace._food);
					_foodList = MainLocationPlace.dbMy.getAllfood();
					/* Set Adapter. */
					_adapterFood = new AdapterFood(getActivity(),R.layout.activity_food_fragment, _foodList);
					/* Set Adapter. */
					_lv.setAdapter(_adapterFood);
					
				} else if (pos == 3) {
					/* Chia Sáº» (Ä�áº¡t Em phÃ¡t triá»ƒn). */
					new CreateFood().execute();

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
		im.setImageResource(R.drawable.food_128);

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
	/** Gá»­i thÃ´ng tin 1 Ä‘á»‹a Ä‘iá»ƒm lÃªn server(Cháº¡y ná»�n). */
	class CreateFood extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Creating Food..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			String lat = MainLocationPlace._food.get_lat();
			String lng = MainLocationPlace._food.get_lng();
			String name = MainLocationPlace._food.get_name();
			String address = MainLocationPlace._food.get_address();
			String ward = MainLocationPlace._food.get_ward();
			String district = MainLocationPlace._food.get_district();
			String city = MainLocationPlace._food.get_city();
			String open = MainLocationPlace._food.get_open();
			String close = MainLocationPlace._food.get_close();
			String info = MainLocationPlace._food.get_info();
			String price = Long.toString(MainLocationPlace._food.get_price());
			String favorites = Boolean.toString(MainLocationPlace._food.get_favorites());

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("lat", lat));
			params.add(new BasicNameValuePair("lng", lng));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("address", address));
			params.add(new BasicNameValuePair("ward", ward));
			params.add(new BasicNameValuePair("district", district));
			params.add(new BasicNameValuePair("city", city));
			params.add(new BasicNameValuePair("open", open));
			params.add(new BasicNameValuePair("close", close));
			params.add(new BasicNameValuePair("info", info));
			params.add(new BasicNameValuePair("price", price));
			params.add(new BasicNameValuePair("favorites", favorites));
			params.add(new BasicNameValuePair("image", ""));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_food,
					"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			try {
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("SUCCESS", "SUCCESS");
					pDialog.dismiss();
				} else {
					Log.d("SUCCESS", "FAIL");
					pDialog.dismiss();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			if (success == 1) {
				Toast.makeText(getActivity(), "SUCCESS",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}


}
