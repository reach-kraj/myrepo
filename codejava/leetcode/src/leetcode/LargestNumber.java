package leetcode;

public class LargestNumber {
	public void largeNum(int[] nums) {
//		String[] s = new String[nums.length];         
		for(int i=0; i<nums.length; i++) {
			
		}
	}
	public static void main(String[] args) {
		LargestNumber l1=new LargestNumber();
		int[] nums = {3,30,34,5,9};
		
		l1.largeNum(nums);
	}
}
//s[i] = String.valueOf(nums[i]);
//Arrays.sort(s, (a,b) -> (b + a).compareTo(a + b));
//return s[0].equals("0") ? "0" : String.join("",s);