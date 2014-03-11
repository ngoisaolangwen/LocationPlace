/**
 * Class dÃ¹ng Ä‘á»ƒ thÃªm má»™t Ä‘á»‹a Ä‘iá»ƒm quÃ¡n Äƒn
 * NgÆ°á»�i dÃ¹ng tá»± thÃªm vÃ o.
 */

package com.tile.locationplace;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tile.locationplace.database.MyFood;

public class AddFood extends Activity {
	
	
	EditText edName, edAddress, edInfo, edOpen, edClose;
	Spinner spward;
	Button btnAdd;
	ImageButton ibtCamera;
	ImageView imageView;
	ArrayAdapter<String> adapter;

	String[] wardList = { "PhÆ°á»�ng 1", "PhÆ°á»�ng 2", "PhÆ°á»�ng 3", "PhÆ°á»�ng 4",
			"PhÆ°á»�ng 5", "PhÆ°á»�ng 6", "PhÆ°á»�ng 7", "PhÆ°á»�ng 8", "PhÆ°á»�ng 9",
			"PhÆ°á»�ng 10", "PhÆ°á»�ng 11", "PhÆ°á»�ng 12", "PhÆ°á»�ng 13", "PhÆ°á»�ng 14",
			"PhÆ°á»�ng 15", "PhÆ°á»�ng 16" };


	String _name, _address, _ward, _info, _open, _close;
	String _district, _city;
	byte[] _byteImage;

	String _latAdd, _lngAdd;

	private static final int REQUEST_CODE = 1; // intent TakePicture

	Bitmap _bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_food);
		
		setVisiable();
		Intent intentAdd = getIntent();
		Bundle bundleAdd = intentAdd.getExtras();
		
		if(bundleAdd != null){
		_latAdd = bundleAdd.getString("lat");
		_lngAdd = bundleAdd.getString("lng");
		}
		
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getInfoAdd();
				MainLocationPlace.dbMy.addFood(getFoodAdd());
				
				finish();
			}
		});

		ibtCamera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		
		
	}
	
	/* ThÃªm cÃ¡c thÃ´ng tin cá»§a ngÆ°á»�i dÃ¹ng nháº­p vÃ o biáº¿n cá»§a App */
	private void getInfoAdd() {
		_name = edName.getText().toString();
		_address = edAddress.getText().toString();
		_info = edInfo.getText().toString();
		_open = edOpen.getText().toString();
		int w = (int) (spward.getSelectedItemId() + 1);
		_ward = Integer.toString(w);
		_close = edClose.getText().toString();
		_district = "Quáº­n 8";
		_city = "HCM";

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		_byteImage = stream.toByteArray();
	}
	
	
	/* Add vÃ o má»™t Object Food */
	private MyFood getFoodAdd() {
		MyFood food = new MyFood();
		food.set_district(_district);
		food.set_address(_address);
		food.set_city(_city);
		food.set_close(_close);
		food.set_info(_info);
		food.set_name(_name);
		food.set_open(_open);
		food.set_ward(_ward);
		food.set_lat(_latAdd);
		food.set_lng(_lngAdd);
		food.set_favorites(false);
		food.set_Image(_byteImage);
		return food;

	}

	/* Ã�nh xáº¡ cÃ¡c biáº¿n trÃªn Xml file lÃªn file .java */
	private void setVisiable() {

		edName = (EditText) findViewById(R.id.edname);
		edAddress = (EditText) findViewById(R.id.edaddress);
		edInfo = (EditText) findViewById(R.id.edinfo);
		edOpen = (EditText) findViewById(R.id.edopen);
		edClose = (EditText) findViewById(R.id.edclose);

		btnAdd = (Button) findViewById(R.id.btnAdd);
		spward = (Spinner) findViewById(R.id.spward);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, wardList);
		spward.setAdapter(adapter);
		ibtCamera = (ImageButton) findViewById(R.id.ibtCamera);
		imageView = (ImageView) findViewById(R.id.imPicture);

	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			_bitmap = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(_bitmap);
		} else {
			Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT)
					.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_food, menu);
		return true;
	}

}
