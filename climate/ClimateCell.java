
public class ClimateCell {

	private int x, y;
	
	
	//Cloud
	private double cloudSize = 0.0;
	
	//wind
	private Direction windDirection = Direction.E;
	private double windDecayMult = 0.75;
	
	public ClimateCell(int x,int y) {
		
		this.x = x;
		this.y = y;
		this.windDirection = Direction.E;
		
	}
	
	protected ClimateCell(ClimateCell copy) {
		this.x = copy.getX();
		this.y = copy.getY();
		this.cloudSize = copy.getCloudSize();
		this.windDirection = copy.getWindDirection();
		this.windDecayMult = copy.getWindDecayMult();
	}
	
	
	public void addCloud(double in, double mult) {
		setCloudSize(getCloudSize() + mult * in);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getCloudSize() {
		return cloudSize;
	}

	public void setCloudSize(double cloudSize) {
		this.cloudSize = cloudSize;
	}

	public Direction getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Direction windDirection) {
		this.windDirection = windDirection;
	}

	public double getWindDecayMult() {
		return windDecayMult;
	}

	public void setWindDecayMult(double windDecayMult) {
		this.windDecayMult = windDecayMult;
	}
}
