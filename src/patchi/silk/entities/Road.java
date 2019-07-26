package patchi.silk.entities;

import patchi.silk.foundation.World;

/**
 * Intermediary region between two settlements.
 */
public class Road {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Road ID. Corresponds to list index. */
	private String id;
		
	/** Connecting settlements */
	private String connectingA;
	private String connectingB;
	
	/** Road name. */
	private String name;
	
	/** Road length. */
	private int length;
	
	public Road(String[] in) {
		
		id = in[0];
		name = in[1];
		connectingA = in[2].split(";")[0];
		WORLD.getSettlementByID(connectingA).getRoads().add(this);
		connectingB = in[2].split(";")[1];
		WORLD.getSettlementByID(connectingB).getRoads().add(this);
		
	}
	
	public Road(String id) {
		this.id = id;
	}
	
	/**
	 * Returns the road ID.
	 *
	 * @return ID
	 */
	public String getID() {
		
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the length of the road.
	 *
	 * @return Road length
	 */
	public int getLength() {
		
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}

	public Settlement getConnectingA() {
		return WORLD.getSettlementByID(connectingA);
	}

	public void setConnectingA(String id) {
		connectingA = id;
	}
	
	public Settlement getConnectingB() {
		return WORLD.getSettlementByID(connectingB);
	}
	
	public void setConnectingB(String id) {
		connectingB = id;
	}
}
