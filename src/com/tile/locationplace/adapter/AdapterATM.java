/**
 * Adapter sá»­ dá»¥ng cho danh sÃ¡ch ATM.
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
import com.tile.locationplace.database.MyATM;



public class AdapterATM extends ArrayAdapter<MyATM> {
	private List<MyATM> myAtm;
	private Context context;
//	private static String distance="";
	ViewHolder vH;

	public AdapterATM(Context context,int resourc, List<MyATM> atm) {
		super(context, resourc, atm);
		this.context = context;
		this.myAtm = atm;
		
	}

	static class ViewHolder {
		protected TextView tvInfo, tvName;
		protected ImageView im;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		

		
		View rowView = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.item_food_and_atm_list, null);

			ViewHolder vh = new ViewHolder();

			/* Ã�nh xáº¡ tá»«ng hÃ ng ListView cáº­p nháº­t thÃ´ng tin. */
			vh.tvInfo = (TextView) rowView
					.findViewById(R.id.tv2_item_food_and_atm);
			vh.tvName = (TextView) rowView
					.findViewById(R.id.tv1_item_food_and_atm);
			vh.im = (ImageView)rowView.findViewById(R.id.im_item_food_and_atm);

			rowView.setTag(vh);

		} else {
			
			rowView = convertView;


		}
		
		
		MyATM atm = myAtm.get(position);
		vH = (ViewHolder) rowView.getTag();
		
		// Set ThÃ´ng tin
		vH.tvName.setText(atm.get_name());
		vH.tvInfo.setText(atm.get_address() + ", PhÆ°á»�ng " + atm.get_ward() + ", Quáº­n "
				+ atm.get_district());
		vH.im.setImageResource(R.drawable.atm);
		
		

		return rowView;

	}

	

}
