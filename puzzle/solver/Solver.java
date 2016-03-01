package puzzle.solver;

import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

import puzzle.board.BoardController;

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

	public static State solve(BoardController board) {
		PriorityQueue<State> openList = new PriorityQueue<State>(11, new Comparator<State>() {
			public int compare(State s1, State s2) {
				return s1.getDistance() - s2.getDistance();
			}
		});
		PriorityQueue<State> closedList = new PriorityQueue<State>(openList);
		openList.add(new State(board.getRow(), board.getCol(), board.getValues()));


		while(!openList.isEmpty()) {
			System.out.println( "============== " + openList.size());
			State bestNode = openList.poll();
			openList.remove(bestNode);
			closedList.add(bestNode);

			if (bestNode.goalTest()) {
				System.out.println("BEST NODE");
				return bestNode;
			}
			System.out.println("Nyaa ");
			for(Action a: bestNode.actions()) {
				State result = bestNode.result(a);
				if(result == null) {
					continue;
				}
				System.out.println("0------------");
				System.out.println(result.toString2());

				int resultDistance = result.getDistance();
				boolean closedContains = closedList.contains(result);
				boolean openContains = openList.contains(result);
				if((!openContains && !closedContains)) {
					System.out.println("1------------");
					System.out.println(result.toString2());
					System.out.println(openList.add(result));
				} else if (openContains) {
					System.out.println("Open Contains!");
					for(State s: openList) {
						if(s.equals(result)) {
							System.out.println(s.toString2());
							if(resultDistance < s.getDistance()) {
								System.out.println("2------------");
								System.out.println(result.toString2());
								openList.remove(s);
								System.out.println(openList.add(result));
							}
							break;
						}
					}
				} else if (closedContains) {
					System.out.println("Closed Contains!");
					for(State s: closedList) {
						if(s.equals(result)) {
							System.out.println(s.toString2());
							if(resultDistance < s.getDistance()) {
								System.out.println("3------------");
								System.out.println(result.toString2());
								closedList.remove(s);
								System.out.println(openList.add(result));
							}
							break;
						}
					}
				}

			}
		}
		System.out.println("End SOlution");
		System.out.println("Closed List");
		for(State s: closedList) {
			System.out.println("------------------");
			System.out.println(s.toString2());
		}
		System.out.println("Open List");
		for(State s: openList) {
			System.out.println("------------------");
			System.out.println(s.toString2());
		}
		return null;
	}
}
