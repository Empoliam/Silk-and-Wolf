package gui;

import java.util.ArrayList;
import java.util.List;

import entities.NPC;
import entities.Settlement;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TravelWindow extends VBox{

	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	/** Reference to list of all travel buttons */
	private static List<Button> travelButtons;
	
	/** NPC references. */
	static final NPC LAWRENCE = NPC.NPCS.get(0);
	static final NPC HOLO = NPC.NPCS.get(1);

	GridPane internal;
	
	public TravelWindow() {

		internal = new GridPane();
		travelButtons = new ArrayList<Button>();
		update();
		getChildren().addAll(internal);

	}
	
	public void update() {

		travelButtons.clear();
		internal.getChildren().clear();
		
		System.out.println("A");
		
		List<Integer> connects = SETTLEMENTS.get(LAWRENCE.getLocation()).connectedTo();
		int r = 0;
				
		for(Integer i : connects) {
			
			Button B = new Button("Go");
			B.setOnAction(e -> {
				
				LAWRENCE.setDepartureHours(0);
				HOLO.setDepartureHours(0);
				LAWRENCE.setDestination(i);
				HOLO.setDestination(i);
				LAWRENCE.setPrepTravel(true);
				HOLO.setPrepTravel(true);
				
			});
			
			internal.add(new Label(SETTLEMENTS.get(i).getName()), 1, r);
			internal.add(B,2,r);
			
			travelButtons.add(B);
			
			r++;
		}
		
	}
	
	public List<Button> getButtons() {
		return travelButtons;
	}

}
