package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;


public class Medium implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private MediumType type;
    private String streetOne;
    private String streetTwo;
	private String city;
    private String stateOrProvince;
	private String country;
    private String postcode;
    private String emailAddress;
    private String number;
    
	public MediumType getType() {
		return type;
	}
	public void setType(MediumType type) {
		this.type = type;
	}
	public String getStreetOne() {
		return streetOne;
	}
	public void setStreetOne(String streetOne) {
		this.streetOne = streetOne;
	}
	public String getStreetTwo() {
		return streetTwo;
	}
	public void setStreetTwo(String streetTwo) {
		this.streetTwo = streetTwo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Medium [type=" + type + ", streetOne=" + streetOne + ", streetTwo=" + streetTwo + ", city=" + city
				+ ", stateOrProvince=" + stateOrProvince + ", country=" + country + ", postcode=" + postcode
				+ ", emailAddress=" + emailAddress + ", number=" + number + "]";
	}

    
}
