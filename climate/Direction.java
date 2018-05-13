
public enum Direction {

	 NW(-1, -1), N(0, -1), NE(1, -1), E(1, 0), SE(1, 1), S(0, 1), SW(-1, 1), W(-1, 0);
	
	int dX, dY;
	
	
	Direction(int dX, int dY) {
		this.dX = dX;
		this.dY = dY;
	}
	
	public int getdX() {
		return dX;
	}

	public void setdX(int dX) {
		this.dX = dX;
	}

	public int getdY() {
		return dY;
	}

	public void setdY(int dY) {
		this.dY = dY;
	}

}
