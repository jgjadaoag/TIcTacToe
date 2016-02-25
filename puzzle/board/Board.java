package puzzle.board;

import java.awt.Point;

public class Board {
	Point blankPosition;
	int row;
	int col;

	public Board(int row, int col, Point positon) {
		this.row = row;
		this.col = col;
		this.blankPosition = positon;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Point getBlankPosition() {
		return blankPosition;
	}

	public void changeBlankPosition(int x, int y) {
		blankPosition.setLocation(x, y);
	}
}

