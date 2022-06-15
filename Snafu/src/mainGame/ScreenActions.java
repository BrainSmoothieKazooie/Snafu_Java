package mainGame;

import java.awt.event.KeyEvent;

/*
 * ScreenActions.java
 * 
 * An interface used to allow every card
 * stored in the MainScreen
 * to be able to use the MainScreens
 * ActionListener and KeyListener, 
 * and initialize the screen again
 * once the card is enabled.
 * 
 * Initialization is done through
 * the initalizeScreen() method.
 */

public interface ScreenActions 
{
	public void step();
	public void initializeScreen();
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
}
