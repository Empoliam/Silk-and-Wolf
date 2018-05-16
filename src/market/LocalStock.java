package market;

public class LocalStock {

	@SuppressWarnings("unused")
	private final GlobalStock parentStock;

	public LocalStock(GlobalStock parentStock) {

		this.parentStock = parentStock;

	}

}
