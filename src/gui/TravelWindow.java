package gui;

import java.util.List;

import entities.NPC;
import entities.Settlement;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TravelWindow extends VBox{

	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;

	/** NPC references. */
	static final NPC LAWRENCE = NPC.NPCS.get(0);
	static final NPC HOLO = NPC.NPCS.get(1);

	GridPane internal;
	Stage mainStage;
	Scene mainScene;
	
	public TravelWindow(Stage a, Scene b) {
		
		mainStage = a;
		mainScene = b;
		
		internal = new GridPane();
		update();
		getChildren().addAll(internal);

	}
	
	public void update() {
		
		internal.getChildren().clear();

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
				mainStage.setScene(mainScene);
				
			});
			
			internal.add(new Label(SETTLEMENTS.get(i).getName()), 1, r);
			internal.add(B,2,r);
			r++;
		}
		
	}

}
