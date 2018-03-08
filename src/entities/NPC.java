package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPC {

	//dataset
	public static List<NPC> NPCS = new ArrayList<NPC>();
	
	//references
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	//"Human" NPC IDs start at 2. Lawrence always has ID '0' and Holo always has ID '1'. If something is really fucked check that this statement is true.
	private static int ID_COUNTER = 0;

	final int id;
	final String firstName;
	final String lastName;
	final boolean female;

	//when travelling, use road ID, otherwise Settlement ID
	int location;

	//during travel parameters
	private boolean travelling = false;
	int destination;
	int remainingDistance = 0;
	
	//travel decision parameters
	boolean doTravel;
	boolean prepTravel = false;
	int departsInHours = 0;

	public NPC(String firstName, String lastName, int location, boolean gender){
		
		//ensures each NPC has a unique ID
		id = ID_COUNTER;		
		ID_COUNTER++;

		this.location = location;
		this.firstName = firstName;
		this.lastName = lastName;
		this.female = gender;

	}
	
	public NPC(String[] in){
		
		id = ID_COUNTER;		
		ID_COUNTER++;
		this.firstName = in[0];
		this.lastName = in[1];
		this.female = Integer.parseInt(in[2]) == 0;
		this.location = Integer.parseInt(in[3]);
		
		this.doTravel = new Random().nextBoolean();
		
	}

	public Settlement getSettlement(){

		return SETTLEMENTS.get(location);

	}

	public int getRemainingDistance() {

		return remainingDistance;

	}


	public void beginTravel(int destination) {

		travelling = true;
		Road path = SETTLEMENTS.get(location).getRoadTo(destination);
		this.destination = destination;
		remainingDistance = path.getLength();
		location = path.getID();

	}

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
	
	public boolean isTravelling() {
		return travelling;
	}
	
	public String getFName() {
		return firstName;
	}
	public String getLName() {
		return lastName;
	}
	public boolean getDoTravel() {
		return doTravel;
	}
	public void setDepartureHours(int h) {
		departsInHours = h;
	}
	public void setPrepTravel(boolean f) {
		prepTravel = f;
	}
	public boolean getPrepTravel() {
		return prepTravel;
	}
	public int getDepartsHours() {
		return departsInHours;
	}
	
}
