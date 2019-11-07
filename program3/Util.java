import java.awt.Dimension;

/** 
 * Contains the static constant values used by the program as well as methods
 * for interacting with the GUI.
 * Students may edit variables in the file as noted by each variable.
 * This file will not be turned in. This means your code should work for any
 * values of variables that say 'OK TO CHANGE VALUE'
 *
 *@author schulzca, matthewb
 */
public class Util 
{
	
	/** 
	 * The characters that represent players in the track.
	 * DO NOT CHANGE
	 */
	public static final char[] PLAYER_IDS = { 'a', 'b', 'c', 'd' };
	
	/**
	 * Character representing an empty slot in the game
	 * OK TO CHANGE VALUE
	 */
	public final static char EMPTY_SLOT = '.';
	
	/**
	 * Character representing a neutral id in the game
	 * OK TO CHANGE VALUE
	 */
	public final static char NEUTRAL_ID = '%';
	
	/**
	 * Seed for the random generator. Change this to get different games.
	 * A value of -1 will cause a random seed to be chosen (as 'new Random()').
	 * OK TO CHANGE VALUE
	 */
	public final static int RANDOM_SEED = -1;
		
	/**
	 * Opens up a modal window that prompts the user to enter new settings for 
	 * the next game.
	 * DO NOT CHANGE
	 * CALL FROM CantStop.main();
	 * 
	 * @param message the message that pops up as the title to the modal window.
	 * 
	 */
	public static void showNewGameDialog(String message)
	{
		StartupDialog sDialog = new StartupDialog(message);
		sDialog.setPreferredSize(new Dimension(350, 250));
		sDialog.pack();
		sDialog.setVisible(true);		
	}
	
	/**
	 * Opens up a modal window that prompts the user to enter new settings for 
	 * the next game. Uses information from previous game to fill in player info
	 * DO NOT CHANGE
	 * DO NOT USE
	 * 
	 * @param message the message that pops up as the title to the modal window.
	 * @param players the player info from previous game
	 */
	public static void showNewGameDialog(String message, Player[] players)
	{
		StartupDialog sDialog = new StartupDialog(message, players);
		sDialog.setPreferredSize(new Dimension(350, 250));
		sDialog.pack();
		sDialog.setVisible(true);		
	}
}
