//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: Constants.java$
//
//	$Date: 01/04/12$
//
//	Holds values that are true throughout the system and do not change.
//*************************************************************

/** $Author: Ross Kahn $ */

package spotify;

public class Constants {

	// The exact number of Employees in each Team
	public static final int TEAM_SIZE = 2;
	
	// City index codes
	public static final int STOCKHOLM = 0;
	public static final int LONDON = 1;
	public static final int EQUAL_CITY_CODES = 2;
	
	// String representations of the cities
	public static final String[] CITY_STR = {"Stockholm", "London"};
	
	// ID ranges for the given cities
	public static final int STOCKHOLM_MIN_ID = 1000;
	public static final int STOCKHOLM_MAX_ID = 1999;
	public static final int LONDON_MIN_ID = 2000;
	public static final int LONDON_MAX_ID = 2999;
	
	// Lowest and highest possible ID numbers
	public static final int MIN_ID_NUMBER = 1000;
	public static final int MAX_ID_NUMBER = 2999;
	
	
	// The ID of the friend you want to go on the retreat
	public static final int FRIEND_ID = 1009;
	
	// The maximum number of teams that can be allowed
	public static final int MAX_TEAMS = 10000;
}
