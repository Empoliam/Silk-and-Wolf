package patchi.silk.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import patchi.silk.entities.Character;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;

public class TravelWindow extends GridPane{

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();
	
	/** Reference to list of all travel buttons */
	private static List<Button> travelButtons;
	
	/** Character references. */
	/** References to Character dataset and important characters */
	static final List<Character> CHARACTERS = WORLD.getCharacterSet();
	static Character LAWRENCE = CHARACTERS.get(0);
	static Character HOLO = CHARACTERS.get(1);
	
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
