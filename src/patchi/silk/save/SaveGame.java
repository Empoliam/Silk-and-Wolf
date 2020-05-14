package patchi.silk.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import patchi.silk.entities.CharacterFlag;
import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;
import patchi.silk.foundation.World;

public final class SaveGame {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** Reference to roads dataset. */
	private static final List<Road> ROADS = WORLD.getRoadSet();

	/** Reference to character dataset*/
	static final List<Person> PEOPLE = WORLD.getPersonSet();

	static private String saveName = new Date().toString();
	static private String PATH;

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
					bw.write("DEST:" + P.getDestination() + "\n");
				} catch (NullPointerException e) {
					bw.write("DEST:null\n");
				}
				bw.write("RDIST:" + P.getRemainingDistance() + "\n");
				bw.write("DEPHRS:" + P.getDepartureHours() + "\n");
				bw.write("CONF:" + String.valueOf(P.getConfidence()) + "\n");

				for(CharacterFlag A : P.getFlags()) {
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
				bw.write("NAME:" + S.getName() + "\n");
				
				bw.write("DPOP:");
				
				Iterator<Integer> daily = S.getDailyPop().iterator();
				while(daily.hasNext()) {
					Integer I = daily.next();
					bw.write(I.toString());
					if(daily.hasNext()) {
						bw.write(",");
					}
				}
				bw.write("\n");
				bw.write("MPOP:" + S.getMonthlyPopString());
				
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
				bw.write("NAME:" + R.getName() + "\n");
				bw.write("SETA:" + R.getConnectingA() + "\n");
				bw.write("SETB:" + R.getConnectingB() + "\n");
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


