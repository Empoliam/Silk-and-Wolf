package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import patchi.silk.main.SaveGame;

public class SaveScreen implements Screen {

	private int cursorX;
	private int cursorY;

	private String saveName = "SAVE";
	
	private boolean saved = false;
	
	public SaveScreen() {

	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		cursorX = 0;
		cursorY = 0;
		
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("Save name:");
		cursorY ++;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write(saveName);
		cursorY += 2;
		terminal.setCursorPosition(cursorX, cursorY);
		terminal.write("Enter - save");
		cursorY ++;
		terminal.setCursorPosition(cursorX, cursorY);
		if(saved) {
			terminal.write("SAVED");
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
				return new MainScreen();
			case(KeyEvent.VK_BACK_SPACE): {
				saveName = saveName.substring(0,saveName.length()-1);
				return this;
			}
			case(KeyEvent.VK_ENTER): {
				SaveGame.saveGame(saveName);
				saved = true;
				return this;
			}
			default:
				return this;
			}

		}
	}

}
