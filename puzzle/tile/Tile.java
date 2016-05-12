package puzzle.tile;

import java.awt.Point;

public class Tile {
    char symbol;
	Point position;
	
	public Tile(Point p) {
		this.symbol = ' ';
		this.position = new Point(p);
	}

	public Tile(Tile copy) {
		symbol = copy.symbol;
		position = new Point(copy.position);
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public Point getPosition() {
		return position;
	}
}
