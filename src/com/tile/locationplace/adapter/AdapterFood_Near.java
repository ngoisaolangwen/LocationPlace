/**
 * QuÃ¡n Äƒn gáº§n.
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

public class AdapterFood_Near extends ArrayAdapter<MyFood> {
	
	List<MyFood> _myFood;
	Context _context;

	public AdapterFood_Near(Context context, int textViewResourceId,
			List<MyFood> objects) {
		super(context, textViewResourceId, objects);
		this._myFood = objects;
		this._context = context;
	}
	
	static class ViewHolder {
		protected TextView tvInfo, tvName;
		protected TextView tvKm;
		protected ImageView im;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) 
					_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_food_atm_near, parent, false);

			vh  = new ViewHolder();

			/* Ã�nh xáº¡ tá»«ng hÃ ng ListView cáº­p nháº­t thÃ´ng tin. */
			vh.tvInfo = (TextView) 
					convertView.findViewById(R.id.tv2_item_food_atm_near);
			vh.tvName = (TextView) 
					convertView.findViewById(R.id.tv1_item_food_atm_near);
			vh.tvKm = (TextView)
					convertView.findViewById(R.id.tvkm_item_food_atm_near);
			vh.im = (ImageView)
					convertView.findViewById(R.id.im_item_food_atm_near);
//			Log.d("if", position+"");
			convertView.setTag(vh);

		} else {
//			Log.d("else", position+"");
			vh = (ViewHolder) convertView.getTag();
			
			
		}
		
		MyFood food = _myFood.get(position);
		
		// Set ThÃ´ng tin
		vh.tvName.setText(food.get_name());
		vh.tvInfo.setText(food.get_address() + ", PhÆ°á»�ng " + food.get_ward() + ", Quáº­n "
				+ food.get_district());
		vh.im.setImageResource(R.drawable.restaurants);
		vh.tvKm.setText("");
		
		
		

		return convertView;

		
	}
}
