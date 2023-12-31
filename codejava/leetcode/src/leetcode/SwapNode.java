package leetcode;

//https://leetcode.com/problems/swap-nodes-in-pairs/

public class SwapNode {
	class Node 
	{
		private int data;
		private Node next;

		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
	}
	Node head;

	public void add(int data) {
		Node container = new Node();
		container.setData(data);
		if (head == null) {
			head = container;
		} else {
			Node tempNode = head;
			while (tempNode.getNext()!= null) {
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(container);
		}
	}

	public void show() {
		Node container = head;
		if(container==null) {
			System.out.println("list is empty");
		}else {
			while (container.getNext() != null) {
				System.out.print(container.getData()+" ");
				container = container.getNext();
			}
			System.out.print(container.getData()+" ");
		}
	}
	public void swap() {
		swapPairs(head);
	}
		
		public Node swapPairs(Node h1) {		
		  Node prev=null;
	        Node curr=h1;
	        Node last=null;
	         int count=0;
	      
	        while(curr!=null && count<2){
	        	last=curr.next;
	            curr.next=prev;
	            prev=curr;
	            curr=last;
	            count++;
	        }
	        if(last!=null)
	        	h1.next=swapPairs(last);
	        return prev;
	    }

	public static void main(String[] args) {
		SwapNode l1=new SwapNode();

		l1.add(50);
		l1.add(30);
		l1.add(40);
		l1.add(70);

		l1.swap();
		l1.show();
	}
}
