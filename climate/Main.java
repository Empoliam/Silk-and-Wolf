
public class Main {

	static ClimateGrid MAINGRID = new ClimateGrid(21, 21);

	public static void main(String[] args) {

		MAINGRID.getCell(0, 10).setCloudSize(5.00);
		MAINGRID.getCell(1, 10).setCloudSize(3.00);	
		MAINGRID.getCell(2, 10).setCloudSize(3.00);
		MAINGRID.getCell(0, 11).setCloudSize(5.00);
		MAINGRID.getCell(0, 9).setCloudSize(5.00);
		
		for (int x = 0; x < 24; x ++) {
			MAINGRID.cloudSizeDump();
			MAINGRID = MAINGRID.CloudPropogate();
		}

		MAINGRID.cloudSizeDump();
		
	}

}
