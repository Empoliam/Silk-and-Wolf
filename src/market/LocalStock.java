package market;

/**
 * Determines the properties of a given commodity within a settlement. Changes to these values may occur
 * instantly if exchanges occur at a market, or otherwise when information propogates.
 */
public class LocalStock {

	/** The parent stock from which local properties are derived. */
	@SuppressWarnings("unused")
	private final GlobalStock parentStock;

	/** The local value of the stock. */
	private double localValue;
	
	/**
	 * Instantiates a new local stock.
	 *
	 * @param parentStock the parent stock
	 */
	public LocalStock(GlobalStock parentStock) {

		this.parentStock = parentStock;

	}

	/**
	 * Gets the local value.
	 *
	 * @return the local value
	 */
	public double getLocalValue() {
		return localValue;
	}

	/**
	 * Sets the local value.
	 *
	 * @param localValue the new local value
	 */
	public void setLocalValue(double localValue) {
		this.localValue = localValue;
	}

}
