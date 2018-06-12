package entities;

import java.util.List;
import java.util.Random;

import foundation.SWMath;
import foundation.Time;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Generic sentient humanoid entity. All human NPCs are handled by the same hourly decision tree, including Lawrence and Holo.
 */
public class NPC {

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementsSet();
			
	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;

	//############################## PROPERTIES ##############################//

	/** NPC ID. Equivalent to HashMap key*/
	private final IntegerProperty id = new SimpleIntegerProperty();	
	/** NPC first name. */
	private final StringProperty firstName = new SimpleStringProperty();	
	/** NPC last name. */
	private final StringProperty lastName = new SimpleStringProperty();
	
	/** Active location. While travel flag is true, road is relevant. Otherwise, settlement. */
	private Settlement locationSettlement;
	private Road locationRoad;
	
	/** Destination settlement list index. */
	private Settlement destination;	
	/** The remaining distance to the next settlement. */
	private IntegerProperty remainingDistance = new SimpleIntegerProperty(0);		
	/** The hours remaining until an NPC begins travelling. */
	private IntegerProperty departureHours = new SimpleIntegerProperty(0);
	/** Confidence modifier. Double between 0.0 and 1.0. Affects departure time. Currently unimplemented. */
	private DoubleProperty confidence = new SimpleDoubleProperty(0.0);

	//############################## FLAGS ##############################//

	/** Gender flag. True if female. */
	private final BooleanProperty female = new SimpleBooleanProperty();
	/** Travel flag. While true, town-based interactions and decisions are disabled. NPCs with this flag will execute the travel routine each hour.*/
	private BooleanProperty travelling = new SimpleBooleanProperty(false);	
	/** Illegitimacy flag. Integer representation of a boolean flag. True if the NPC is conducting shady business. Affects departure time. Currently unimplemented. */
	private IntegerProperty illegitimacy = new SimpleIntegerProperty(0);
	/** DoTravel flag. While true, NPC is subject to travel decision making at midnight each day.*/
	private BooleanProperty doTravel = new SimpleBooleanProperty();
	/** PrepTravel flag. While true, the NPC is preparing to leave.*/
	private BooleanProperty prepTravel = new SimpleBooleanProperty(false);

	/**
	 * Instantiates a new npc.
	 *
	 * @param id NPC id. Same as HashMap key.
	 * @param firstName NPC First Name
	 * @param lastName NPC Last Name
	 * @param location Settlement that an NPC initializes in
	 * @param female NPC gender flag
	 */
	public NPC(int id, String firstName, String lastName, Settlement location, boolean female, boolean doTravel){

		this.id.set(id);
		locationSettlement = location;
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.female.set(female);
		this.doTravel.set(doTravel);
		
	}

	/**
	 * Instantiates a new npc.
	 *
	 * @param id NPC id. Same as HashMap key.
	 * @param in Input String array. Format described <a href="https://github.com/Empoliam/Silk-and-Wolf/wiki/CSV-Data-Structure">here</a>
	 */
	public NPC(int id, String[] in){

		this.id.set(id);
		this.firstName.set(in[0]);
		this.lastName.set(in[1]);
		this.female.set(Integer.parseInt(in[2]) == 0);
		locationSettlement = SETTLEMENTS.get(Integer.parseInt(in[3]));

		this.doTravel.set(new Random().nextBoolean());

	}

	/** Initiates NPC travel state. Sets travel flag, and handles setting of route length and destination. Automatically identifies route to the destination.  */
	public void beginTravel() {

		prepTravel.set(false);
		departureHours.set(0);
		travelling.set(true);	
		Road path = locationSettlement.getRoadTo(destination);
		remainingDistance.set(path.getLength());
		locationRoad = path;
	}

	/**
	 * Advance travel.<br>
	 * Decreases distance to next settlement by 40km. Automatically handles arrival at settlement if remaining distance decreases.
	 *
	 * @return False if destination is reached, else true.
	 */
	public boolean advanceTravel() {

		if(travelling.get() == true) {

			remainingDistance.set(remainingDistance.get() - 6);

			if(remainingDistance.get() <= 0) {
				remainingDistance.set(0);
				travelling.set(false);
				locationSettlement = destination;
			}
		}

		return travelling.get();

	}

	/**
	 * Generates a departure hour for this NPC.
	 *
	 * @param RANDOM Project wide Math.Random object
	 * @return Generated hour of departure.
	 */
	public int generateDepartureHour(Random RANDOM) {

		int departure = SWMath.generateBinomialInt(23,((2.0 *Math.cos((Math.PI * CLOCK.getCurrentDayCount()) / 182.0 + (5.0 * Math.PI) / 91.0) + 6.0 + 1 - illegitimacy.get()) / 23),RANDOM);
		return departure;

	}

	//############################## GETTERS AND SETTERS AND ALL THAT ##############################//

	/**
	 * Returns the remaining distance to the next settlement.
	 *
	 * @return Remaining distance
	 */
	public int getRemainingDistance() {

		return remainingDistance.get();

	}

	/**
	 * Returns the remainingDistance Property.
	 *
	 * @return  remainingDistance IntegerProperty
	 */
	public IntegerProperty getRemainingDistanceProperty() {
		return remainingDistance;
	}
	
	/**
	 * Returns the state of the NPC travel flag.
	 *
	 * @return travel flag state
	 */
	public boolean getTravelling() {
		return travelling.get();
	}

	/**
	 * Returns the travelling Property.
	 *
	 * @return travelling BolleanProperty
	 */
	public BooleanProperty getTravellingProperty() {
		return travelling;
	} 
	
	/**
	 * Returns the first name of the NPC.
	 *
	 * @return NPC's First Name
	 */
	public String getFirstName() {
		return firstName.get();
	}

	/** 
	 * Returns the firstName Property of the NPC.
	 * 
	 * @return lastName Property
	 */
	public StringProperty getFirstNameProperty() {
		return firstName;
	}

	/**
	 * Returns the last name of the NPC.
	 *
	 * @return NPC's Last Name
	 */
	public String getLastName() {
		return lastName.get();
	}

	/**
	 * Returns the lastName Property of the NPC
	 * 
	 * @return lastName property
	 */
	public StringProperty getLastNameProperty() {
		return lastName;
	}

	/**
	 * Checks if the doTravel flag is set.
	 *
	 * @return doTravel flag state
	 */
	public boolean getDoTravel() {
		return doTravel.get();
	}

	/**
	 * Sets the number of hours until an NPC departs.
	 *
	 * @param h Hours from set time
	 */
	public void setDepartureHours(int h) {
		departureHours.set(h);
	}

	/**
	 * Returns the remaining hours until departure.
	 *
	 * @return Remaining hours until departure
	 */
	public int getDepartureHours() {
		return departureHours.get();
	}

	/**
	 * Returns the IntegerProperty of departureHours.
	 * 
	 * @return Returns departureHours as IntegerProperty
	 */
	public IntegerProperty getDepartureHoursProperty() {
		return departureHours;
	}

	/** decrements the departureHours property */
	public void decrementDepartureHours() {
		departureHours.set(departureHours.get()-1);
	}

	/**
	 * Sets the prepTravel flag.
	 *
	 * @param f Desired state
	 */
	public void setPrepTravel(boolean f) {
		prepTravel.set(f);
	}

	/**
	 * Returns the value of the prepTravel flag.
	 *
	 * @return prepTravel flag state
	 */
	public boolean getPrepTravel() {
		return prepTravel.get();
	}

	/**
	 * Returns the prepTravel Property.
	 *
	 * @return prepTravel BooleanProperty
	 */
	public BooleanProperty getPrepTravelProperty() {
		return prepTravel;
	}

	/**
	 * Returns the NPC id.
	 *
	 * @return NPC id
	 */
	public int getId() {
		return id.get();
	}

	/** 
	 * Returns the id Property 
	 * 
	 * @return id Property
	 */
	public IntegerProperty getIdProperty() {
		return id;
	}

	/**
	 * Returns the name of the current location.
	 *
	 * @return Location name as ReadOnlyStringWrapper
	 */
	public ReadOnlyStringWrapper locationNameProperty() {
		String locationName;
		locationName = (travelling.get()) ? locationRoad.getName() : locationSettlement.getName();
		return new ReadOnlyStringWrapper(locationName);
	}

	/**
	 * Sets the confidence coefficient. Automatically trims to [0.0,1.0].
	 *
	 * @param d confidence
	 */
	public void setConfidence(double d) {
		d = SWMath.cutDoubleToRange(d, 0.0, 1.0);
		confidence.set(d);
	}

	/**
	 * Returns the NPC confidence coefficient.
	 *
	 * @return confidence coefficient
	 */
	public double getConfidence() {
		return confidence.get();
	}

	/**
	 * Returns the confidence Property.
	 *
	 * @return confidence Property
	 */
	public DoubleProperty getConfidenceProperty() {
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
	
}
