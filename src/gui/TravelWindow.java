package gui;

import java.util.ArrayList;
import java.util.List;

import entities.NPC;
import entities.Settlement;
import entities.World;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TravelWindow extends GridPane{

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementsSet();
	
	/** Reference to list of all travel buttons */
	private static List<Button> travelButtons;
	
	/** NPC references. */
	/** References to NPC dataset and important NPC objects */
	static final ArrayList<NPC> NPCS = WORLD.getNPCS();
	static NPC LAWRENCE = NPCS.get(0);
	static NPC HOLO = NPCS.get(1);
	
	public TravelWindow() {

		travelButtons = new ArrayList<Button>();
		update();

	}
	
	public void update() {

		travelButtons.clear();
		getChildren().clear();
		
		List<Settlement> connects = LAWRENCE.getLocationSettlement().getConnectedSettlements();
		int r = 0;
				
		for(Settlement S : connects) {
			
			Button B = new Button("Go");
			B.setOnAction(e -> {
				
				LAWRENCE.setDepartureHours(0);
				HOLO.setDepartureHours(0);
				LAWRENCE.setDestination(S);
				HOLO.setDestination(S);
				LAWRENCE.setPrepTravel(true);
				HOLO.setPrepTravel(true);
				
			});
			
			add(new Label(S.getName()), 1, r);
			add(B,2,r);
			
			travelButtons.add(B);
			
			r++;
		}
		
	}
	
	public List<Button> getButtons() {
		return travelButtons;
	}

}
