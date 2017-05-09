package entities;

public class Road {

	final private int id;
	final public int[] connects = new int[2];
	final private String name;
	final private int length;

	public Road(String[] in) {

		id = Integer.parseInt(in[0]);
		name = in[1];
		connects[0] = Integer.parseInt(in[2].split(";")[0]);
		connects[1] = Integer.parseInt(in[2].split(";")[1]);
		length = Integer.parseInt(in[3]);

	}

	public int getID() {
		
		return id;	
	}
	
	public String getName() {

		return name;
	}

	public int[] getConnects() {

		return connects;
	}
	
	public int getLength() {
		
		return length;
	}
}
