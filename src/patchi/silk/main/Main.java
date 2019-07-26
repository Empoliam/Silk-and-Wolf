package patchi.silk.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import patchi.silk.entities.Person;
import patchi.silk.entities.Settlement;
import patchi.silk.foundation.World;
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
	
	/** Main World reference */
	public static final World WORLD = World.getMainWorld();

	/** Reference to settlement dataset ArrayList*/
	public static final List<Settlement> SETTLEMENTS = WORLD.getSettlementSet();

	/** References to character dataset and important Characters */
	static final List<Person> PEOPLE = WORLD.getPersonSet();

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


