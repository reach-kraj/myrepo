package leetcode;

import java.util.LinkedList;

public class AddTwoNodes{

	Node head;
	
	private class Node{
		private int data;
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		private Node next;
	}
	
	public void add(int data) {
		Node container = new Node();
		container.setData(data);
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
	
	public void addNodes(LinkedList a,LinkedList b) {
		LinkedList<Integer> l3=new LinkedList<Integer>();
		int sum=0;
		while(a!=null || b!=null) {
			if(a!=null) {
				sum+=a.data;
				a=a.next;
				
			}
		}
	}
	
	public static void main(String[] args) {
		AddTwoNodes l1=new AddTwoNodes();
		AddTwoNodes l2=new AddTwoNodes();
		
		
		
		//System.out.println(l1);
		//System.out.println(l2);
		
		
		
		
	}

}
