package entities;

import main.Main;

public class Human {

	//"Human" NPC IDs start at 2. Lawrence always has ID '0' and Holo always has ID '1'. If something is really fucked check that this statement is true.
	private static int ID_COUNTER = 0;

	final int id;
	final String firstName;
	final String lastName;

	//when travelling, use road ID, otherwise Settlement ID
	int location;

	private boolean travelling = false;
	int destination;
	int remainingDistance = 0;

	public Human(int location, String firstName, String lastName){
		
		//ensures each NPC has a unique ID
		id = ID_COUNTER;		
		ID_COUNTER++;

		this.location = location;
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Settlement getSettlement(){

		return Main.SETTLEMENTS.get(location);

	}

	public int getRemainingDistance() {

		return remainingDistance;

	}


	public void beginTravel(int destination) {

		travelling = true;
		Road path = Main.SETTLEMENTS.get(location).getRoadTo(destination);
		this.destination = destination;
		remainingDistance = path.getLength();
		location = path.getID();

	}

	public boolean advanceTravel() {

		if(travelling = true) {
			
			remainingDistance -= 40;

			if(remainingDistance<=0) {
				travelling = false;
				location = destination;
			}
		}

		return travelling;

	}
	
	public boolean isTravelling() {
		return travelling;
	}

}
