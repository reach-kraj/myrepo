package threadconcept;

import java.util.concurrent.ConcurrentSkipListMap;

class ccslm{
	
	ConcurrentSkipListMap<Integer,String> slm =new ConcurrentSkipListMap<>();

	public ConcurrentSkipListMap<Integer, String> ccslminput(int key, String value) {
		slm.put(key, value);
		return slm;
	}

	public void print() {
		System.out.println(slm);
	}
	public void getlist(int i) {

		System.out.println(slm.get(i));
	}
}

class trd1 extends Thread{
	private ccslm l1;

	trd1(ccslm l1){
		this.l1=l1;
	}
	public void run() {
		l1.ccslminput(3,"Test3");
	}
}
class trd2 extends Thread{

	private ccslm l1;

	trd2(ccslm l1){
		this.l1=l1;
	}
	public void run() {
		l1.ccslminput(2,"Test2");
	}
}
class trd3 extends Thread{

	private ccslm l1;

	trd3(ccslm l1){
		this.l1=l1;
	}
	public void run() {
		l1.ccslminput(1,"Test1");
	}
}

class trd4 extends Thread{
	private ccslm l1;

	trd4(ccslm l1){
		this.l1=l1;
	}
	public void run() {
		for(int i=1;i<4;i++) {
			l1.getlist(i);
		}
	}
}