package leetcode;

public class SudokoSolver {

	public void solution(int[][] board) {
		for(int i=0; i<9;i++) {
			for(int j=0;j<9;j++) {
				if(board[i][j]==0) {
					for(int num=1;num<=9;num++) {
						boolean test= isValid(board,i,j,num);
						if(test) {
							board[i][j]=num;	
						}
					}
				}
			}
		}
	}

	public boolean isValid(int[][] board, int r, int c,int num){
		for(int i=0; i<9; i++){
			if(board[i][c] == num) {
				return false; 
			}
			if(board[r][i] == num) {
				return false; 
			}
		}
		return true;
	}
	
	public void print(int[][] board) {
		for(int i=0;i<9;i++) {
			System.out.print(board[i][0]+" ");	
			System.out.print(board[i][1]+" ");	
			System.out.print(board[i][2]+" ");	
			System.out.print(board[i][3]+" ");	
			System.out.print(board[i][4]+" ");	
			System.out.print(board[i][5]+" ");	
			System.out.print(board[i][6]+" ");	
			System.out.print(board[i][7]+" ");	
			System.out.print(board[i][8]+" ");	
			System.out.println(" ");	
		}
	}
	public static void main(String[] args) {
		int[][] board = 
			{{5,3,0,0,7,0,0,0,0}
			,{6,0,0,1,9,5,0,0,0}
			,{0,9,8,0,0,0,0,6,0}
			,{8,0,0,0,6,0,0,0,3}
			,{4,0,0,8,0,3,0,0,1}
			,{7,0,0,0,2,0,0,0,6}
			,{0,6,0,0,0,0,2,8,0}
			,{0,0,0,4,1,9,0,0,5}
			,{0,0,0,0,8,0,0,7,9}};

		SudokoSolver s1=new SudokoSolver();
		s1.solution(board);
		s1.print(board);

	}
}
