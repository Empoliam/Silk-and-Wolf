package patchi.silk.item;

/**
 * Item held by an entity.
 */
public class Item {

	private final ItemDef itemDef;
	private double quantity;
	
	Item(String itemID, double quantity) {
		
		this.itemDef = ItemDef.getByID(itemID);
		this.quantity = quantity;
		
	}
	
	public String getName() {
		return itemDef.getName();
	}
	
	public void setQuantity(double q) {
		quantity = q;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
}
