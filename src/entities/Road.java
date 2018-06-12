package entities;

import java.util.List;

/**
 * Intermediary region between two settlements.
 */
public class Road {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementsSet();

	/** Road ID. Corresponds to list index. */
	final private int id;
		
	/** Connecting settlements */
	final private Settlement connectingA;
	final private Settlement connectingB;
	
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
		length = Integer.parseInt(in[3]);
		
		connectingA = SETTLEMENTS.get(Integer.parseInt(in[2].split(";")[0]));
		connectingB = SETTLEMENTS.get(Integer.parseInt(in[2].split(";")[1]));		
		connectingA.getRoads().add(this);
		connectingB.getRoads().add(this);
		
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
	 * Returns the length of the road.
	 *
	 * @return Road length
	 */
	public int getLength() {
		
		return length;
	}

	public Settlement getConnectingA() {
		return connectingA;
	}

	public Settlement getConnectingB() {
		return connectingB;
	}
}
