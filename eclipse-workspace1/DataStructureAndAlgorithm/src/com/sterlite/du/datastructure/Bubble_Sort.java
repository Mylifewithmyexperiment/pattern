package com.sterlite.du.datastructure;

import java.util.Arrays;

public class Bubble_Sort {

	public static void main(String[] args) {
	 int arr[] = new int[] {2,5,1,3,4,5};
	 int brr[]= new int [1];
	  
	 for (int i = 0; i < arr.length-1; i++) {
		
		 for (int j = i+1; j < arr.length-1; j++) {
			
			 if(arr[i]>arr[j])
			 { 
				 brr[0]=arr[j];
				 arr[j]=arr[i];
    			 arr[i]=brr[0];
			 }
			  System.out.print( " "+ arr[i]+" " );
			 
		}
		 
	}
		
		
	}
	
	
}
