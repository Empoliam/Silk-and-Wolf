package patchi.silk.item;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Item held by an entity.
 */
public class Item {

	private List<TagPair> tags = new LinkedList<TagPair>();
	
	public Item(List<TagPair> inputData) {
		
		tags = inputData;
		
	}
	
	public void addTag(TagPair T) {
		tags.add(T);
	}
		
	public Collection<TagPair> getTag() {
		return tags;
	}
	
	public TagPair getTag(int i) {
		return tags.get(i);
	}
		
}
