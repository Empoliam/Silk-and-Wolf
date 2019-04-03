package patchi.silk.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import patchi.silk.entities.Character;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;
import patchi.silk.foundation.Time;
import patchi.silk.market.GlobalStock;
import patchi.silk.gui.Screen;
import patchi.silk.gui.TitleScreen;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;


/**
 * Main class.
 */
public class Main extends JFrame implements KeyListener{

	/** */
	private static final long serialVersionUID = 1L;

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
	private AsciiPanel terminal;
	private Screen screen;



	/**
	 * Primary initialization method.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		Load.load();
		System.out.println("Loaded in " + (System.currentTimeMillis()-start) + "ms");

		CHARACTERS.add(new Character(0,"Kraft","Lawrence",SETTLEMENTS.get(0),false,true,false));
		CHARACTERS.add(new Character(1,"Holo","",SETTLEMENTS.get(0),true,true,false));

		LAWRENCE = CHARACTERS.get(0);
		HOLO = CHARACTERS.get(1);

		Main app = new Main();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);

	}

	public Main() {

		super();
		terminal = new AsciiPanel(100, 48, AsciiFont.CP437_12x12);
		add(terminal);
		pack();
		
		screen = new TitleScreen();
		addKeyListener(this);
		repaint();	
		
	}
	
	public void repaint() {
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
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

	@Override
	public void keyPressed(KeyEvent key) {

		screen = screen.respondToUserInput(key);
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

}


