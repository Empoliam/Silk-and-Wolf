package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Person;

public class PersonScreen implements Screen {

	private Person P;
	
	public PersonScreen(Person p) {
		this.P = p;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		terminal.setCursorPosition(0, 0);
		terminal.write("Summary: " + P.getName(), AsciiPanel.black, AsciiPanel.white);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		switch(key.getKeyCode()) {

				
		case(KeyEvent.VK_ESCAPE):
			return new PersonSummaryScreen();
		default:
			return this;

		}
		
	}

}
