package patchi.silk.item;

import java.util.ArrayList;

public class Inventory {

	private final ArrayList<Item> internalList;
	
	public Inventory() {
		internalList = new ArrayList<>();
	}
	
	public ArrayList<Item> getAll(){
		return internalList;
	}
	
	public <T> ArrayList<Item> getAllBundlesOfType(int t) {
		
		ArrayList<Item> sublist = new ArrayList<>();
		
		for(Item I : internalList) {
			if(I.getType() == t) sublist.add(I);
		}
		
		return sublist;
		
	}

}
