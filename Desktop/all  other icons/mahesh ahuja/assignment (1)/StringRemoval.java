package com.assignment;

import java.util.ArrayList;
import java.util.Arrays;

// author Akash Ojha
public class StringRemoval {

	public static void main(String[] args) {
		ArrayList<String> as =new ArrayList<>(Arrays.asList("Delhi","Bangalore"));
		as.add("Lucknow");
		as.add("Bhopal");
		as.add("Pune");
		System.out.println("Initial List:");
		System.out.println(as);
		
		for(int i=as.size()-1;i>=0;i--) {
			if(as.get(i).toLowerCase().charAt(0) =='b') {
				as.remove(i);
			}			
		}
		System.out.println("Final List:");
		System.out.println(as);
		
	}// end of main

}
