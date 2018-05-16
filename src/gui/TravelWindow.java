package gui;

import java.util.ArrayList;
import java.util.List;

import entities.NPC;
import entities.Settlement;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TravelWindow extends GridPane{

	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	/** Reference to list of all travel buttons */
	private static List<Button> travelButtons;
	
	/** NPC references. */
	static final NPC LAWRENCE = NPC.NPCS.get(0);
	static final NPC HOLO = NPC.NPCS.get(1);
	
	public TravelWindow() {

		travelButtons = new ArrayList<Button>();
		update();

	}
	
	public void update() {

		travelButtons.clear();
		getChildren().clear();
		
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
			
			add(new Label(SETTLEMENTS.get(i).getName()), 1, r);
			add(B,2,r);
			
			travelButtons.add(B);
			
			r++;
		}
		
	}
	
	public List<Button> getButtons() {
		return travelButtons;
	}

}
