package puzzle.solver;

import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

import puzzle.board.BoardController;

public class Solver {
	public static State solve(BoardController board) {
		PriorityQueue<State> openList = new PriorityQueue<State>(11, new Comparator<State>() {
			public int compare(State s1, State s2) {
				return s1.getF() - s2.getF();
			}
		});
		PriorityQueue<State> closedList = new PriorityQueue<State>(openList);
		openList.add(new State(board.getRow(), board.getCol(), board.getSymbols()));


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

				int resultDistance = result.getF();
				boolean closedContains = closedList.contains(result);
				boolean openContains = openList.contains(result);
				if((!openContains && !closedContains)) {
					openList.add(result);
				} else if (openContains) {
					for(State s: openList) {
						if(s.equals(result)) {
							if(resultDistance < s.getF()) {
								openList.remove(s);
								openList.add(result);
							}
							break;
						}
					}
				} else if (closedContains) {
					for(State s: closedList) {
						if(s.equals(result)) {
							if(resultDistance < s.getF()) {
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
