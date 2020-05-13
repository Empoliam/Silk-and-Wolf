package patchi.silk.save;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import patchi.silk.entities.CharacterFlag;
import patchi.silk.entities.Person;

public class PeopleGen {

	final static Random RANDOM = new Random();

	public static List<Person> generate(){

		List<Person> people = new ArrayList<Person>();

		try {

			List<String> snames = new ArrayList<String>();
			List<String> mnames = new ArrayList<String>();
			List<String> fnames = new ArrayList<String>();
			List<String[]> settlements = new ArrayList<String[]>();

			//load surnames
			BufferedReader rSnames = new BufferedReader(new FileReader("resources/snames.txt"));

			for(String line = rSnames.readLine(); line != null;){
				snames.add(line);
				line = rSnames.readLine();
			}	

			rSnames.close();

			//load male names
			BufferedReader rMnames = new BufferedReader(new FileReader("resources/mnames.txt"));

			for(String line = rMnames.readLine(); line != null;){
				mnames.add(line);
				line = rMnames.readLine();
			}	
			rMnames.close();

			//load female names
			BufferedReader rFnames = new BufferedReader(new FileReader("resources/fnames.txt"));

			for(String line = rFnames.readLine(); line != null;){
				fnames.add(line);
				line = rFnames.readLine();
			}	
			rFnames.close();

			//load settlements
			BufferedReader rSettlements = new BufferedReader(new FileReader("resources/settlements.csv"));

			for(String line = rSettlements.readLine(); line != null;){
				String[] split = line.split(",");				
				String[] arr = { split[0] , split[2] };
				settlements.add(arr);
				line = rSettlements.readLine();
			}	
			rSettlements.close();

			int idCounter = 199;

			for(String[] s : settlements) {

				for(int k = 1; k <= Integer.parseInt(s[1]); k++) {

					Person P = new Person(Integer.toString(idCounter));
					
					boolean female = RANDOM.nextBoolean();
					if(female) {
						P.addFlags(CharacterFlag.FEMALE);
					}

					//generate first name
					if(female) {
						P.setFirstName(fnames.get(RANDOM.nextInt(fnames.size())));
					}
					else {
						P.setFirstName(mnames.get(RANDOM.nextInt(mnames.size())));
					}

					//generate last name
					P.setLastName(snames.get(RANDOM.nextInt(snames.size())));

					//town ID
					P.setLocationID((s[0]));
					
					if(RANDOM.nextBoolean()) P.addFlags(CharacterFlag.DO_TRAVEL);
					P.addFlags(CharacterFlag.DO_DECISION_TREE);
										
					people.add(P);
					idCounter++;

				}

			}

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return people;
		
	}

}
