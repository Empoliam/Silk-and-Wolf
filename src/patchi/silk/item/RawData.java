package patchi.silk.item;

import java.util.Collection;

public interface RawData {

	public void addTag(TagPair T);
		
	public Collection<TagPair> getTag(); 
	
	public TagPair getTag(int i);
	
}
