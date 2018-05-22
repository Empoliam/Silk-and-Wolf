package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import market.GlobalStock;
import market.LocalStock;

/**
 * A generic location in which NPCs and buildings are contained.
 */
public class Settlement {

	/** Full settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = new ArrayList<Settlement>();
	
	/** Reference to global stock dataset */
	private static final HashMap<Integer, GlobalStock> STOCKS = GlobalStock.STOCKS;
	
	/** Reference to roads dataset. */
	//references
	private static final List<Road> ROADS = Road.ROADS;
		
	/** Array of roads connected to the settlement. */
	final private int[] roads;
	
	/** Name of the settlement. */
	final private String name;
	
	/** Settlement ID. Corresponds to list index. */
	final private int id;
	
	/** Local market details */
	final private List<LocalStock> regionalMarket = new ArrayList<LocalStock>();
	
	/**
	 * Instantiates a new settlement.
	 *
	 * @param in input String array. Format described <a href="https://github.com/Empoliam/Silk-and-Wolf/wiki/CSV-Data-Structure">here</a>
	 */
	public Settlement(String[] in) {

		id = Integer.parseInt(in[0]);
		name = in[1];

		String[] r = in[2].split(";");
		roads = new int[r.length];
		for(int k = 0; k < r.length; k++) {
			roads[k] = Integer.parseInt(r[k]);
		}	
		
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

	/**
	 * Gets the settlement ID.
	 *
	 * @return Settlement ID.
	 */
	public int getID(){

		return id;
	}

	/**
	 * Computes which other settlements are directly connected to the settlement by roads.
	 *
	 * @return List of indexes of connected settlements
	 */
	public List<Integer> connectedTo()
	{

		List<Integer> out = new ArrayList<Integer>();
		for (int rID : roads) {
			int[] connected = ROADS.get(rID).getConnects();		
			for (int cID : connected) {
				if(cID != id) {
					out.add(cID);
				}
			}
		}

		return out;
	}

	/**
	 * Returns the road to the given settlement.
	 *
	 * @param b ID of the destination settlement
	 * @return Road connecting the two settlements. Null if unconnected.
	 */
	public Road getRoadTo(int b){

		//Check each connecting road until the appropriate connection is found
		for(int r : roads) {
			if(Road.ROADS.get(r).getConnects()[0] == b || ROADS.get(r).getConnects()[1] == b) {
				return ROADS.get(r);
			}
		}

		return null;
	}
}
