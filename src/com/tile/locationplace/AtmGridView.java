/**
 * Má»™t GridView chá»©a danh sÃ¡ch cÃ¡c ngÃ¢n hÃ ng cÃ³ ATM.
 */


package com.tile.locationplace;




import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class AtmGridView extends SherlockActivity {
	
	
	/* Variable Search ATM. */
	Spinner spTenATM, spTP, spQH, spPTX;
	CheckBox cbTenDuong;
	EditText edTenDuong;
	Button btntim;
	
	ArrayAdapter<String> adaptertenATM;
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
	
	
	
	/* 000000000000000000 END 0000000000000000000000000 */
	
	String[] tenAtm = {"Ã� ChÃ¢u - ACB",
			"TMCP Ä�Ã´ng Ã� - DongA Bank", "TMCP Ä�Ã´ng Nam Ã� - SeABank",
			"TMCP Ká»¹ ThÆ°Æ¡ng Viá»‡t Nam - Techcombank", 
			"TMCP PhÃ¡t Triá»ƒn NhÃ  TPHCM - HDBank",
			"QuÃ¢n Ä�á»™i - MB", "Quá»‘c Táº¿ - VIBBank",
			"TMCP SÃ i GÃ²n CÃ´ng ThÆ°Æ¡ng - Saigonbank",
			"TMCP SÃ i GÃ²n ThÆ°Æ¡ng TÃ­n - Sacombank",
			"Xuáº¥t Nháº­p Kháº©u Viá»‡t Nam - Eximbank",
			"TMCP Ngoáº¡i ThÆ°Æ¡ng Viá»‡t Nam - Vietcombank",
			"TMCP CÃ´ng ThÆ°Æ¡ng Viá»‡t Nam - Vietinbank",
			"TMCP Ä�T&PT Viá»‡t Nam - BIDV",
			"NN&PT NÃ´ng ThÃ´n Viá»‡t Nam - Agribank"};
	/** Name and Image ATM */
	String[] tenatm = {"ACB","DongA","SeABank","Techcombank","HDBank","VIBank"
			,"SaigonBank","Sacombank","EximBank","Vietcombank","Vietinbank","BIDV","Agribank"};
	
	int[] hinh = {R.drawable.acb,R.drawable.donga,R.drawable.seabank,R.drawable.techcombank,
			R.drawable.hdbank,R.drawable.saigonbank,R.drawable.saigonbank,R.drawable.sacombank,
			R.drawable.eximbank,R.drawable.vietcombank,R.drawable.vietinbank,R.drawable.bidv,R.drawable.agribank};
	
	GridView gv;
	Intent atm; // Intent call List ATM.
	boolean i = false;// variable count of menu.
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atm_grid_view);
		
		
		
		atm = new Intent(AtmGridView.this,ListAtm.class);
		gv = (GridView)findViewById(R.id.gv_atm);
		/* Set Adapter for GridView. */
		gv.setAdapter(new myadapter(this));
		/* Set event for GridView. */
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0: // ACB
					atm.putExtra("nameId", "ACB");
					startActivity(atm);
					break;
				case 1: // DongA
					atm.putExtra("nameId", "DongABank");
					startActivity(atm);
					break;
				case 2: // SeABank
					atm.putExtra("nameId", "SeABank");
					startActivity(atm);
					break;
				case 3: // TechcomBank
					atm.putExtra("nameId", "Techcombank");
					startActivity(atm);
					break;
				case 4: // HDBank
					atm.putExtra("nameId", "HDBank");
					startActivity(atm);
					break;
				case 5: // VIBBank
					atm.putExtra("nameId", "VIBBank");
					startActivity(atm);
					break;
				case 6: // SaigonBank
					atm.putExtra("nameId", "Saigonbank");
					startActivity(atm);
					break;
				case 7: // SacomBank
					atm.putExtra("nameId", "Sacombank");
					startActivity(atm);
					break;
				case 8: // EximBank
					atm.putExtra("nameId", "Eximbank");
					startActivity(atm);
					break;
				case 9: // VietcomBank
					atm.putExtra("nameId", "Vietcombank");
					startActivity(atm);
					break;
				case 10: // ViettinBank
					atm.putExtra("nameId", "Vietinbank");
					startActivity(atm);
					break;
				case 11: // BIDV
					atm.putExtra("nameId", "BIDV");
					startActivity(atm);
					break;
				case 12: // AgriBank
					atm.putExtra("nameId", "Argibank");
					startActivity(atm);
					break;

				}

			}
		});
		
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		 SubMenu subMenu1 = menu.addSubMenu("Action Item");
	        subMenu1.add("TÃ¬m NÃ¢ng Cao");

	        MenuItem subMenu1Item = subMenu1.getItem();
	        subMenu1Item.setIcon(R.drawable.ic_title_share_default);
	        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//
//	        SubMenu subMenu2 = menu.addSubMenu("Overflow Item");
//	        subMenu2.add("These");
//	        subMenu2.add("Are");
//	        subMenu2.add("Sample");
//	        subMenu2.add("Items");
//
//	        MenuItem subMenu2Item = subMenu2.getItem();
//	        subMenu2Item.setIcon(R.drawable.ic_compose);
		
		
		
		return super.onCreateOptionsMenu(menu);
	}

	
		@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 0:
			if(!i){
				i = true;
				break;
			}
			if(i){
				Intent a = new Intent(AtmGridView.this, FindATM.class);
				startActivity(a);
				i = false;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}



		// ///////////class for 1 Ã´ //////
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
					arg1 = layoutinflater.inflate(R.layout.gridview_atm, null);
					mot_o.textview = (TextView) arg1.findViewById(R.id.tv_gridview_atm);
					mot_o.imageview = (ImageView) arg1
							.findViewById(R.id.im_gridview_atm);
					arg1.setTag(mot_o);
				} else
					mot_o = (View_Mot_O) arg1.getTag();

				mot_o.imageview.setImageResource(hinh[arg0]);
				mot_o.textview.setText(tenatm[arg0]);

				return arg1;
			}

		}


}
