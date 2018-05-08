package main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entities.Road;
import entities.Settlement;
import entities.NPC;

import foundation.*;
import gui.NPCTable;
import gui.TravelWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	/** References to NPC dataset and important NPC objects */
	static final HashMap<Integer,NPC> NPCS = NPC.NPCS;
	static NPC LAWRENCE;
	static NPC HOLO;

	//UI Elements
	Button advHourButton;
	TextField loopTimes;
	TextField loopDelay;
	int loop = 0;
	NPCTable npcTable;
	Label timeLabel;
	
	Button backButton = new Button("Back");
	static Button travelButton = new Button("Go to");

	static TravelWindow travelWindow;
	
	/**
	 * Primary initialization method.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		Load.settlements();
		System.out.println("Loaded Settlements in " + (System.currentTimeMillis()-start) + "ms");
		start = System.currentTimeMillis();
		Load.roads();
		System.out.println("Loaded roads in " + (System.currentTimeMillis()-start) + "ms");
		start = System.currentTimeMillis();
		Load.npcs();
		System.out.println("Loaded NPCs in " + (System.currentTimeMillis()-start) + "ms");

		NPCS.put(0, new NPC(0,"Kraft","Lawrence",1,false,true));
		NPCS.put(1, new NPC(1,"Holo","",1,false,true));

		LAWRENCE = NPC.NPCS.get(0);
		HOLO = NPC.NPCS.get(1);
		
		launch(args);

	}

	/**
	 * 	Debug interface builder
	 *	@see javafx.application.Application#start(javafx.stage.Stage)
	 *	@param primaryStage	First Stage
	 *	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		System.out.println("Launch successful");
		primaryStage.setTitle("Debug build");

		timeLabel = new Label(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
				
		npcTable = new NPCTable();

		loopTimes = new TextField("1");
		loopDelay = new TextField("500");

		advHourButton = new Button("Advance Time");
		advHourButton.setOnAction(e -> { 
			e.consume();
			Task<Void> t = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					
					int l;
					try { l = Integer.parseInt(loopTimes.getText()); }
					catch (NumberFormatException nfe) { l = 1; }

					for(int x = 0; x < l; x++) {
						doHourTick();
						updateMessage(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
						Platform.runLater(new Runnable() {
							
							//Update table
							//TODO please don't do this in the future
							
							@Override
							public void run() {
								npcTable.refresh();
							}
						});
						int d; 
						try {d = Integer.parseInt(loopDelay.getText());}
						catch (NumberFormatException nfe){d = 500;}
						Thread.sleep(d);
					}
					
					advHourButton.setDisable(false);
					
					//Detect if player is travelling. Allow access to travel window if not
					if (!LAWRENCE.getTravelling()) {
						travelWindow.update();
						travelButton.setDisable(false);
					}
					stop();
					return null;

				};

			};
			timeLabel.textProperty().bind(t.messageProperty());
			new Thread(t).start();
			advHourButton.setDisable(true);
			travelButton.setDisable(true);
			timeLabel.textProperty();
		});

		VBox mainLayout = new VBox();
		mainLayout.getChildren().addAll(timeLabel,npcTable,loopTimes,loopDelay,advHourButton,travelButton);
		
		Scene mainScene = new Scene(mainLayout, 1000, 500);
		
		travelWindow = new TravelWindow(primaryStage, mainScene);		
		Scene travelScene = new Scene(travelWindow, 1000, 500);

		travelButton.setOnAction(e -> {
			primaryStage.setScene(travelScene);
		});
		
		backButton.setOnAction(e -> {
			primaryStage.setScene(mainScene);
		});
		
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	/**
	 * Executes a single hour tick. The bulk of all game logic happens in these ticks.
	 */
	private static void doHourTick() {

		CLOCK.advanceHour();

		for(NPC h : NPCS.values()) {

			//Travel decision making stage. Placeholder. Selects a random destination from all connected towns
			if (h.getId() != 0 && h.getId() != 1) {
				if(		CLOCK.getHour() == 0 
						&& h.getDoTravel() 
						&& !h.getPrepTravel() 
						&& !h.getTravelling()) {

					h.setDepartureHours(h.generateDepartureHour(RANDOM));			
					List<Integer> destinationPool = SETTLEMENTS.get(h.getLocation()).connectedTo();			
					h.setDestination(SETTLEMENTS.get(destinationPool.get(RANDOM.nextInt(destinationPool.size()))).getID());
					h.setPrepTravel(true);
				}
			}

			//NPCs advance
			if(h.getTravelling()) h.advanceTravel();

			//NPCs depart
			if(h.getPrepTravel()) {
				if(h.getDepartureHours() == 0) h.beginTravel();
				else h.decrementDepartureHours();
			}
		}
		
	}

}


