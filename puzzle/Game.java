package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import puzzle.board.BoardController;

public class Game {
	public static void main(String[] args) {
		final int ROW = 3;
		final int COL = 3;
		final int TILE_SIZE = 200;


		JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(650,780));
		mainFrame.setLayout(new FlowLayout());

		final BoardController board = new BoardController(ROW, COL, TILE_SIZE, 0);


		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.restart();
			}
		});

		board.getView().setPreferredSize(new Dimension(600, 600));
		reset.setPreferredSize(new Dimension(100, 50));
		mainFrame.add(board.getView());
		mainFrame.add(reset);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
