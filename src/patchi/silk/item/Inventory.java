package patchi.silk.item;

import java.util.ArrayList;

public class Inventory {

	private final ArrayList<Item> internalList;
	
	public Inventory() {
		internalList = new ArrayList<>();
	}
	
	public ArrayList<Item> getList() {
		return internalList;
	}
	
	public void addItem(Item I) {
		
		internalList.add(I);
		
	}
	
}
