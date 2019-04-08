package patchi.silk.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import patchi.silk.entities.Person;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;
import patchi.silk.market.GlobalStock;
import patchi.silk.gui.Screen;
import patchi.silk.gui.TitleScreen;
import patchi.silk.gui.AsciiPanel;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;



/**
 * Main class.
 */
public class Main extends JFrame implements KeyListener{

	/** */
	private static final long serialVersionUID = 1L;
	
	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** References to character dataset and important Characters */
	static final List<Person> PEOPLE = WORLD.getPersonSet();
	static Person LAWRENCE;
	static Person HOLO;

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

		PEOPLE.add(0, new Person("00000000","Kraft","Lawrence",SETTLEMENTS.get(0),false,true,false));
		PEOPLE.add(1, new Person("00000001","Holo","",SETTLEMENTS.get(0),true,true,false));

		LAWRENCE = WORLD.getPersonByID("00000000");
		HOLO = WORLD.getPersonByID("00000001");

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

	@Override
	public void keyPressed(KeyEvent key) {

		screen = screen.respondToUserInput(key);
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent key) {

		if(key.getKeyCode() == KeyEvent.VK_SHIFT) {
			screen = screen.keyReleased(key);
			repaint();
		}

	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

}


