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
		model = new Board(row, col, new Point(row - 1, col - 1));

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
		int deltaX = p.getX() - board.getBlankPosition().getX();
		int deltaY = p.getY() - board.getBlankPosition().getY();

		if (Math.abs(deltaX) + Math.abs(deltaY) != 1) {
			return;
		}

		Action a;
		if(deltaX == -1 ) {
			a = LEFT;
		} else if (deltaX == 1) {
			a = RIGHT;
		} else if(deltaY == -1 ) {
			a = DOWN;
		} else (deltaY == 1) {
			a = UP;
		}

		move(a);
		
	}

	public boolean move(Action a) {
		int x = (int)board.getBlankPosition().getX();
		int y = (int)board.getBlankPosition().getY();

		int row = board.getRow();
		int col = board.getCol();

	
		if(y == 0 && a == UP) {
			return false;
		}

		if(y == row && a == DOWN) {
			return false;
		}

		if(x == 0 && a == LEFT) {
			return false;
		}

		if(x == col && a == RIGHT) {
			return false;
		}

		switch(a) {
			case UP:
				tiles[y][x].swap(tiles[y-1][x]);
				y--;
				break;
			case DOWN:
				tiles[y][x].swap(tiles[y+1][x]);
				y++;
				break;
			case RIGHT:
				tiles[y][x].swap(tiles[y][x+1]);
				x++;
				break;
			case LEFT:
				tiles[y][x].swap(tiles[y][x-1]);
				x--;
				break;
		}

		board.changeBlankPosition(x,y);

		return true;
	}
}
