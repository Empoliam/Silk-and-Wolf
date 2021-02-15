package patchi.silk.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Item held by an entity.
 */
public class Item implements RawData {

	private HashMap<String,String> tags = new HashMap<String,String>();

	public Item() {
			
	}

	public Item(HashMap<String,String> inputData) {

		super();
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
	
	public void updateSize() {
		
		float shapeWeight = 1.0f;
		float materialDensity = 1.0f;

		String mat = tags.get("MATERIAL");
		String shape = tags.get("SHAPE");		
		
		if(mat != null) {
			Material M = Material.MATERIALS.get(mat);
			materialDensity = Float.parseFloat(M.getTag("DENSITY"));
		}
		
		if(shape != null) {
			Shape S = Shape.SHAPES.get(shape);
			shapeWeight = Float.parseFloat(S.getTag("UNIT_WEIGHT"));
		}
		
		float volume = shapeWeight / materialDensity;
		
		tags.replace("WEIGHT", Float.toString(shapeWeight));
		tags.replace("VOLUME", Float.toString(volume));
		
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

		String name = matName + " " + shapeName;		
		
		if(name.isEmpty()) {
			name = "BadEGG";
		}
		
		tags.replace("NAME",name);

	}

	public float getWeight() {
		return Float.parseFloat(tags.get("WEIGHT"));
	}

	public String getName() {
		return tags.get("NAME");
	}

}
