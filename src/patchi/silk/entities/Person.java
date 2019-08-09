package patchi.silk.entities;

import java.util.EnumSet;
import java.util.Random;

import patchi.patchiLib.math.PatchiMath;
import patchi.silk.foundation.Time;
import patchi.silk.foundation.World;
import patchi.silk.item.Inventory;

/**
 * Generic sentient humanoid entity.
 */
public class Person {

	/** Main World reference */
	static final World WORLD = World.getMainWorld();
			
	/** Reference to global clock */
	static final Time CLOCK = WORLD.getClock();
		
	//############################## PROPERTIES ##############################//

	/** Character ID. */
	private final String id;	
	/** Character first name. */
	private String firstName = "null";	
	/** Character last name. */
	private String lastName = "null";
	
	/** Active location. Refers to settlement or road. */
	private String location;

	/** Destination settlement list index. */
	private String destination;	
	/** The remaining distance to the next settlement. */
	private int remainingDistance = 0;		
	/** The hours remaining until an Character begins travelling. */
	private int departureHours = 0;
	/** Confidence modifier. Double between 0.0 and 1.0. Affects departure time. Currently unimplemented. */
	private float confidence = 1.0f;
	
	//############################## OTHER ##############################//
	
	private final Inventory inventory = new Inventory();
	
	private EnumSet<CharacterFlags> FLAGS = EnumSet.noneOf(CharacterFlags.class);
	
	public Person(String id) {
		
		this.id = id;
		
	}
	
	/**
	 * Instantiates a new Character.
	 *
	 * @param id Character id.
	 * @param firstName Character First Name
	 * @param lastName Character Last Name
	 * @param location Settlement that an Character initializes in
	 * @param female Character gender flag
	 * @param doTravel doTravel flag
	 * @param doDecisionTree doDecisionTree flag
	 */
	public Person(String id, String firstName, String lastName, String location){

		this.id = id;
		
		this.location = location;
		WORLD.getSettlementByID(location).addCharacter(this);
		
		this.firstName = firstName;
		this.lastName = lastName;
				
	}

	public Person(String[] in) {
		
		this.id = in[0];
		
		this.firstName = in[1];
		this.lastName = in[2];
		
		this.location = in[4];
		WORLD.getSettlementByID(location).addCharacter(this);
		
		if(Integer.parseInt(in[3]) == 0) FLAGS.add(CharacterFlags.FEMALE);
		if(new Random().nextBoolean()) FLAGS.add(CharacterFlags.DO_TRAVEL);
		FLAGS.add(CharacterFlags.DO_DECISION_TREE);
		
	}
	
	
	/** Initiates Character travel state. Sets travel flag, and handles setting of route length and destination. Automatically identifies route to the destination.  */
	public void beginTravel() {

		FLAGS.remove(CharacterFlags.PREP_TRAVEL);
		departureHours = 0;
		FLAGS.add(CharacterFlags.TRAVELLING);	
		Road path = WORLD.getSettlementByID(location).getRoadTo(destination);
		remainingDistance = path.getLength();
		WORLD.getSettlementByID(location).removeCharacter(this);
		location = path.getID();
	}

	/**
	 * Advance travel.<br>
	 * Decreases distance to next settlement by 40km. Automatically handles arrival at settlement if remaining distance decreases.
	 *
	 * @return False if destination is reached, else true.
	 */
	public boolean advanceTravel() {

		if(isTravelling()) {
			
			remainingDistance = remainingDistance - 6;

			if(remainingDistance <= 0) {
				remainingDistance = 0;
				FLAGS.remove(CharacterFlags.TRAVELLING);
				location = destination;
				WORLD.getSettlementByID(location).addCharacter(this);
			}
		}

		return isTravelling();

	}

	/**
	 * Generates a departure hour for this Character.
	 *
	 * @param RANDOM Project wide Math.Random object
	 * @return Generated hour of departure.
	 */
	public int generateDepartureHour(Random RANDOM) {

		double sunrise = CLOCK.getSunriseTime();
		double daylength = CLOCK.getCurrentDayLength();
		
		double base = sunrise + 1;
		double confidenceMod = ((1 - confidence) / 5.0) * daylength;
		double finalProb = (base + confidenceMod) / 23.0;
		
		int departure = PatchiMath.generateBinomialInt(23,finalProb,RANDOM);
		return departure;

	}

	//############################## INVENTORY MANAGEMENT ##############################//
		
	public Inventory getInventory() {
		return inventory;
	}
	
	//############################## GETTERS / SETTERS ##############################//

	/**
	 * Sets the remaining distance to the next settlement.
	 *
	 * @return Remaining distance
	 */
	public void setRemainingDistance(int d) {

		remainingDistance = d;

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
	 * Returns the first name of the Character.
	 *
	 * @return Character's First Name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the last name of the Character.
	 *
	 * @return Character's Last Name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Checks if the doTravel flag is set.
	 *
	 * @return doTravel flag state
	 */
	public boolean getDoTravel() {
		return FLAGS.contains(CharacterFlags.DO_TRAVEL);
	}

	/**
	 * Sets the number of hours until an Character departs.
	 *
	 * @param h Hours from set time
	 */
	public void setDepartureHours(int h) {
		departureHours = h;
	}

	/**
	 * Returns the remaining hours until departure.
	 *
	 * @return Remaining hours until departure
	 */
	public int getDepartureHours() {
		return departureHours;
	}

	/** decrements the departureHours property */
	public void decrementDepartureHours() {
		departureHours--
		;
	}


	/**
	 * Returns the value of the prepTravel flag.
	 *
	 * @return prepTravel flag state
	 */
	public boolean getPrepTravel() {
		return FLAGS.contains(CharacterFlags.PREP_TRAVEL);
	}

	/**
	 * Returns the Character id.
	 *
	 * @return Character id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Returns the name of the current location.
	 *
	 * @return Location name as String
	 */
	public String locationName() {
		String locationName;
		locationName = (isTravelling()) ? WORLD.getRoadByID(location).getName() : WORLD.getSettlementByID(location).getName();
		return locationName;
	}
	
	/**
	 * Sets the confidence coefficient. Automatically trims to [0.0,1.0].
	 *
	 * @param d confidence
	 */
	public void setConfidence(float d) {
		d = (float) PatchiMath.cutDoubleToRange(d, 0.0, 1.0);
		confidence = d;
	}

	/**
	 * Returns the Character confidence coefficient.
	 *
	 * @return confidence coefficient
	 */
	public float getConfidence() {
		return confidence;
	}

	/**
	 * Returns a reference to the destination settlement
	 * 
	 * @return Settlement reference
	 */
	public String getDestination() {
		return destination;
	}

	/** Sets the destination
	 * @param i Destination settlement reference
	 */
	public void setDestination(String dest) {
		destination = dest;
	}
	
	public String getLocationID() {
		return location;
	}
	
	public void setLocationID(String location) {
		this.location = location;
	}
	
	public boolean getFemale() {
		return FLAGS.contains(CharacterFlags.FEMALE);
	}

	public boolean getDoDecisionTree() {
		return FLAGS.contains(CharacterFlags.DO_DECISION_TREE);
	}
	
	public boolean isTravelling() {
		return FLAGS.contains(CharacterFlags.TRAVELLING);
	}

	public void setPrepTravel() {
		FLAGS.add(CharacterFlags.PREP_TRAVEL);
	}

	public String getName() {

		return getFirstName() + " " + getLastName();
	}

	public void addFlags(CharacterFlags ... flags) {
		
		for(CharacterFlags F : flags) {
			
			FLAGS.add(F);
			
		}
		
	}
	
	public EnumSet<CharacterFlags> getFlags () {
		
		return FLAGS;
		
	}
	
	public void setFirstName(String fName) {
		this.firstName = fName;
	}
	
	public void setLastName(String lName) {
		this.lastName = lName;
	}
	
}
