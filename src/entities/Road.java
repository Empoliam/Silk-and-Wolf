package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Intermediary region between two settlements.
 */
public class Road {

	/** Full road set as ArrayList.*/
	public static final List<Road> ROADS = new ArrayList<Road>();
	
	/** Road ID. Corresponds to list index. */
	final private int id;
	
	/** Array containing IDs of both settlements conencted by the road.*/
	final private int[] connects = new int[2];
	
	/** Road name. */
	final private String name;
	
	/** Road length. */
	final private int length;

	/**
	 * Instantiates a new road.
	 *
	 * @param in Input String array. Format described <a href="https://github.com/Empoliam/Silk-and-Wolf/wiki/CSV-Data-Structure">here</a>
	 */
	public Road(String[] in) {

		id = Integer.parseInt(in[0]);
		name = in[1];
		connects[0] = Integer.parseInt(in[2].split(";")[0]);
		connects[1] = Integer.parseInt(in[2].split(";")[1]);
		length = Integer.parseInt(in[3]);

	}

	/**
	 * Returns the road ID.
	 *
	 * @return ID
	 */
	public int getID() {
		
		return id;	
	}
	
	/**
	 * Returns the road name.
	 *
	 * @return Road name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Returns the array describing the connected settlements.
	 *
	 * @return Array of connected settlements
	 */
	public int[] getConnects() {

		return connects;
	}
	
	/**
	 * Returns the length of the road.
	 *
	 * @return Road length
	 */
	public int getLength() {
		
		return length;
	}
}
