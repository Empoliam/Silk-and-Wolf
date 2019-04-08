package patchi.silk.gui;

import java.awt.event.KeyEvent;
import java.util.Queue;

import patchi.patchiLib.util.LimitedLinkedList;

public class TestScreen implements Screen{

	Queue<Float> numbers = new LimitedLinkedList<Float>(20);

	public TestScreen() {

		for(int k = -2; k <= 10; k +=1) { 
			numbers.add((float) k);
		}
		for(int k = 9; k >= 4; k -=1) { 
			numbers.add((float) k);
		}

	}

	@Override
	public void displayOutput(AsciiPanel terminal) {

		System.out.println(numbers);
		
		int xSize = 20;
		int ySize = 20;
		int xTL = 2;
		int yTL = 2;

		int xCursor = 0;
		int yCursor = 0;

		terminal.drawBox(1, 1, xSize + 2, ySize + 2);
		
		float lMax = Float.NEGATIVE_INFINITY;
		float lMin = Float.POSITIVE_INFINITY;

		for(Float F : numbers) {
			lMax = Math.max(lMax, F.floatValue());
			lMin = Math.min(lMin, F.floatValue());
		}

		float yDiv = (float) (ySize-1) / (lMax-lMin);

		System.out.println(lMin);
		System.out.println(lMax);
		System.out.println(yDiv);

		xCursor = xTL;

		for(Float F : numbers) {

			int y  = (int) Math.floor((F.floatValue()-lMin)*yDiv);

			System.out.println(y);
			
			yCursor = yTL - y + ySize-1;
			terminal.setCursorPosition(xCursor, yCursor);
			terminal.write(' ', AsciiPanel.white, AsciiPanel.white);
			xCursor++;
		}

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch(key.getKeyCode()) {

		case(KeyEvent.VK_ESCAPE):
			return new MainScreen();

		default:
			return this;


		}

	}

}