package puzzle.tile;

import java.awt.*;

public class TileView extends Button {
	Point position;
	public TileView(Tile model) {
		super(model.getNumber() + "");
		position = model.getPosition();
	}

	public void update(Tile model) {
		return;
	}

	public Point getPosition() {
		return position;
	}
}
