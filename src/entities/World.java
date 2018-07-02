package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import market.GlobalStock;

/**
 * World class. Acts as a central access point for all datasets. Also functions as a graph of settlements connected by roads.
 */
public class World {
	
	/** The Constant WORLD. */
	private static final World WORLD = new World();
	
	/**  Full settlement dataset ArrayList. */
	private final ArrayList<Settlement> SETTLEMENTS = new ArrayList<Settlement>();
	
	/** Full road set as ArrayList.*/
	private final ArrayList<Road> ROADS = new ArrayList<Road>();
	
	/** Global stock list. */
	private final HashMap<Integer,GlobalStock> STOCKS = new HashMap<Integer,GlobalStock>();
	
	/** Full NPC dataset ArrayList. Automatically sorts when adding new NPC. */
	private final ArrayList<NPC> NPCS = new ArrayList<NPC>() {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(NPC A) {
			
			super.add(A);
			
			sort((X, Y) -> {
				return X.getId() - Y.getId();
			});
			
			return true;	
		}
				
	};	
	
	/**
	 * Instantiates a new world.
	 */
	public World() {
		
	}

	/**
	 * Gets the main overworld.
	 *
	 * @return reference to overworld
	 */
	public static World getMainWorld() {
		return WORLD;
	}
	
	/**
	 * Gets the settlements set.
	 *
	 * @return the settlements set
	 */
	public ArrayList<Settlement> getSettlementsSet(){
		return SETTLEMENTS;
	}
	
	/**
	 * Gets the road set.
	 *
	 * @return the road set
	 */
	public ArrayList<Road> getRoadSet(){
		return ROADS;
	}
	
	/**
	 * Gets the global stock set.
	 *
	 * @return the global stock set
	 */
	public HashMap<Integer,GlobalStock> getGlobalStockSet() {
		return STOCKS;
	}
	
	/**
	 * Gets the NPC list.
	 *
	 * @return the NPC list
	 */
	public ArrayList<NPC> getNPCS() {
		return NPCS;
	}
	
	public void printWorld() {

		ArrayList<Road> roads = new ArrayList<Road>();

		for(Settlement N : SETTLEMENTS) {
			ArrayList<Road> L = N.getRoads();
			for(Road E : L) {
				if(!roads.contains(E)) roads.add(E);
			}
		}

		for(Road E : roads) {
			System.out.println(E.getConnectingA().getName() + " - " + E.getConnectingB().getName() + " : " + E.getLength());
		}

	}
	
	public ArrayList<Settlement> Dijkstra(Settlement start, Settlement end) {

		
		ArrayList<Settlement> route = new ArrayList<Settlement>();

		for(Settlement S : SETTLEMENTS) S.purge();

		start.setFinalValue(0);
		start.setDone(true);

		Settlement active = start;

		while(!end.getDone()) {

			for(Road R : active.getRoads()) {

				Settlement check;

				if(R.getConnectingA() == active) check = R.getConnectingB();
				else check = R.getConnectingA();

				if(! check.getDone()) {
					int newWorking = R.getLength() + active.getFinalValue();
					if(check.getWorkingValue() > newWorking) check.setWorkingValue(newWorking);
				}

			}

			for(Settlement S : SETTLEMENTS) {
				if(!S.getDone()) {
					if(active.getDone() || active.getWorkingValue() > S.getWorkingValue()) active = S;
				}
			}

			active.setFinalValue(active.getWorkingValue());
			active.setDone(true);

		}

		for(Settlement S : SETTLEMENTS) System.out.println(S.getName() + " : " + S.getFinalValue());

		route.add(end);

		Settlement reverse = end;
		while(!reverse.equals(start)) {
			for(Road R : reverse.getRoads()) {
				if((reverse.getFinalValue() - R.getLength()) == R.getConnectingA().getFinalValue()) {
					reverse = R.getConnectingA();
					break;
				}
				else if((reverse.getFinalValue() - R.getLength()) == R.getConnectingB().getFinalValue()) {
					reverse = R.getConnectingB();
					break;
				}
			}
			route.add(reverse);
		}

		Collections.reverse(route);

		return route;

	}
	
	
}
