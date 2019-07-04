package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Settlement;

public class SettlementSummaryScreen implements Screen {

	private Settlement S;
	
	private int xCursor;
	private int yCursor;
	
	public SettlementSummaryScreen(Settlement s) {
		this.S = s;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		xCursor = 0;
		yCursor = 0;
		
		terminal.setCursorPosition(xCursor, yCursor);
		terminal.write("Summary: " + S.getName(), AsciiPanel.black, AsciiPanel.white);
		yCursor+=2;
		terminal.setCursorPosition(xCursor, yCursor);
		terminal.write("id: " + S.getID());
		yCursor++;
		terminal.setCursorPosition(xCursor, yCursor);
		terminal.write("Population: " + S.getCurrentPopulation());
		yCursor++;
		terminal.setCursorPosition(xCursor, yCursor);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		switch(key.getKeyCode()) {

				
		case(KeyEvent.VK_ESCAPE):
			return new SettlementScreen();
		default:
			return this;

		}
		
	}

}
