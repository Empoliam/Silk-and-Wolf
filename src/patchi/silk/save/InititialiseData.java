package patchi.silk.save;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import patchi.silk.item.Material;
import patchi.silk.item.TagPair;

public class InititialiseData {

	private InititialiseData() {}

	public static void initialise() {

		mats();

		for(Material M : Material.MATERIALS.values()) {
			
			for(TagPair T : M.getTag()) {
				
				System.out.println(T.getTag());
				
			}
			
		}
		
	}

	private static void mats() {
		
		try {

			BufferedReader br = new BufferedReader(new FileReader("resources/items/materials.silk"));
			String line = br.readLine();

			Material M = null;
			
			while (line != null) {
				
				if(line.isEmpty()) {
					
				} else if(line.charAt(0) == '#') {
					
					String key = line.substring(1);
					Material.MATERIALS.put(key, new Material());
					M = Material.MATERIALS.get(key);
					
				} else if(line.charAt(0) == '<' || line.charAt(0) == '[') {
					
					line = line.substring(1,line.length()-1);
					String[] s = line.split(":");
					M.addTag(new TagPair(s[0],s[1]));
					
				}
								
				line = br.readLine();
				
			}

			br.close();
		} 
		catch (FileNotFoundException e) {}
		catch (IOException e) {}


	}

}
