package puzzle.tile;

import java.awt.event.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Point;

public class TileController {
	Tile model;
	TileView view;

	public TileController(ActionListener l, Point p) {
		model = new Tile(p);
        view = new TileView(model);
		view.addActionListener(l);
	}

	public TileView getView() {
		return view;
	}

	public Point getPosition() {
		return model.getPosition();
	}

	public char getSymbol() {
		return model.getSymbol();
	}

	public void setSymbol(char symbol) {
		model.setSymbol(symbol);
		view.update(model);
		view.setEnabled(false);
	}

	public void reset() {
		model.setSymbol(' ');
		view.update(model);
		view.setEnabled(true);
	}
}
