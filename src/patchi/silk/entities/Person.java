package patchi.silk.entities;

import java.util.EnumSet;
import java.util.Random;

import patchi.patchiLib.math.PatchiMath;
import patchi.silk.foundation.Time;
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
	private String id;	
	/** Character first name. */
	private String firstName;	
	/** Character last name. */
	private String lastName;
	
	/** Active location. While travel flag is true, road is relevant. Otherwise, settlement. */
	private Settlement locationSettlement;
	private Road locationRoad;
	
	/** Destination settlement list index. */
	private Settlement destination;	
	/** The remaining distance to the next settlement. */
	private int remainingDistance = 0;		
	/** The hours remaining until an Character begins travelling. */
	private int departureHours = 0;
	/** Confidence modifier. Double between 0.0 and 1.0. Affects departure time. Currently unimplemented. */
	private double confidence = 1.0;
	
	//############################## OTHER ##############################//
	
	private final Inventory inventory = new Inventory();
	
	private EnumSet<CharacterFlags> FLAGS = EnumSet.noneOf(CharacterFlags.class);
	
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
	public Person(String id, String firstName, String lastName, Settlement location, boolean female, boolean doTravel, boolean doDecisionTree){

		this.id = id;
		locationSettlement = location;
		this.firstName = firstName;
		this.lastName = lastName;
		if(female) FLAGS.add(CharacterFlags.FEMALE);
		locationSettlement.addCharacter(this);
		if(doTravel) FLAGS.add(CharacterFlags.DO_TRAVEL);
		if(doDecisionTree) FLAGS.add(CharacterFlags.DO_DECISION_TREE);
		
	}

	public Person(String[] in) {
		
		this.id = in[0];
		this.firstName = in[1];
		this.lastName = in[2];
		if(Integer.parseInt(in[3]) == 0) FLAGS.add(CharacterFlags.FEMALE);
		locationSettlement = WORLD.getSettlementByID(in[4]);
		locationSettlement.addCharacter(this);

		if(new Random().nextBoolean()) FLAGS.add(CharacterFlags.DO_TRAVEL);
		FLAGS.add(CharacterFlags.DO_DECISION_TREE);
		
	}
	
	
	/** Initiates Character travel state. Sets travel flag, and handles setting of route length and destination. Automatically identifies route to the destination.  */
	public void beginTravel() {

		FLAGS.remove(CharacterFlags.PREP_TRAVEL);
		departureHours = 0;
		FLAGS.add(CharacterFlags.TRAVELLING);	
		Road path = locationSettlement.getRoadTo(destination);
		remainingDistance = path.getLength();
		locationRoad = path;
		locationSettlement.removeCharacter(this);
		
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
				locationSettlement = destination;
				locationSettlement.addCharacter(this);
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
		locationName = (isTravelling()) ? locationRoad.getName() : locationSettlement.getName();
		return locationName;
	}
	
	/**
	 * Sets the confidence coefficient. Automatically trims to [0.0,1.0].
	 *
	 * @param d confidence
	 */
	public void setConfidence(double d) {
		d = PatchiMath.cutDoubleToRange(d, 0.0, 1.0);
		confidence = d;
	}

	/**
	 * Returns the Character confidence coefficient.
	 *
	 * @return confidence coefficient
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * Returns a reference to the destination settlement
	 * 
	 * @return Settlement reference
	 */
	public Settlement getDestination() {
		return destination;
	}

	/** Sets the destination
	 * @param i Destination settlement reference
	 */
	public void setDestination(Settlement i) {
		destination = i;
	}

	public Settlement getLocationSettlement() {
		return locationSettlement;
	}

	public void setLocationSettlement(Settlement locationSettlement) {
		this.locationSettlement = locationSettlement;
	}

	public Road getLocationRoad() {
		return locationRoad;
	}

	public void setLocationRoad(Road locationRoad) {
		this.locationRoad = locationRoad;
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
	
}
