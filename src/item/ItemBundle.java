package item;

public class ItemBundle {

	private final Item parentItem;
	private int quantity;
	
	public ItemBundle(Item parent, int quantity) {
		parentItem = parent;
		this.setQuantity(quantity);
	}

	public Item getParentItem() {
		return parentItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		return parentItem.getName();
	}

}
