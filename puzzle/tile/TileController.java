package puzzle.tile;

import java.awt.event.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Point;

public class TileController {
	Tile model;
	TileView view;

	public TileController(ActionListener l, Point p, int tileNumber, int imageNumber) {
		model = new Tile(p, tileNumber);
		if(imageNumber > 0) {
			view = new TileView(model, new ImageIcon(imageNumber + "/" + tileNumber + ".jpg"));
		} else {
			view = new TileView(model);
		}
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

		Icon holderIcon = view.getIcon();
		view.setIcon(other.getView().getIcon());
		other.getView().setIcon(holderIcon);
		view.update(model);
		other.getView().update(other.model);
	}

	public TileView getView() {
		return view;
	}

	public Point getPosition() {
		return model.getPosition();
	}

	public int getValue() {
		return model.getNumber();
	}

	public void changeIcon(int imageNumber) {
		if(imageNumber > 0) {
			view.setText(null);
			view.setIcon(new ImageIcon(imageNumber + "/" + model.getNumber() + ".jpg"));
		} else {
			view.setIcon(null);
			view.setText(model.getNumber() + "");
		}
	}
}
