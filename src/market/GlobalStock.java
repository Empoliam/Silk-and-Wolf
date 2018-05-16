package market;

/**
 * Tracks universal data for a commodity, such as global availability. Contains information used to determine the price of a given object in any particular place. Object value is further subject to local demand, as detailed by town-specific LocalStocks.
 */
public class GlobalStock {

	/** 
	 * Increments with instantiation of a new stock. 
	 * New stocks automatically recieve a unique ID as determined by the ID tracker.
	 */
	private static int idTracker = 0;

	/**  Stock ID. */
	private int id;

	/**
	 * Instantiates a new GlobalStock.
	 */
	public GlobalStock() {

		setId(idTracker);
		idTracker++;

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

}
