package patchi.silk.gui;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public interface Screen {
	
    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
        
    default public Screen keyReleased(KeyEvent key) {
    	return this;
    }
    
}
