package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import patchi.silk.entities.Person;
import patchi.silk.entities.World;

public class PersonSummaryScreen implements Screen {

	private static final int MAX_PAGE_LENGTH = 26;

	private static int staticPage = 1;

	private final World world = World.getMainWorld();
	private final List<Person> PEOPLE = world.getPersonSet();

	private int currentPage = 1;
	private int totalPages = (int) Math.ceil(PEOPLE.size() / (float) MAX_PAGE_LENGTH);
	private List<Person> currentPageList;

	@Override
	public void displayOutput(AsciiPanel terminal) {

		terminal.getHeightInCharacters();
		currentPage = (staticPage <= totalPages) ? staticPage : 1;
		currentPageList = PEOPLE.subList((currentPage-1)*MAX_PAGE_LENGTH, Math.min(PEOPLE.size(), MAX_PAGE_LENGTH*currentPage));
		char listIndex = 'a';		

		terminal.setCursorPosition(0, 0);
		terminal.write("Settlements:", AsciiPanel.black, AsciiPanel.white);
		terminal.setCursorPosition(0, 1);
		terminal.write("Page " + currentPage + " of " + totalPages);
		terminal.setCursorPosition(0, 2);
		terminal.write("Use /* to navigate pages");

		terminal.setCursorPosition(0, 4);
		for(Person C : currentPageList) {
			terminal.write(listIndex + ". " + C.getName());
			listIndex++;
			terminal.setCursorPosition(0, terminal.getCursorY() + 1);;
		}

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		char keyC = key.getKeyChar();

		if(keyC >= 97 && keyC <= 122) {

			try {
				return new PersonScreen(currentPageList.get(keyC - 97));
			} catch (ArrayIndexOutOfBoundsException e) {
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
