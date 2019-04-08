package patchi.silk.gui;

import java.awt.event.KeyEvent;

import patchi.silk.entities.World;
import patchi.silk.foundation.Time;

public class WaitScreen implements Screen {

	final World WORLD = World.getMainWorld();
	final Time CLOCK = WORLD.getClock();

	private int cursorX;
	private int cursorY;

	static private int waitHours = 0;

	@Override
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;

		int waitDays = waitHours/24;
		int waitYears = waitDays/365;
		
		terminal.setCursorPosition(cursorX,cursorY);
		terminal.write(CLOCK.getFormattedDate() + " " + CLOCK.getFormattedTime(), AsciiPanel.black, AsciiPanel.white);
		cursorY+= 2;
		terminal.setCursorPosition(cursorX, cursorY);
		if(waitDays == 0) {
			terminal.write("w - Wait for " + waitHours + " hours");
		} else if (waitYears == 0) {
			terminal.write("w - Wait for " + waitHours/24 + " days " + waitHours%24 + " hours");
		} else {
			terminal.write("w - Wait for " + waitYears + " years " + (waitDays - 365*waitYears) + " days " + waitHours%24 + " hours");
		}
		cursorY++;
		terminal.setCursorPosition(cursorX, cursorY);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_ESCAPE):
			waitHours = 0;
		return new MainScreen();

		case(KeyEvent.VK_NUMPAD7):
			waitHours++;
		return this;

		case(KeyEvent.VK_NUMPAD1):			
			waitHours = Math.max(waitHours-1, 0);
		return this;

		case(KeyEvent.VK_NUMPAD8):
			waitHours+=24;
		return this;

		case(KeyEvent.VK_NUMPAD2):			
			waitHours = Math.max(waitHours-8760, 0);
		return this;
		
		case(KeyEvent.VK_NUMPAD9):
			waitHours+=8760;
		return this;

		case(KeyEvent.VK_NUMPAD3):			
			waitHours = Math.max(waitHours-8760, 0);
		return this;

		case(KeyEvent.VK_W):
			while(waitHours > 0) {

				WORLD.doHourTick();
				waitHours--;

			}
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
