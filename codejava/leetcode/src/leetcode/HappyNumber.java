package leetcode;
//https://leetcode.com/problems/happy-number/solutions/3767573/easy-java-solution-two-pointers-floyd-s-tortoise-and-hare-detailed-explanation/
public class HappyNumber {
	public boolean isHappy(int n) {

		int slow = n;
		int fast = n;
		do {
			slow = square(slow);
			fast = square(square(fast));
		} while (slow != fast);
		return slow == 1;
	}

	public int square(int num) {

		int ans = 0;

		while(num > 0) {
			int remainder = num % 10;
			ans += remainder * remainder;
			num /= 10;
		}

		return ans;
	}

	public static void main(String[] args) {
		HappyNumber h1=new HappyNumber();
		System.out.println(h1.isHappy(30));
	}

}
