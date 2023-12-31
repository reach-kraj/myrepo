package leetcode;


public class RemoveNthNode {
	class Node 
	{
		private int data;
		private Node next;
		private Node previous;

		public Node getPrevious() {
			return previous;
		}
		public void setPrevious(Node previous) {
			this.previous = previous;
		}
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

	public void deleteNth(int n) 
	{

		Node temp=head.next;
		Node nxt=temp.getNext();
		Node pre=head;
		int i=1,j=n-1;
		if(n==1) {
			System.out.println("Deleted: "+pre.getData()+" from the list");
			pre=head.getNext();
			head=pre;
		}else {
			while((temp!=null)&&(i<j)) {
				pre=temp;
				temp=temp.getNext();
				nxt=temp.getNext();
				i++;
			}if(nxt!=null) {
				pre.setNext(nxt);
				temp=null;
			}else {
				pre.next=null;
			}
		}
	}
	public static void main(String[] args) {
		RemoveNthNode n1=new RemoveNthNode();

		int n=5;

		n1.add(10);
		n1.add(20);
		n1.add(30);
		n1.add(40);
		n1.add(50);
		n1.add(60);

		n1.show();

		n1.deleteNth(n);
		System.out.println();

		n1.show();
	}

}
