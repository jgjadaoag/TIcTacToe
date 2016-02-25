package puzzle.board;

import java.awt.Point;
import java.awt.event.*;

import puzzle.tile.TileController;

public class BoardController implements ActionListener{
	Board model;
	BoardView view;
	TileController[][] tileController;

	public BoardController(int row, int col) {
		tileController = new TileController[row][col];
		view = new BoardView(row, col);
		model = new Board(row, col, new Point(row - 1, col - 1));

		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				tileController[iii][jjj] = new TileController(this, new Point(jjj, iii), jjj + iii * row);
				view.add(tileController[iii][jjj].getView());
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
	}
}
