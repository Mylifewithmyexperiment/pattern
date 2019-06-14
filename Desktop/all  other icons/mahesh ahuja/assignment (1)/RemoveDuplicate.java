package com.assignment;

import java.util.HashSet;
import java.util.Set;

// author- Akash Ojha

class Node{
     int data;
	 Node next;
	
	Node(int data){
		this.data=data;
		next=null;
	}	
}


public class RemoveDuplicate {

	public static void main(String[] args) {
		RemoveDuplicate rd=new RemoveDuplicate();
		Node head=null;
		head= rd.populateList(head);
		System.out.println("Current List :");
		rd.printList(head);
		head=rd.removeDuplicates(head);
		System.out.println("New List :");
		rd.printList(head);
		
		
	}// end of main
	
	//method to create a dummy list
	public Node populateList(Node head) {
		Node temp=new Node(5);
		head=temp;
		temp.next=new Node(19);
		temp=temp.next;
		temp.next=new Node(14);
		temp=temp.next;
		temp.next=new Node(8);
		temp=temp.next;
		temp.next=new Node(14);		
		temp=temp.next;
		temp.next=new Node(19);
		temp=temp.next;
		temp.next=new Node(21);
		return head;
	}
	
	public void printList(Node head) {
		while(head!=null) {
			System.out.println(head.data);
			head=head.next;
		}
		
	}
	// time complexity O(n) and space complexity O(n)
	public Node removeDuplicates(Node head) {
		Set<Integer> st=new HashSet<>();
		Node prev=null,temp=head;
		while(temp!=null) {
			if(st.contains(temp.data)) {
				prev.next=temp.next;
			}
			else {
				st.add(temp.data);
				prev=temp;
			}
			temp=temp.next;
		}
		
		return head;
	}
	
}
