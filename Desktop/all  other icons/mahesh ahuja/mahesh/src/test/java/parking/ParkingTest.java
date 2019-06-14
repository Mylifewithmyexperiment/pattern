package parking;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gojek.parking.Parking;
import com.gojek.parking.ParkingLot;

import junit.framework.TestCase;

public class ParkingTest extends TestCase {

	  
	   
		   // test method to add two values
	   public void testCreateParkingSlot(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("test", parking);
	       assertTrue(parking.getAvailableSlots() == null);
	       parking.parseInput("create_parking_lot", parking);
	       assertTrue(parking.getAvailableSlots() == null);
	       parking.parseInput("create_parking_lot test", parking);
	       assertTrue(parking.getAvailableSlots() == null);
	       parking.parseInput("create_parking_lot 6", parking);
	       assertTrue(parking.getAvailableSlots().size() == 6);
	   }
	   
	   public void testAddParkingSlot(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("create_parking_lot 6", parking);
	       parking.parseInput("park 6", parking);
	       assertTrue(parking.getAvailableSlots().size()== 6);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       assertTrue(parking.getAvailableSlots().size()== 5);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("park TN-53-EB-7714 Black", parking);
	       parking.parseInput("park DL-03-EB-7865 red", parking);
	       parking.parseInput("park MH-53-EB-7899 white", parking);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       assertTrue(parking.getAvailableSlots().size()== 0);
	   }
	   
	   public void testRemoveFromParking(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("create_parking_lot 6", parking);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("park TN-53-EB-7714 Black", parking);
	       parking.parseInput("park DL-03-EB-7865 red", parking);
	       parking.parseInput("park MH-53-EB-7899 white", parking);
	       parking.parseInput("park SA-53-EB-3714 red", parking);
	       parking.parseInput("park NA-53-EB-3714 red", parking);
	       assertTrue(parking.getAvailableSlots().size()== 0);
	       parking.parseInput("leave", parking);
	       assertTrue(parking.getAvailableSlots().size()== 0);
	       parking.parseInput("leave 4", parking);
	       assertTrue(parking.getAvailableSlots().size()== 1);
	       assertFalse(parking.getParkedCarMap().containsKey(4));
	       parking.parseInput("leave 1", parking);
	       assertTrue(parking.getAvailableSlots().size()== 2);
	       assertFalse(parking.getParkedCarMap().containsKey(1));
	       assertTrue(parking.getAvailableSlots().first()==1);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("leave 2", parking);
	       assertTrue(parking.getAvailableSlots().first()==2);
	   }
	   
	   public void testSearchByColour(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("create_parking_lot 6", parking);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("park TN-53-EB-7714 Black", parking);
	       parking.parseInput("park DL-03-EB-7865 red", parking);
	       parking.parseInput("park MH-53-EB-7899 white", parking);
	       parking.parseInput("park SA-53-EB-3714 red", parking);
	       parking.parseInput("park NA-53-EB-3714 red", parking);
	       assertTrue(parking.getRegistrationNumbersForColour("red").equals("KA-53-EB-3714,DL-03-EB-7865,SA-53-EB-3714,NA-53-EB-3714"));
	       assertTrue(parking.getRegistrationNumbersForColour("Black").equals("TN-53-EB-7714"));
	       assertTrue(parking.getRegistrationNumbersForColour("Green").equals("Not found"));
	   }
	   
	   public void testslotSearchByColour(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("create_parking_lot 6", parking);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("park TN-53-EB-7714 Black", parking);
	       parking.parseInput("park DL-03-EB-7865 red", parking);
	       parking.parseInput("park MH-53-EB-7899 white", parking);
	       parking.parseInput("park SA-53-EB-3714 red", parking);
	       parking.parseInput("park NA-53-EB-3714 red", parking);
	       assertTrue(parking.getSlotNumberForColour("red").equals("1,3,5,6"));
	       assertTrue(parking.getSlotNumberForColour("Black").equals("2"));
	       assertTrue(parking.getSlotNumberForColour("Blue").equals("Not found"));
	   }
	   
	   public void testslotSearchByNumber(){
		   Parking parking = new ParkingLot();
	       parking.parseInput("create_parking_lot 6", parking);
	       parking.parseInput("park KA-53-EB-3714 red", parking);
	       parking.parseInput("park TN-53-EB-7714 Black", parking);
	       parking.parseInput("park DL-03-EB-7865 red", parking);
	       parking.parseInput("park MH-53-EB-7899 white", parking);
	       parking.parseInput("park SA-53-EB-3714 red", parking);
	       parking.parseInput("park NA-53-EB-3714 red", parking);
	       assertTrue(parking.getSlotNumberForRegistrationNumber("KA-53-EB-3714").equals("1"));
	       assertTrue(parking.getSlotNumberForRegistrationNumber("DL-03-EB-7865").equals("3"));
	       assertTrue(parking.getSlotNumberForRegistrationNumber("Unknown").equals("Not found"));
	   }
}
