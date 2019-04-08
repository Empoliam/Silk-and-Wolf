package patchi.silk.gui;

import java.awt.event.KeyEvent;

import patchi.silk.entities.Settlement;

public class SettlementScreen implements Screen {

	private Settlement S;
	
	public SettlementScreen(Settlement s) {
		this.S = s;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		terminal.setCursorPosition(0, 0);
		terminal.write("Summary: " + S.getName(), AsciiPanel.black, AsciiPanel.white);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		switch(key.getKeyCode()) {

				
		case(KeyEvent.VK_ESCAPE):
			return new SettlementSummaryScreen();
		default:
			return this;

		}
		
	}

}
