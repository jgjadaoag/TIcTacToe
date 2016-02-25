package puzzle.board;

import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class BoardView extends JPanel{
	private int buttons;
	private int maxButtons;

	public BoardView(int row, int col) {
		buttons = 0;
		maxButtons = row * col;
		setLayout(new GridLayout(row, col));
	}

	public Component add(Component comp) {
		if(buttons < maxButtons) {
			if(comp instanceof Button) {
				buttons++;
				return super.add(comp);
			}
		}
		return null;
	}
}
