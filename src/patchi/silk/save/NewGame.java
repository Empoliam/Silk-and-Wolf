package patchi.silk.save;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;

import patchi.silk.entities.CharacterFlags;
import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;
import patchi.silk.foundation.World;
import patchi.silk.market.GlobalStock;

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
	static final HashMap<Integer,GlobalStock> STOCKS = WORLD.getGlobalStockSet();
	
	/** Reference to character dataset*/
	static final List<Person> PEOPLE = WORLD.getPersonSet();
	
	private NewGame() {};
	
	public static void newGame() {
		
		stocks();
		settlements();
		roads();
		WORLD.updateRoadConnections();
		
		PEOPLE.addAll(PeopleGen.generate());
		WORLD.updateCharacterLocations();
		
		
		WORLD.printWorld();
		
		Person lawrence = new Person("0","Kraft","Lawrence",SETTLEMENTS.get(0).getID());
		lawrence.addFlags(CharacterFlags.DO_TRAVEL);
		PEOPLE.add(0,lawrence);
		Person holo = new Person("1","Holo","Wisewolf",SETTLEMENTS.get(0).getID());
		holo.addFlags(CharacterFlags.FEMALE, CharacterFlags.DO_TRAVEL);
		PEOPLE.add(1, holo);
		PEOPLE.sort((p1,p2) -> p1.getID().compareTo(p2.getID()));
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
	
	private static void stocks() { 
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/stocks.csv"));
			String line = br.readLine();
			
			while(line!=null) {
				
				STOCKS.put(GlobalStock.getIdTracker(),new GlobalStock(line));
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		
	}
	
}