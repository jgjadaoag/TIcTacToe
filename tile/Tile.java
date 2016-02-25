public class Tile {
	int number;
	String imageLocation;
	
	public Tile(int number) {
		this.number = number;
		imageLocation = "";
	}

	public Tile(Tile copy) {
		number = copy.number;
		imageLocation = copy.imageLocation;
	}

	public int getNumber() {
		return number;
	}

	public String getImageLocation() {
		return imageLocation;
	}
}
