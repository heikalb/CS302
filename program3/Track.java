///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  Can't Stop
//File:             Track.java
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

/**
 * A track in Can't Stop is associated with a number from 2-12.
 * The likelihood of rolling this number determines the length of the track.
 * Each track consists of a set of spaces, each of which may either be
 * empty or may contain a single peg belonging to one of the players.
 * 
 * <p>A track may be "owned" by a single player, in which case no one may play
 * in this track anymore. A player owns a track when the player advances past
 * the entire track.
 * 
 * @author albrooks
 */
public class Track 
{
	//VARIABLES
	private int length; 			//Track length.
	private char[] track = null; 	//Character array associated with a track.
	
    /**
     * Create a track with the appropriate length, given the probability of
     * rolling the given number.
	 * <p> One formula that works is to use either (12-roll+1)*2 or
	 * (roll-1)*2 depending on whether the roll is greater than 7.
     * 
     * @param roll The number associated with this track.
     */
    public Track(int roll) 
    {
        //Set length based on roll number
    	if(roll > 7)
        	length = (12 - roll + 1) * 2;  
        else
        	length = (roll - 1) * 2;
        
    	length += 1; //+1 for the spot where track ownership is claimed
    	
    	//Assign length of track array
    	track = new char[length];
    	
    	//Initialize track with periods ('.')
    	for(int i = 0; i < track.length; i++)
    		track[i] = Util.EMPTY_SLOT;
    }

    
    /**
     * Report whether this track is already owned by a player or the neutral id.
     * 
     * @return Whether the track is owned.
     */
    public boolean isOwned() 
    {
    	//Return true if the owner spot is occupied by a neutral cone or a  
    	//player ID.
    	return  track[length - 1] == Util.NEUTRAL_ID || 
    			track[length - 1] == Util.PLAYER_IDS[0] ||
    			track[length - 1] == Util.PLAYER_IDS[1] ||
    			track[length - 1] == Util.PLAYER_IDS[2] ||
    			track[length - 1] == Util.PLAYER_IDS[3];						
    		   
    }

    
    /**
     * Return the name of the owner (if any). EMPTY_SPACE is returned if no one
     * owns the track.
     * 
     * @return The name of the owner.
     */
    public char getOwner() 
    {
    	return track[length - 1];	//Return the char at the owner spot.
    }

    
    /**
     * Advance the neutral position. If no neutral marker is found, place one
     * in the next available space above the current player's position. If the
     * current player has not yet played in this track, her position is
     * considered to be "-1".
     * 
     * @param player The current player id.
     */
    public void advance(char player) 
    {
        //VARIABLES
    	boolean theresNeutralCone = false;
        // If there's a neutral cone on the track
        boolean theresPlayerCone = false;
        // If there's a player cone on the track
        
        int neutralConePos = 0;	//Index of the neutral cone.
        int playerPos = 0;		//Index of the player's cone.
        
        boolean placedConeDone = false; //Limits cone placement to once.
        
        //Look for a neutral cone and the player's cone in the track
        //and record their positions
        for(int i = 0; i < track.length; i++)
        {
        	if(track[i] == Util.NEUTRAL_ID)
        	{
    			theresNeutralCone = true;
    			neutralConePos = i;
        	}
        	
        	if(track[i] == player)
        	{
        		theresPlayerCone = true;
        		playerPos = i;
        	}
        }
        
        //If there's a neutral cone, advance it to the next empty slot, 
        //jumping over other players if necessary
        if(theresNeutralCone)
        {
        	for(int s = neutralConePos + 1; s < track.length && 
        		!placedConeDone; s++ )
        	{
        		if(track[s] == Util.EMPTY_SLOT)
        		{
        			track[s] = Util.NEUTRAL_ID;
        			placedConeDone = true;
        			track[neutralConePos] = Util.EMPTY_SLOT;
        		}
        	}
        }
        
        //If there's the player's cone, put a neutral cone in the next available
        //space ahead, jumping over other players' cones if necessary
        else if(theresPlayerCone)
        {
        	for(int s = playerPos + 1; s < track.length && !placedConeDone; s++)
        	{
        		if(track[s] == Util.EMPTY_SLOT)
        		{
        			track[s] = Util.NEUTRAL_ID;
        			placedConeDone = true;
        		}
        	}
        }
        
        //If the player has not played this track, place a neutral on the lowest
        //available space
        else if(!theresNeutralCone && !theresPlayerCone)
        {
        	for(int s = 0; s < track.length && !placedConeDone; s++ )
        	{
        		if(track[s] == Util.EMPTY_SLOT)
        		{
        			track[s] = Util.NEUTRAL_ID;
        			placedConeDone = true;
        		}
        	}
        }
        
    }

    
    /**
     * Determine whether there is a neutral cone in this track already.
     * 
     * <p>If the track is owned, it is not active.
     * 
     * @return True if there is a neutral cone in the track, false otherwise.
     */
    public boolean active() 
    {
    	boolean theresNeutralCone = false;  //Whether there's a neutral cone
    	
    	//Check whether there's a neutral cone on the track
    	for(int i = 0; i < track.length - 1; i++)
    	{
    		if(track[i] == Util.NEUTRAL_ID)
    			theresNeutralCone = true;
    	}
    	
    	//Return true if there's a neutral cone, return false otherwise
        return theresNeutralCone; 
    }

    
    /**
     * Replace the neutral cone (if present) with one of the appropriate id.
     * If the current player now owns the track, also remove all other players'
     * cones from the track.
     * 
     * @param id The id of the player being committed.
     */
    public void commit(char id) 
    {
    	//Find an existing player cone and erase it
    	for(int i = 0; i < track.length; i++)
    	{
    		if(track[i] == id)
    			track[i] = Util.EMPTY_SLOT;
    	}
    	
    	//Replace the neutral cone with the player ID
    	for(int i = 0; i < track.length; i++)
    	{
    		if(track[i] == Util.NEUTRAL_ID) 
    			track[i] = id;
    	}
    
    	//Replace all other players' cones with empty spaces
    	//if the player now owns the track
    	if(getOwner() == id)  
    	{
    		for(int i = 0; i < track.length - 1; i++)
    		{
    			if(track[i] != id)
    				track[i] = Util.EMPTY_SLOT;
    		}
    			
    	}
    }

    
    /**
     * Remove the neutral cone (if present). Do not replace it.
     * 
     * <p>Used when going bust.
     */
    public void clear() 
    {
    	//Replace a neutral cone with empty spaces
    	for(int i = 0; i < track.length; i++) 
    	{
    		if(track[i] == Util.NEUTRAL_ID)
    			track[i] = Util.EMPTY_SLOT; 
    	}
    }

    
    /**
     * Report the length of the track.
     * 
     * @return The length of the track.
     */
    public int getLength() 
    {
        return (length - 1); //-1 to exclude the owner's spot
    }

    
    /**
     * Report the player at a given position. 
     * 
     * @param i The position queried.
     * @return The character at the given position in the track.
     */
    public char getIdAt(int i) 
    {
        //Returns the ID of the player at the index 'i' of the track
    	return track[i];
    }
}
