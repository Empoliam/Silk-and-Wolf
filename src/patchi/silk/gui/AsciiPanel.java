package patchi.silk.gui;

import java.awt.Color;

import asciiPanel.AsciiFont;

public class AsciiPanel extends asciiPanel.AsciiPanel {

	private static final long serialVersionUID = 1L;

	public AsciiPanel() {
		super();
	}

	public AsciiPanel(int width, int height) {
		super(width, height);
	}

	public AsciiPanel(int width, int height, AsciiFont font) {
		super(width, height, font);
	}

	public AsciiPanel drawBox(int xTL, int yTL, int xSize, int ySize) {
		
		for(int x = xTL; x <
				xTL + xSize; x++) {
			
			Color C = (x%2 == 0) ? green : blue;
			this.setCursorPosition(x, yTL);
			this.write(' ', C, C);
			this.setCursorPosition(x, yTL + ySize-1);
			this.write(' ', C, C);
			
		}
		
		for(int y = yTL; y < yTL + ySize; y++) {
		
			Color C = (y%2 == 0) ? green : blue;
			this.setCursorPosition(xTL, y);
			this.write(' ', C, C);
			this.setCursorPosition(xTL + xSize-1, y);
			this.write(' ', C, C);
			
		}
		
		return this;
		
	}
	
}
