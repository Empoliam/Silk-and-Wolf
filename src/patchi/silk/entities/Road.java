package patchi.silk.entities;

/**
 * Intermediary region between two settlements.
 */
public class Road {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Road ID. Corresponds to list index. */
	private String id;
		
	/** Connecting settlements */
	private Settlement connectingA;
	private Settlement connectingB;
	
	/** Road name. */
	private String name;
	
	/** Road length. */
	private int length;
	
	public Road(String[] in) {
		
		id = in[0];
		name = in[1];
		connectingA = WORLD.getSettlementByID(in[2].split(";")[0]);
		connectingA.getRoads().add(this);
		connectingB = WORLD.getSettlementByID(in[2].split(";")[1]);
		connectingB.getRoads().add(this);
		
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
