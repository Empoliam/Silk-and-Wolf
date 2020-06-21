package patchi.silk.save;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import patchi.silk.entities.CharacterFlag;
import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;
import patchi.silk.foundation.World;

/**
 * Handles all file loading at the start of the game.
 */
public class NewGame {
	
	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** Reference to roads dataset. */
	private static final List<Road> ROADS = WORLD.getRoadSet();
	
	/** Reference to global stock dataset */
	
	/** Reference to character dataset*/
	static final TreeMap<String,Person> PEOPLE = WORLD.getPersonSet();
	
	private NewGame() {};
	
	public static void newGame() {
		
		settlements();
		roads();
		WORLD.updateRoadConnections();
		
		PEOPLE.putAll(PeopleGen.generate());
		WORLD.updateCharacterLocations();
					
		Person lawrence = new Person("0","Kraft","Lawrence","sw.yorenz");
		lawrence.addFlags(CharacterFlag.DO_TRAVEL);
		PEOPLE.put("0",lawrence);
		Person holo = new Person("1","Holo","Wisewolf","sw.yorenz");
		holo.addFlags(CharacterFlag.FEMALE, CharacterFlag.DO_TRAVEL);
		PEOPLE.put("1", holo);
					
	}
	
	/**
	 * Load settlements.
	 */
	private static void settlements() {

		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/settlements.csv"));
			String line = br.readLine();

			while (line != null) {

				SETTLEMENTS.add(new Settlement(line.split(",")));
				line = br.readLine();
			}

			br.close();
		} 
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
	}

	/**
	 * Load roads.
	 */
	private static void roads() {
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader("resources/roads.csv"));
			String line = br.readLine();
			
			while(line != null) {
				
				String[] data = line.split(",");
				Road R = new Road(data[0]);
				R.setName(data[1]);
				R.setConnectingA(data[2]);
				R.setConnectingB(data[3]);
				R.setLength(Integer.parseInt(data[4]));
				
				ROADS.add(R);
				line = br.readLine();
				
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
				
	}
	
}
