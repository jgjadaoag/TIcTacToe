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
	int imageNumber;

	public BoardController(int row, int col, int size, int imageNumber) {
		tileController = new TileController[row][col];
		view = new BoardView(row, col, size);
		model = new Board(row, col, new Point(col - 1, row - 1));

		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				tileController[iii][jjj] = new TileController(this, new Point(jjj, iii),  (1 + jjj + iii * row == row*col)?0:1 + jjj + iii * row, imageNumber);
				view.add(tileController[iii][jjj].getView());
			}
		}
		this.imageNumber = imageNumber;
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

		if(move(a) && goalTest()) {
			int row = model.getRow();
			int col = model.getCol();

			JButton blankButton = (JButton)tileController[row-1][col-1].getView();
			if(imageNumber > 0) {
				blankButton.setIcon(new ImageIcon(imageNumber + "/" + row*col + ".jpg"));
			}
			if(JOptionPane.showConfirmDialog(view, "YOU WIN\nTry again?", "You Win", JOptionPane.YES_NO_OPTION) == 1) {
				System.exit(0);
			}
			if(imageNumber > 0) {
				blankButton.setIcon(new ImageIcon(imageNumber + "/0.jpg"));
			}

			randomize(50);
		}
		
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

	public boolean goalTest() {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				if(tileController[iii][jjj].getValue() != iii * model.getRow() + jjj + 1 &&
						tileController[iii][jjj].getValue() != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public int[][] getValues() {
		int[][] values = new int[model.getRow()][model.getCol()];
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				values[iii][jjj] = tileController[iii][jjj].getValue();
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

	public void changeIcon(String imageNumber) {
		this.imageNumber = Integer.decode(imageNumber);

		for(TileController[] tcs: tileController) {
			for(TileController tc: tcs) {
				tc.changeIcon(this.imageNumber);
			}
		}
	}

	public void setBoard(int[][] values) {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				tileController[iii][jjj].setValue(values[iii][jjj]);
				if(values[iii][jjj] == 0) {
					model.changeBlankPosition(jjj, iii);
				}
			}
		}
	}

	public void randomize(int moves) {
		int[][] board = new int[][]{
			{1,2,0},{4,5,3},{7,8,6}
		};
		State s = new State(board);

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

		setBoard(s.getValues());
	}
}
