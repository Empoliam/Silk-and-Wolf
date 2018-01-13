package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entities.NPC;
import entities.Road;
import entities.Settlement;

public class Load {
	
	public static void settlements() {

		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/settlements.csv"));
			String line = br.readLine();

			while (line != null) {

				Settlement.SETTLEMENTS.add(new Settlement(line.split(",")));
				line = br.readLine();
			}

			br.close();
		} 
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
	}

	public static void roads() {
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader("resources/roads.csv"));
			String line = br.readLine();
			
			while(line != null) {
				
				Road.ROADS.add(new Road(line.split(",")));
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	
	public static void npcs() { 
	
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/npcs.csv"));
			String line = br.readLine();
			
			while(line!=null) {
				
				NPC.NPCS.add(new NPC(line.split(",")));
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		
	}
		
}
