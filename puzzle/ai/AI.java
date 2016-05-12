package puzzle.ai;

import java.awt.Point;
import java.util.Random;
import java.util.LinkedList;

import puzzle.board.BoardController;

public class AI {
	public static void act(BoardController board) {
		Point p = nextMove(board.getSymbols(), board.getAISymbol(), board.getHumanSymbol());
		if (p != null) board.click(p);
	}
	public static Point nextMove(char[][] board, char me, char opponent) {
		LinkedList<Point> freeTiles = new LinkedList<Point>();
		for (int iii = 0; iii < board.length; iii++) {
			for (int jjj = 0; jjj < board[iii].length; jjj++) {
				if(board[iii][jjj] == ' ') {
					freeTiles.add(new Point(jjj, iii));
				}
			}
		}
		return freeTiles.size() > 0? freeTiles.get((new Random()).nextInt(freeTiles.size())): null;
	}
}
