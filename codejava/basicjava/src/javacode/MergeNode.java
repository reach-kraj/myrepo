package javacode;

public class MergeNode
{
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

	public void merge(MergeNode List1,MergeNode List2) {
		Node one=List1.head;
		Node two=List2.head;
		
		while(one!=null&&two!=null) {
			if(one.getData()>two.getData()) {

			}else {

			}
		}
	}
	public static void main(String[] args)
	{
		MergeNode List1 = new MergeNode();
		MergeNode List2 = new MergeNode();
		MergeNode List3= new MergeNode();

		List1.add(10);
		List1.add(12);
		List1.add(17);
		List1.add(25);
		
		System.out.println("List 1 :");
		List1.show();

		System.out.println(" ");
		List2.add(5);
		List2.add(11);
		List2.add(22);
		List2.add(26);

		System.out.println("List 2: ");
		List2.show();	
		System.out.println();
		System.out.println("Merged List :");

		Node one=List1.head;
		Node two=List2.head;

		while(one!=null && two!=null) {
			if(one.getData()>two.getData()) {
				List3.add(two.getData());
				two=two.getNext();
			}else {
				List3.add(one.getData());
				one=one.getNext();
			}
		}
		while(one!=null) {
			List3.add(one.getData());
			one=one.getNext();
		}
		while(two!=null) {
			List3.add(two.getData());
			two=two.getNext();
		}
		List3.show();
	}
}

