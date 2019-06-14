package com.assignment;


import java.util.ArrayList;
import java.util.Collections;

//author Akash Ojha

class Person{
	
	String name;
	int age;
	int id;
	Person(String name,int age,int id){
		this.name=name;
		this.age=age;
		this.id=id;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", id=" + id + "]";
	}
	
	
}

public class PersonSorting {

	public static void main(String[] args) {
		PersonSorting ps=new PersonSorting();
		ArrayList<Person> ls=new ArrayList<>();
		ls=ps.populateList();
		System.out.println("Current List:");
		ps.printList(ls);
		ls=ps.sortById(ls, "desc"); // sortByName,sortByAge,sortById should be called here
		System.out.println("After Sorting:");
		ps.printList(ls);
	}//end of main
	
	// populate the initial list
	public ArrayList<Person> populateList(){
		ArrayList<Person> ls=new ArrayList<>();
		Person p1=new Person("Shawn Mendes",27,11);
		Person p2=new Person("Ed Sheeren",31,12);
		Person p3=new Person("The Weeknd",26,16);
		Person p4=new Person("Luis Fonsi",29,14);
		Person p5=new Person("Justin Bieber",24,15);
		ls.add(p1);
		ls.add(p2);
		ls.add(p3);
		ls.add(p4);
		ls.add(p5);
		return ls;
	}
	// sorting on basis of name using lambdas and comparator
	public ArrayList<Person> sortByName(ArrayList<Person> ls,String sortType ){
		if(sortType.equals("asc")) {
			Collections.sort(ls, (Person p1,Person p2)->(p1.name.compareTo(p2.name)) );
		}
		else if(sortType.equals("desc")) {
			Collections.sort(ls, (Person p1,Person p2)->(p2.name.compareTo(p1.name)) );
		}
		else {
			System.out.println("Wrong value entered");
		}
		return ls;
	}
	
	// sorting on basis of age using lambdas and comparator
		public ArrayList<Person> sortByAge(ArrayList<Person> ls,String sortType ){
			if(sortType.equals("asc")) {
				Collections.sort(ls, (Person p1,Person p2)->(p1.age-p2.age) );
			}
			else if(sortType.equals("desc")) {
				Collections.sort(ls, (Person p1,Person p2)->(p2.age-p1.age) );
			}
			else {
				System.out.println("Wrong value entered");
			}
			return ls;
		}
	
	// sorting on basis of ID using lambdas and comparator
		public ArrayList<Person> sortById(ArrayList<Person> ls,String sortType ){
			if(sortType.equals("asc")) {
				Collections.sort(ls, (Person p1,Person p2)->(p1.id-p2.id) );
			}
			else if(sortType.equals("desc")) {
				Collections.sort(ls, (Person p1,Person p2)->(p2.id-p1.id) );
			}
			else {
				System.out.println("Wrong value entered");
			}
			return ls;
		}
		
	
	public void printList(ArrayList<Person> list) {
		
		for(Person p:list) {
			System.out.println(p);
		}
	}
	
}
