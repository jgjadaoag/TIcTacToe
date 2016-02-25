package puzzle.board;

import java.awt.Point;
import java.awt.event.*;

import puzzle.tile.TileController;
import puzzle.tile.TileView;
import puzzle.solver.Action;

public class BoardController implements ActionListener{
	Board model;
	BoardView view;
	TileController[][] tileController;

	public BoardController(int row, int col) {
		tileController = new TileController[row][col];
		view = new BoardView(row, col);
		model = new Board(row, col, new Point(col - 1, row - 1));

		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				tileController[iii][jjj] = new TileController(this, new Point(jjj, iii),  (1 + jjj + iii * row == row*col)?0:1 + jjj + iii * row );
				view.add(tileController[iii][jjj].getView());
			}
		}
	}

	public BoardView getView() {
		return view;
	}

	public void actionPerformed(ActionEvent e) {
		Point p = new Point(((TileView)e.getSource()).getPosition());
		int deltaX = (int)(p.getX() - model.getBlankPosition().getX());
		int deltaY = (int)(p.getY() - model.getBlankPosition().getY());

		if (Math.abs(deltaX) + Math.abs(deltaY) != 1) {
			return;
		}

		Action a;
		if(deltaX == -1 ) {
			a = Action.LEFT;
		} else if (deltaX == 1) {
			a = Action.RIGHT;
		} else if(deltaY == 1 ) {
			a = Action.DOWN;
		} else { //deltaY == -1
			a = Action.UP;
		}

		System.out.println(move(a));
		
	}

	public boolean move(Action a) {
		int x = (int)model.getBlankPosition().getX();
		int y = (int)model.getBlankPosition().getY();

		int row = model.getRow();
		int col = model.getCol();

	
		if(y == 0 && a == Action.UP) {
			return false;
		} else if(y == row - 1 && a == Action.DOWN) {
			return false;
		} else if(x == 0 && a == Action.LEFT) {
			return false;
		} else if(x == col - 1&& a == Action.RIGHT) {
			return false;
		}

		switch(a) {
			case UP:
				tileController[y][x].swap(tileController[y-1][x]);
				y--;
				break;
			case DOWN:
				tileController[y][x].swap(tileController[y+1][x]);
				y++;
				break;
			case RIGHT:
				tileController[y][x].swap(tileController[y][x+1]);
				x++;
				break;
			case LEFT:
				tileController[y][x].swap(tileController[y][x-1]);
				x--;
				break;
		}

		model.changeBlankPosition(x,y);

		return true;
	}
}
