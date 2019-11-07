///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Can't Stop
//File:             Gameboard.java
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
import java.util.ArrayList;														

/**
 * The GameBoard handles the flow of the game, as well as maintaining the set
 * of tracks and players which make up the state of the game.
 * 
 * @author albrooks
 */
public class GameBoard 
{
	//CONSTANTS
	private final int NUM_TRACKS = 11;		 //Number of tracks in a game board. 
	private final int MAX_TRACK_LENGTH = 12;  //Longest possible track length. 
	private final int MAX_NEUTRAL_CONES = 3;  //Maximum number of neutral cones.
	private final int WINNING_NUM = 3;		  //Number of tracks needed to win.
	
	//VARIABLES 
	private Player[] playerArray = null; //Array of Player objects. 
	private int turnCounter = 0;		 //Determines whose turn is up.
	private Track[] trackArray = new Track[NUM_TRACKS];//Array of Track objects.
	private int numNeutralCones = 0; 	//Number of neutral cones (Max is 3).
	
    /**
     * Initialize all GameBoard information
     * 
     * @param names The names of the players
     * @param colors The colors of the players (respective to names)
     */
    public GameBoard(String[] names, Color[] colors) 
    {
    	//Assign size of PlayerArray based on number of names received 
    	playerArray = new Player[names.length];
    	
    	//Assign names, IDs, and colors to Player objects in PlayerArray
    	for(int i = 0; i < names.length; i++)
    		playerArray[i] = new Player(names[i],Util.PLAYER_IDS[i],colors[i]);
    
    	//Initialize trackArray with Track objects. 
    	for(int i = 0; i < NUM_TRACKS; i++)    
    	   trackArray[i] = new Track(i+2);   //Parameters are roll numbers 2-12 
    }

    
    /**
     * Get the player whose turn it is.
     * 
     * @return The current Player object.
     */
    public Player getCurrentPlayer() 
    {
    	//Returns which player whose turn it is based on the turnCounter
    	return playerArray[turnCounter];
    }

    
    /**
     * Get the players
     * 
     * @return The array of Player objects
     */
    public Player[] getPlayers() 
    {
    	//Returns the playerArray
        return playerArray;
    }

    
    /**
     * Get the tracks
     * 
     * @return The array of Track objects
     */
    public Track[] getTracks() 
    {  
    	//Returns trackArray
        return trackArray;
    }

    
    /**
     * Check whether the given player won.
     * 
     * @param toCheck The player to check.
     * @return Whether the given player won.
     */
    public boolean isWinner(Player toCheck) 
    {
        int toChecksOwnedTracks = 0; 	//Number of tracks owned.
        
    	//See how many tracks owned by the player
        for(int t = 0; t < trackArray.length; t++) 
        {									   
        	if( trackArray[t].getOwner() == toCheck.getId() )
        		toChecksOwnedTracks++;
        }
    	
        //Player wins if they own three tracks
        return toChecksOwnedTracks == WINNING_NUM;  
        
    }

    
    /**
     * Checks if a dice pair sum is a valid track choice.
     *  
     * @param sum the sum of the chosen dice pair
     * @return null if choice is valid. Otherwise return (with this priority):
     * <br />
     * "Sorry, that track is already owned." if the chosen track is owned
     * (including by the current player)<br />
     * "Sorry, you are out of neutral cones." if placing in that track would
     * require a cone, and they are out.
     * 
     */
    public String isValidChoice(int sum) 
    {
        String retString = null; 			//String to be returned.
        
        //If the sum is less than 2, add 2 to avoid an IndexOutOfBoundsError
        if(sum < 2)
        	sum +=2;
       
        numNeutralCones = 0; //Reset to zero to avoid accumulating previous sums
        
        //Count number of neutral cones in TrackArray
        for(int t = 0; t < trackArray.length; t++)
        {
        	for(int s = 0; s < trackArray[t].getLength() + 1; s++)
        	{
        		if(trackArray[t].getIdAt(s) == Util.NEUTRAL_ID)
        			numNeutralCones++;
        	}
        }
        
        //If the chosen track is owned return this string						
    	if( trackArray[sum - 2].isOwned() )  								
    		retString = "Sorry, that track is already owned.";
    	
        //If there are no more neutral cones and the chosen track is inactive
    	//return this string
        if( numNeutralCones >= MAX_NEUTRAL_CONES && 
        	!trackArray[sum - 2].active() )
        	retString = "Sorry, you are out of neutral cones.";
      //If the chosen track is owned return this string						
    	if( trackArray[sum - 2].isOwned() )  								
    		retString = "Sorry, that track is already owned.";
    	
        return retString;
    }
    
    /**
     * Initializes information required to start a new player's turn.
     */
    public void startNewTurn() 
    {
    	numNeutralCones = 0;	//Reset number of neutral cones on the board.
    }

    
    /**
     * Checks if a roll results in the player busting.
     * @param rolls The four dice roll values (1-6)
     * @return true if the player busted, false otherwise.
     */
    public boolean didPlayerBust(int[] rolls) 
    {
    	//Array of possible pairs
    	ArrayList<Integer> possPairs = new ArrayList<Integer>();				
    	
    	//Make every possible pair from rolls array
    	for (int i = 0; i < rolls.length; i++) 
    	{
    		for (int j = 0; j < rolls.length; j++ ) 
    		{
    			if(i != j)
    			{
    				possPairs.add(rolls[i] + rolls[j]);
    			}
    		}
    	}
    	
    	numNeutralCones = 0;  //Resets numNeutralCones to avoid double counting
    	//Count the number of neutral cones.
    	for(int i = 0; i < trackArray.length; i++)
    	{
    		for(int j = 0; j < trackArray[i].getLength() + 1; j++)
    		{
    			if(trackArray[i].getIdAt(j) == Util.NEUTRAL_ID)
    				numNeutralCones++;
    		}
    	}
    	
    	//Check whether any possible pair makes a valid move
    	for (int a = 0; a < possPairs.size(); a++)
    	{
    		//If the possible pair gives a valid choice, return false
    		if( (numNeutralCones < MAX_NEUTRAL_CONES) || 
    			trackArray[possPairs.get(a) - 2].active() ) 
    		{ 					                   
    			return false;
    		}
    	}
    	
    	return true;
    }

    
    /**
     * Updates the board according to the dice rolls and the sum of a valid 
     * pair chosen by the user. If the second pair produces a valid move, 
     * updates the board for that pair sum as well.
     *  
     * @param rolls The four dice roll values (1-6)
     * @param firstPairSum The sum of the dice the user chose to pair.
     */
    public void handleUserChoice(int[] rolls, int firstPairSum) 
    {
        int totalSum = 0; 		//Total sum of rolls.
    	int secondPairSum = 0;	//Second pair sum from the rolls.
    	boolean firstSumImplemented = false; //Whether the first pair is valid
    	
    	if(!didPlayerBust(rolls))  //See if player busted before proceeding
    	{
    		//Calculate the second pair sum
    		for(int s = 0; s < rolls.length; s++)
    		{
    			totalSum += rolls[s];
    		}
    		
    		secondPairSum = totalSum - firstPairSum;
    	}
    	
    	//Implement the first choice, if there are enough neutral cones or 
    	//the chosen track is active
    	if( (numNeutralCones < MAX_NEUTRAL_CONES) || 
    		trackArray[firstPairSum - 2].active() )
    	{
    		trackArray[firstPairSum - 2].advance(getCurrentPlayer().getId());
    		firstSumImplemented = true;
    	}
    	
    	numNeutralCones = 0; //Reset value to avoid double-counting
    	//Count number of neutral cones after the first move
    	for(int i = 0; i < trackArray.length; i++)
    	{
    		for(int j = 0; j < trackArray[i].getLength() + 1; j++)
    		{
    			if(trackArray[i].getIdAt(j) == Util.NEUTRAL_ID)
    				numNeutralCones++;
    		}
    	}
    	
    	//If there are enough neutral cones, or the track is active and the 
    	//track is not owned implement the move
    	if( firstSumImplemented && 
    	  ( numNeutralCones < MAX_NEUTRAL_CONES || 
    	    trackArray[secondPairSum - 2].active() )
    	    && !trackArray[secondPairSum - 2].isOwned() )
    	{
    		trackArray[secondPairSum-2].advance(getCurrentPlayer().getId());
    	}
    	
    	System.out.print(this.toString());										//PRINT HERE
    }
    
    
    /**
     * Ends the current users turn. If the player busted, progress is lost.
     * If the player did not bust, neutral cones are replaced with the player's
     * tokens. Checks to see if the current player won and advances to next 
     * player's turn.
     * 
     * @param busted Whether the player busted or not.
     * @return Whether or not the current player won.
     */
    public boolean endTurn(boolean busted) 
    {
    	for(int t = 0; t < trackArray.length; t++)
    	{
    		if(trackArray[t].active() || 
    		   trackArray[t].getOwner() == Util.NEUTRAL_ID)
    		{
    			//If player busted take out the neutral cones
    			if(busted)		
    				trackArray[t].clear();

    			//If player did not bust replace neutral cones with player ID
    			else  
    				trackArray[t].commit(getCurrentPlayer().getId());
    		}
    	}
    	
    	System.out.print(this.toString());										//PRINT HERE
    	
    	//If the current player is a winner, return true, else move to the next
    	//player and return false
    	if (isWinner( getCurrentPlayer()))
    		return true;
    	else 
    	{
    		advancePlayer();
    		return false;
    	}	
    }

    
    /**
     * Advances the current player to the next player.
     */
    public void advancePlayer() 
    {
    	//Reset the turn counter after the last player in the playerArray, else
    	//it progresses to the next player
    	if(turnCounter >= playerArray.length - 1)								
    	{
    		turnCounter = 0;
    	}	
    	else
    		turnCounter++;
    }

    
    /**
     * Return an ASCII version of the game board for display.
     * 
     * @return An ASCII game board, including track owners.
     */
    @Override
    public String toString() 
    {
    	//VARIABLES
    	String totalString;			//Entire game board string.
    	String ownerRow = "";		//String showing owners of rows.
    	
    	//Horizontal version of the game board. To be turned into
    	//actual vertical version later
    	char[][] hBoard = new char[trackArray.length][MAX_TRACK_LENGTH];
    	
    	//Vertical version of the game board.
    	char[][] vBoard = new char[MAX_TRACK_LENGTH][trackArray.length];
    	
    	String boardString = "";		//String rep of board array
    	
    	//CONSTANTS
    	final char SPACE = '-';		   //Char for empty spaces around the board
    	
    	//Adding owner IDs or empty spaces to ownerRow
    	for(int o = 0; o < trackArray.length; o++)
    	{
    		ownerRow += trackArray[o].getOwner();
    	}
    	
    	//Filling all horizontal version of the game board with '-'
    	for(int v = 0; v < trackArray.length ; v++)
    	{
    		for(int s = 0; s < hBoard[v].length; s++)
    		{
    			hBoard[v][s] = SPACE;
    		}
    	}
    	
    	//Change certain hBoard characters into IDs on the tracks
    	//in a way such that there is a  rhombus formation
    	for(int v = 0; v < hBoard.length; v++)
    	{
    		int i = 0;	//Index for individual tracks.
    		int jump = hBoard[v].length/2 - trackArray[v].getLength()/2;
    		//jump :Length of the gap between the bottom of the board and the 
    		//start of the track array. (to get a rhombus formation)
    		
    		for(int s = jump; s < jump + trackArray[v].getLength(); s++)
    		{
    			hBoard[v][s] = trackArray[v].getIdAt(i);
    			i++;
    		}
    	}
    	
    	//Transform the horizontal board into a vertical board
    	for(int r = 0; r < vBoard.length; r++)
    	{
    		for(int c = 0; c < vBoard[r].length; c++)
    		{
    			vBoard[r][c] = hBoard[c][vBoard.length - r - 1];
    		}
    	}
    	
    	//Turn the board array into a string representation
    	for(int r = 0; r < vBoard.length; r++)
    	{
    		for(int c = 0; c < vBoard[r].length; c++)
    		{
    			boardString += vBoard[r][c];
    		}
    		
    		boardString += "\n";	//Make new lines
    	}
    	
    	//Combine the components of the total string
    	totalString = "OWNERS: \n" + ownerRow + "\nBOARD: \n" + boardString;
       
    	return totalString;
    }
}
