package puzzle.board;

import java.awt.Point;

public class Board {
	int row;
	int col;
	char p1;
	char p2;

	public Board(int row, int col) {
		this.row = row;
		this.col = col;
		this.p1 = 'X';
		this.p2 = 'O';
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getP1() {
		return p1;
	}

	public char getP2() {
		return p2;
	}

	public void changeSymbol(char p1, char p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
}

