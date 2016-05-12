package puzzle.board;

import java.awt.Point;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import puzzle.tile.TileController;
import puzzle.tile.TileView;

import puzzle.ai.AI;

public class BoardController implements ActionListener{
	Board model;
	BoardView view;
	TileController[][] tileController;
	boolean isPlayer1; //assumes human

	public BoardController(int row, int col, int size) {
		tileController = new TileController[row][col];
		view = new BoardView(row, col, size);
		model = new Board(row, col);

		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				tileController[iii][jjj] = new TileController(this, new Point(jjj, iii));
				view.add(tileController[iii][jjj].getView());
			}
		}

		isPlayer1 = true;

		nextMove();
	}

	public BoardController(int row, int col, int size, int imageNumber, boolean enabled) {
		this(row, col, size);
		for(TileController[] tcs: tileController) {
			for(TileController tc: tcs) {
				tc.getView().setEnabled(false);
			}
		}
	}

	public BoardView getView() {
		return view;
	}

	public void setTile(Point p) {
		TileController tc = tileController[(int)p.getY()][(int)p.getX()];
		if (isPlayer1) {
			tc.setSymbol(model.getP1());
		} else {
			tc.setSymbol(model.getP2());
		}
		isPlayer1 = !isPlayer1;
	}

	public void changeSymbol(char p1, char p2) {
		char temp;
		for (TileController[] tcs: tileController) {
			for (TileController tc: tcs) {
				temp = tc.getSymbol();
				if (temp != ' ') {
					if (temp == model.getP1()) {
						tc.changeSymbol(p1);
					} else {
						tc.changeSymbol(p2);
					}
				}
			}
		}

		model.changeSymbol(p1, p2);
	}

	public void nextMove() {
		if (isPlayer1) {
			view.setEnabled(true);
		} else {
			view.setEnabled(false);
			AI.act(this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Point p = new Point(((TileView)e.getSource()).getPosition());
		setTile(p);

		char goal = goalTest((int)p.getY(), (int)p.getX());
		String message = null;

		if(goal != ' ') {
			if(goal == model.getP1()) {
				message = "Player 1 WINS\nTry again?";
			}
			else if(goal == model.getP2()) {
				message = "AI WINS\nTry again?";
			}
			else {
				message = "DRAW\nTry again?";
			}
			
			if(JOptionPane.showConfirmDialog(view, message, message, JOptionPane.YES_NO_OPTION) == 1) {
				System.exit(0);
			}

			restart();
		}

		nextMove();
		
	}

	public char goalTest(int row, int col) {
		char winner = tileController[row][col].getSymbol();
		
		if(winner == tileController[row][0].getSymbol() &&
					tileController[row][0].getSymbol() == tileController[row][1].getSymbol() &&
					tileController[row][1].getSymbol() == tileController[row][2].getSymbol()) {
			return winner;
		}
		else if(winner == tileController[0][col].getSymbol() &&
					tileController[0][col].getSymbol() == tileController[1][col].getSymbol() &&
					tileController[1][col].getSymbol() == tileController[2][col].getSymbol()) {
			return winner;				
		}
		else if(winner == tileController[1][1].getSymbol() &&
					(row == col || row + col == model.getRow() - 1)) {
			if(tileController[0][0].getSymbol() == tileController[1][1].getSymbol() &&
					tileController[1][1].getSymbol() == tileController[2][2].getSymbol()) {
				return winner;
			}
			else if(tileController[2][0].getSymbol() == tileController[1][1].getSymbol() &&
					tileController[1][1].getSymbol() == tileController[0][2].getSymbol()) {
				return winner;
			}
		}
		
		if(checkTie()) {
			return '+';
		}
		
		return ' ';
	}
	
	private boolean checkTie() {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getRow(); jjj++) {
				if(tileController[iii][jjj].getSymbol() == ' ') {
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

	public char getAISymbol() {
		return model.getP2();
	}

	public char getHumanSymbol() {
		return model.getP1();
	}

	public void setBoard(char[][] values) {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				tileController[iii][jjj].reset();
				tileController[iii][jjj].setSymbol(values[iii][jjj]);
			}
		}
	}

	public void restart() {
		for(int iii = 0; iii < model.getRow(); iii++) {
			for(int jjj = 0; jjj < model.getCol(); jjj++) {
				tileController[iii][jjj].reset();
			}
		}
	}
}
