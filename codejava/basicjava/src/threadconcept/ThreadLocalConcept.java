package threadconcept;
class IrctcLoginLocal{
	public void loginDetails(String thread,String name,String name1) {
		System.out.println(thread+" Please Enter UserName: "+name);
		System.out.println(thread+" Please Enter Password: "+name1);
	}
	public void selectTrain(String thread,String src,String dst) {
		System.out.println(thread+" Logging in");
		System.out.println(thread+" Enter Your Source: "+src);
		System.out.println(thread+" Enter Your Destination Station: "+dst);
		System.out.println(thread+" Kindly Select Train from the List");
	}
	public  void checkAvailability(String thread) {
		System.out.println(thread+" Select No of Seat");
		System.out.println(thread+" Enter Passanger Details");
	}
	public  void paymentGateway(String thread,String payment) {
		System.out.println(thread+" UPI, 2.NetBanking, 3.DebitCard,4.CreditCard: "+payment);
	}
	public void transcation(String thread) {
		System.out.println(thread+" Payment Sucessfull");
	}
	public void pnrGeneration(String thread,String name) {
		System.out.println(thread+" Pnr for: "+name);
	}
}
class NewThread extends Thread {
	IrctcLoginLocal l1;
	private static int question = 15;
	  private static ThreadLocal<Object> gfg = new ThreadLocal<Object>() {
	        protected Object initialValue()
	        {
	        	question--;
	            return question;
	        }
	    };	
	NewThread(String name,IrctcLoginLocal l1)
	{
		super(name);
		this.l1=l1;
		start();
	}

	public void run()
	{
		for (int i = 0; i < 2; i++) {
		System.out.println(getName() + " " + gfg.get());
		}
		String thread=getName();
		String username="user1";
		String password="1q2w3e4r5t6y";
		String src="Villupuram";
		String dst="Egmore";
		String payment="UPI";
		l1.loginDetails(thread,username,password);
		try { 
			Thread.sleep(3000);
		} catch (InterruptedException e) { 
			e.printStackTrace(); 
		}
		l1.selectTrain(thread,src,dst);
		l1.checkAvailability(thread);
		l1.paymentGateway(thread,payment);
		l1.transcation(thread);
		l1.pnrGeneration(thread,username);
	}
}
public class ThreadLocalConcept {

	public static void main(String[] args) {
		IrctcLoginLocal obj = new IrctcLoginLocal();
		NewThread t1 = new NewThread("Thread1",obj);
		NewThread t2 = new NewThread("Thread2",obj);
	}
}
