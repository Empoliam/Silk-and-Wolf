package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.foundation.Time;
import patchi.silk.foundation.World;

public class MainScreen implements Screen {

	final World WORLD = World.getMainWorld();
	final Time CLOCK = WORLD.getClock();
	
	private int cursorX;
	private int cursorY;
	
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;
		
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime(), AsciiPanel.black, AsciiPanel.white);
		
		cursorY+= 2;
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write("s - Settlements");
		
		cursorY++;
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write("p - People");
		
		cursorY++;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("w - Wait");
		
		cursorY++;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("z - Save");
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_S):
			return new SettlementScreen();
		case(KeyEvent.VK_P):
			return new PersonScreen();
		case(KeyEvent.VK_W):
			return new WaitScreen();
		case(KeyEvent.VK_T):
			return new TestScreen();
		case(KeyEvent.VK_Z):
			return new SaveScreen();
		default:
			return this;

		}

	}

	@Override
	public Screen keyReleased(KeyEvent key) {

		return this;
	}


}
