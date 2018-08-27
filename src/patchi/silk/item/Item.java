package patchi.silk.item;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * Item object. Some fields are unused for some item types.
 */
public class Item {

	/** Default templates for items */
	private static ArrayList<Item> ITEM_TEMPLATES = new ArrayList<>();

	/** Item ID*/
	private final int ID; 
	
	/** Item name. */
	private final String name;	

	/** Item type. Refer to doucmentation for types*/
	private final int type;

	/**
	 * Clones an item, producing an identical copy.
	 *
	 * @param I item to be cloned
	 */
	public Item(Item I) {

		this.ID = I.getID();
		this.name = I.getName();
		this.type = I.getType();
	}

	/**
	 * Clones an item from the specified templates.
	 *
	 * @param i template index
	 */
	public Item(int i) {
		this(ITEM_TEMPLATES.get(i));
	}

	/**
	 * Instantiates a new item with the given parameters.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public Item(int ID, String name, int type) {

		this.ID = ID;
		this.name = name;
		this.type = type;
	}
	
	public Item(String in) {
				
		String[] split = in.split(",");
		
		this.ID = Integer.parseInt(split[0]);
		this.name = split[1];
		this.type = Integer.parseInt(split[2]);
		
	}

	/**
	 * Returns Item name.
	 *
	 * @return Item name
	 */
	public String getName() {
		return name;
	}

	public ReadOnlyStringWrapper getNameProperty() {
		return new ReadOnlyStringWrapper(name);
	}

	/**
	 * Returns item type.
	 *
	 * @return Item type
	 */
	public int getType() {
		return type;
	}
	
	public int getID() {
		return ID;
	}

}
