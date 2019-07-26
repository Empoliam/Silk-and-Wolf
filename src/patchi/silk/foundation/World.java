package patchi.silk.foundation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import patchi.silk.entities.Person;
import patchi.silk.entities.Road;
import patchi.silk.entities.Settlement;
import patchi.silk.market.GlobalStock;

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

	/** Full character dataset ArrayList. Automatically sorts when adding new character. */
	private final ArrayList<Person> PEOPLE = new ArrayList<Person>();

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
	 * Gets the global stock set.
	 *
	 * @return the global stock set
	 */
	public HashMap<Integer,GlobalStock> getGlobalStockSet() {
		return STOCKS;
	}

	/**
	 * Gets the character list.
	 *
	 * @return the character list
	 */
	public ArrayList<Person> getPersonSet() {
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
		for(Person C : PEOPLE) {
			if(C.getID().equals(id)) return C;
		}

		throw new IllegalArgumentException();
	}

	public void updateCharacterLocations() {

		for(Person P : PEOPLE) {
			
			if(!P.isTravelling()) {
				
				Settlement S = getSettlementByID(P.getLocationID());
				S.setPopulation(S.getCurrentPopulation() + 1);
				
			}
			
		}

	}
	
	public void updateRoadConnections() {
		
		for(Road R : ROADS) {
						
			if(!R.getConnectingA().getRoads().contains(R)) {
				R.getConnectingA().getRoads().add(R);
			}
			
			if(!R.getConnectingB().getRoads().contains(R)) {
				R.getConnectingB().getRoads().add(R);
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

	/**
	 * Executes a single hour tick.
	 */
	public void doHourTick() {

		int timeStatus = 0;

		timeStatus = CLOCK.advanceHour();

		for(Person P : PEOPLE) {

			//Travel decision making stage. Placeholder. Selects a random destination from all connected towns
			if (P.getDoDecisionTree()) {
				if(		CLOCK.getHour() == 0 
						&& P.getDoTravel() 
						&& !P.getPrepTravel() 
						&& !P.isTravelling()) {

					P.setDepartureHours(P.generateDepartureHour(RANDOM));			
					List<Settlement> destinationPool = WORLD.getSettlementByID(P.getLocationID()).getConnectedSettlements();
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
