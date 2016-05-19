package puzzle.state;

import java.awt.Point;
import java.util.LinkedList;

public class State {
	char[][] boardState;
	char aiSymbol;
	char humanSymbol;

	public State(char[][] boardState, int row, int col, char aiSymbol, char humanSymbol) {
		this.boardState = new char[row][col];
		for (int iii = 0; iii < row; iii++) {
			for (int jjj = 0; jjj < col; jjj++) {
				this.boardState[iii][jjj] = boardState[iii][jjj];
			}
		}
		this.aiSymbol = aiSymbol;
		this.humanSymbol = humanSymbol;
	}

	public State(char[][] boardState, int xAction, int yAction, char symbol, char aiSymbol, char humanSymbol) {
		this.boardState = new char[3][3];
		for (int iii = 0; iii < 3; iii++) {
			for (int jjj = 0; jjj < 3; jjj++) {
				this.boardState[iii][jjj] = boardState[iii][jjj];
			}
		}
		this.boardState[xAction][yAction] = symbol;
		this.aiSymbol = aiSymbol;
		this.humanSymbol = humanSymbol;
	}

	public char getAISymbol() {
		return aiSymbol;
	}

	public char getHumanSymbol() {
		return humanSymbol;
	}

	public char[][] getBoardState() {
		return boardState;
	}

	public Point nextMove(State compare) {
		for (int iii = 0; iii < 3; iii++) {
			for (int jjj = 0; jjj < 3; jjj++) {
				if(boardState[iii][jjj] != compare.getBoardState()[iii][jjj]) {
					return new Point(jjj, iii);
				}
			}
		}
		return new Point(-1, -1);
	}

	public void printState() {
		for (int iii = 0; iii < 3; iii++) {
			for (int jjj = 0; jjj < 3; jjj++) {
				if(boardState[iii][jjj]==' ')
					System.out.print("- ");
				else
					System.out.print(boardState[iii][jjj] + " ");
			}
			System.out.println();
		}
	}

	public LinkedList<State> successors(char symbol) {
		LinkedList<State> successors = new LinkedList<State>();
		for (int iii = 0; iii < 3; iii++) {
			for (int jjj = 0; jjj < 3; jjj++) {
				if (boardState[iii][jjj]==' ') {
					State successor = new State(boardState, iii, jjj, symbol, aiSymbol, humanSymbol);
					successors.add(successor);
				}
			}
		}
		return successors;
	}

	public int terminal() {
		for (int iii = 0; iii < 3; iii++) {
			if (boardState[iii][0] == ' ' || boardState[0][iii] == ' ') {
				continue;
			}
			if (boardState[iii][0] == boardState[iii][1] && boardState[iii][1] == boardState[iii][2]) {
				return (boardState[iii][0] == aiSymbol) ? 10 : -10;
			}
			if (boardState[0][iii] == boardState[1][iii] && boardState[1][iii] == boardState[2][iii]) {
				return (boardState[0][iii] == aiSymbol) ? 10 : -10;
			}
		}

		if (boardState[1][1] != ' ') {
			if (boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2] ||
				boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2]) {
				
				return (boardState[1][1] == aiSymbol) ? 10 : -10;
			}
		}

		if (checkTie()) {
			return 0;
		}

		return -1;
	}

	private boolean checkTie() {
		for(int iii = 0; iii < 3; iii++) {
			for(int jjj = 0; jjj < 3; jjj++) {
				if(boardState[iii][jjj] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
}