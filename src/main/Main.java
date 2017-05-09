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

		System.out.println(Settlement.getRoadBetween(3,4).getName());
		
	}
}
