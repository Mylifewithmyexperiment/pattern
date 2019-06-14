package com.assignment;
// author Akash Ojha


import java.util.TreeSet;

/*class Person{
	
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
	
	
}*/

public class PersonWithoutDuplicates {

	public static void main(String[] args) {
		PersonWithoutDuplicates pwd=new PersonWithoutDuplicates();
		TreeSet<Person> ls=new TreeSet<>();
		ls=pwd.populateList();
		System.out.println("Current List:");
		pwd.printList(ls);
		
	}// end of main
	
	// populate the initial TreeSet using lambda 
	public TreeSet<Person> populateList(){
		TreeSet<Person> ls=new TreeSet<>((Person p1,Person p2) ->(p1.age-p2.age));
		Person p1=new Person("Shawn Mendes",27,11);
		Person p2=new Person("Ed Sheeren",31,12);
		Person p3=new Person("The Weeknd",26,16);
		Person p4=new Person("Luis Fonsi",29,14);
		Person p5=new Person("Justin Bieber",24,15);
		Person p6=new Person("The Weeknd",26,16);
		ls.add(p1);
		ls.add(p2);
		ls.add(p3);
		ls.add(p4);
		ls.add(p5);
		ls.add(p6);
		return ls;
	}
	
	public void printList(TreeSet<Person> list) {
		
		for(Person p:list) {
			System.out.println(p);
		}
	}

}
