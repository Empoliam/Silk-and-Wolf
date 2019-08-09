package patchi.silk.save;

import java.io.BufferedReader;
import java.io.File;
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

public class LoadGame {

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

	static String PATH; 

	private LoadGame() {}

	public static void loadGame(String saveName) throws FileNotFoundException {

		PATH = "saves/" + saveName;

		loadWorld();
		loadChar();
		loadSettlements();
		loadRoads();

		WORLD.updateCharacterLocations();
		WORLD.updateRoadConnections();

	}

	private static void loadChar() {

		String charPath = PATH + "/characters";

		File[] charFiles = new File(charPath).listFiles();

		for(File F : charFiles) {

			try {

				BufferedReader br = new BufferedReader(new FileReader(F));

				String ID = br.readLine().split(":")[1];
				Person P = new Person(ID);
				String line;

				while((line = br.readLine()) != null) {

					String tag = line.split(":")[0];
					String data = line.split(":")[1];

					switch(tag)  {
					case "FNAME": {
						P.setFirstName(data);
						break;
					} 
					case "LNAME": {
						P.setLastName(data);
						break;
					}
					case "LOC": {
						P.setLocationID(data);
						break;
					} 
					case "DEST" : {

						if(!data.equals("null")) {
							P.setDestination(data);
						}
						break;

					} 
					case "RDIST": {
						P.setRemainingDistance(Integer.parseInt(data));
						break;
					} 
					case "DEPHRS": {
						P.setDepartureHours(Integer.parseInt(data));
						break;
					} 
					case "CONF": {
						P.setConfidence(Float.parseFloat(data));
						break;
					} 
					case "FLAG": {
						P.addFlags(CharacterFlags.valueOf(data));
						break;
					}
					}
				}

				br.close();

				PEOPLE.add(P);

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

	}

	private static void loadSettlements() {

		String settlementPath = PATH + "/settlements";

		File[] settlementFiles = new File(settlementPath).listFiles();

		for(File F : settlementFiles) {

			try {

				BufferedReader br = new BufferedReader(new FileReader(F));

				String ID = br.readLine().split(":")[1];
				Settlement S = new Settlement(ID);
				String line;

				while((line = br.readLine()) != null) {

					String tag = line.split(":")[0];
					String data = line.split(":")[1];

					switch(tag)  {
					
					case "NAME": {
						S.setName(data);
						break;
					}
					
					case "DPOP": {
						
						S.parseDailyPopString(data);
						
						break;
						
					}
					
					case "MPOP": {
						S.parseMonthlyPopString(data);												
					}

					}
				}

				br.close();

				SETTLEMENTS.add(S);

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}


	}

	private static void loadRoads() {

		String roadPath = PATH + "/roads";

		File[] roadFiles = new File(roadPath).listFiles();

		for(File F : roadFiles) {

			try {

				BufferedReader br = new BufferedReader(new FileReader(F));

				String ID = br.readLine().split(":")[1];
				Road R = new Road(ID);
				String line;
				
				
				while((line = br.readLine()) != null) {
										
					String tag = line.split(":")[0];
					String data = line.split(":")[1];

					switch(tag)  {
					case "NAME": {
						R.setName(data);
						break;
					} 
					case "LENGTH": {
						R.setLength(Integer.parseInt(data));
						break;
					}
					case "SETA": {
						R.setConnectingA(data);
						break;
					}
					case "SETB": {
						R.setConnectingB(data);
						break;
					}

					}
				}

				br.close();
				
				ROADS.add(R);

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

	}

	private static void loadWorld() {

		File F = new File(PATH + "/world.dat");

		try {

			BufferedReader br = new BufferedReader(new FileReader(F));

			String line;

			while((line = br.readLine()) != null) {

				String tag = line.split(":")[0];
				String data = line.split(":")[1];

				switch(tag)  {
				case "TIME": {
					WORLD.setNewTime(data);
					break;
				} 

				}
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}			


	}

}
