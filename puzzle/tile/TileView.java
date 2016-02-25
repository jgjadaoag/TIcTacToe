package puzzle.tile;

import java.awt.*;

public class TileView extends Button {
	Point position;
	public TileView(Tile model) {
		super(model.getNumber() + "");
		position = new Point(model.getPosition());
	}

	public TileView(TileView tv) {
		super(tv.getLabel());
		position = new Point(tv.position);
	}

	public void update(Tile model) {
		setLabel(model.getNumber() + "");
		return;
	}

	public Point getPosition() {
		return position;
	}
}
