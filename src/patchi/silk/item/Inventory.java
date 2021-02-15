package patchi.silk.item;

import java.util.ArrayList;
import java.util.Iterator;

public class Inventory {

	private final ArrayList<ItemStack> internalList;

	public Inventory() {
		internalList = new ArrayList<>();
	}

	public ArrayList<ItemStack> getList() {
		return internalList;
	}

	public void addItem(Item I) {

		for(ItemStack J : internalList) {
			if(J.itemCompare(I)) {
				J.addQuantity(1);
				return;
			}
		}

		internalList.add(new ItemStack(I));

	}

	public boolean hasItem(Item I) {
		for(ItemStack J : internalList) {
			if(J.itemCompare(I)) {
				return true;
			}
		}
		return false;
	}

	public Iterator<ItemStack> getIterator() {
		return internalList.iterator();
	}

}
