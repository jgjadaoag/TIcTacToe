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

	public TileController(TileController tc) {
		model = new Tile(tc.model);
		view = new TileView(tc.view);
	}

	public void swap(TileController other) {
		Tile holder = other.model;
		other.model = model;
		model = holder;

		Point p = new Point(model.getPosition());
		model.setPosition(other.model.getPosition());
		other.model.setPosition(p);

		view.update(model);
		other.getView().update(other.model);
	}

	public TileView getView() {
		return view;
	}

	public Point getPosition() {
		return model.getPosition();
	}
}
