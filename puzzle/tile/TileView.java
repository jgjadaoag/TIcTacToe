package puzzle.tile;

import java.awt.*;
import javax.swing.*;

public class TileView extends JButton {
	Point position;
	public TileView(Tile model) {
		position = new Point(model.getPosition());
	}

	public TileView(TileView tv) {
		super(tv.getText());
		position = new Point(tv.position);
	}

	public void update(Tile model) {
        setText(model.getSymbol() + "");
	}

	public Point getPosition() {
		return position;
	}
}
