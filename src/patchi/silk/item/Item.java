package patchi.silk.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Item held by an entity.
 */
public class Item {

	private HashMap<String,String> tags = new HashMap<String,String>();

	private float weight = 0.0f;
	private String name = "Placeholder";

	public Item() {}
	
	public Item(HashMap<String,String> inputData) {

		tags = inputData;

	}

	public void addTag(String tag, String data) {
		tags.put(tag,data);
	}

	public Set<Map.Entry<String, String>> getTagSet() {
		return tags.entrySet();
	}

	public String getTag(String tag) {
		return tags.get(tag);
	}

	public void updateWeight() {

		float shapeVolume = 1.0f;
		float materialDensity = 1.0f;
		
		String mat = tags.get("MATERIAL");
		String shape = tags.get("SHAPE");
		
		if(mat != null) {
			Material M = Material.MATERIALS.get(mat);
			materialDensity = Float.parseFloat(M.getTag("DENSITY"));
		}
		
		if(shape != null) {
			Shape S = Shape.SHAPES.get(shape);
			shapeVolume = Float.parseFloat(S.getTag("VOLUME"));
		}
		
		weight = shapeVolume * materialDensity;		

	}

	public void updateName() {

		String matName = "";
		String shapeName = "";

		String mat = tags.get("MATERIAL");
		String shape = tags.get("SHAPE");
		
		if(mat != null) {
			Material M = Material.MATERIALS.get(mat);
			matName = M.getTag("NAME");
		}
		
		if(shape != null) {
			Shape S = Shape.SHAPES.get(shape);
			shapeName = S.getTag("NAME");
		}

		name = matName + shapeName;		

	}

	public float getWeight() {
		return weight;
	}

	public String getName() {
		return name;
	}
	
}
