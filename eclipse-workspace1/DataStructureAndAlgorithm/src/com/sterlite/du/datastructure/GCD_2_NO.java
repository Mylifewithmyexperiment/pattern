package com.sterlite.du.datastructure;

import java.util.Scanner;

public class GCD_2_NO {
    
	public static int m,n;
	
	public static void main(String[] args) {

		System.out.println("Enter first no.");
		Scanner sc = new Scanner(System.in);
		  m = sc.nextInt();
		System.out.println("Enter Second no");
		 n = sc.nextInt();
		 if(m>n)
		 {
			 System.out.println("first no is greater case");
			 gcd(m,n); 
		 }
		 else {
			 System.out.println("second no is greater case");
			 gcd(n,m);
		 }
		sc.close();
	}
	
	public static void gcd( int k,int l) {
		System.out.println(" k amd l are " +k+"  &  " +l);
		if (k % l == 0) {
			System.out.println("GCD of TWO no is" + l);
		}
		else {
			int gcd =1;
			for (int i = 2; i < l; i++) {
				if (k%i == 0 & l%i == 0) {
					gcd=i;
				}
			}
			System.out.println("GCD of two no is  " + gcd);	 
		}
	}
}
