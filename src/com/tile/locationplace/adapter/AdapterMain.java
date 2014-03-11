package com.tile.locationplace.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AdapterMain extends ArrayAdapter<String> {
	
	String[] danhMuc;
	

	public AdapterMain(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.danhMuc = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
	

}
