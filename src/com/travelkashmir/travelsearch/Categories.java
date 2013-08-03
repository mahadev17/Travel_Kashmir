package com.travelkashmir.travelsearch;

import java.io.Serializable;

public class Categories implements Serializable {
	private static final long serialVersionUID = 1L;
	public String hotelid, hotel_title, address, locationid, telephone, fax;
	public String email;
	public String website;
	public String conact_person;
	public String image_file;
	public String userid;
	public String cat_id;
	public String desc;
	public String facility;
	public String menu;
	

	Categories(String _hotelid, String _hotel_title, String _address,
			String _locationid, String _telephone, String _fax, String _email,
			String _website, String _conact_person, String _image_file,
			String _userid, String _cat_id, String _desc,
			String _facility, String _menu) {
		this.hotelid = _hotelid;
		this.hotel_title = _hotel_title;
		this.address = _address;
		this.locationid = _locationid;
		this.telephone = _telephone;
		this.fax = _fax;
		this.email = _email;
		this.website = _website;
		this.conact_person = _conact_person;
		this.image_file = _image_file;
		this.userid = _userid;
		this.cat_id = _cat_id;
		this.desc = _desc;
		this.facility = _facility;
		this.menu = _menu;
	}

}
