/**
 * Database yÃªu thÃ­ch.
 * Chá»©a cÃ¡c Ä‘á»‹a Ä‘iá»ƒm yÃªu thÃ­ch cá»§a ngÆ°á»�i dÃ¹ng.
 */

package com.tile.locationplace.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabasesLove extends SQLiteOpenHelper {

	/** Database name */
	private static String DATABASE_NAME = "placeDBLove";

	/** The table name */
	private static final String DATABASE_TABLE_ATM = "ATM";
	private static final String DATABASE_TABLE_FOOD = "FOOD";

	/** Version number of the database */
	private static int VERSION = 1;

	/* ___________________MyLocations__________________________ */

	/** ID */
	public static final String _ID = "_id";
	/** Latitude */
	public static final String _LAT = "lat";
	/** Longitude */
	public static final String _LNG = "lng";

	/** Name + Info + type + address + image */
	public static final String _NAME_ID = "nameid";// tÃªn Ä‘á»ƒ Ä‘á»‹nh dáº¡ng ATM.
	public static final String _NAME = "name"; // TÃªn
	public static final String _INFO = "info"; // ThÃ´ng tin chung cá»§a Ä‘á»‹a Ä‘iá»ƒm.
	public static final String _WARD = "ward"; // PhÆ°á»�ng
	public static final String _ADDRESS = "address"; // Ä�á»‹a chá»‰ ( sá»‘ nhÃ ,
														// Ä‘Æ°á»�ng).
	public static final String _DISTRICT = "district"; // Quáº­n.
	public static final String _CITY = "city"; // ThÃ nh Phá»‘.
	public static final String _FAVORITES = "favorites"; // YÃªu thÃ­ch.
	public static final String _IMAGE = "image"; // HÃ¬nh áº£nh.

	/* ________________________ ATM ___________________ */
	/** Táº¡o báº£ng ATM trong database. */
	private String sqlAtm = " create table " + DATABASE_TABLE_ATM + " ( " + _ID
			+ " integer primary key," + _LAT + " text not null,"
			+ _LNG + " text not null," + _NAME_ID + " text UNICODE," + _NAME
			+ " text UNICODE not null," + _ADDRESS + " text UNICODE not null,"
			+ _WARD + " text UNICODE," + _DISTRICT + " text UNICODE," + _CITY
			+ " text UNICODE," + _INFO + " text UNICODE," + _FAVORITES
			+ " BOOLEAN," + _IMAGE + " BLOB" + " ) ";

	/* ________________________ FOOD _______________________ */

	/* Má»™t vÃ i thÃ´ng tin thÃªm cá»§a FOOD. */
	public static final String _PRICE = "price"; // GiÃ¡
	public static final String _OPEN = "open"; // Giá»� má»Ÿ cá»­a
	public static final String _CLOSE = "close"; // Giá»� Ä‘Ã³ng cá»­a

	/** Táº¡o báº£ng FOOD trong database. */
	private String sqlfood = " create table " + DATABASE_TABLE_FOOD + " ( "
			+ _ID + " integer primary key," + _LAT
			+ " text not null," + _LNG + " text not null," + _NAME
			+ " text UNICODE not null," + _ADDRESS + " text UNICODE not null,"
			+ _WARD + " text UNICODE," + _DISTRICT + " text UNICODE," + _CITY
			+ " text UNICODE," + _OPEN + " text," + _CLOSE + " text," + _INFO
			+ " text," + _PRICE + " long," + _FAVORITES + " BOOLEAN," + _IMAGE
			+ " BLOB" + " ) ";

	public DatabasesLove(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/* Táº¡o Database vá»›i 2 báº£ng FOOD vÃ  ATM. */
		db.execSQL(sqlAtm);
		db.execSQL(sqlfood);
	}

	/** Sá»­ dá»¥ng khi thay Ä‘á»•i database */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		// Create tables again
		onCreate(db);

	}
	
	/** Add new food */
	public void addFood(MyFood food) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(_ID, food.get_id());
		values.put(_NAME, food.get_name());
		values.put(_ADDRESS, food.get_address());
		values.put(_WARD, food.get_ward());
		values.put(_DISTRICT, food.get_district());
		values.put(_CITY, food.get_city());
		values.put(_IMAGE, food.get_Image());
		values.put(_INFO, food.get_info());
		values.put(_LAT, food.get_lat());
		values.put(_LNG, food.get_lng());
		values.put(_OPEN, food.get_open());
		values.put(_CLOSE, food.get_close());
		values.put(_PRICE, food.get_price());
		values.put(_FAVORITES, food.get_favorites());

		// Inserting row
		db.insert(DATABASE_TABLE_FOOD, null, values);
		db.close();

	}

	/** Getting single food */
	public MyFood getfood(int id) {
		return null;
	}

	/** Láº¥y 1 vá»‹ trÃ­ quÃ¡n Äƒn tá»« cursor */
	private MyFood getFood(Cursor cursor) {

		MyFood food = new MyFood();
		food.set_id(Integer.parseInt(cursor.getString(0)));
		food.set_lat(cursor.getString(1));
		food.set_lng(cursor.getString(2));
		food.set_name(cursor.getString(3));
		food.set_address(cursor.getString(4));
		food.set_ward(cursor.getString(5));
		food.set_district(cursor.getString(6));
		food.set_city(cursor.getString(7));
		food.set_open(cursor.getString(8));
		food.set_close(cursor.getString(9));
		food.set_info(cursor.getString(10));
		food.set_price(Long.parseLong(cursor.getString(11)));
		if (cursor.getString(12).toString().equals("1"))
			food.set_favorites(true);
		else
			food.set_favorites(false);
		food.set_Image(cursor.getBlob(13));
		Log.d("Image", cursor.getBlob(13) + "");

		return food;

	}

	/** Getting All food */
	public List<MyFood> getAllfood() {

		List<MyFood> foodList = new ArrayList<MyFood>();

		String select = "SELECT * FROM " + DATABASE_TABLE_FOOD;

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(select, null);

		// Add all food to List
		if (cursor.moveToFirst()) {
			do {
				MyFood food = getFood(cursor);

				foodList.add(food);

			} while (cursor.moveToNext());

		}

		return foodList;

	}

	/** Delete one food */
	public void deletefood(MyFood food) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(DATABASE_TABLE_FOOD, _ID + " =?",
				new String[] { String.valueOf(food.get_id()) });
		db.close();

	}
	
	/* ___________________________ ATM _________________________ */
	
	/** Add new ATM */
	public void addATM(MyATM atm) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(_NAME, atm.get_name());
		values.put(_ADDRESS, atm.get_address());
		values.put(_WARD, atm.get_ward());
		values.put(_DISTRICT, atm.get_district());
		values.put(_CITY, atm.get_city());
		values.put(_IMAGE, atm.get_Image());
		values.put(_INFO, atm.get_info());
		values.put(_LAT, atm.get_lat());
		values.put(_LNG, atm.get_lng());
		values.put(_FAVORITES, atm.get_favorites());

		// Inserting row
		db.insert(DATABASE_TABLE_ATM, null, values);
		db.close();

	}

	/** Láº¥y 1 vá»‹ trÃ­ atm tá»« cursor */
	private MyATM getATM(Cursor cursor) {

		MyATM atm = new MyATM();
		atm.set_id(Integer.parseInt(cursor.getString(0)));
		atm.set_lat(cursor.getString(1));
		atm.set_lng(cursor.getString(2));
		atm.set_Nameid(cursor.getString(3));
		atm.set_name(cursor.getString(4));
		atm.set_address(cursor.getString(5));
		atm.set_ward(cursor.getString(6));
		atm.set_district(cursor.getString(7));
		atm.set_city(cursor.getString(8));
		atm.set_info(cursor.getString(9));
		if (cursor.getString(10).toString().equals("1"))
			atm.set_favorites(true);
		else
			atm.set_favorites(false);
		// atm.set_favorites(Boolean.parseBoolean(cursor.getString(8)));
		atm.set_Image(cursor.getBlob(11));

		return atm;

	}

	/** Getting All ATM */
	public List<MyATM> getAllATM() {

		List<MyATM> atmList = new ArrayList<MyATM>();

		String select = "SELECT * FROM " + DATABASE_TABLE_ATM;

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(select, null);

		// Add all food to List
		if (cursor.moveToFirst()) {
			do {
				MyATM atm = getATM(cursor);

				atmList.add(atm);

			} while (cursor.moveToNext());

		}
		db.close();
		return atmList;

	}

	/** Láº¥y táº¥t cáº£ cÃ¡c Ä‘á»‹a Ä‘iá»ƒm yÃªu thÃ­ch. */
	public List<MyATM> getATMFavorites() {
		List<MyATM> atmList = new ArrayList<MyATM>();

		String select = "SELECT * FROM " + DATABASE_TABLE_ATM + " WHERE "
				+ _FAVORITES + " =" + 1;

		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(select, null);

		// Add all atm to List
		if (cursor.moveToFirst()) {
			do {
				MyATM atm = getATM(cursor);

				atmList.add(atm);

			} while (cursor.moveToNext());

		}

		return atmList;
	}

	// Delete one ATM
		public void deleteATM(MyATM atm) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(DATABASE_TABLE_ATM, _ID + " =?",
					new String[] { String.valueOf(atm.get_id()) });
			db.close();

		}
	
	
	


}
