package main;

import java.util.List;
import java.util.Random;

import entities.Road;
import entities.Settlement;
import entities.NPC;

import foundation.*;

public class Main {

	public static final Time CLOCK = Time.getInstance();
	static final Random RANDOM = new Random(System.nanoTime());
	static final List<Road> ROADS = Road.ROADS;
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	static final List<NPC> NPCS = NPC.NPCS;
	
	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		Load.npcs();
		
		for(int x = 0; x < 8760; x ++) {
			
			Time.advanceHour();
			if (Time.getHour() == 0) System.out.println(Time.getFormattedDate() + " - Day " + Time.getCurrentDayCount());
			
		}
		
	}
	
	private static void doHourTick() {
		
		Time.advanceHour();
				
		for(NPC h : NPCS) {
						
			if(h.isTravelling()) h.advanceTravel(); 
			
			else if(Time.getHour() == 0 && h.getDoTravel() && !h.getPrepTravel()) {
				
				h.setDepartureHours(SWMath.generateBinomialInt(23,0.347826087,RANDOM));
				h.setPrepTravel(true);
				
			}
		}	
	}
}