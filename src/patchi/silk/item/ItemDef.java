package patchi.silk.item;

import java.util.EnumSet;

public class ItemDef {

	private String id;
	private String name;
	private EnumSet<ItemTag> tags = EnumSet.noneOf(ItemTag.class);
	
	public ItemDef(String id, String name) {
		
		this.id = id;
		this.name = name;
		
	}

	public ItemDef(String[] in) {
		
		this.id = in[0];
		this.name = in[1];
	
	}

	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static ItemDef getByID(String id) {	
		return null;
	}
	
	public boolean isCurrency() {
		return tags.contains(ItemTag.CURRENCY);
	}
	
}
