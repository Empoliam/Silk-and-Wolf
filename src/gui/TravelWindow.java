package gui;

import java.util.List;

import entities.NPC;
import entities.Settlement;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TravelWindow extends VBox{

	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;
	
	static final NPC LAWRENCE = NPC.NPCS.get(0);
	static final NPC HOLO = NPC.NPCS.get(1);
	
	public TravelWindow() {
		
		List<Integer> connects = SETTLEMENTS.get(LAWRENCE.getLocation()).connectedTo();
		
		for(Integer i : connects) {
			this.getChildren().add(new Label(SETTLEMENTS.get(i).getName()));
		}
		
	}
	
}
