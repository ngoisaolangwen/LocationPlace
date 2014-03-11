/**
 * Adapter sá»­ dá»¥ng cho cÃ¡c ATM gáº§n.
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

public class AdapterATM_Near extends ArrayAdapter<MyATM> {
	
	List<MyATM> _myATM;
	Context _context;

	public AdapterATM_Near(Context context, int textViewResourceId,
			List<MyATM> objects) {
		super(context, textViewResourceId, objects);
		this._myATM = objects;
		this._context = context;
	}
	
	class ViewHolder {
		protected TextView tvInfo, tvName;
		protected TextView tvKm;
		protected ImageView im;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		
		View view = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) 
					_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_food_atm_near, parent, false);

			ViewHolder vh = new ViewHolder();

			/* Ã�nh xáº¡ tá»«ng hÃ ng ListView cáº­p nháº­t thÃ´ng tin. */
			vh.tvInfo = (TextView) 
					view.findViewById(R.id.tv2_item_food_atm_near);
			vh.tvName = (TextView) 
					view.findViewById(R.id.tv1_item_food_atm_near);
			vh.tvKm = (TextView)
					view.findViewById(R.id.tvkm_item_food_atm_near);
			vh.im = (ImageView)
					view.findViewById(R.id.im_item_food_atm_near);

			view.setTag(vh);

		} else {
			
			view = convertView;


		}
		
		
		MyATM atm = _myATM.get(position);
		ViewHolder vH = (ViewHolder) view.getTag();
		
		// Set ThÃ´ng tin
		vH.tvName.setText(atm.get_name());
		vH.tvInfo.setText(atm.get_address() + ", PhÆ°á»�ng " + atm.get_ward() + ", Quáº­n "
				+ atm.get_district());
		vH.im.setImageResource(R.drawable.atm);
		vH.tvKm.setText(String.valueOf(atm.getKm())+" Km");
		
		

		return view;

		
	}
	
	
	
	
	
	
	

}
