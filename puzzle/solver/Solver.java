package puzzle.solver;

import java.util.Random;

public class Solver {
	public static void main(String[] args) {
		int[][] board = new int[][]{
			{1,2,0},{4,5,3},{7,8,6}
		};
		State s = new State(board);
		System.out.println("Manhattan :" + s.computeDistance());
	}

	public static void generateState(State s, int moves) {
		Random r =  new Random();
		for(int iii = 0; iii < moves; iii++) {
			switch(r.nextInt(4)) {
				case 0:
					s.move(Action.UP);
					break;
				case 1:
					s.move(Action.RIGHT);
					break;
				case 2:
					s.move(Action.LEFT);
					break;
				case 3:
					s.move(Action.DOWN);
			}
		}
	}
}
