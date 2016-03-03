package puzzle.tile;

import java.awt.Point;

public class Tile {
	int number;
	Point position;
	
	public Tile(Point p, int number) {
		this.number = number;
		this.position = new Point(p);
	}

	public Tile(Tile copy) {
		number = copy.number;
		position = new Point(copy.position);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setPosition(Point p) {
		position.setLocation(p);
	}

	public Point getPosition() {
		return position;
	}
}
