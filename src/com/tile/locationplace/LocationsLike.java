/**
 * Danh sÃ¡ch cÃ¡c Ä‘á»‹a Ä‘iá»ƒm mÃ  ngÆ°á»�i dÃ¹ng yÃªu thÃ­ch.
 * Sá»­ dá»¥ng ViewPager.
 */


package com.tile.locationplace;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.tile.locationplace.adapter.FragmentFavorites;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.database.MyFood;

public class LocationsLike extends FragmentActivity {
	
	
	
	MyAdapter myAdapter;
	ViewPager myViewPager;
	String[] title = { "QuÃ¡n Ä‚n", "ATM" };
	List<MyFood> _foodList; // Danh sÃ¡ch Food yÃªu thÃ­ch.
	List<MyATM> _atmList; // Danh sÃ¡ch ATM yÃªu thÃ­ch.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations_like);
		
		/* Láº¥y danh sÃ¡ch yÃªu thÃ­ch cá»§a Food vÃ  ATM. */
		_foodList = MainLocationPlace.dbLove.getAllfood();
		_atmList = MainLocationPlace.dbLove.getAllATM();

		myViewPager = (ViewPager) findViewById(R.id.viewpagerLike);
		myAdapter = new MyAdapter(getSupportFragmentManager());
		myViewPager.setAdapter(myAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locations_like, menu);
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
				return new FragmentFavorites(1, _foodList, _atmList);
			case 1:
				return new FragmentFavorites(2, _foodList, _atmList);

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

}
