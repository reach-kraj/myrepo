package leetcode;


public class SortLinkedList {
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
	public void sorted() {
		Node rootNode=head;
		sort(rootNode);
		show();
	}

	public Node sort(Node n1) {
		if(n1==null||n1.next==null){
			return n1;
		}
		Node temp=n1;
		Node slow=n1;
		Node fast=n1;

		while(fast!=null&&fast.next!=null){
			temp=slow;
			slow=slow.next;
			fast=fast.next.next;
		}
		temp.next=null;

		Node l1 = sort(head);
		Node l2 = sort(slow);

		return mergeTwoLists(l1,l2);
	}

	public Node mergeTwoLists(Node l1, Node l2){

		Node temp= new Node();

		while (l1 != null && l2 != null) {
			if (l1.getData() < l2.getData()) {
				
				temp.next = l1;
				l1 = l1.next;
			} else {
				
				temp.next = l2;

				l2 = l2.next;
			}
			temp = temp.next;
		}

		if (l1 != null) {
			temp.next = l1;
		}
		if (l2 != null) {
			temp.next = l2;
		}

		return temp.next;
	}

	public static void main(String[] args) {

		SortLinkedList l1 =new SortLinkedList();

		l1.add(50);
		l1.add(30);
		l1.add(40);
		l1.add(70);
		l1.add(90);

		l1.sorted();
		l1.show();
	}
}
