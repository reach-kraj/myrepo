package leetcode;

//import leetcode.AddTwoNode.Node;

public class Test {
	
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
	public void add1(int data) {
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
	public void string(Test one) {
		Node temp=one.head;
		String a = "0";
		//String b = a;
		
		while(temp!=null) {
			if(temp!=null) {
				System.out.println("Test "+temp.getData());
			a=temp.getData()+a;
			System.out.println(a);
			temp=temp.getNext();
	}
}
		//System.out.println("Test "+ a);
	}
	public static void main(String[] args) {
		Test one=new Test();
		
		one.add1(1);
		one.add1(2);
		one.add1(3);
		//one.show();
		one.string(one);
		
		
	}
}