//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: Main.java$
//
//	$Date: 01/04/12$
//
//	Instantiates and runs all major parts of the system and runs them. These 
//	parts include the EventPlanner, which holds the algorithm for finding the
// 	guest list, and the input reader responsible for reading and parsing
// 	standard input.
//*************************************************************

/** $Author: Ross Kahn $ */

package spotify;
 

//==============================================================
public class Main {

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {		
		EventPlanner planner = new EventPlanner();
		InputReader reader = new InputReader(planner);
		reader.read();
		planner.plan();
	}
}








