package main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entities.Road;
import entities.Settlement;
import entities.NPC;

import foundation.*;

/**
 * Main class.
 */
public class Main {
	
	/** Universal random number generator. */
	static final Random RANDOM = new Random(System.nanoTime());
	
	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;
	
	/** Reference to road dataset. */
	static final List<Road> ROADS = Road.ROADS;
	
	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	/** Reference to NPC dataset. */
	static final HashMap<Integer,NPC> NPCS = NPC.NPCS;

	/**
	 * Primary initialization method
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		Load.npcs();
		
		for(int x = 0; x < 8760; x ++) {
			int d = NPCS.get(2).generateDepartureHour(RANDOM);
			if(CLOCK.getHour() == 0) {
				
				System.out.println(CLOCK.getCurrentDayCount() + "," + d);
				
			}
			CLOCK.advanceHour();

		}

	}

	/**
	 * Executes a single hour tick. The bulk of all game logic happens in these ticks.
	 */
	private static void doHourTick() {

		CLOCK.advanceHour();

		for(NPC h : NPCS.values()) {

			if(CLOCK.getHour() == 0 && h.getDoTravel() && !h.getPrepTravel()) {

				h.setDepartureHours(h.generateDepartureHour(RANDOM));
				h.setPrepTravel(true);

			}

			else if(h.isTravelling()) h.advanceTravel();
		}	
	}
}