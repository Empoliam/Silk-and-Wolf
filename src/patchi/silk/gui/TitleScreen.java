package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class TitleScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		
		terminal.writeCenter("~~ Silk and Wolf ~~", terminal.getHeightInCharacters()/2 - 2);
		terminal.writeCenter("~~ 0.0.1-SNAPSHOT ~~", terminal.getHeightInCharacters()/2);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new MainScreen() : this;
		
	}

}
