///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            Can't Stop
//Files:            Gameboard.java, Track.java, Player.java, Gui.java, 
//    				Util.java, CantStop.java, StartupDialog.java
//Semester:         CS302 Fall 2014
//
//Author:           Lexi Oxborough
//Email:            oxborough@wisc.edu
//CS Login:         oxborough
//Lecturer's Name:  Debra Deppeler
//Lab Section:      314
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//If allowed, learn what PAIR-PROGRAMMING IS, 
//choose a partner wisely, and complete this section.
//
//Pair Partner:     Heikal Badrulhisham
//Email:            heikal@cs.wisc.edu
//CS Login:         heikal
//Lecturer's Name:  Laura Legault
//Lab Section:      345
//
//STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
//Credits:          (list anyone who helped you write your program)
////////////////////////////80 columns wide //////////////////////////////////

import java.awt.Color;

public class CantStop 
{
	GameBoard cantStopGameBoard = null; //Create a GameBoard object
	
    /**
     * Creates a new GameBoard instance
     * 
     * @param names The names of the players
     * @param colors The colors of the players (respective to names)
     */
    public CantStop(String[] names, Color[] colors) 
    {
    	//Pass array of names and colors to the GameBoard object
    	cantStopGameBoard = new GameBoard(names, colors);
    }

    /**
     * Get the GameBoard
     * @return the GameBoard instance created in the constructor
     */
    public GameBoard getGameBoard() 
    {
        //return the GameBoard object
    	return cantStopGameBoard;
    }

    
    /**
     * Creates a player selection window.
     * Once players have been chosen, the game GUI will
     * start, creating a CantStop object in the process.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        Util.showNewGameDialog("Choose player names and colors.");
    }
}
