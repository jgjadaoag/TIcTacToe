package puzzle.tile;

import java.awt.event.*;

import java.awt.Point;

public class TileController {
	Tile model;
	TileView view;

	public TileController(ActionListener l, Point p, int tileNumber) {
		model = new Tile(p, tileNumber);
		view = new TileView(model);
		view.addActionListener(l);
	}

	public void swap(TileController other) {
		Tile holder = other.model;
		other.model = model;
		model = holder;

		Point p = new Point(model.getPosition());
		model.setPosition(other.model.getPosition());
		other.model.setPosition(p);
	}

	public TileView getView() {
		return view;
	}
}
