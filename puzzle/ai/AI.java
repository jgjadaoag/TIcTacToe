package puzzle.ai;

import java.awt.Point;
import java.util.Random;
import java.util.LinkedList;

import puzzle.board.BoardController;
import puzzle.state.State;

public class AI {
	static Point nextMove;
	public static void act(BoardController board) {
		State initialState = new State(board.getSymbols(), 3, 3, board.getAISymbol(), board.getHumanSymbol());
		System.out.println(value(initialState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
		System.out.println(nextMove);
		board.click(nextMove);
	}
	public static int value(State board, int currDepth, int alpha, int beta) {
		
		//if CUTOFF(s, depth) Eval(s);
		int terminal = board.terminal();
		if (terminal != -1) {
			return terminal;
		}
		if (currDepth % 2 == 0) {			//maximizer
			return maxValue(board, currDepth, alpha, beta);
		}
		else if (currDepth % 2 == 1) {		//minimizer
			return minValue(board, currDepth, alpha, beta);
		}
		return 0;
	}
	public static int maxValue(State board, int currDepth, int alpha, int beta) {
		int value = Integer.MIN_VALUE;
		for (State state : board.successors(board.getAISymbol())) {
			int newValue = value(state, currDepth + 1, alpha, beta);
			if (value <= newValue) {
				value = newValue;
				nextMove = state.nextMove(board);
			}
			if (value >= beta) {
				nextMove = state.nextMove(board);
				return value;
			}
			alpha = Math.max(value, alpha);
		}
		return value;
	}
	public static int minValue(State board, int currDepth, int alpha, int beta) {
		int value = Integer.MAX_VALUE;
		for (State state : board.successors(board.getHumanSymbol())) {
			int newValue = value(state, currDepth + 1, alpha, beta);
			value = Math.min(value, newValue);
			if (value <= alpha) return value;
			beta = Math.min(value, beta);
		}
		return value;
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
