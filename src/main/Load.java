package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;

import entities.NPC;
import entities.Road;
import entities.Settlement;
import entities.World;

import market.GlobalStock;

/**
 * Handles all file loading at the start of the game.
 */
public class Load {
	
	/** NPC ID counter. For keeping track of the current NPC ID.<br>
	 *	All NPC IDs start at 2. Lawrence always has ID '0' and Holo always has ID '1'.
	 */
	@SuppressWarnings("unused")
	private static int NPC_ID_COUNTER = 2;

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementsSet();

	/** Reference to roads dataset. */
	private static final List<Road> ROADS = WORLD.getRoadSet();
	
	/** Reference to global stock dataset */
	static final HashMap<Integer,GlobalStock> STOCKS = WORLD.getGlobalStockSet();
	
	/** Reference to NPC dataset*/
	static final List<NPC> NPCS = WORLD.getNPCS();
	
	/**
	 * Load settlements.
	 */
	public static void settlements() {

		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/settlements.csv"));
			String line = br.readLine();

			while (line != null) {

				int idSplitPoint = line.indexOf(",");
				
				SETTLEMENTS.add(new Settlement(line.substring(0, idSplitPoint), line.substring(idSplitPoint+1, line.length())));
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
				
				int idSplitPoint = line.indexOf(",");
				
				ROADS.add(new Road(line.substring(0, idSplitPoint), line.substring(idSplitPoint+1, line.length())));
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
				
				NPCS.add(new NPC(line));
				NPC_ID_COUNTER ++;
				line = br.readLine();
				
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
		
	}

	public static void stocks() { 
		
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
	
	public static void unpack() {
		
		for(Settlement S : SETTLEMENTS) {
			S.unpack();
		}
		
		for(Road R : ROADS) {
			R.unpack();
		}
		
		for(NPC N : NPCS) {
			N.unpack();
		}
		
	}
}
