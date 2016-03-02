package puzzle.tile;

import java.awt.*;
import javax.swing.*;

public class TileView extends JButton {
	Point position;
	boolean usingIcon;
	public TileView(Tile model) {
		super(model.getNumber() + "");
		position = new Point(model.getPosition());
		usingIcon = false;
	}

	public TileView(Tile model, Icon icon) {
		super("", icon);
		position = new Point(model.getPosition());
		usingIcon = true;
	}

	public TileView(TileView tv) {
		super(tv.getText(), tv.getIcon());
		position = new Point(tv.position);
	}

	public void update(Tile model) {
		if(!usingIcon) {
			setText(model.getNumber() + "");
		}
		return;
	}

	public Point getPosition() {
		return position;
	}
}
