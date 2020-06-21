package patchi.silk.main;

import patchi.silk.item.Item;
import patchi.silk.item.ItemFactory;

public class TestCode {

	public static void itemTestCode() {
		ItemFactory testFactory = new ItemFactory();
		testFactory.buildFromTemplate("BAR_METAL");
		testFactory.addTag("MATERIAL", "METAL_GOLD");
		Item testItem = testFactory.generate();
		System.out.println(testItem.getName());
	}
	
}
