package com.gojek.parking;

import java.util.Map;
import java.util.TreeSet;

public interface Parking {
	
	public TreeSet<Integer> getAvailableSlots();
	
	public Map<Integer, Car> getParkedCarMap();
	
	public void createParkingWithSizeOf(String size);
	
	public void addForParking(String number, String colour);
	
	public void printParkingStatus();
	
	public void removeFromParking(String slot_number);
	
	public void parseInput(String input,Parking parking);
	
	public String getSlotNumberForRegistrationNumber(String number);
	
	public String getSlotNumberForColour(String colour);
	
	public String getRegistrationNumbersForColour(String colour);
	

}
