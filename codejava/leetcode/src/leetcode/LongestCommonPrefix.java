package leetcode;
import java.util.Arrays;
public class LongestCommonPrefix {
	
	public void longPrefix(String[] strs) { 
		Arrays.sort(strs);
		
		String s1 = strs[0];
		String s2 = strs[strs.length-1];
		int id = 0;
		while(id < s1.length() && id < s2.length()){
			if(s1.charAt(id) == s2.charAt(id)){
				id++;	
			}else {
				break;
			}
		}
		System.out.println(s1.substring(0, id));	
	}
	public static void main(String[] args) {
		String[] s1= {"flower","flow","flour","floor"};

		LongestCommonPrefix s = new LongestCommonPrefix();
		s.longPrefix(s1);

	}
}
