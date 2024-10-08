package javacode;

abstract class Base {
	 final int a = 10;
	abstract void fun();
	abstract void funny();
}
	// Class 2
//class Derived implements Base {
	class Derived extends Base {
	    void fun()
	    {
	        System.out.println("Derived fun() called "+ a);
	    }
	    void funny() {
	    	System.out.println("Derived funny() called");
	    }
	}
	// Class 3
public class AbstractClassExample {
	public static void main(String args[])
    {
        // Uncommenting the following line will cause
        // compiler error as the line tries to create an
        // instance of abstract class. Base b = new Base();
        // We can have references of Base type.
		Base b = new Derived();
        b.fun();
        b.funny();
    }
}
