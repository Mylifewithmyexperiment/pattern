package com.assignment;
import java.util.Vector;

// author Akash Ojha
public class AddNumber {

	public static void main(String[] args) {
		AddNumber add=new AddNumber();
		Vector<Integer> v=new Vector<>();
		v.add(9);
		v.add(9);
		v.add(9);
		v.add(9);
		v=add.addDigit(v);	
		System.out.println(v);
	}// end of main
	
	//adding 1 to vector number
	//time complexity O(N) and Space Complexity O(1)
	Vector<Integer> addDigit(Vector<Integer> v){
		int carry=0,len,temp;
		len=v.size();
		temp=v.get(len-1)+1;
		carry=temp/10;
		v.set(len-1,temp%10);
		for(int j=len-2;j>=0;j--) {
			if(carry==0) break;
			else {
				temp=v.get(j)+1;
				carry=temp/10;
				v.set(j,temp%10);
			}
		}
		if(carry==1) {
			v.add(0, 1);
		}
		return v;
	}

}
