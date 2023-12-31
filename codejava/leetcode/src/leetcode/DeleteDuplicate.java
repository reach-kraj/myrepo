package leetcode;
//https://leetcode.com/problems/remove-duplicates-from-sorted-list/solutions/4318377/remove-duplicates-from-sorted-linked-list-in-java-beats-100/

public class DeleteDuplicate {

	class Node 
	{
		private int data;
		private Node next;
	//	private Node previous;

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

	public Node deleteDuplicates() {
		if(head == null || head.next == null) {
			return  head;
		}

		Node temp = head;
		Node curr = head.next;

		while(curr != null) {
			if(temp.getData() == curr.getData()) {
				temp.next = curr.next;
				curr = curr.next;
			} else {
				temp = curr;
				curr = curr.next;
			}
		}
		return head;
	}

	public static void main(String[] args) {

		DeleteDuplicate l1 =new DeleteDuplicate();

		l1.add(50);
		l1.add(30);
		l1.add(40);
		l1.add(40);
		l1.add(70);
		l1.add(90);

		System.out.println("Before Deleting Duplicate: ");
		l1.show();
		System.out.println(" ");
		System.out.println("After Deleting Duplicate: ");
		l1.deleteDuplicates();
		l1.show();

	}

}
