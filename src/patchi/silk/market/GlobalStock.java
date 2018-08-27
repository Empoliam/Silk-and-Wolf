package patchi.silk.market;

/**
 * Tracks universal data for a commodity, such as global availability. 
 * Contains information used to determine the price of a given object in any particular place. 
 * Object value is further subject to local demand, as detailed by town-specific LocalStocks.
 * There are no changes to the properties within a GlobalStock. Changes only occur locally, influenced by
 * the spread of information between people and towns.
 */
public class GlobalStock {

	/** 
	 * Increments with instantiation of a new stock. 
	 * New stocks automatically recieve a unique ID as determined by the ID tracker.
	 */
	private static int idTracker = 0;
	
	/**  Stock ID. */
	private int id;

	/** Base global value */
	private final int baseValue;
	
	/** Stock name */
	private final String name;
	
	/**
	 * Instantiates a new GlobalStock.
	 *
	 * @param values the values
	 */
	public GlobalStock(String values) {

		setId(idTracker);
		idTracker++;
		
		String[] v = values.split(",");
		
		name = v[0];
		baseValue = Integer.parseInt(v[1]);
		
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the basic global value of the stock
	 * 
	 * @return stock baseValue
	 */
	public int getBaseValue() {
		return baseValue;
	}

	/**
	 * Gets the value id tracker.
	 *
	 * @return the value of the stock id tracker
	 */
	public static int getIdTracker() {
		return idTracker;
	}

	/**
	 * Returns the name of the stock
	 * 
	 * @return Stock name
	 */
	public String getName() {
		return name;
	}

}
