package com.gojek.parking;

public class Car {
	
	private String colour;
	private String number;
	
	Car(String colour,String number){
		this.colour = colour;
		this.number = number;
		}

	
	public String getColour() {
		return colour;
	}


	public void setColour(String colour) {
		this.colour = colour;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
