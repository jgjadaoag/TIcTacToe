package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import puzzle.board.BoardController;
import puzzle.solver.Solver;
import puzzle.solver.State;

public class Game {
	public static void main(String[] args) {
		final int ROW = 3;
		final int COL = 3;

		JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(600,720));
		//mainFrame.setLayout(new GridLayout(4, 1));
		mainFrame.setLayout(new FlowLayout());

		final BoardController board = new BoardController(ROW, COL);

		JLabel solutionLabel = new JLabel("Solution: ");
		final JLabel solutionText = new JLabel();
		Button solveButton = new Button("Solve!");
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				State s = Solver.solve(board);
				String sol = s == null? "": s.toString();
				solutionText.setText(sol);
			}
		});

		board.getView().setPreferredSize(new Dimension(550, 550));
		solveButton.setPreferredSize(new Dimension(550, 50));
		solutionLabel.setPreferredSize(new Dimension(100, 50));
		solutionText.setPreferredSize(new Dimension(450, 50));
		mainFrame.add(board.getView());
		mainFrame.add(solveButton);
		mainFrame.add(solutionLabel);
		mainFrame.add(solutionText);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
