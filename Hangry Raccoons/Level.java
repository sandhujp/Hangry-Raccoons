import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/*
 * Last updated: March 8, 2018
 * File: Level.java
 * 
 * This program is the super class for all the levels
 */


public class Level{
private int levelNumber;

//Constants for Level 1
private final int LEVEL_ONE_REQUIRED_CLICKS = 60;
private final int LEVEL_ONE_TIME_LIMIT = 20;
//Constants for Level 2
private final int LEVEL_TWO_REQUIRED_CLICKS = 30;
private final int LEVEL_TWO_TIME_LIMIT = 15;
//Constants for Level 3
private final int LEVEL_THREE_REQUIRED_CLICKS = 30;
private final int LEVEL_THREE_TIME_LIMIT = 15;
//Constants for Level 4
private final int LEVEL_FOUR_REQUIRED_CLICKS = 30;
private final int LEVEL_FOUR_TIME_LIMIT = 30;
//Constants for Level 5
private final int LEVEL_FIVE_REQUIRED_CLICKS = 55;
private final int LEVEL_FIVE_TIME_LIMIT = 30;
//Constants for Level 6
private final int LEVEL_SIX_REQUIRED_CLICKS = 45;
private final int LEVEL_SIX_TIME_LIMIT = 30;
//Constants for Level 7
private final int LEVEL_SEVEN_REQUIRED_CLICKS = 30;
private final int LEVEL_SEVEN_TIME_LIMIT = 30;

/*
	 * Name: Level(int levelNumber))
	 * 
	 * Purpose: The purpose of this constructor is to create 
	 * a level containing the level number.
	 * 
	 * Return: void
	 */

public Level(int levelNumber){
   this.levelNumber = levelNumber;
}

public int getClicksRequired(){
 // Return the clicks 
 // required for each level
 switch(levelNumber){
 case 1:
   return LEVEL_ONE_REQUIRED_CLICKS;
 case 2:
   return LEVEL_TWO_REQUIRED_CLICKS;
 case 3:
   return LEVEL_THREE_REQUIRED_CLICKS;
 case 4:
   return LEVEL_FOUR_REQUIRED_CLICKS;
 case 5:
   return LEVEL_FIVE_REQUIRED_CLICKS;
 case 6:
   return LEVEL_SIX_REQUIRED_CLICKS;
 case 7:
   return LEVEL_SEVEN_REQUIRED_CLICKS; 
      }
// error occurred if it reaches here
    return 0;
   }

public int getTimeLimit(){

switch(levelNumber){
 case 1:
   return LEVEL_ONE_TIME_LIMIT;
 case 2:
   return LEVEL_TWO_TIME_LIMIT;
 case 3:
   return LEVEL_THREE_TIME_LIMIT;
 case 4:
   return LEVEL_FOUR_TIME_LIMIT;
 case 5:
   return LEVEL_FIVE_TIME_LIMIT;
 case 6:
   return LEVEL_SIX_TIME_LIMIT;
 case 7:
   return LEVEL_SEVEN_TIME_LIMIT; 
    }
// Error occurred if it reaches here
   return 0;    
   }
}