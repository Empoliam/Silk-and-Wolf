package patchi.silk.item;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class ItemFactory {

		private static HashMap<String, Item> TEMPLATES;
	
		Item item;
		
		public ItemFactory() {
			item = new Item();
		}
		
		public Item generate() {
			return item;
		}
	
		public void buildFromTemplate(String templateName) {
			
			Set<Entry<String,String>> templateTags = TEMPLATES.get(templateName).getTagSet();
			
			for(Entry<String,String> E : templateTags) {
				
				item.addTag(E.getKey(), E.getValue());
				
			}
			
		}
		
		public void addTag(String tag, String data) {
			item.addTag(tag, data);
			item.updateName();
		}

		public static void storeTemplates(HashMap<String, Item> H) {
			TEMPLATES = H;
		}
		
		public static HashMap<String, Item> getTemplates() {
			return TEMPLATES;
		}
		
}
