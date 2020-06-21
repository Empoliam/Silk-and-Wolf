package patchi.silk.save;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import patchi.silk.item.Material;
import patchi.silk.item.RawData;
import patchi.silk.item.Shape;

public class InititialiseData {

	private InititialiseData() {}

	public static void initialise() {

		Material.MATERIALS = loadFromFile("resources/items/materials.silk", Material.class);
		Shape.SHAPES = loadFromFile("resources/items/shapes.silk", Shape.class);

		for(Entry<String, Shape> M : Shape.SHAPES.entrySet()) {

			Shape S = M.getValue();			

			for(Entry<String, String> T : S.getTagSet()) {
				
				System.out.println(T.getKey() + ":" + T.getValue());
				
			}

		}

	}

	private static <T extends RawData> HashMap<String,T> loadFromFile(String path, Class<T> OBJ) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();

			HashMap<String,T> placeholderMap = new HashMap<String, T>();

			T M = null;

			while (line != null) {

				if(line.isEmpty()) {

				} else if(line.charAt(0) == '#') {

					String key = line.substring(1);
					placeholderMap.put(key, OBJ.newInstance());
					M = placeholderMap.get(key);

				} else if(line.charAt(0) == '[') {

					line = line.substring(1,line.length()-1);
					String[] s = line.split(":");
					M.addTag(s[0],s[1]);

				}

				line = br.readLine();

			}

			br.close();

			return placeholderMap;

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		catch (InstantiationException e) {
			e.printStackTrace();
		}

		return null;

	}

}
