package patchi.silk.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import patchi.silk.entities.CharacterFlags;
import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;
import patchi.silk.foundation.World;
import patchi.silk.market.GlobalStock;

public class SaveGame {

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

	static private String saveName = new Date().toString();
	static private String PATH;

	private SaveGame() { }

	public static void saveGame(String name) {

		saveName = name;
		PATH = "saves/" + saveName;

		try {
			FileUtils.deleteDirectory(new File(PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		new File(PATH).mkdirs();

		savePeople();
		saveSettlements();
		saveRoads();
		saveWorld();
		
	}

	private static void savePeople() {

		for(Person P : PEOPLE) {

			new File(PATH + "/characters/").mkdirs();
			File F = new File(PATH + "/characters/" + P.getID() + ".dat");

			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(F));

				bw.write("ID:" + P.getID() + "\n");
				bw.write("FNAME:" + P.getFirstName() + "\n");
				bw.write("LNAME:" + P.getLastName() + "\n");
				bw.write("LOC:" + P.getLocationID() + "\n");
				try {
					bw.write("DESTINATION:" + P.getDestination().getID() + "\n");
				} catch (NullPointerException e) {
					bw.write("DEST:null\n");
				}
				bw.write("RDIST:" + P.getRemainingDistance() + "\n");
				bw.write("DEPHRS:" + P.getDepartureHours() + "\n");
				bw.write("CONF:" + String.valueOf(P.getConfidence()) + "\n");

				for(CharacterFlags A : P.getFlags()) {
					bw.write("FLAG:" + A.toString() +"\n");
				}

				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static void saveSettlements() {

		for(Settlement S : SETTLEMENTS) {

			new File(PATH + "/settlements/").mkdirs();
			File F = new File(PATH + "/settlements/" + S.getID() + ".dat");

			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(F));

				bw.write("ID:" + S.getID() + "\n");
				bw.write("NAME:" + S.getName());

				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static void saveRoads() {

		for(Road R : ROADS) {

			new File(PATH + "/roads/").mkdirs();
			File F = new File(PATH + "/roads/" + R.getID() + ".dat");

			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(F));

				bw.write("ID:" + R.getID() + "\n");
				bw.write("NAME:" + R.getName());
				bw.write("SETA:" + R.getConnectingA().getID());
				bw.write("SETB:" + R.getConnectingB().getID());
				bw.write("LENGTH:" + R.getLength());

				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	private static void saveWorld() {
		
		File F = new File(PATH + "/world.dat");

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(F));

			bw.write("TIME:" + WORLD.getClock().toString() + "\n");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}


