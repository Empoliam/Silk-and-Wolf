package patchi.silk.main;

import patchi.silk.entities.Person;
import patchi.silk.foundation.World;
import patchi.silk.item.Item;
import patchi.silk.item.ItemFactory;

public class TestCode {

	private static final World WORLD = World.getMainWorld(); 
	
	public static void itemTestCode() {
		
		Person LAWRENCE = WORLD.getPersonByID("0");
		
		ItemFactory testFactory = new ItemFactory();
		testFactory.buildFromTemplate("BAR_METAL");
		testFactory.addTag("MATERIAL", "METAL_GOLD");
		Item testItem = testFactory.generate();
		
		ItemFactory testFactory2 = new ItemFactory();
		testFactory2.buildFromTemplate("BAR_METAL");
		testFactory2.addTag("MATERIAL", "METAL_IRON");
		Item testItem2 = testFactory2.generate();
		
		ItemFactory testFactory3 = new ItemFactory();
		testFactory3.buildFromTemplate("INGOT_METAL");
		testFactory3.addTag("MATERIAL", "METAL_IRON");
		Item testItem3 = testFactory3.generate();
		
		System.out.println(testItem3.getName());
		System.out.println(testItem3.getTagSet());
		
		
		LAWRENCE.getInventory().addItem(testItem);
		LAWRENCE.getInventory().addItem(testItem2);
		LAWRENCE.getInventory().addItem(testItem3);
		
		
	}
	
}
