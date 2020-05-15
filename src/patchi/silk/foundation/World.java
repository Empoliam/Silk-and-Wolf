package patchi.silk.foundation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;

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

	/** Full character dataset ArrayList. Automatically sorts when adding new character. */
	private final TreeMap<String,Person> PEOPLE = new TreeMap<>();

	/** Universal random number generator. */
	private final Random RANDOM = new Random(System.nanoTime());

	/** Global clock. Synchronizes all game events */
	private Time CLOCK = new Time();	

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
	public ArrayList<Settlement> getSettlementSet(){
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

	public Time getClock() {
		return CLOCK;
	}


	/**
	 * Gets the character list.
	 *
	 * @return the character list
	 */
	public TreeMap<String,Person> getPersonSet() {
		return PEOPLE;
	}

	/**
	 * Returns a reference to the specified Settlement, if it exists. Otherwise, throws IllegalArgumentException.
	 *
	 * @param id Settlement String ID
	 * @return Settlement by ID
	 */
	public Settlement getSettlementByID(String id) {
						
		for(Settlement S : SETTLEMENTS) {
						
			if(S.getID().equals(id)) return S;
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Returns a reference to the specified Road, if it exists. Otherwise, throws IllegalArgumentException.
	 *
	 * @param Road String ID
	 * @return Road by ID
	 */
	public Road getRoadByID(String id) {
		for(Road R : ROADS) {
			if(R.getID().equals(id)) return R;
		}

		throw new IllegalArgumentException();
	}

	public Person getPersonByID(String id) { 
		for(Person C : PEOPLE.values()) {
			if(C.getID().equals(id)) return C;
		}

		throw new IllegalArgumentException();
	}

	public void updateCharacterLocations() {

		for(Person P : PEOPLE.values()) {
			
			if(!P.isTravelling()) {
				
				Settlement S = getSettlementByID(P.getLocationID());
				S.setPopulation(S.getCurrentPopulation() + 1);
				
			}
			
		}

	}
	
	public void updateRoadConnections() {
		
		for(Road R : ROADS) {
			
			Settlement A = getSettlementByID(R.getConnectingA());
			Settlement B = getSettlementByID(R.getConnectingB());
			
			if(!A.getRoads().contains(R)) {
				A.getRoads().add(R);
			}
			
			if(!B.getRoads().contains(R)) {
				B.getRoads().add(R);
			}
			
			
		}
		
	}

	public void setNewTime(String in) {
		CLOCK = new Time(in);
	}
	
	public Random getRandom() {
		return RANDOM;
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
			System.out.println(getSettlementByID(E.getConnectingA()).getName() + " - " + getSettlementByID(E.getConnectingB()).getName() + " : " + E.getLength());
		}

	}

	public ArrayList<String> Dijkstra(String startID, String endID) {


		ArrayList<String> route = new ArrayList<String>();

		Settlement start = WORLD.getSettlementByID(startID);
		Settlement end = WORLD.getSettlementByID(endID);
		
		for(Settlement S : SETTLEMENTS) S.purge();

		start.setFinalValue(0);
		start.setDone(true);

		Settlement active = start;

		while(!end.getDone()) {

			for(Road R : active.getRoads()) {
			
				String checkID;
				if(R.getConnectingA() == active.getID()) checkID = R.getConnectingB();
				else checkID = R.getConnectingA();
				
				Settlement check = WORLD.getSettlementByID(checkID);
				if(!check.getDone()) {
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

		route.add(endID);

		Settlement reverse = end;
		
		while(!reverse.equals(start)) {
			
			for(Road R : reverse.getRoads()) {
				
				if((reverse.getFinalValue() - R.getLength()) == getSettlementByID(R.getConnectingA()).getFinalValue()) {
					reverse = getSettlementByID(R.getConnectingA());
					break;
				}
				else if((reverse.getFinalValue() - R.getLength()) == getSettlementByID(R.getConnectingB()).getFinalValue()) {
					reverse = getSettlementByID(R.getConnectingB());
					break;
				}
			}
			
			route.add(reverse.getID());
		}

		Collections.reverse(route);

		return route;

	}

	/**
	 * Executes a single hour tick.
	 */
	public void doHourTick() {
		
		int timeStatus = 0;

		timeStatus = CLOCK.advanceHour();

		for(Person P : PEOPLE.values()) {

			//Travel decision making stage. Placeholder. Selects a random destination from all connected towns
			if (P.getDoDecisionTree()) {
				if(		CLOCK.getHour() == 0 
						&& P.getDoTravel() 
						&& !P.getPrepTravel() 
						&& !P.isTravelling()) {

					P.setDepartureHours(P.generateDepartureHour(RANDOM));			
					List<String> destinationPool = WORLD.getSettlementByID(P.getLocationID()).getConnectedSettlements();
					P.setDestination(destinationPool.get(RANDOM.nextInt(destinationPool.size())));
					P.setPrepTravel();
				}
			}

			//Characters advance
			if(P.isTravelling()) P.advanceTravel();

			//Characters depart
			if(P.getPrepTravel()) {
				if(P.getDepartureHours() == 0) {
					P.beginTravel();
				} else {
					P.decrementDepartureHours();
				}
			}
		}

		for(Settlement S : SETTLEMENTS) {

			//update population trackers
			if(timeStatus >= 2) {

				S.writeDailyPop();

				if(timeStatus >= 3) {

					S.writeMonthlyPop();
				}

			}

		}

	}

}
