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

		for(Settlement s : SETTLEMENTS) {
			
			System.out.print(SETTLEMENTS.get(s.getID()).getName() + " is connected to: ");

			List<Integer> test = SETTLEMENTS.get(s.getID()).connectedTo();
			for(int i : test) {
				System.out.print(SETTLEMENTS.get(i).getName() + ", ");
			}
			System.out.println();
		}
	}
}
