///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Can't Stop
//File:             Player.java
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

/**
 * A single player of the game has a name and an id.
 * 
 * @author albrooks
 */
public class Player 
{
	//VARIABLES
	private String name = null;				//The player's name.
	private char id = 'a';					//The player's ID.
	private Color color = Color.black;		//The player's color
	
    /**
     * Create a player.
     * 
     * @param name The player's name.
     * @param id The player's assigned id.
     * @param color The player's color.
     */
    public Player(String name, char id, Color color) 
    {
        this.name = name;
        this.id = id;
        this.color = color;
    }

    /**
     * Get the player's name.
     * 
     * @return The player's name.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Get the player's id.
     * 
     * @return The player's id.
     */
    public char getId() 
    {
        return id;
    }

    /**
     * Get the player's color
     * 
     * @return The player's color
     */
    public Color getColor() 
    {
        return color;
    }
}
