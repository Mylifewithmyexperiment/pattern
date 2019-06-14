package com.gojek.parking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParkingMain {

	public static void main(String args[]){
	//	String input = "create_parking_lot 4";
		ParkingLot parking = new ParkingLot();

	/*	parking.parseInput(input,parking);
		input = "park KA-01:CJ-1957 Red";
		parking.parseInput(input,parking);
		input = "park MH-01:CJ-1958 Blue";
		parking.parseInput(input,parking);
		input = "park KL-01:CJ-1957 White";
		parking.parseInput(input,parking);
		input = "park UP-01:CJ-1957 Red";
		parking.parseInput(input,parking);
		parking.parseInput("status", parking);
		parking.parseInput("leave 3",parking);
		parking.parseInput("leave 2",parking);
		parking.parseInput("status", parking);
		input = "park GJ-01:CJ-1957 Red";
		parking.parseInput(input,parking);
		input = "park TN-01:CJ-1957 Black";
		parking.parseInput(input,parking);
		parking.parseInput("status", parking);
		parking.parseInput("registration_numbers_for_cars_with_colour Red",parking);
		parking.parseInput("slot_numbers_for_cars_with_colour Red",parking);
		*/
		
		 
	        switch (args.length) {
	            case 0:
	                System.out.println("Please enter 'exit' to quit");
	                System.out.println("Waiting for input...");
	                // Interactive command-line input/output
	                // Run an infinite loop
	                for (;;) {
	                    try {
	                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	                        String inputString = bufferRead.readLine();
	                        if (inputString.equalsIgnoreCase("exit")) {
	                            break;
	                        } else if ((inputString == null) || (inputString.isEmpty())) {
	                            // Do nothing
	                        } else {
	                        	parking.parseInput(inputString.trim(),parking);
	                        }
	                    } catch(IOException e) {
	                        System.out.println("Oops! Error in reading the input from console.");
	                        e.printStackTrace();
	                    }
	                }
	                break;
	            case 1:
	                // File input/output
	            	FileInputStream fis;
	        		BufferedReader br;
	        		try {
	        			fis = new FileInputStream(args[0]);
	        			//Construct BufferedReader from InputStreamReader
	        			 br = new BufferedReader(new InputStreamReader(fis));
	        		 
	        			String line = null;
	        			while ((line = br.readLine()) != null) {
	        				System.out.println(line);
	        				parking.parseInput(line,parking);
	        			}
	        			br.close();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                break;
	            default:
	                System.out.println("Invalid input. Usage: java -jar <jar_file_path> <input_file_path>");
	        }

		
	}

}
