package puzzle.solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Point;

public class State {
	private int[][] board;
	int cost;
	int distance;
	int row;
	int col;
	LinkedList<Action> parent;
	Point space;
	
	public State(int row, int col, int[][] board) {
		this.board = new int[row][col];
		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				if((this.board[iii][jjj] = board[iii][jjj]) == 0) {
					space = new Point(jjj, iii);
				}
			}
		}
		cost = 0;
		this.row = row;
		this.col = col;
		this.parent = new LinkedList<Action>();
		distance = computeDistance();
	}

	public State(int[][] board) {
		this(3, 3, board);
	}

	public State(State s) {
		this.board = new int[s.row][s.col];
		this.row = s.row;
		this.col = s.col;
		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				if((this.board[iii][jjj] = s.board[iii][jjj]) == 0) {
					space = new Point(jjj, iii);
				}
			}
		}
		cost = s.cost;
		distance = s.distance;
		parent = new LinkedList<Action>(s.parent);
	}

	public LinkedList<Action> actions() {
		LinkedList<Action> actionList = new LinkedList<Action>();
		int x = (int)space.getX();
		int y = (int)space.getY();

		if(y != 0) {
			actionList.add(Action.UP);
		}

		if(y != row - 1) {
			actionList.add(Action.DOWN);
		}

		if(x != 0) {
			actionList.add(Action.LEFT);
		}

		if(x != col - 1) {
			actionList.add(Action.RIGHT);
		}

		return actionList;
	}

	public State result(Action a) {
		State s = new State(this);
		if(s.move(a)) {
			return s;
		}
		return null;
	}

	public int computeDistance() {
		int tempDistance = 0;
		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				if(board[iii][jjj] != 0) {
					tempDistance += Math.abs((board[iii][jjj]-1)/col - iii) + Math.abs((board[iii][jjj]-1)%col - jjj);
				}
			}
		}

		return tempDistance;
	}
	
	public boolean move(Action a) {
		int x = (int)space.getX();
		int y = (int)space.getY();

		if(y == 0 && a == Action.UP) {
			return false;
		} else if(y == row - 1 && a == Action.DOWN) {
			return false;
		} else if(x == 0 && a == Action.LEFT) {
			return false;
		} else if(x == col - 1 && a == Action.RIGHT) {
			return false;
		}

		switch(a) {
			case UP:
				board[y][x] = board[y-1][x];
				board[y-1][x] = 0;
				y--;
				break;
			case DOWN:
				board[y][x] = board[y+1][x];
				board[y+1][x] = 0;
				y++;
				break;
			case RIGHT:
				board[y][x] = board[y][x+1];
				board[y][x+1] = 0;
				x++;
				break;
			case LEFT:
				board[y][x] = board[y][x-1];
				board[y][x-1] = 0;
				x--;
				break;
		}

		space.setLocation(x, y);
		cost++;
		distance = computeDistance();
		parent.add(a);

		return true;
	}

	public boolean goalTest() {
		return distance == 0;
	}

	public int getDistance() {
		return distance;
	}

	public String toString() {
		String s = "";
		for(Action a: parent) {
			s += a.toString();
		}
		return s;
	}

	public boolean equals(Object st) {
		if(!(st instanceof State)) {
			return false;
		}
		State s = (State)st;
		for(int iii = 0; iii < row; iii++) {
			for (int jjj = 0; jjj < col; jjj++) {
				if(board[iii][jjj] != s.board[iii][jjj]) {
					return false;
				}
			}
		}
		return true;
	}

	public int hashCode() {
		return parent.size() * 31;
	}

	public String toString2() {
		String s = "";
		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				s += board[iii][jjj] + " ";
			}
			s+= "\n";
		}

		return s;
	}

}
