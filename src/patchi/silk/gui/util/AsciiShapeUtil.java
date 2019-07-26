package patchi.silk.gui.util;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public final class AsciiShapeUtil {

	private AsciiShapeUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void drawVLine(AsciiPanel terminal, int x, int yS, int length) {

		for(int y = yS; y < yS + length; y++) {

			Color C = (y%2 == 0) ? AsciiPanel.green : AsciiPanel.blue;
			terminal.setCursorPosition(x, y);
			terminal.write(' ', C, C);

		}
	
	}
	
	public static void drawHLine(AsciiPanel terminal, int xS, int y, int length) {

		for(int x = xS; x < xS + length; x++) {

			Color C = (x%2 == 0) ? AsciiPanel.green : AsciiPanel.blue;
			terminal.setCursorPosition(x, y);
			terminal.write(' ', C, C);

		}

	}
	
	public static void drawBox(AsciiPanel terminal, int xTL, int yTL, int xSize, int ySize) {

		drawVLine(terminal, xTL, yTL, ySize);
		drawVLine(terminal, xTL+xSize-1, yTL, ySize);
		drawHLine(terminal, xTL, yTL, xSize);
		drawHLine(terminal, xTL, yTL+ySize-1, xSize);

	}

	
}
