package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.TreeMap;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Person;
import patchi.silk.foundation.World;

public class PersonScreen implements Screen {

	private static final int MAX_PAGE_LENGTH = 26;

	private static int staticPage = 1;

	private final World world = World.getMainWorld();
	private final TreeMap<String,Person> PEOPLE = world.getPersonSet();

	private int currentPage = 1;
	private int totalPages = (int) Math.ceil(PEOPLE.size() / (float) MAX_PAGE_LENGTH);
	private String[] keys = PEOPLE.keySet().toArray(new String[0]);
	private String[] currentPageKeys;

	private int cursorX;
	private int cursorY;
	
	@Override
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;
		
		terminal.getHeightInCharacters();
		currentPage = (staticPage <= totalPages) ? staticPage : 1;
		currentPageKeys = Arrays.copyOfRange(keys, (currentPage-1)*MAX_PAGE_LENGTH, Math.min((currentPage)*MAX_PAGE_LENGTH,keys.length));
		char listIndex = 'a';		

		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("People", AsciiPanel.black, AsciiPanel.white);
		cursorY+=2;
		terminal.setCursorPosition(cursorX, cursorY);
		
		terminal.write("Page " + currentPage + " of " + totalPages);
		cursorY++;
		terminal.setCursorPosition(cursorX, cursorY);
		
		terminal.write("Use /* to navigate pages");
		cursorY+=2;
		terminal.setCursorPosition(cursorX, cursorY);
		
		for(int i = 0; i < Math.min(26,currentPageKeys.length); i++) {
			terminal.write(listIndex + ". " + PEOPLE.get(currentPageKeys[i]));
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
				return new PersonSummaryScreen(PEOPLE.get(currentPageKeys[keyC - 97]));
			} catch (IndexOutOfBoundsException e) {
				return this;
			}

		} else {

			switch(key.getKeyCode()) {

			case(KeyEvent.VK_ASTERISK):
			case(KeyEvent.VK_MULTIPLY):
				currentPage = Math.min(currentPage + 1, totalPages);
			staticPage = currentPage;
			return this;

			case(KeyEvent.VK_SLASH):
			case(KeyEvent.VK_DIVIDE):			
				currentPage = Math.max(currentPage - 1, 1);
			staticPage = currentPage;
			return this;

			case(KeyEvent.VK_ESCAPE):
				staticPage = 1;
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
