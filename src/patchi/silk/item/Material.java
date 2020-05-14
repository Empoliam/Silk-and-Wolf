package patchi.silk.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public  class Material {

	public static HashMap<String,Material> MATERIALS = new HashMap<>();
	
	private List<TagPair> tags = new LinkedList<>();
	
	public Material() {}
	
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
