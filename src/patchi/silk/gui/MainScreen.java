package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class MainScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {

		terminal.setCursorPosition(0, 0);
		terminal.write("s - settlements");
		terminal.setCursorPosition(0, 1);
		terminal.write("p - people");

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_S):
			return new SettlementSummaryScreen();
		default:
			return this;

		}

	}

}
