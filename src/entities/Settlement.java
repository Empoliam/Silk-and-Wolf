package entities;

import java.util.ArrayList;
import java.util.List;
import static main.Main.ROADS;

public class Settlement {

	final private int[] roads;
	final private String name;
	final private int id;

	public Settlement(String[] in) {

		id = Integer.parseInt(in[0]);
		name = in[1];

		String[] r = in[2].split(";");
		roads = new int[r.length];
		for(int k = 0; k < r.length; k++) {
			roads[k] = Integer.parseInt(r[k]);
		}	
	}

	public String getName() {

		return name;
	}

	public int getID(){

		return id;
	}

	public List<Integer> connectedTo()
	{

		List<Integer> out = new ArrayList<Integer>();
		for (int rID : roads) {
			int[] connected = ROADS.get(rID).getConnects();		
			for (int cID : connected) {
				if(cID != id) {
					out.add(cID);
				}
			}
		}

		return out;
	}

	public Road getRoadTo(int b){

		//Check each connecting road until the appropriate connection is found
		for(int r : roads) {
			if(ROADS.get(r).getConnects()[0] == b || ROADS.get(r).getConnects()[1] == b) {
				return ROADS.get(r);
			}
		}

		return null;
	}
}
