package main;

import java.util.ArrayList;
import java.util.List;

import entities.Road;
import entities.Settlement;
import entities.Human;

import foundation.*;

public class Main {

	public static final Time CLOCK = Time.getInstance();
	static final List<Road> ROADS = Road.ROADS;
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	static final List<Human> HUMANS = new ArrayList<Human>();
	
	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		
		HUMANS.add(new Human(1,"Lawrence","Kraft"));		
		Human lawrence = HUMANS.get(0);
		
		for(int k = 0; k < 700; k++){
			
			doHourTick();
			System.out.println(Time.getFormattedDate() + ", " + Time.getFormattedTime());
		}
	}
	
	private static void doHourTick() {
		
		Time.advanceHour();
		
		for(Human h : HUMANS) {
			if(h.isTravelling()) {
				
				h.advanceTravel();		
			}
		}	
	}
}