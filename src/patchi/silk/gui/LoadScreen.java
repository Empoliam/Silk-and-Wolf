package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import asciiPanel.AsciiPanel;
import patchi.silk.main.LoadGame;

public class LoadScreen implements Screen {

	int cursorX;
	int cursorY;

	String saveName = "SAVE";

	boolean failure = false;

	public LoadScreen() {

	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;

		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("Load game:");
		cursorY ++;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write(saveName);
		cursorY += 2;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("Enter - load");
		cursorY ++;
		terminal.setCursorPosition(cursorX, cursorY);
		if(failure) {
			terminal.write("Failed to load save");
		}

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		char code = key.getKeyChar();

		if((code >= 65 && code <= 90) || (code >= 97 && code <= 122)) { 

			saveName = saveName + (char)code;
			return this;

		} else {

			switch(code) {
			case(KeyEvent.VK_ESCAPE):
				return new TitleScreen();
			case(KeyEvent.VK_BACK_SPACE): {
				saveName = saveName.substring(0,saveName.length()-1);
				return this;
			}
			case(KeyEvent.VK_ENTER): {
				try {
					LoadGame.loadGame(saveName);
					return new MainScreen();
				} catch(FileNotFoundException e) {
					failure = true;
				}
				return this;
			}
			default:
				return this;
			}

		}

	}

}
