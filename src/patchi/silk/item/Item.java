package patchi.silk.item;

import java.util.LinkedList;
import java.util.List;

/**
 * Item held by an entity.
 */
public class Item {

	private List<TagPair> data = new LinkedList<TagPair>();
	private float quantity;
	
	public Item(List<TagPair> inputData) {
		
		data = inputData;
		
	}
		
}
