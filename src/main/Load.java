package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entities.NPC;
import entities.Road;
import entities.Settlement;

/**
 * Handles all file loading at the start of the game.
 */
public class Load {
	
	/** NPC ID counter. Ensures that all NPC objects have a unique ID.<br>
	 *	All NPC IDs start at 2. Lawrence always has ID '0' and Holo always has ID '1'.
	 */
	private static int NPC_ID_COUNTER = 2;
	
	/**
	 * Load settlements.
	 */
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

	/**
	 * Load roads.
	 */
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
	
	/**
	 * Load NPCs.
	 */
	public static void npcs() { 
	
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/npcs.csv"));
			String line = br.readLine();
			
			while(line!=null) {
				
				NPC.NPCS.put(NPC_ID_COUNTER,new NPC(NPC_ID_COUNTER,line.split(",")));
				NPC_ID_COUNTER ++;
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		
	}
		
}
