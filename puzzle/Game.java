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
		mainFrame.setPreferredSize(new Dimension(600,600));
		mainFrame.setLayout(new GridLayout(4, 1));

		final BoardController board = new BoardController(ROW, COL);

		final JLabel solutionLabel = new JLabel("Solution: ");
		Button solveButton = new Button("Solve!");
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				State s = Solver.solve(board);
				String sol = s == null? "": s.toString();
				System.out.println("--" + sol + "|||");
				solutionLabel.setText(sol);
			}
		});

		mainFrame.add(board.getView());
		mainFrame.add(solveButton);
		mainFrame.add(solutionLabel);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
