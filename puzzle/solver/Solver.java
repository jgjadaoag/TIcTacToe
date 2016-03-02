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
			State bestNode = openList.poll();
			openList.remove(bestNode);
			closedList.add(bestNode);

			if (bestNode.goalTest()) {
				return bestNode;
			}
			for(Action a: bestNode.actions()) {
				State result = bestNode.result(a);
				if(result == null) {
					continue;
				}

				int resultDistance = result.getDistance();
				boolean closedContains = closedList.contains(result);
				boolean openContains = openList.contains(result);
				if((!openContains && !closedContains)) {
					openList.add(result);
				} else if (openContains) {
					for(State s: openList) {
						if(s.equals(result)) {
							if(resultDistance < s.getDistance()) {
								openList.remove(s);
								openList.add(result);
							}
							break;
						}
					}
				} else if (closedContains) {
					for(State s: closedList) {
						if(s.equals(result)) {
							if(resultDistance < s.getDistance()) {
								closedList.remove(s);
								openList.add(result);
							}
							break;
						}
					}
				}

			}
		}
		return null;
	}
}
