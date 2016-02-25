package puzzle;

import javax.swing.*;
import java.awt.*;

import puzzle.board.BoardController;

public class Game {
	public static void main(String[] args) {
		final int ROW = 3;
		final int COL = 3;

		JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(600,600));

		BoardController board = new BoardController(ROW, COL);
		mainFrame.add(board.getView());
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
