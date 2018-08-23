package main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entities.NPC;
import entities.Settlement;
import entities.World;

import foundation.Time;

import gui.NPCTable;
import gui.TravelWindow;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import market.GlobalStock;

/**
 * Main class.
 */
public class Main extends Application {

	/** Universal random number generator. */
	static final Random RANDOM = new Random(System.nanoTime());

	/** Reference to global clock */
	static final Time CLOCK = Time.CLOCK;

	/** Main World reference */
	public static final World WORLD = World.getMainWorld();
	
	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementsSet();

	/** References to NPC dataset and important NPC objects */
	static final List<NPC> NPCS = WORLD.getNPCS();
	static NPC LAWRENCE;
	static NPC HOLO;

	/** Reference to global stock dataset */
	static final HashMap<Integer,GlobalStock> STOCKS = WORLD.getGlobalStockSet();
	
	//UI Elements
	
	Button advHourButton;
	TextField loopTimes;
	TextField loopDelay;
	int loop = 0;
	
	Label timeLabel;
	
	TabPane mainTabPane;
	Tab npcTab;
	Tab travelTab;
	Tab stockTab;
	NPCTable npcTable;
	
	static TravelWindow travelWindow;
	
	/**
	 * Primary initialization method.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		Load.roads();
		System.out.println("Loaded roads in " + (System.currentTimeMillis()-start) + "ms");
		
		start = System.currentTimeMillis();
		Load.npcs();
		System.out.println("Loaded NPCs in " + (System.currentTimeMillis()-start) + "ms");
		
		start = System.currentTimeMillis();
		Load.settlements();
		System.out.println("Loaded settlements in " + (System.currentTimeMillis()-start) + "ms");
				
		start = System.currentTimeMillis();
		Load.stocks();
		System.out.println("Loaded stocks in " + (System.currentTimeMillis()-start) + "ms");
		
		start = System.currentTimeMillis();
		Load.unpack();
		System.out.println("Unpacked in " + (System.currentTimeMillis()-start) + "ms");
		
		NPCS.add(new NPC(0,"Kraft","Lawrence",SETTLEMENTS.get(0),false,true,false));
		NPCS.add(new NPC(1,"Holo","",SETTLEMENTS.get(0),true,true,false));

		LAWRENCE = NPCS.get(0);
		HOLO = NPCS.get(1);
				
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
				
		loopTimes = new TextField("1");
		loopDelay = new TextField("50");

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
						updateMessage(Integer.toString(x+1) + "," + l);					
						int d; 
						try {d = Integer.parseInt(loopDelay.getText());}
						catch (NumberFormatException nfe){d = 500;}
						Thread.sleep(d);
						
					}
					
					advHourButton.setDisable(false);					
					
					return null;

				};

			};
	
			t.messageProperty().addListener((observable, old, updated) -> {

					timeLabel.setText(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
					npcTable.refresh();
					
					
					String[] s = t.getMessage().split(",");
					//Detect if player is travelling. Allow access to travel window if not
					if (!LAWRENCE.getTravelling() && (Integer.parseInt(s[0])==Integer.parseInt(s[1]))) {
						
						for(Button B : travelWindow.getButtons()) B.setDisable(false);
						travelWindow.update();
						
				}
				
			});
			new Thread(t).start();
			advHourButton.setDisable(true);
			for(Button B : travelWindow.getButtons()) B.setDisable(true);
					
		});
		
		npcTable = new NPCTable(NPCS);
		VBox npcContainer = new VBox(npcTable);
		npcTab = new Tab("NPCs",npcContainer);
		npcTab.setClosable(false);
		
		travelWindow = new TravelWindow();
		travelTab = new Tab("Travel",travelWindow);
		travelTab.setClosable(false);
		
		mainTabPane = new TabPane();
		mainTabPane.getTabs().addAll(npcTab, travelTab);
		
		VBox mainLayout = new VBox();
		mainLayout.getChildren().addAll(timeLabel,mainTabPane,loopTimes,loopDelay,advHourButton);
		
		Scene mainScene = new Scene(mainLayout, 1000, 500);
						
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	/**
	 * Executes a single hour tick.
	 */
	private static void doHourTick() {

		CLOCK.advanceHour();

		for(NPC h : NPCS) {

			//Travel decision making stage. Placeholder. Selects a random destination from all connected towns
			if (h.getDoDecisionTree()) {
				if(		CLOCK.getHour() == 0 
						&& h.getDoTravel() 
						&& !h.getPrepTravel() 
						&& !h.getTravelling()) {
					
					h.setDepartureHours(h.generateDepartureHour(RANDOM));			
					List<Settlement> destinationPool = h.getLocationSettlement().getConnectedSettlements();			
					h.setDestination(destinationPool.get(RANDOM.nextInt(destinationPool.size())));
					h.setPrepTravel(true);
				}
			}

			//NPCs advance
			if(h.getTravelling()) h.advanceTravel();

			//NPCs depart
			if(h.getPrepTravel()) {
				if(h.getDepartureHours() == 0) {
					h.beginTravel();
				} else {
					h.decrementDepartureHours();
				}
			}
		}
		
	}

}


