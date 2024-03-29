package predefinedjavaclass;

//import java.util.ArrayList; another way to use ArrayList class by importing

public class ArrayListConcept {

	public static void main(String[] args) {
		java.util.ArrayList<String> cars = new java.util.ArrayList<String>();//types of declaring ArrayList
		//ArrayList<String> cars = new ArrayList<String>();
		cars.add("Volvo");
	    cars.add("BMW");
	    cars.add("Ford");
	    cars.add("Mazda");//add();
	    cars.remove(2);//remove();
	    java.util.Iterator<String> it = cars.iterator();//Iterator();
	    while(it.hasNext()) {//hasNext() -> to check the list contain next element in the ArrayList
	    System.out.println("Example of Iterator method: "+it.next());
	    }
	    System.out.println("To check Particular String Present inside List: "+cars.contains("BMW"));
	    System.out.println("To check arraylist is empty: "+cars.isEmpty());//isempty
	    System.out.println("To print Size of List: "+cars.size());//size();
	    System.out.println("Original List: "+cars);
	    cars.clear();
	    System.out.println("After Clearing list: "+cars);
	}
}
