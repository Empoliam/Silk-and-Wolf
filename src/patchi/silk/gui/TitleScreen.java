package patchi.silk.gui;

import java.awt.event.KeyEvent;

public class TitleScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		
		terminal.writeCenter("~~ Silk and Wolf ~~", terminal.getHeightInCharacters()/2 - 2);
		terminal.writeCenter("~~ PROTOTYPE ~~", terminal.getHeightInCharacters()/2);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new MainScreen() : this;
		
	}

}
