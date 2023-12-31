package leetcode;
//https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/description/

public class BinaryLinkedList {
	class Node{
		int data;
		Node next;
	}
	Node head;
	public void add(int data) {
		Node container = new Node();
		container.data=data;
		if (head == null) {
			head = container;
		} else {
			Node tempNode = head;
			while (tempNode.next!= null) {
				tempNode = tempNode.next;
			}
			tempNode.next=container;
		}
	}
	
	  public void decimal() {
	        int base = 0;
	        while (head != null) {
	        		base*=2;
	        		base+=head.data;
		            head = head.next;
	        }	        
	        System.out.println(base);
	    }
	public static void main(String[] args) {
		BinaryLinkedList l1=new BinaryLinkedList();
		l1.add(1);
		l1.add(1);
		l1.add(0);
		l1.add(1);
		
		l1.decimal();
	}
}
