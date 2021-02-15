package patchi.silk.main;

import patchi.silk.foundation.World;
import patchi.silk.item.Item;
import patchi.silk.item.ItemFactory;

public class TestCode {

	private static final World WORLD = World.getMainWorld(); 
	
	public static void itemTestCode() {
		ItemFactory testFactory = new ItemFactory();
		testFactory.buildFromTemplate("BAR_METAL");
		testFactory.addTag("MATERIAL", "METAL_GOLD");
		Item testItem = testFactory.generate();
		System.out.println(testItem.getName());
		
		System.out.println(testItem.getTagSet());
		
		WORLD.getPersonByID("0").getInventory().addItem(testItem);
		
	}
	
}
