package oppConcept;

public class Car {
	
	private Engine engine;
	private String name;
	private int noOfTyres;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNoOfTyres() {
		return noOfTyres;
	}

	public void setNoOfTyres(int noOfTyres) {
		this.noOfTyres = noOfTyres;
	}

	public void carDetails(String name,int noOfTyre,String engineType,String engineCc,String enginePower) {
		this.engine=new Engine();
		this.name=name;
		this.noOfTyres=noOfTyre;
		engine.setEngineType(engineType);
		engine.setEngineCc(engineCc);
		engine.setEnginePower(enginePower);
		
		System.out.println("Make: "+name);
		System.out.println("No of Tyre: "+noOfTyre);
		System.out.println("Engine Type: "+engineType);
		System.out.println("Engine Cc: "+engineCc);
		System.out.println("Engine Power: "+enginePower);
	}
	
	public static void main(String[] args) {
		Car c1=new Car();
		c1.carDetails("BMW",4,"v8","800cc","10hp");
	}
}
