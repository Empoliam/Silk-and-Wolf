package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;

public class SettlementSummaryScreen implements Screen {

	private static final int MAX_PAGE_LENGTH = 26;

	private final World world = World.getMainWorld();
	private final ArrayList<Settlement> SETTLEMENTS = world.getSettlementSet();

	private int screenHeight;
	private int currentPage = 1;
	private int totalPages = (int) Math.ceil(SETTLEMENTS.size() / (float) MAX_PAGE_LENGTH);
	private List<Settlement> currentPageList;

	@Override
	public void displayOutput(AsciiPanel terminal) {

		screenHeight = terminal.getHeightInCharacters();
		currentPageList = SETTLEMENTS.subList((currentPage-1)*MAX_PAGE_LENGTH, Math.min(SETTLEMENTS.size(), MAX_PAGE_LENGTH*currentPage));
		char listIndex = 'a';		

		terminal.setCursorPosition(0, 0);
		terminal.write("Settlements:", AsciiPanel.black, AsciiPanel.white);
		terminal.setCursorPosition(0, 1);
		terminal.write("Page " + currentPage + " of " + totalPages);
		terminal.setCursorPosition(0, 2);
		terminal.write("Use /* to navigate pages");

		terminal.setCursorPosition(0, 4);
		for(Settlement S : currentPageList) {
			terminal.write(listIndex + ". " + S.getName());
			listIndex++;
			terminal.setCursorPosition(0, terminal.getCursorY() + 1);;
		}

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_ASTERISK):
		case(KeyEvent.VK_MULTIPLY):
			currentPage = Math.min(currentPage + 1, totalPages);
		return this;
		case(KeyEvent.VK_SLASH):
		case(KeyEvent.VK_DIVIDE):
			currentPage = Math.max(currentPage - 1, 1);
		return this;
		default:
			return this;

		}

	}

}
