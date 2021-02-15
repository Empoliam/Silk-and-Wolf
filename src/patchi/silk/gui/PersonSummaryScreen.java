package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.Iterator;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Person;
import patchi.silk.item.ItemStack;

public class PersonSummaryScreen implements Screen {

	private Person P;
	
	public PersonSummaryScreen(Person p) {
		this.P = p;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		int yPos = 0;
		terminal.setCursorPosition(0, yPos);
		terminal.write("Summary: " + P.getName(), AsciiPanel.black, AsciiPanel.white);
		yPos++;
		terminal.setCursorPosition(0,yPos);
		terminal.write("Location: " + P.getLocationID());
		yPos += 2;
		terminal.setCursorPosition(0, yPos);
		terminal.write("Inventory:");
		yPos++;
		terminal.setCursorPosition(0, yPos);
		Iterator<ItemStack> invIterator = P.getInventory().getIterator();
		while(invIterator.hasNext()) {
			terminal.write(invIterator.next().getStackName());
			yPos++;
			terminal.setCursorPosition(0, yPos);
		}
		
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
