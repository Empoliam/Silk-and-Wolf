package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;

public class SettlementScreen implements Screen {

	private static final int MAX_PAGE_LENGTH = 26;

	private final World world = World.getMainWorld();
	private final ArrayList<Settlement> SETTLEMENTS = world.getSettlementSet();

	private int currentPage = 1;
	private int totalPages = (int) Math.ceil(SETTLEMENTS.size() / (float) MAX_PAGE_LENGTH);
	private List<Settlement> currentPageList;

	private int cursorX;
	private int cursorY;
	
	@Override
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;
		
		terminal.getHeightInCharacters();
		currentPageList = SETTLEMENTS.subList((currentPage-1)*MAX_PAGE_LENGTH, Math.min(SETTLEMENTS.size(), MAX_PAGE_LENGTH*currentPage));
		char listIndex = 'a';		

		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("Settlements", AsciiPanel.black, AsciiPanel.white);
		cursorY+=2;
		terminal.setCursorPosition(cursorX, cursorY);
		
		terminal.write("Page " + currentPage + " of " + totalPages);
		cursorY++;
		terminal.setCursorPosition(cursorX, cursorY);
		
		terminal.write("Use /* to navigate pages");
		cursorY+=2;
		terminal.setCursorPosition(cursorX, cursorY);
		
		for(Settlement S : currentPageList) {
			terminal.write(listIndex + ". " + S.getName());
			listIndex++;
			cursorY++;
			terminal.setCursorPosition(cursorX, cursorY);
		}

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		char keyC = key.getKeyChar();

		if(keyC >= 97 && keyC <= 122) {

			try {
				return new SettlementSummaryScreen(currentPageList.get(keyC - 97));
			} catch (IndexOutOfBoundsException e) {
				return this;
			}
		} else {

			switch(key.getKeyCode()) {

			case(KeyEvent.VK_ASTERISK):
			case(KeyEvent.VK_MULTIPLY):
				currentPage = Math.min(currentPage + 1, totalPages);
			return this;

			case(KeyEvent.VK_SLASH):
			case(KeyEvent.VK_DIVIDE):
				currentPage = Math.max(currentPage - 1, 1);
			return this;

			case(KeyEvent.VK_ESCAPE):
				return new MainScreen();

			default:
				return this;

			}

		}

	}

	@Override
	public Screen keyReleased(KeyEvent key) {
		return this;
	}

}
