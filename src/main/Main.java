package main;

import java.util.ArrayList;
import java.util.List;

import entities.Road;
import entities.Settlement;

public class Main {

	public static List<Settlement> SETTLEMENTS = new ArrayList<Settlement>();
	public static List<Road> ROADS = new ArrayList<Road>();

	public static void main(String[] args) {

		Load.settlements();
		Load.roads();

		Settlement a = SETTLEMENTS.get(6);
		Settlement b = SETTLEMENTS.get(10);
		Road r = a.getRoadTo(10);
		System.out.println(b.getName() + " is " + r.getLength() + " miles away, across the " + r.getName());
		
	}
}
