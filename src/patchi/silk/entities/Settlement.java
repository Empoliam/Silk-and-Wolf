package patchi.silk.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import patchi.patchiLib.util.LimitedLinkedList;
import patchi.silk.foundation.World;

/**
 * A generic location in which characters and buildings are contained.
 */
public class Settlement {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** Arraylist of connecting roads */
	private ArrayList<Road> connectingRoads = new ArrayList<Road>();

	/** Settlement ID.*/
	private final String id;

	/** Name of the settlement. */
	private String name;

	private List<Person> currentInhabitants = new LinkedList<Person>();
	private int population;
	
	private static int POPULATION_TRACKING_SIZE = 30;
	private LimitedLinkedList<Integer> dailyPopulation = new LimitedLinkedList<>(POPULATION_TRACKING_SIZE);
	private LimitedLinkedList<Integer> monthlyPopulation = new LimitedLinkedList<>(POPULATION_TRACKING_SIZE);
	private int monthRunningTot = 0;
	private int monthN = 0;


	/**Parameters used in executing dijkstra's algorithm */
	protected int workingValue;
	protected int finalValue;
	protected boolean done;

	/**
	 * Instantiates a new settlement.
	 *
	 * @param in input String array. Format described <a href="https://github.com/Empoliam/Silk-and-Wolf/wiki/CSV-Data-Structure">here</a>
	 */
	public Settlement(String[] in) {

		id = in[0];
		name = in[1];

	}

	public Settlement(String id) {
		this.id = id;
	}


	/**
	 * Returns the settlement name.
	 *
	 * @return Settlement name
	 */
	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the settlement ID.
	 *
	 * @return Settlement ID.
	 */
	public String getID(){

		return id;
	}

	public ArrayList<String> getConnectedSettlements() {
		
		ArrayList<String> C = new ArrayList<>();
		for(Road R : connectingRoads) {
			if(!R.getConnectingA().equals(this.id)) C.add(R.getConnectingA());
			else C.add(R.getConnectingB());
		}				
		return C;

	}

	/**
	 * Returns the road to the given settlement.
	 *
	 * @param s ID of the destination settlement
	 * @return Road connecting the two settlements. Null if unconnected.
	 */
	public Road getRoadTo(String s){

		//Check each connecting road until the appropriate connection is found
		for(Road r : connectingRoads) {
			if(r.getConnectingA().equals(s) || r.getConnectingB().equals(s)) {
				return r;
			}
		}

		return null;
	}

	/** Returns arraylist of connected roads */
	public ArrayList<Road> getRoads() {
		return connectingRoads;
	}

	//----------------------------------Dijkstra methods--------------------------------------------//

	public void purge() {
		workingValue = Integer.MAX_VALUE;
		done = false;
	}

	public int getWorkingValue() {
		return workingValue;
	}

	public void setWorkingValue(int workingValue) {
		this.workingValue = workingValue;
	}

	public int getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(int finalValue) {
		this.finalValue = finalValue;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	//--------------------------------------------END----------------------------------------------//

	public void addCharacter(Person A) {
		currentInhabitants.add(A);
		population++;
	}

	public void removeCharacter(Person A) {
		currentInhabitants.remove(A);
		population--;
	}

	public int getCurrentPopulation() {
		return population;
	}

	public void setPopulation(int pop) {
		this.population = pop;
	}

	public int writeDailyPop() {

		dailyPopulation.add(population);
		monthRunningTot += population;
		monthN++;
		return population;

	}

	public List<Integer> getDailyPop() {
		return dailyPopulation;
	}

	public int writeMonthlyPop() {

		monthlyPopulation.add(Math.floorDiv(monthRunningTot, monthN));
		monthRunningTot = 0;
		monthN = 0;
		return population;

	}

	public List<Integer> getMonthlyPop() {
		return monthlyPopulation;
	}

	public String getMonthlyPopString() {

		String s = monthRunningTot + ";" +  monthN + ";";

		Iterator<Integer> monthly = getDailyPop().iterator();
		while(monthly.hasNext()) {
			Integer I = monthly.next();
			s = s + (I.toString());
			if(monthly.hasNext()) {
				s = s + ",";
			}
		}

		return s;

	}

	public void setDailyPop(LimitedLinkedList<Integer> in) {
		dailyPopulation = in;
	}

	public void parseDailyPopString(String in) {

		String[] stringList = in.split(",");
		List<Integer> popList = new LinkedList<Integer>();
		for(String str : stringList) {
			popList.add(Integer.parseInt(str));
		}
		
		dailyPopulation = new LimitedLinkedList<Integer>(popList, POPULATION_TRACKING_SIZE);
		
	}

	public void parseMonthlyPopString(String in) {

		String[] init = in.split(";");
		
		monthRunningTot = Integer.parseInt(init[0]);
		monthN = Integer.parseInt(init[1]);
		
		String[] stringList = init[2].split(",");
		List<Integer> popList = new LinkedList<Integer>();
		for(String str : stringList) {
			popList.add(Integer.parseInt(str));
		}
		
		monthlyPopulation = new LimitedLinkedList<Integer>(popList, POPULATION_TRACKING_SIZE);
		
	}

}
