package puzzle.solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import puzzle.board.BoardController;
import puzzle.solver.Solver;
import puzzle.solver.State;
import puzzle.solver.Browser;
public class Browser extends JPanel implements ActionListener {
	static State state;
	static BoardController board;
	static LinkedList<Action> actions;
	static int currentStep;
	JButton prev;
	JButton next;

	public Browser(BoardController board) {
		this.board = board;
		currentStep = 0;

		prev = new JButton("Previous");
		next = new JButton("Next");

		add(prev);
		add(next);

		prev.addActionListener(this);
		next.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(state == null) return;

		boolean forward = ((JButton)e.getSource()).getText() == "Next";

		if(forward && currentStep < actions.size()) {
			state.move(actions.get(currentStep++));
		} else if(!forward && currentStep > 0) {
			state.backMove(actions.get(--currentStep));
		}

		if (currentStep == actions.size()){
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
		}
		
		if (currentStep == 0) {
			prev.setEnabled(false);
		} else {
			prev.setEnabled(true);
		}

		board.setBoard(state.getValues());
	}

	public void setState(State s) {
		state = new State(s);
		actions = new LinkedList<Action>(s.getPath());
		currentStep = 0;

		for(int iii = actions.size() - 1; iii >= 0; iii--) {
			state.backMove(actions.get(iii));
		}
		board.setBoard(state.getValues());

		if (currentStep == actions.size()){
			next.setEnabled(false);
		} else {
			next.setEnabled(true);
		}
		
		if (currentStep == 0) {
			prev.setEnabled(false);
		} else {
			prev.setEnabled(true);
		}
	}
}

