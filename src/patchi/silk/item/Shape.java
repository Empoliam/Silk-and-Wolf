package patchi.silk.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Shape implements RawData {

	public static HashMap<String,Shape> SHAPES = new HashMap<>();
	
	private List<TagPair> tags = new LinkedList<>();
	
	public Shape() {}
	
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
