package puzzle.board;

import java.util.Random;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import puzzle.tile.TileController;
import puzzle.tile.TileView;
import puzzle.solver.Action;
import puzzle.solver.State;

public class BoardController implements ActionListener{
	Board model;
	BoardView view;
	TileController[][] tileController;

	public BoardController(int row, int col, int size, int imageNumber) {
		tileController = new TileController[row][col];
		view = new BoardView(row, col, size);
		model = new Board(row, col);

		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				tileController[iii][jjj] = new TileController(this, new Point(jjj, iii));
				view.add(tileController[iii][jjj].getView());
			}
		}
	}

	public BoardController(int row, int col, int size, int imageNumber, boolean enabled) {
		this(row, col, size, imageNumber);
		for(TileController[] tcs: tileController) {
			for(TileController tc: tcs) {
				tc.getView().setEnabled(false);
			}
		}
	}

	public BoardView getView() {
		return view;
	}

	public void actionPerformed(ActionEvent e) {
		Point p = new Point(((TileView)e.getSource()).getPosition());

		if(goalTest()) {
			int row = model.getRow();
			int col = model.getCol();

			if(JOptionPane.showConfirmDialog(view, "YOU WIN\nTry again?", "You Win", JOptionPane.YES_NO_OPTION) == 1) {
				System.exit(0);
			}

            restart();
		}
		
	}

	public boolean goalTest() {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				if(tileController[iii][jjj].getSymbol() != iii * model.getRow() + jjj + 1 &&
						tileController[iii][jjj].getSymbol() != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public char[][] getSymbols() {
		char[][] values = new char[model.getRow()][model.getCol()];
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				values[iii][jjj] = tileController[iii][jjj].getSymbol();
			}
		}

		return values;
	}

	public int getRow() {
		return model.getRow();
	}

	public int getCol() {
		return model.getCol();
	}

	public void setBoard(char[][] values) {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				tileController[iii][jjj].setSymbol(values[iii][jjj]);
			}
		}
	}

    public void restart() {
        setBoard(new char[][]{
			{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}
		});
    }
}
