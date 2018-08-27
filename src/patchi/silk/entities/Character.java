package patchi.silk.entities;

import java.util.Random;

import patchi.math.PatchiMath;
import patchi.silk.foundation.Time;
import patchi.silk.item.Inventory;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * Generic sentient humanoid entity.
 */
public class Character {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
			
	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;

	//############################## DATA PACKING ##############################//
	
	private String packedData;
	
	//############################## PROPERTIES ##############################//

	/** Character ID. */
	private int id;	
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
	
	//############################## FLAGS ##############################//

	/** Gender flag. True if female. */
	private boolean female;
	/** Travel flag. While true, town-based interactions and decisions are disabled. Characters with this flag will execute the travel routine each hour.*/
	private boolean travelling = false;	
	/** Illegitimacy flag. Integer representation of a boolean flag. True if the Character is conducting shady business. Affects departure time. Currently unimplemented. */
	private int illegitimacy = 0;
	/** DoTravel flag. While true, Character is subject to travel decision making at midnight each day.*/
	private boolean doTravel;
	/** PrepTravel flag. While true, the Character is preparing to leave.*/
	private boolean prepTravel = false;
	/** doDecisionTree flag. If true, run full decision tree each game tick **/
	private boolean doDecisionTree;	
	
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
	public Character(int id, String firstName, String lastName, Settlement location, boolean female, boolean doTravel, boolean doDecisionTree){

		this.id = id;
		locationSettlement = location;
		this.firstName = firstName;
		this.lastName = lastName;
		this.female = female;
		this.doTravel= doTravel;
		this.doDecisionTree = doDecisionTree;
		
	}

	/**
	 * Begins instantiation of a new Character. Populates Character list with empty Characters, ready to be unpacked from a data string.
	 * Ensures that loading never throws a NullPointer, regardless of initialisation order.
	 *
	 * @param packedData packed Character data string.
	 */
	public Character(String packedData) {
		this.packedData = packedData;
	}
	
	public void unpack() {
		
		String[] in = packedData.split(",");
		
		this.id = Integer.parseInt(in[0]);
		this.firstName = in[1];
		this.lastName = in[2];
		this.female = (Integer.parseInt(in[3]) == 0);
		locationSettlement = WORLD.getSettlementByID(in[4]);
		locationSettlement.addCharacter(this);

		this.doTravel = new Random().nextBoolean();
		this.doDecisionTree = true;
		
	}
	
	/** Initiates Character travel state. Sets travel flag, and handles setting of route length and destination. Automatically identifies route to the destination.  */
	public void beginTravel() {

		prepTravel = false;
		departureHours = 0;
		travelling = true;	
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

		if(travelling) {

			remainingDistance = remainingDistance - 6;

			if(remainingDistance <= 0) {
				remainingDistance = 0;
				travelling = false;
				locationSettlement = destination;
				locationSettlement.addCharacter(this);
			}
		}

		return travelling;

	}

	/**
	 * Generates a departure hour for this Character.
	 *
	 * @param RANDOM Project wide Math.Random object
	 * @return Generated hour of departure.
	 */
	public int generateDepartureHour(Random RANDOM) {

		int departure = PatchiMath.generateBinomialInt(23,((2.0 *Math.cos((Math.PI * CLOCK.getCurrentDayCount()) / 182.0 + (5.0 * Math.PI) / 91.0) + 6.0 + 1 - illegitimacy) / 23),RANDOM);
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
	 * Returns the remainingDistance as a read-only Property.
	 *
	 * @return  remainingDistance IntegerProperty
	 */
	public ReadOnlyIntegerWrapper getRemainingDistanceProperty() {
		return new ReadOnlyIntegerWrapper(remainingDistance);
	}
	
	/**
	 * Returns the state of the Character travel flag.
	 *
	 * @return travel flag state
	 */
	public boolean getTravelling() {
		return travelling;
	}

	/**
	 * Returns the travelling Property.
	 *
	 * @return travelling BolleanProperty
	 */
	public ReadOnlyBooleanWrapper getTravellingProperty() {
		return new ReadOnlyBooleanWrapper(travelling);
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
	 * Returns the firstName Property of the Character.
	 * 
	 * @return lastName Property
	 */
	public ReadOnlyStringWrapper getFirstNameProperty() {
		return new ReadOnlyStringWrapper(firstName);
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
	 * Returns the lastName Property of the Character
	 * 
	 * @return lastName property
	 */
	public ReadOnlyStringWrapper getLastNameProperty() {
		return new ReadOnlyStringWrapper(lastName);
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

	/**
	 * Returns departureHours as a property.
	 * 
	 * @return Returns departureHours as ReadOnlyIntegerWrapper
	 */
	public ReadOnlyIntegerWrapper getDepartureHoursProperty() {
		return new ReadOnlyIntegerWrapper(departureHours);
	}

	/** decrements the departureHours property */
	public void decrementDepartureHours() {
		departureHours--
		;
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
	 * Returns the value of the prepTravel flag.
	 *
	 * @return prepTravel flag state
	 */
	public boolean getPrepTravel() {
		return prepTravel;
	}

	/**
	 * Returns the prepTravel Property.
	 *
	 * @return prepTravel BooleanProperty
	 */
	public ReadOnlyBooleanWrapper getPrepTravelProperty() {
		return new ReadOnlyBooleanWrapper(prepTravel);
	}

	/**
	 * Returns the Character id.
	 *
	 * @return Character id
	 */
	public int getId() {
		return id;
	}

	/** 
	 * Returns the id Property 
	 * 
	 * @return id Property
	 */
	public ReadOnlyIntegerWrapper getIdProperty() {
		return new ReadOnlyIntegerWrapper(id);
	}

	/**
	 * Returns the name of the current location.
	 *
	 * @return Location name as ReadOnlyStringWrapper
	 */
	public ReadOnlyStringWrapper locationNameProperty() {
		String locationName;
		locationName = (travelling) ? locationRoad.getName() : locationSettlement.getName();
		return new ReadOnlyStringWrapper(locationName);
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
	 * Returns the confidence Property.
	 *
	 * @return confidence Property
	 */
	public ReadOnlyDoubleWrapper getConfidenceProperty() {
		return new ReadOnlyDoubleWrapper(confidence);
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
		return female;
	}
	
	public ReadOnlyBooleanWrapper getFemaleProperty() {
		return new ReadOnlyBooleanWrapper(female);
	}
	
	public boolean getDoDecisionTree() {
		return doDecisionTree;
	}
	
}
