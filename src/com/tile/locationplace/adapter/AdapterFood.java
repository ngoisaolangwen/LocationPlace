/**
	Class nÃ y dÃ¹ng Ä‘á»ƒ táº¡o má»™t Adapter cho ListView
	ListView sá»­ dá»¥ng Ä‘á»ƒ hiá»ƒn thá»‹ táº¥t cáº£ cÃ¡c Ä‘á»‹a Ä‘iá»ƒm cá»§a Food trong database.
 */

package com.tile.locationplace.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tile.locationplace.R;
import com.tile.locationplace.database.MyFood;

public class AdapterFood extends ArrayAdapter<MyFood> {
	private List<MyFood> myFood;
	private Context context;

	public AdapterFood(Context context,int resourc, List<MyFood> food) {
		super(context, resourc, food);
		this.context = context;
		this.myFood = food;
	}

	static class ViewHolder {
		protected TextView tvInfo, tvName;
		protected ImageView im;
 	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = null;
		ViewHolder viewH;
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.item_food_and_atm_list, null);

			viewH = new ViewHolder();

			/* Ã�nh xáº¡ tá»«ng hÃ ng ListView cáº­p nháº­t thÃ´ng tin. */
			viewH.tvInfo = (TextView) rowView
					.findViewById(R.id.tv2_item_food_and_atm);
			viewH.tvName = (TextView) rowView
					.findViewById(R.id.tv1_item_food_and_atm);
			viewH.im = (ImageView)rowView.findViewById(R.id.im_item_food_and_atm);

			rowView.setTag(viewH);

		} else {
			rowView = convertView;
		}
		MyFood food = myFood.get(position);

		// Set ThÃ´ng tin
		viewH = (ViewHolder) rowView.getTag();
		viewH.tvName.setText(food.get_name());
		viewH.tvInfo.setText(food.get_address() + ", PhÆ°á»�ng " + food.get_ward() + ", Quáº­n "
				+ food.get_district());
		viewH.im.setImageResource(R.drawable.restaurants);

		return rowView;
	}

}
