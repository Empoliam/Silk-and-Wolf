package patchi.silk.item;

public class ItemStack {

	final private Item item;

	private int quantity;
	private float stackVolume;
	private float stackWeight;

	ItemStack(Item I) {

		item = I;
		quantity = 1;

		calculateWeight();
		calculateVolume();
		
	}
	
	private void calculateWeight() {
		stackWeight =  quantity * item.getWeight();
	}
	
	private void calculateVolume() {
		stackVolume = quantity * item.getVolume();
	}
	
	public float getStackWeight() {
		return stackWeight;
	}
	
	public float getStackVolume() {
		return stackVolume;
	}
	
	public float getQuantity() {
		return quantity;
	}
	
	public void addQuantity(int i) {
		setQuantity(quantity + i);
	}
	
	public void setQuantity(int i) {
		quantity = i;
		
		calculateWeight();
		calculateVolume();
			
	}

	public boolean itemCompare(Item I) {
		
		return(I.equals(item));
		
	}
	
	public String getStackName() {
		return quantity + " " + item.getName();
	}
	
}
