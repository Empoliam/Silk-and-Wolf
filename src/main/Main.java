package main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entities.Road;
import entities.Settlement;
import entities.NPC;

import foundation.*;
import gui.NPCTable;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Main class.
 */
public class Main extends Application {

	/** Universal random number generator. */
	static final Random RANDOM = new Random(System.nanoTime());

	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;

	/** Reference to road dataset. */
	static final List<Road> ROADS = Road.ROADS;

	/** Reference to settlement dataset. */
	static final List<Settlement> SETTLEMENTS = Settlement.SETTLEMENTS;

	/** Reference to NPC dataset. */
	static final HashMap<Integer,NPC> NPCS = NPC.NPCS;

	//UI Elements
	Button advHourButton;

	/**
	 * Primary initialization method.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		long start = System.nanoTime();
		Load.settlements();
		System.out.println("Loaded Settlements in " + (System.nanoTime()-start) + "ns");
		start = System.nanoTime();
		Load.roads();
		System.out.println("Loaded roads in " + (System.nanoTime()-start) + "ns");
		start = System.nanoTime();
		Load.npcs();
		System.out.println("Loaded NPCs in " + (System.nanoTime()-start) + "ns");

		launch(args);

	}

	/**
	 * 	User interface builder. Establishes 
	 *	@see javafx.application.Application#start(javafx.stage.Stage)
	 *	@param primaryStage	First Stage
	 *	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.println("Launch successful");
		primaryStage.setTitle("Debug build");
		
		Label timeLabel = new Label(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
		
		NPCTable npcTable = new NPCTable();

		advHourButton = new Button("Advance Time 1hr");
		advHourButton.setOnAction(e -> {
			doHourTick();
			npcTable.refresh();
			timeLabel.setText(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
			});

		VBox layout = new VBox();
		layout.getChildren().addAll(timeLabel,npcTable,advHourButton);

		Scene scene = new Scene(layout, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Executes a single hour tick. The bulk of all game logic happens in these ticks.
	 */
	private static void doHourTick() {

		CLOCK.advanceHour();

		System.out.println(CLOCK.getFormattedDate() + ", " + CLOCK.getFormattedTime());

		int departureDecisionCount = 0;
		
		for(NPC h : NPCS.values()) {

			if(CLOCK.getHour() == 0 && h.getDoTravel() && !h.getPrepTravel()) {

				h.setDepartureHours(h.generateDepartureHour(RANDOM));
				h.setPrepTravel(true);
				departureDecisionCount ++;
			}

			else if(h.getTravelling()) h.advanceTravel();
		}
		
		System.out.println(departureDecisionCount + " NPCs decided to leave");

	}

}