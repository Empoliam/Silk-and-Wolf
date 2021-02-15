package patchi.silk.item;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public Iterator<Item> getIterator() {
		return internalList.iterator();
	}
	
}
