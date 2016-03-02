package puzzle.board;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

public class BoardView extends JPanel{
	private int buttons;
	private int maxButtons;
	private int tileSize;

	public BoardView(int row, int col, int size) {
		buttons = 0;
		maxButtons = row * col;
		setLayout(new GridLayout(row, col));
		tileSize = size;
	}

	public Component add(Component comp) {
		if(buttons < maxButtons) {
			if(comp instanceof JButton) {
				buttons++;
				comp.setSize(new Dimension(tileSize, tileSize));
				comp.setBackground(Color.WHITE);
				return super.add(comp);
			}
		}
		return null;
	}
}
