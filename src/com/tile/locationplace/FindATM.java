/**
 * TÃ¬m ATM.
 */

package com.tile.locationplace;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class FindATM extends Activity {
	
	
	
	Spinner spTenATM, spTP, spQH, spPTX;
	CheckBox cbTenDuong;
	EditText edTenDuong;
	Button btntim;

	ArrayAdapter<String> adaptertenATM;
	String[] tenATM = { "Ã� ChÃ¢u - ACB", "TMCP Ä�Ã´ng Ã� - DongA Bank",
			"TMCP Ä�Ã´ng Nam Ã� - SeABank",
			"TMCP Ká»¹ ThÆ°Æ¡ng Viá»‡t Nam - Techcombank",
			"TMCP PhÃ¡t Triá»ƒn NhÃ  TPHCM - HDBank",
			"Quá»‘c Táº¿ - VIBBank", "TMCP SÃ i GÃ²n CÃ´ng ThÆ°Æ¡ng - Saigonbank",
			"TMCP SÃ i GÃ²n ThÆ°Æ¡ng TÃ­n - Sacombank",
			"Xuáº¥t Nháº­p Kháº©u Viá»‡t Nam - Eximbank",
			"TMCP Ngoáº¡i ThÆ°Æ¡ng Viá»‡t Nam - Vietcombank",
			"TMCP CÃ´ng ThÆ°Æ¡ng Viá»‡t Nam - Vietinbank",
			"TMCP Ä�T&PT Viá»‡t Nam - BIDV",
			"NN&PT NÃ´ng ThÃ´n Viá»‡t Nam - Agribank" };
	
	ArrayAdapter<String> adapterThanhPho;
	String[] tenTP = { "TP Há»“ ChÃ­ Minh" };

	ArrayAdapter<String> adaptertenQuan;
	String[] tenQuan = { "Quáº­n 8" };

	/*  */
	ArrayAdapter<String> adaptertenPhuong;
	String[] tenPhuong = {"ALL", "PhÆ°á»�ng 1", "PhÆ°á»�ng 2", "PhÆ°á»�ng 3", "PhÆ°á»�ng 4",
			"PhÆ°á»�ng 5", "PhÆ°á»�ng 6", "PhÆ°á»�ng 7", "PhÆ°á»�ng 8", "PhÆ°á»�ng 9",
			"PhÆ°á»�ng 10", "PhÆ°á»�ng 11", "PhÆ°á»�ng 12", "PhÆ°á»�ng 13", "PhÆ°á»�ng 14",
			"PhÆ°á»�ng 15", "PhÆ°á»�ng 16" };
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_atm);
		setId();
		
		/* Nháº¥n nÃºt tÃ¬m. */
		btntim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getInfo();
				finish();
			}
		});

		cbTenDuong.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					edTenDuong.setEnabled(true);
					// if(edTenDuong.getText().toString() == null){
					// btntim.setEnabled(false);
					// }
				} else {
					edTenDuong.setEnabled(false);
					// btntim.setEnabled(true);
				}

			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_atm, menu);
		return true;
	}
	
	


	private void getInfo() {
		String nameAtm = getNameAtm();
		long phuong = spPTX.getSelectedItemId();

		String p = Long.toString(phuong);
		Log.d("P", p);
		if (cbTenDuong.isChecked()) {
			String tenduong = edTenDuong.getText().toString();
			Intent a = new Intent(FindATM.this,ListAtmFind.class);
			a.putExtra("phuong", p);
			a.putExtra("duong", tenduong);
			a.putExtra("atmname", nameAtm);
			startActivity(a);
			return;
		}

		Intent a = new Intent(FindATM.this,ListAtmFind.class);
		a.putExtra("phuong", p);
		a.putExtra("duong", "");
		a.putExtra("atmname", nameAtm);
		startActivity(a);

	}
		private void setId() {
			spPTX = (Spinner) findViewById(R.id.sp_ptx_atm);
			spQH = (Spinner) findViewById(R.id.sp_qh_atm);
			spTP = (Spinner) findViewById(R.id.sp_ttp_atm);
			spTenATM = (Spinner) findViewById(R.id.sp_ten_atm);
			btntim = (Button) findViewById(R.id.btn_tim_atm);
			edTenDuong = (EditText) findViewById(R.id.ed_tenduong_atm);

			cbTenDuong = (CheckBox) findViewById(R.id.cb_tenduong_atm);

			adaptertenATM = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, tenATM);
			spTenATM.setAdapter(adaptertenATM);

			adapterThanhPho = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, tenTP);
			spTP.setAdapter(adapterThanhPho);

			adaptertenQuan = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, tenQuan);
			spQH.setAdapter(adaptertenQuan);
			adaptertenPhuong = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, tenPhuong);
			spPTX.setAdapter(adaptertenPhuong);
			spQH.setEnabled(false);
			spTP.setEnabled(false);

		}
		private String getNameAtm() {
			String _name = "";
			int idTenAtm = (int) spTenATM.getSelectedItemId();
			switch (idTenAtm) {
	
			case 0:
				_name = "ACB";
				break;
			case 1: // DongA
				_name = "DongABank";
				break;
			case 2: // SeABank
				_name = "SeABank";
				break;
			case 3: // TechcomBank
				_name = "Techcombank";
				break;
			case 4: // HDBank
				_name = "HDBank";
				
				break;
			case 5: // VIBBank
				_name = "VIBBank";
				
				break;
			case 6: // SaigonBank
				_name = "Saigonbank";
				
				break;
			case 7: // SacomBank
				_name = "Sacombank";
				
				break;
			case 8: // EximBank
				_name = "Eximbank";
				
				break;
			case 9: // VietcomBank
				_name = "Vietcombank";
				
				break;
			case 10: // ViettinBank
				_name = "Vietinbank";
				
				break;
			case 11: // BIDV
				_name = "BIDV";
				
				break;
			case 12: // AgriBank
				_name = "Argibank";
				
				break;
			}
			return _name;
		}

	
	
	
	
	

}
