package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Person;

public class PersonSummaryScreen implements Screen {

	private Person P;
	
	public PersonSummaryScreen(Person p) {
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
			return new PersonScreen();
		default:
			return this;

		}
		
	}

}
