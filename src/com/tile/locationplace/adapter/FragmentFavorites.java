/**
 * Adapter sá»­ dá»¥ng cho danh sÃ¡ch yÃªu thÃ­ch.
 */

package com.tile.locationplace.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tile.locationplace.AtmGoogleMap;
import com.tile.locationplace.FoodGoogleMap;
import com.tile.locationplace.MainLocationPlace;
import com.tile.locationplace.R;
import com.tile.locationplace.database.MyATM;
import com.tile.locationplace.database.MyFood;
import com.tile.locationplace.menu.ActionItem;
import com.tile.locationplace.menu.QuickAction;
import com.tile.locationplace.menu.QuickAction.OnActionItemClickListener;

@SuppressLint("ValidFragment")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentFavorites extends Fragment {

	ListView _lv;
	AdapterFood _adapterFood;
	AdapterATM _adapterATM;
	List<MyFood> _foodList; // Danh sÃ¡ch Food yÃªu thÃ­ch.
	List<MyATM> _atmList; // Danh sÃ¡ch ATM yÃªu thÃ­ch.
	int _id;
	QuickAction mQ;

	@SuppressLint("ValidFragment")
	public FragmentFavorites(int id, List<MyFood> foodList, List<MyATM> atmList) {
		super();
		this._foodList = foodList;
		this._atmList = atmList;
		this._id = id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_fragment_favorites,
				container, false);
		_lv = (ListView) view.findViewById(R.id.lv_farment_favorites);
		mQ = new QuickAction(getActivity());

		/* Náº¿u lÃ  Food. */
		if (_id == 1) {

			_adapterFood = new AdapterFood(getActivity(),R.layout.activity_fragment_favorites, _foodList);
			/* Set Adapter. */
			_lv.setAdapter(_adapterFood);

		} else if (_id == 2) {

			_adapterATM = new AdapterATM(getActivity(),R.layout.activity_fragment_favorites, _atmList);
			/* Set Adapter. */
			_lv.setAdapter(_adapterATM);

		}

		/* Sá»± kiá»‡n click vÃ o ListView. */
		_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (_id == 1) {
					MainLocationPlace._food = _foodList.get(arg2);
				}
				if (_id == 2) {
					MainLocationPlace._atm = _atmList.get(arg2);
				}

				mQ.show(arg1);

			}
		});

		clickListView();
		return view;
	}

	private void clickListView() {

		ActionItem addActionMap = new ActionItem();
		addActionMap.setIcon(getResources().getDrawable(R.drawable.navigation));
		addActionMap.setTitle("TÃ¬m Ä�Æ°á»�ng");

		ActionItem addActionDelete = new ActionItem();
		addActionDelete.setIcon(getResources().getDrawable(R.drawable.delete));
		addActionDelete.setTitle("KhÃ´ng thÃ­ch");

		mQ.addActionItem(addActionMap);
		mQ.addActionItem(addActionDelete);

		mQ.setOnActionItemClickListener(new OnActionItemClickListener() {

			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {

				if (pos == 0) {
					if (_id == 1) {
						/* Hiá»ƒn thá»‹ lÃªn map. */
						Intent food = new Intent(getActivity(),FoodGoogleMap.class);
						food.putExtra("food", true);
						startActivity(food);
					}
					if (_id == 2) {
						/* Hiá»ƒn thá»‹ lÃªn map. */
						Intent atm = new Intent(getActivity(),AtmGoogleMap.class);
						startActivity(atm);
					}

				} else if (pos == 1) {
					/* KhÃ´ng thÃ­ch. */
					if (_id == 1) {
						MainLocationPlace.dbLove.deletefood(MainLocationPlace._food);
						_foodList = MainLocationPlace.dbLove.getAllfood();
						_adapterFood.notifyDataSetChanged();
						_adapterFood = new AdapterFood(getActivity(),R.layout.activity_fragment_favorites, _foodList);
						/* Set Adapter. */
						_lv.setAdapter(_adapterFood);
					}
					if (_id == 2) {
						MainLocationPlace.dbLove.deleteATM(MainLocationPlace._atm);
						_atmList = MainLocationPlace.dbLove.getAllATM();
						_adapterATM.notifyDataSetChanged();
						_adapterATM = new AdapterATM(getActivity(),R.layout.activity_fragment_favorites, _atmList);
						/* Set Adapter. */
						_lv.setAdapter(_adapterATM);
					}

				}

			}
		});

	}

}
