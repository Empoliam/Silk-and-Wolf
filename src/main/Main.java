package main;

import java.util.List;

import entities.Road;
import entities.Settlement;
import entities.NPC;

import foundation.*;

public class Main {

	public static final Time CLOCK = Time.getInstance();
	static final List<Road> ROADS = Road.ROADS;
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	static final List<NPC> NPCS = NPC.NPCS;
	
	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		Load.npcs();
		
		NPC A = NPCS.get(0);
		NPC B = NPCS.get(476);
		
		System.out.println(A.getSettlement().getName());
		System.out.println(B.getSettlement().getName());
		A.beginTravel(1);
		B.beginTravel(8);
		for(int x = 0; x < 10; x++) doHourTick();
		System.out.println(A.getSettlement().getName());
		System.out.println(B.getSettlement().getName());
		
	}
	
	private static void doHourTick() {
		
		Time.advanceHour();
		
		for(NPC h : NPCS) {
			if(h.isTravelling()) {
				
				h.advanceTravel();		
			}
		}	
	}
}