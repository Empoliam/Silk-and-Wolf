package patchi.silk.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public  class Material implements RawData {

	public static HashMap<String,Material> MATERIALS = new HashMap<>();

	private HashMap<String,String> tags = new HashMap<>();

	public Material() {}

	public void addTag(String tag, String data) {
		tags.put(tag, data);
	}

	public String getTag(String tag) {	
		return tags.get(tag);	
	}

	public Set<Map.Entry<String, String>> getTagSet() {
		return tags.entrySet();
	}
	
}
