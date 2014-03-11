/**
 * Ä�á»‘i tÆ°á»£ng ATM káº¿ thá»«a tá»« MyLocations.
 */

package com.tile.locationplace.database;

public class MyATM extends MyLocations {

	private String nameid;

	// Contructor
	public MyATM() {
		super();
	}

	// Contructor full variable
	public MyATM(int id, String lat, String lng, String name, String info,
			String address, String ward, String district, String city,
			boolean favorites, byte[] image) {
		super(id, lat, lng, name, info, address, ward, district, city,
				favorites, image);
	}
	
	/** Getting/Setting */

	public String get_Nameid() {
		return nameid;
	}

	public void set_Nameid(String nameid) {
		this.nameid = nameid;
	}

	
	
	

}
