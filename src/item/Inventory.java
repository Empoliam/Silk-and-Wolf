package item;

import java.util.ArrayList;

public class Inventory {

	private final ArrayList<ItemBundle> internalList;
	
	public Inventory() {
		internalList = new ArrayList<>();
	}
	
	public ArrayList<ItemBundle> getAll(){
		return internalList;
	}
	
	public <T> ArrayList<ItemBundle> getAllBundlesOfType(Class<T> t) {
		
		ArrayList<ItemBundle> sublist = new ArrayList<>();
		
		for(ItemBundle I : internalList) {
			if(t.isAssignableFrom(I.getParentItem().getClass())) sublist.add(I);
		}
		
		return sublist;
		
	}

}
