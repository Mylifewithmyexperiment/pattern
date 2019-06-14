package com.gojek.parking;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ParkingLot implements Parking {
	
	private Map<Integer,Car> parkedCarMap = null;
	
	private int park_lot_max_size;
	
	private TreeSet<Integer> availableSlots = null;
	
	public TreeSet<Integer> getAvailableSlots() {
		return availableSlots;
	}

	public Map<Integer, Car> getParkedCarMap() {
		return parkedCarMap;
	}

	public int getPark_lot_max_size() {
		return park_lot_max_size;
	}


	public void parseInput(String input, Parking parking) {
		String action[] = input.split(" ");
		switch(action[0]){
			case "create_parking_lot":
				if(isValid(action,2,true,1)){
    				parking.createParkingWithSizeOf(action[1]);
				    ((ParkingLot) parking).printSlots();
				}
				  break;
			case "park":
				if(isValid(action,3,false,0)){
				parking.addForParking(action[1],action[2]);
				}
				  break;
			case "leave":
				if(isValid(action,2,true,1)){
				parking.removeFromParking(action[1]);
				}
				  break;
			case "status":
				parking.printParkingStatus();
				  break;
			case "registration_numbers_for_cars_with_colour":
				if(isValid(action,2,false,0)){
				System.out.println(parking.getRegistrationNumbersForColour(action[1]));
				}
				  break;
			case "slot_numbers_for_cars_with_colour":
				if(isValid(action,2,false,0)){
				 System.out.println(parking.getSlotNumberForColour(action[1]));
				}
				  break;
			case "slot_number_for_registration_number":
				if(isValid(action,2,false,0)){
				System.out.println(parking.getSlotNumberForRegistrationNumber(action[1]));
				}
				break;
			default:
				   System.out.println("Invalid Input");
				   printUsage();
				        
		}
	}

	
	public  void createParkingWithSizeOf(String size) {
		int lot_size = Integer.parseInt(size);
		if(lot_size > 0){
			availableSlots = new TreeSet<Integer>();
			parkedCarMap = new TreeMap<Integer,Car>();
			park_lot_max_size = lot_size;
			for(int i=1;i<=lot_size;i++){
				availableSlots.add(i);
			}
		
		} else {
			System.out.println("Enter valid size for parking lot");
		}
		
	}
	
	
	public void addForParking(String number, String colour) {
		if(availableSlots == null){
			System.out.println("Create Parking lot of perticular size, then try adding car for parking");
		}else if(availableSlots.size() == 0 && parkedCarMap.size() == park_lot_max_size){
			System.out.println("Sorry, parking lot is full");
		}else if(parkedCarMap.size() < park_lot_max_size){
			parkedCarMap.put(availableSlots.first(), new Car(colour,number));
			availableSlots.remove(availableSlots.first());
		}
		
	}

	public void printParkingStatus() {
		if(availableSlots == null || parkedCarMap == null || parkedCarMap.size() == 0){
			System.out.println("Parking is empty");
		}else if(parkedCarMap.size() >0){
			System.out.println("Slot No. \t Registration No \t Colour");
			for(Map.Entry<Integer,Car> entry : parkedCarMap.entrySet()) {
				  int slot = entry.getKey().intValue();
				  Car value = entry.getValue();
				  System.out.println(slot + " \t " + value.getNumber()+ " \t " + value.getColour());
				}
		}
		
	}

	public void removeFromParking(String slot_number) {
		int slot = Integer.parseInt(slot_number);
		if(availableSlots == null || parkedCarMap == null || parkedCarMap.size() == 0 || slot > park_lot_max_size){
			System.out.println("Car not parked");
		} else {
			parkedCarMap.remove(slot);
			availableSlots.add(slot);
			System.out.println("Slot number "+slot +" is free");
		}
		
	}


	public void printSlots() {
		if(availableSlots != null && availableSlots.size() != 0){
			Iterator<Integer> iterator;
			   iterator = availableSlots.iterator();
			   while (iterator.hasNext()){
			   System.out.println("Allocated slot number: "+ iterator.next());
			   }
		}
		
	}

	private boolean isValid(String[] inputArray, int expected_array_size, boolean verifyNumber, int number_position) {
		if(inputArray.length != expected_array_size){
			System.out.println("Invalid input");
			printUsage();
			return false;
		}
		if(verifyNumber){
			try {
			Integer.parseInt(inputArray[number_position]);
			} catch (NumberFormatException e){
				System.out.println("Invalid input: Enter valid number");
				printUsage();
				return false;
			}
		}
		return true;
	}

	public String getSlotNumberForRegistrationNumber(String number) {
		if(availableSlots == null || parkedCarMap == null || parkedCarMap.size() == 0){
			return "Parking is empty";
		}else if(parkedCarMap.size() >0){
			for(Map.Entry<Integer,Car> entry : parkedCarMap.entrySet()) {
				  if(entry.getValue().getNumber().equalsIgnoreCase(number)){
					return entry.getKey().intValue()+"";
				  }
				}
		}
		
		return "Not found";
	}

	public String getSlotNumberForColour(String colour) {
		String car = "";
		if(availableSlots == null || parkedCarMap == null || parkedCarMap.size() == 0){
			return "Parking is empty";
		}else if(parkedCarMap.size() >0){
			for(Map.Entry<Integer,Car> entry : parkedCarMap.entrySet()) {
				  if(entry.getValue().getColour().equalsIgnoreCase(colour)){
					  car = car.isEmpty()?+entry.getKey().intValue()+"":car+","+entry.getKey().intValue();
				  }

				}
		}
		return car.isEmpty()?"Not found":car;
	}

	public String getRegistrationNumbersForColour(String colour) {
		String car = "";
		if(availableSlots == null || parkedCarMap == null || parkedCarMap.size() == 0){
			return "Parking is empty";
		}else if(parkedCarMap.size() >0){
			
			for(Map.Entry<Integer,Car> entry : parkedCarMap.entrySet()) {
				  if(entry.getValue().getColour().equalsIgnoreCase(colour)){
					  car = car.isEmpty()?entry.getValue().getNumber()+"":car+","+entry.getValue().getNumber();
				  }

				}
		}
		
		return car.isEmpty()?"Not found":car;
	}
	
	private static void printUsage() {
		System.out.println("Only below mentioned inputs are valid");
		System.out.println("--------------------------------------");
		System.out.println("1) To create parking lot of size n --> create_parking_lot <n>");
		System.out.println("Ex: create_parking_lot 5");
		System.out.println("2)To park a car --> park <car_number> <car_clour>");
		System.out.println("Ex: park KA­01­HH­1234 White");
		System.out.println("3)To remove car from parking --> leave <slot_number>");
		System.out.println("Ex: leave 4");
		System.out.println("4)To print status of parking slot --> status");
		System.out.println("Ex: status");
		System.out.println("5)To get cars registration number for the given car colour --> registration_numbers_for_cars_with_colour <car_colour>");
		System.out.println("Ex: registration_numbers_for_cars_with_colour White");
		System.out.println("6)To get slot numbers for the given car colour --> slot_numbers_for_cars_with_colour <car_colour>");
		System.out.println("Ex: slot_numbers_for_cars_with_colour White");
		System.out.println("7)To get slot number for the given car number --> slot_number_for_registration_number <car_number>");
		System.out.println("Ex: slot_number_for_registration_number KA­01­HH­1234");
	}

}
