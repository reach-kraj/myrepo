package predefinedjavaclass;

public class HashmapConcept {

	public static void main(String[] args) {
		java.util.HashMap<String,String> cars=new java.util.HashMap<String,String>();
		cars.put("Honda", "V4");
		cars.put("Tesla", "0");
		cars.put("Tata", "V4");
		cars.put("Suzuki", "V8");
		cars.remove("Tesla");
		for (String i : cars.keySet()) {
			  System.out.println("HashMap values: "+i);
			}
		System.out.println(cars);
		
	}
}