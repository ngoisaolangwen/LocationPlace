/**
 * Ä�á»‘i tÆ°á»£ng cÃ³ cÃ¡c thuá»™c tÃ­nh chung cá»§a cÃ¡c Ä‘á»‹a Ä‘iá»ƒm.
 */

package com.tile.locationplace.database;

public class MyLocations {

	// Variable
	protected int _id;
	protected String _lat;
	protected String _lng;
	protected String _name;
	protected String _info;
	protected String _address;
	protected String _ward;
	protected String _district;
	protected String _city;
	protected byte[] _image;
	protected boolean _favorites;
	float km;

	// Contructor
	public MyLocations() {
		super();
	}

	// Contructor full variable
	public MyLocations(int id, String lat, String lng, String name,
			String info, String address, String ward, String district,
			String city, boolean favorites, byte[] image) {
		this._id = id;
		this._address = address;
		this._info = info;
		this._lat = lat;
		this._lng = lng;
		this._name = name;
		this._ward = ward;
		this._district = district;
		this._city = city;
		this._image = image;
		this._favorites = favorites;

	}

	/** Getting/Setting */
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_lat() {
		return _lat;
	}

	public void set_lat(String _lat) {
		this._lat = _lat;
	}

	public String get_lng() {
		return _lng;
	}

	public void set_lng(String _lng) {
		this._lng = _lng;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_info() {
		return _info;
	}

	public void set_info(String _info) {
		this._info = _info;
	}

	public String get_address() {
		return _address;
	}

	public void set_address(String _address) {
		this._address = _address;
	}

	public String get_ward() {
		return _ward;
	}

	public void set_ward(String _ward) {
		this._ward = _ward;
	}

	public String get_district() {
		return _district;
	}

	public void set_district(String _district) {
		this._district = _district;
	}

	public String get_city() {
		return _city;
	}

	public void set_city(String _city) {
		this._city = _city;
	}

	public byte[] get_Image() {
		return _image;
	}

	public void set_Image(byte[] _Image) {
		this._image = _Image;
	}

	public boolean get_favorites() {
		return _favorites;
	}

	public void set_favorites(boolean _favorites) {
		this._favorites = _favorites;
	}
	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}


}
