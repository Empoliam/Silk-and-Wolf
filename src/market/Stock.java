package market;

public class Stock {

	private final int id;
	private String name;
	private float value;
	private float demand;
	private float demand_mult;

	public Stock(String[] i) {

		id = Integer.parseInt(i[0]);
		name = i[1];
		value = Float.parseFloat(i[2]);
		demand = Float.parseFloat(i[3]);
		demand_mult = Float.parseFloat(i[4]);
	}

	public void buy(int count) {

		demand += count*demand_mult;
	}

	public void sell(int count) {

		demand -= count*demand_mult;
	}

	public float getValue() {

		return value;
	}

	public String getName() {

		return name;
	}

	public float getDemand() {

		return demand;
	}

}
