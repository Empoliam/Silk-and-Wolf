package patchi.silk.item;

import java.util.Map;
import java.util.Set;

public interface RawData {

	public void addTag(String tag, String data);
	public String getTag(String tag);
	public Set<Map.Entry<String, String>> getTagSet();
			
}
