import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClimateGrid {

	//Outer list X, Inner list Y;
	private ArrayList<ArrayList<ClimateCell>> Grid = new ArrayList<ArrayList<ClimateCell>>();

	private int XSIZE, YSIZE;

	private ClimateGrid() {}
	
	public ClimateGrid(int xsize, int ysize) {

		XSIZE = xsize;
		YSIZE = ysize;

		for(int y = 0; y < ysize; y ++) {

			Grid.add(new ArrayList<ClimateCell>());
			ArrayList<ClimateCell> active = Grid.get(y);

			for(int x = 0; x < ysize; x ++) {

				active.add(new ClimateCell(x, y));

			}
		}

	}

	public ClimateGrid CloneGrid() {

		ClimateGrid clone = new ClimateGrid();

		clone.XSIZE = XSIZE;
		clone.YSIZE = YSIZE;
		
		for(int y = 0; y < YSIZE; y ++) {

			clone.Grid.add(new ArrayList<ClimateCell>());
			ArrayList<ClimateCell> activeRow = clone.Grid.get(y);

			for(int x = 0; x < XSIZE; x ++) {

				activeRow.add(new ClimateCell(getCell(x,y)));
				
			}
		}
		
		return clone;

	}

	public ClimateGrid CloudPropogate() {

		ClimateGrid newGrid = CloneGrid();
		
		for(int y = 0; y < YSIZE; y ++) {

			for(int x = 0; x < XSIZE; x ++) {

				try {
					
					ClimateCell activeCell = getCell(x, y);
					Direction prevail = activeCell.getWindDirection();
					
					ClimateCell updateCellA = newGrid.getCell(x + prevail.getdX(), y + prevail.getdY());
					ClimateCell updateCellO = newGrid.getCell(x, y);	
					
					
					updateCellA.addCloud(activeCell.getCloudSize(), 1 - activeCell.getWindDecayMult());
					updateCellO.setCloudSize(updateCellO.getCloudSize() * activeCell.getWindDecayMult());
					
					
				}
				catch (Exception e) {}

			}

		}

		return newGrid;

	}	

	public void cloudSizeDump() {

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.CEILING);

		for(ArrayList<ClimateCell> X : Grid) {
			for(ClimateCell C : X) {
				System.out.print(df.format(C.getCloudSize()) + "\t");
			}
			System.out.print("\r");
		}
		System.out.print("\r");

	}

	public void windDirDump() {

		for(ArrayList<ClimateCell> X : Grid) {
			for(ClimateCell C : X) {
				System.out.print(C.getWindDirection() + "\t");
			}
			System.out.print("\r");
		}

	}

	public ClimateCell getCell(int x, int y) {

		return Grid.get(y).get(x);

	}

	public int getXSIZE() {
		return XSIZE;
	}

	public void setXSIZE(int xSIZE) {
		XSIZE = xSIZE;
	}

	public int getYSIZE() {
		return YSIZE;
	}

	public void setYSIZE(int ySIZE) {
		YSIZE = ySIZE;
	}

}
