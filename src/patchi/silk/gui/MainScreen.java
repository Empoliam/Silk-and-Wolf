package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.World;
import patchi.silk.foundation.Time;

public class MainScreen implements Screen {

	final World WORLD = World.getMainWorld();
	final Time CLOCK = WORLD.getClock();
	
	int cursorX;
	int cursorY;
	
	@Override
	public void displayOutput(AsciiPanel terminal) {

		int cursorX = 0;
		int cursorY = 0;
		
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime());
		cursorY+= 2;
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write("s - Settlements");
		cursorY++;
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write("p - People");
		cursorY++;
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write("a - Advance Hour");
		cursorY++;
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_S):
			return new SettlementSummaryScreen();
		case(KeyEvent.VK_P):
			return new PersonSummaryScreen();
		case(KeyEvent.VK_A):
			WORLD.doHourTick();
			return this;
		default:
			return this;

		}

	}

	@Override
	public Screen keyReleased(KeyEvent key) {

		return this;
	}

}
