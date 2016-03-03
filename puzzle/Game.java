package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import puzzle.board.BoardController;
import puzzle.solver.Solver;
import puzzle.solver.State;
import puzzle.solver.Browser;

public class Game {
	public static void main(String[] args) {
		final int ROW = 3;
		final int COL = 3;
		final int TILE_SIZE = 200;
		final int IMAGE_NUMBER = 4;

		final JFrame solutionFrame = new JFrame("Solution");
		solutionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		solutionFrame.setPreferredSize(new Dimension(650, 700));
		solutionFrame.setLayout(new FlowLayout());

		final BoardController solutionBoard = new BoardController(ROW, COL, TILE_SIZE, 0, false);

		final Browser prevNextButton = new Browser(solutionBoard);
		prevNextButton.setPreferredSize(new Dimension(600, 50));

		solutionBoard.getView().setPreferredSize(new Dimension(600,600));
		solutionFrame.add(solutionBoard.getView());
		solutionFrame.add(prevNextButton);
		solutionFrame.pack();

		JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(650,780));
		mainFrame.setLayout(new FlowLayout());

		final BoardController board = new BoardController(ROW, COL, TILE_SIZE, 0);
		board.randomize(20);

		JButton[] imageSelect = new JButton[IMAGE_NUMBER+1];
		Dimension imageSelectDimension = new Dimension(600/(IMAGE_NUMBER + 1), 30);
		for(int iii = 0; iii <= IMAGE_NUMBER; iii++) {
			imageSelect[iii] = new JButton("" + iii);
			imageSelect[iii].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					board.changeIcon(((JButton)e.getSource()).getText());
					solutionBoard.changeIcon(((JButton)e.getSource()).getText());
				}
			});
			imageSelect[iii].setPreferredSize(imageSelectDimension);
			mainFrame.add(imageSelect[iii]);
		}

		JLabel solutionLabel = new JLabel("Solution: ");
		final JLabel solutionText = new JLabel();
		JButton solveButton = new JButton("Solve!");
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				State s = Solver.solve(board);
				String sol = s == null? "": s.toString();
				solutionText.setText(sol);
				prevNextButton.setState(s);
				solutionFrame.setVisible(true);
			}
		});

		board.getView().setPreferredSize(new Dimension(600, 600));
		solveButton.setPreferredSize(new Dimension(600, 50));
		solutionLabel.setPreferredSize(new Dimension(100, 50));
		solutionText.setPreferredSize(new Dimension(500, 50));
		mainFrame.add(board.getView());
		mainFrame.add(solveButton);
		mainFrame.add(solutionLabel);
		mainFrame.add(solutionText);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
