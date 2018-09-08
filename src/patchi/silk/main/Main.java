package patchi.silk.main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import patchi.silk.entities.Character;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;
import patchi.silk.foundation.Time;
import patchi.silk.gui.CharacterOverview;
import patchi.silk.gui.SettlementOverview;
import patchi.silk.gui.TravelWindow;
import patchi.silk.market.GlobalStock;

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
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** References to character dataset and important Characters */
	static final List<Character> CHARACTERS = WORLD.getCharacterSet();
	static Character LAWRENCE;
	static Character HOLO;

	/** Reference to global stock dataset */
	static final HashMap<Integer,GlobalStock> STOCKS = WORLD.getGlobalStockSet();

	//UI Elements
	private static Stage primaryStage;	
	private static Scene mainScene;
	private static CharacterOverview charTable;
	private static SettlementOverview settlementTable; 
	private static TravelWindow travelWindow;

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
		Load.characters();
		System.out.println("Loaded characters in " + (System.currentTimeMillis()-start) + "ms");

		start = System.currentTimeMillis();
		Load.settlements();
		System.out.println("Loaded settlements in " + (System.currentTimeMillis()-start) + "ms");

		start = System.currentTimeMillis();
		Load.stocks();
		System.out.println("Loaded stocks in " + (System.currentTimeMillis()-start) + "ms");

		start = System.currentTimeMillis();
		Load.unpack();
		System.out.println("Unpacked in " + (System.currentTimeMillis()-start) + "ms");

		CHARACTERS.add(new Character(0,"Kraft","Lawrence",SETTLEMENTS.get(0),false,true,false));
		CHARACTERS.add(new Character(1,"Holo","",SETTLEMENTS.get(0),true,true,false));

		LAWRENCE = CHARACTERS.get(0);
		HOLO = CHARACTERS.get(1);

		launch(args);

	}

	/**
	 * 	Debug interface builder
	 *	@see javafx.application.Application#start(javafx.stage.Stage)
	 *	@param primaryStage	First Stage
	 *	 */
	@Override
	public void start(Stage mainStage) throws Exception {

		primaryStage = mainStage;

		System.out.println("Launch successful");
		primaryStage.setTitle("Debug build");

		Label timeLabel = new Label(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());

		TextField loopTimes = new TextField("1");
		TextField loopDelay = new TextField("50");

		Button advHourButton = new Button("Advance Time");

		SimpleIntegerProperty remainingLoops = new SimpleIntegerProperty();

		Service<Void> LoopService = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {

				Task<Void> gameLoopTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						doHourTick();

						int d; 
						try {d = Integer.parseInt(loopDelay.getText());}
						catch (NumberFormatException nfe){d = 500;}
						Thread.sleep(d);
						updateMessage("ping");					

						return null;

					};

				};

				return gameLoopTask;
			}

		};


		advHourButton.setOnAction(e -> {

			e.consume();

			try {
				remainingLoops.set(Integer.parseInt(loopTimes.getText()));
			} catch( NumberFormatException nfe) {
				remainingLoops.set(1);
			}

			advHourButton.setDisable(true);	
			for(Button B : travelWindow.getButtons()) B.setDisable(true);	
			LoopService.start();

		});

		LoopService.runningProperty().addListener((observable, oldValue, newValue) -> {

			if(!LoopService.runningProperty().get()) {

				LoopService.reset();

				timeLabel.setText(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
				charTable.refresh();
				settlementTable.refresh();

				remainingLoops.set(remainingLoops.get() - 1);

				if(remainingLoops.get() > 0) {						
					LoopService.start();			
				} else {

					advHourButton.setDisable(false);	

					if (!LAWRENCE.isTravelling()) {
						for(Button B : travelWindow.getButtons()) B.setDisable(false);			
						travelWindow.update();
					}

				}

			}

		});

		charTable = new CharacterOverview(CHARACTERS);
		VBox charContainer = new VBox(charTable);
		Tab charTab = new Tab("Characters",charContainer);
		charTab.setClosable(false);

		travelWindow = new TravelWindow();
		Tab travelTab = new Tab("Travel",travelWindow);
		travelTab.setClosable(false);

		settlementTable = new SettlementOverview();
		VBox settlementContainer = new VBox(settlementTable);
		Tab settlementTab = new Tab("Settlements", settlementContainer);
		settlementTab.setClosable(false);

		TabPane mainTabPane = new TabPane();
		mainTabPane.getTabs().addAll(charTab, settlementTab, travelTab);

		VBox mainLayout = new VBox();
		mainLayout.getChildren().addAll(timeLabel,mainTabPane,loopTimes,loopDelay,advHourButton);

		mainScene = new Scene(mainLayout, 1000, 500);

		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	/**
	 * Executes a single hour tick.
	 */
	private static void doHourTick() {

		CLOCK.advanceHour();

		for(Character h : CHARACTERS) {

			//Travel decision making stage. Placeholder. Selects a random destination from all connected towns
			if (h.getDoDecisionTree()) {
				if(		CLOCK.getHour() == 0 
						&& h.getDoTravel() 
						&& !h.getPrepTravel() 
						&& !h.isTravelling()) {

					h.setDepartureHours(h.generateDepartureHour(RANDOM));			
					List<Settlement> destinationPool = h.getLocationSettlement().getConnectedSettlements();			
					h.setDestination(destinationPool.get(RANDOM.nextInt(destinationPool.size())));
					h.setPrepTravel();
				}
			}

			//Characters advance
			if(h.isTravelling()) h.advanceTravel();

			//Characters depart
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


