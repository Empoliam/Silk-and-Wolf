package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import foundation.SWMath;
import foundation.Time;

/**
 * Generic sentient humanoid entity. All human NPCs are handled by the same hourly decision tree, including Lawrence and Holo.
 */
public class NPC {
	
	/** Full NPC set as HashMap. */
	public static HashMap<Integer,NPC> NPCS = new HashMap<Integer,NPC>();
	
	/** Reference to Settlements List */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;
	
	/** NPC first name. */
	final String firstName;
	
	/** NPC last name. */
	final String lastName;
	
	/** NPC gender flag. True if female. */
	final boolean female;

	/** Active location id. While travel flag is true, refers to a road. Otherwise, refers to a settlement. */
	int location;

	/** Travel flag. While true, town-based interactions and decisions are disabled. NPCs with this flag will execute the travel routine each hour.*/
	private boolean travelling = false;
	
	/** Destination settlement list index. */
	int destination;
	
	/** The remaining distance to the next settlement. */
	int remainingDistance = 0;
	
	/** DoTravel flag. While true, NPC is subject to travel decision making at midnight each day.*/
	boolean doTravel;
	
	/** PrepTravel flag. While true, the NPC is preparing to leave.*/
	boolean prepTravel = false;
	
	/** The hours remaining until an NPC begins travelling. */
	int departsInHours = 0;
	
	/** Illegitimacy flag. Integer representation of a boolean flag. True if the NPC is conducting shady business. Affects departure time. Currently unimplemented. */
	int illegitimacy = 0;
	
	/** Confidence modiefier. Double between 0.0 and 1.0. Affects departure time. Currently unimplemented. */
	double confidence = 1.0;

	/**
	 * Instantiates a new npc.
	 *
	 * @param firstName NPC First Name
	 * @param lastName NPC Last Name
	 * @param location Settlement that an NPC initializes in
	 * @param gender NPC gender flag
	 */
	public NPC(String firstName, String lastName, int location, boolean gender){
		
		this.location = location;
		this.firstName = firstName;
		this.lastName = lastName;
		this.female = gender;

	}
	
	/**
	 * Instantiates a new npc.
	 *
	 * @param in Input String array. Format described <a href="https://github.com/Empoliam/Silk-and-Wolf/wiki/CSV-Data-Structure">here</a>
	 */
	public NPC(String[] in){
		
		this.firstName = in[0];
		this.lastName = in[1];
		this.female = Integer.parseInt(in[2]) == 0;
		this.location = Integer.parseInt(in[3]);
		
		this.doTravel = new Random().nextBoolean();
		
	}

	/**
	 * Returns the location index of the NPC. Refers to a settlement usually, but refers to a road if the travel flag is set.
	 *
	 * @return Location index
	 */
	public int getLocation(){

		return location;

	}

	/**
	 * Returns the remaining distance to the next settlement.
	 *
	 * @return Remaining distance
	 */
	public int getRemainingDistance() {

		return remainingDistance;

	}


	/**
	 * Initiates NPC travel state. Sets travel flag, and handles setting of route length and destination. Automatically identifies route to the destination. 
	 *
	 * @param destination Destination settlement list index
	 */
	public void beginTravel(int destination) {

		travelling = true;
		Road path = SETTLEMENTS.get(location).getRoadTo(destination);
		this.destination = destination;
		remainingDistance = path.getLength();
		location = path.getID();

	}

	/**
	 * Advance travel.<br>
	 * Decreases distance to next settlement by 40km. Automatically handles arrival at settlement if remaining distance decreases.
	 *
	 * @return False if destination is reached, else true.
	 */
	public boolean advanceTravel() {

		if(travelling = true) {
			
			remainingDistance -= 40;

			if(remainingDistance<=0) {
				remainingDistance = 0;
				travelling = false;
				location = destination;
			}
		}

		return travelling;

	}
	
	/**
	 * Checks if NPC travel flag is set
	 *
	 * @return travel flag state
	 */
	public boolean isTravelling() {
		return travelling;
	}
	
	/**
	 * Returns the First Name of the NPC.
	 *
	 * @return NPC's First Name
	 */
	public String getFName() {
		return firstName;
	}
	
	/**
	 * Returns the Last Name of the NPC.
	 *
	 * @return NPC's Last Name
	 */
	public String getLName() {
		return lastName;
	}
	
	/**
	 * Checks if the doTravel flag is set.
	 *
	 * @return doTravel flag state
	 */
	public boolean getDoTravel() {
		return doTravel;
	}
	
	/**
	 * Sets the number of hours until an NPC departs.
	 *
	 * @param h Hours from set time
	 */
	public void setDepartureHours(int h) {
		departsInHours = h;
	}
	
	/**
	 * Sets the prepTravel flag.
	 *
	 * @param f Desired state
	 */
	public void setPrepTravel(boolean f) {
		prepTravel = f;
	}
	
	/**
	 * Checks if the prepTravel flag is set.
	 *
	 * @return prepTravel flag state
	 */
	public boolean getPrepTravel() {
		return prepTravel;
	}
	
	/**
	 * Returns the remaining hours until departure.
	 *
	 * @return Remaining hours until departure
	 */
	public int getDepartsHours() {
		return departsInHours;
	}
	
	/**
	 * Generates a departure hour for this NPC.
	 *
	 * @param RANDOM Project wide Math.Random object
	 * @return Generated hour of departure.
	 */
	public int generateDepartureHour(Random RANDOM) {
		
		int departure = SWMath.generateBinomialInt(23,((2.0 *Math.cos((Math.PI * CLOCK.getCurrentDayCount()) / 182.0 + (5.0 * Math.PI) / 91.0) + 6.0 + 1 - illegitimacy) / 23),RANDOM);
		return departure;
		
	}
	
}
