package com.assignment;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

// author Akash Ojha
public class PascalTriangle {

	public static void main(String[] args)throws IOException {
		Scanner sc=new Scanner(System.in);
		System.out.println("enter number of rows :");
		int numrows=sc.nextInt();
		BigInteger number;
		ArrayList<BigInteger> as ;
		for(int line=1;line<=numrows;line++) {
			number=BigInteger.ONE;
			as=new ArrayList<>();
			for(int j=1;j<=line;j++) {
				as.add(number);
				number=number.multiply(BigInteger.valueOf(line-j)).divide(BigInteger.valueOf(j));
			}
			System.out.println(as);
		}// outer for
		sc.close();
	}//end of main
	
}
