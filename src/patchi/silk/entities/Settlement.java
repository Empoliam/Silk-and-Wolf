package patchi.silk.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import patchi.patchiLib.util.LimitedLinkedList;
import patchi.silk.market.GlobalStock;
import patchi.silk.market.LocalStock;

/**
 * A generic location in which characters and buildings are contained.
 */
public class Settlement {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Reference to global stock dataset */
	private static final HashMap<Integer, GlobalStock> STOCKS = WORLD.getGlobalStockSet();

	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** Arraylist of connecting roads */
	private ArrayList<Road> connectingRoads = new ArrayList<Road>();

	/** Name of the settlement. */
	private String name;

	/** Settlement ID.*/
	private String id;

	private List<Person> currentInhabitants = new LinkedList<Person>();
	private int population;
	private LimitedLinkedList<Integer> dailyPopulation = new LimitedLinkedList<>(30);
	
	/** Local market details */
	final private List<LocalStock> regionalMarket = new ArrayList<LocalStock>();

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

		for(GlobalStock G : STOCKS.values()) {
			regionalMarket.add(new LocalStock(G));
		}

	}


	/**
	 * Returns the settlement name.
	 *
	 * @return Settlement name
	 */
	public String getName() {

		return name;
	}
	
	public ReadOnlyStringWrapper getNameProperty() {
		
		return new ReadOnlyStringWrapper(name);
		
	}

	/**
	 * Gets the settlement ID.
	 *
	 * @return Settlement ID.
	 */
	public String getID(){

		return id;
	}

	public ArrayList<Settlement> getConnectedSettlements() {

		ArrayList<Settlement> C = new ArrayList<>();
		for(Road R : connectingRoads) {
			if(R.getConnectingA() != this) C.add(R.getConnectingA());
			else C.add(R.getConnectingB());
		}
		return C;

	}

	/**
	 * Returns the road to the given settlement.
	 *
	 * @param b ID of the destination settlement
	 * @return Road connecting the two settlements. Null if unconnected.
	 */
	public Road getRoadTo(Settlement s){

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

	public ReadOnlyIntegerWrapper getCurrentPopulationProperty() {
		return new ReadOnlyIntegerWrapper(currentInhabitants.size());
	}
	
	public int writeDailyPop() {
		
		dailyPopulation.add(population);
		return population;
		
	}
	
	public LinkedList<Integer> getDailyPop() {
		return dailyPopulation;
	}
	
}
