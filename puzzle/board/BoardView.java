package puzzle.board;

import java.awt.Button;
import java.awt.Component;
import javax.swing.JPanel;

public class BoardView extends JPanel{
	Button[][] tiles;
	private boolean initiated;

	public BoardView(Board board, Button[][] tiles) {
		int row = board.getRow();
		int col = board.getCol();

		this.tiles = new Button[row][col];
		for(int iii = 0; iii < row; iii++) {
			for(int jjj = 0; jjj < col; jjj++) {
				this.tiles[row][col] = tiles[row][col];
			}
		}

		initiated = true;
	}

	public BoardView(int row, int col) {
		initiated = false;
		this.tiles = new Button[row][col];
	}

	public Component add(Component comp) {
		if(!initiated) {
			if(comp instanceof Button) {
				return super.add(comp);
			}
		}
		return null;
	}
}
