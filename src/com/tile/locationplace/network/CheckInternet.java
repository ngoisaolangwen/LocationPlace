/* 
 Class kiá»ƒm tra káº¿t ná»‘i Internet.
 */


package com.tile.locationplace.network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class CheckInternet {
	
	private Context context;
	
	public CheckInternet(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		
	}
	
	
	public Boolean isOnline() {
		
		    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		    NetworkInfo ni = cm.getActiveNetworkInfo();
		
		    if(ni != null && ni.isConnected()) {
		
		         return true;
		
		        }
		
		    return false;
		
		}
	
	/**
	 * Báº­t Internet.
	 */
	public void showSettingAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle("Wifi is settings");

		// Message
		alertDialog
				.setMessage("Not Internet!");

		// OK Button
		alertDialog.setPositiveButton("Setting",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						/* Gá»�i má»™t Activity chá»©a cÃ i Ä‘áº·t láº¥y vá»‹ trÃ­. */
						Intent intent = new Intent(
								Settings.ACTION_WIFI_SETTINGS);
						context.startActivity(intent);
					}

				});

		// Cancel Button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}


}
