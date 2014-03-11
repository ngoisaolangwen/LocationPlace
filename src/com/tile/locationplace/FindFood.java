/**
 * TÃ¬m Food.
 */

package com.tile.locationplace;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class FindFood extends Activity {

	Spinner spTP, spQH, spPTX;
	CheckBox cbTenDuong, cbTenQuan, cbTTC;
	EditText edTenDuong, edTenQuan, edTTC;
	Button btntim;
	String tenDuong = "";
	String phuong = "";
	String tenquan = "";
	String thongTinchung = "";

	ArrayAdapter<String> adapterThanhPho;
	String[] tenTP = { "TP Há»“ ChÃ­ Minh" };

	ArrayAdapter<String> adaptertenQuan;
	String[] tenQuan = { "Quáº­n 8" };

	/*  */
	ArrayAdapter<String> adaptertenPhuong;
	String[] tenPhuong = { "ALL", "PhÆ°á»�ng 1", "PhÆ°á»�ng 2", "PhÆ°á»�ng 3",
			"PhÆ°á»�ng 4", "PhÆ°á»�ng 5", "PhÆ°á»�ng 6", "PhÆ°á»�ng 7", "PhÆ°á»�ng 8",
			"PhÆ°á»�ng 9", "PhÆ°á»�ng 10", "PhÆ°á»�ng 11", "PhÆ°á»�ng 12", "PhÆ°á»�ng 13",
			"PhÆ°á»�ng 14", "PhÆ°á»�ng 15", "PhÆ°á»�ng 16" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_food);

		setId();

		/* Nháº¥n nÃºt tÃ¬m. */
		btntim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getInfo();
				Intent i = new Intent(FindFood.this, FoodListFind.class);
				i.putExtra("name", tenquan);
				i.putExtra("duong", tenDuong);
				i.putExtra("phuong", phuong);
				i.putExtra("info", thongTinchung);
				startActivity(i);
				finish();
			}
		});

		cbTenDuong.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					edTenDuong.setEnabled(true);
				} else {
					edTenDuong.setEnabled(false);
				}

			}
		});

		cbTenQuan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					edTenQuan.setEnabled(true);
				} else {
					edTenQuan.setEnabled(false);
				}

			}
		});

		cbTTC.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					edTTC.setEnabled(true);
				} else {
					edTTC.setEnabled(false);
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_food, menu);
		return true;
	}

	private void setId() {
		spPTX = (Spinner) findViewById(R.id.sp_ptx_food);
		spQH = (Spinner) findViewById(R.id.sp_qh_food);
		spTP = (Spinner) findViewById(R.id.sp_ttp_food);

		btntim = (Button) findViewById(R.id.btn_tim_food);
		edTenDuong = (EditText) findViewById(R.id.ed_tenduong_food);
		edTenQuan = (EditText) findViewById(R.id.ed_tenquan_food);
		edTTC = (EditText) findViewById(R.id.ed_thongtinchung_food);

		cbTenDuong = (CheckBox) findViewById(R.id.cb_tenduong_food);
		cbTenQuan = (CheckBox) findViewById(R.id.cb_tenquan_food);
		cbTTC = (CheckBox) findViewById(R.id.cb_thongtinchung);

		spTP.setEnabled(false);
		spQH.setEnabled(false);
		adapterThanhPho = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tenTP);
		spTP.setAdapter(adapterThanhPho);

		adaptertenQuan = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tenQuan);
		spQH.setAdapter(adaptertenQuan);
		adaptertenPhuong = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tenPhuong);
		spPTX.setAdapter(adaptertenPhuong);

	}

	private void getInfo() {
		// Food_List.myFood.clear();

		
		long p = spPTX.getSelectedItemId();
		phuong = Long.toString(p);
		Log.d("id Phuong", phuong);
		if (cbTenDuong.isChecked()) {
			tenDuong = edTenDuong.getText().toString();
		}
		if (cbTenQuan.isChecked()) {
			tenquan = (String) edTenQuan.getText().toString();
		}
		if (cbTTC.isChecked()) {
			thongTinchung = edTTC.getText().toString();
		}
		// Food_List.myFood = Place.db.findFood(p, tenDuong, tenQuan,
		// thongTinchung);

	}

}
