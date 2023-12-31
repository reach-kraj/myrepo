package leetcode;

//https://leetcode.com/problems/count-complete-tree-nodes/solutions/2815747/java-solution-easy-4-liner-code/

public class CountCompleteTreeNode {
	private class Tree {

		private int data;
		private Tree right;
		private Tree left;

		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
	}

	Tree rootNode;

	public void addTree(int data) {
		rootNode=addTreeRec(rootNode, data);
	}
	public Tree addTreeRec(Tree rootNode,int data) {
		if(rootNode==null) {
			rootNode = new Tree();
			rootNode.setData(data);
		}
		else if(data<rootNode.getData()) {
			rootNode.left=addTreeRec(rootNode.left, data);
		}else if(data>rootNode.getData()){
			rootNode.right=addTreeRec(rootNode.right, data);
		}

		return rootNode;
	}

	public void showDepth() {
		showDepthRec(rootNode);
	}
	public void showDepthRec(Tree rootNode) {
		if (rootNode!=null) {
			System.out.print(rootNode.getData()+" ");
			showDepthRec(rootNode.left);
			showDepthRec(rootNode.right);
		}
	}
	int count=0;
	public void count() {
		countNodeRec(rootNode);
		System.out.println(count);
	}
	public int countNodeRec(Tree rootNode) {

		if(rootNode==null) {
			//  System.out.println("Tree is empty");
			return 0;
		}else {  
			count++;
			countNodeRec(rootNode.left);
			countNodeRec(rootNode.right);
		}
		//System.out.println(count);
		return count;    
	}
	public static void main(String[] args) {
		CountCompleteTreeNode t1 = new CountCompleteTreeNode();
		//CountCompleteTreeNode t2 = new CountCompleteTreeNode();

		t1.addTree(10);
		t1.addTree(5);
		t1.addTree(12);
		t1.addTree(11);
		t1.addTree(7);
		t1.addTree(15);
		t1.addTree(6);

		t1.count();
	}
}
