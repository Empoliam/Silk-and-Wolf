package main;

import java.util.ArrayList;
import java.util.List;

import entities.Road;
import entities.Settlement;
import entities.Human;

public class Main {

	public static List<Settlement> SETTLEMENTS = new ArrayList<Settlement>();
	public static List<Road> ROADS = new ArrayList<Road>();
	public static List<Human> HUMANS = new ArrayList<Human>();
	
	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		
		HUMANS.add(new Human(1,"Lawrence","Kraft"));
		
		Human lawrence = HUMANS.get(0);
		
		System.out.println(lawrence.getSettlement().getName());
		lawrence.beginTravel(2);
		System.out.println(lawrence.getRemainingDistance());
		lawrence.advanceTravel();
		System.out.println(lawrence.getRemainingDistance());
		lawrence.advanceTravel();
		System.out.println(lawrence.getRemainingDistance());
		lawrence.advanceTravel();
		System.out.println(lawrence.getRemainingDistance());
		System.out.println(lawrence.getSettlement().getName());
		
	}
}
