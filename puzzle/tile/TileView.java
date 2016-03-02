package puzzle.tile;

import java.awt.*;
import javax.swing.*;

public class TileView extends JButton {
	Point position;
	public TileView(Tile model) {
		super(model.getNumber() + "");
		position = new Point(model.getPosition());
	}

	public TileView(Tile model, Icon icon) {
		super(model.getNumber() + "", icon);
		System.out.println("ICONS!!!!" + icon);
		position = new Point(model.getPosition());
	}

	public TileView(TileView tv) {
		super(tv.getText(), tv.getIcon());
		position = new Point(tv.position);
	}

	public void update(Tile model) {
		setText(model.getNumber() + "");
		return;
	}

	public Point getPosition() {
		return position;
	}
}
