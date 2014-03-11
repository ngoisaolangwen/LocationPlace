/**
 * Food.
 */


package com.tile.locationplace.database;

public class MyFood extends MyLocations {

	// Variable
	private String _open;
	private long _price;
	private String _close;

	// Contructor
	public MyFood() {
		super();
	}

	// Contructor full variable
	public MyFood(int id, String lat, String lng, String name, String info,
			String open, String close, String address, String ward,
			String district, String city, long price, boolean favorite,
			byte[] image) {
		super(id, lat, lng, name, info, address, ward, district, city,
				favorite, image);

		this._open = open;
		this._price = price;
		this._close = close;

	}

	/** Getting/Setting */
	public String get_open() {
		return _open;
	}

	public void set_open(String _open) {
		this._open = _open;
	}

	public long get_price() {
		return _price;
	}

	public void set_price(long _price) {
		this._price = _price;
	}

	public String get_close() {
		return _close;
	}

	public void set_close(String _close) {
		this._close = _close;
	}

}
