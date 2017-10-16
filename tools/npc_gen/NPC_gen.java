package npc_gen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPC_gen {

	final static int NPCS_TO_GENERATE = 1000;
	final static Random RANDOM = new Random();

	public static void main(String[] args){

		try {

			List<String> snames = new ArrayList<String>();
			List<String> mnames = new ArrayList<String>();
			List<String> fnames = new ArrayList<String>();

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

			BufferedWriter output = new BufferedWriter(new FileWriter("npcs.txt"));

			for(int k = 1; k <= NPCS_TO_GENERATE; k++) {

				boolean female = RANDOM.nextBoolean();
				output.append((female ? "0" : "1") + ",");

				//generate first name
				if(female) {
					output.append(fnames.get(RANDOM.nextInt(fnames.size())));
				}
				else {
					output.append(mnames.get(RANDOM.nextInt(mnames.size())));
				}

				output.append(",");

				//generate last name
				output.append(snames.get(RANDOM.nextInt(snames.size())));

				if(k != NPCS_TO_GENERATE) { output.newLine(); }

			}

			output.close();

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
