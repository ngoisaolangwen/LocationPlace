/**
 * Danh sÃ¡ch quÃ¡n Äƒn.
 * Bao gá»“m má»™t ViewPager chá»©a 2 fragment cá»§a tÃ´i vÃ  server.
 */

package com.tile.locationplace;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tile.locationplace.database.MyFood;

public class FoodList extends FragmentActivity {
	
	
	MyAdapter myAdapter;
	ViewPager myViewPager;
	String[] title = { "Server", "Cá»§a TÃ´i" };
	List<MyFood> _foodListS; // Danh sÃ¡ch Food server.
	List<MyFood> _foodListC; // Danh sÃ¡ch Food Client.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_list);
		
		/* Ä�á»• dá»¯ liá»‡u vÃ o danh sÃ¡ch. */
		_foodListC = MainLocationPlace.dbMy.getAllfood();
		_foodListS = MainLocationPlace.arrayFood;
		myViewPager = (ViewPager)findViewById(R.id.viewpagerFood);
		myAdapter = new MyAdapter(getSupportFragmentManager());
		myViewPager.setAdapter(myAdapter);
		
		
		
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myViewPager.setAdapter(myAdapter);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_list, menu);
		return true;
	}
	
	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int arg0) {

			switch (arg0) {
			case 0:
				if(_foodListS.size() < 0){
					Toast.makeText(FoodList.this,
							"KhÃ´ng cÃ³ dá»¯ liá»‡u!", Toast.LENGTH_LONG).show();
				}
				return new FoodFragmentS(_foodListS);
			case 1:
				if(_foodListC.size() < 0){
					Toast.makeText(FoodList.this,
							"KhÃ´ng cÃ³ dá»¯ liá»‡u!", Toast.LENGTH_LONG).show();
				}
				return new FoodFragment(_foodListC);

			}

			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return title[position];
		}

		@Override
		public int getCount() {

			return 2;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.add:
			/* ThÃªm quÃ¡n Äƒn. */
			
			
			
			break;
			
		case R.id.item1:
			/* Add QuÃ¡n Ä‚n táº¡i vá»‹ trÃ­ Ä‘ang Ä‘á»©ng. */
			Intent iAddFood = new Intent(FoodList.this, AddFood.class);
			Bundle bundle = new Bundle();
			bundle.putString("lat", String.valueOf(MainLocationPlace._getMyLocation.getLatitude()));
			bundle.putString("lng", String.valueOf(MainLocationPlace._getMyLocation.getLongitude()));
			iAddFood.putExtras(bundle);
			startActivity(iAddFood);
			
			break;
		case R.id.item2:
			/* ThÃªm má»™t vá»‹ trÃ­ báº¥t ká»³. */
			
			Intent iViewM = new Intent(FoodList.this, FoodGoogleMap.class);
			iViewM.putExtra("food", false);
			startActivity(iViewM);
			Toast.makeText(getBaseContext(),
					"Nháº¥n giá»­ má»™t vá»‹ trÃ­ trÃªn báº£n Ä‘á»“ Ä‘á»ƒ thÃªm!",
					Toast.LENGTH_LONG).show();
			break;
			
		case R.id.find:
			/* TÃ¬m quÃ¡n Äƒn. */
			Intent find = new Intent(FoodList.this,FindFood.class);
			startActivity(find);
			
			break;
			

		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	

}
