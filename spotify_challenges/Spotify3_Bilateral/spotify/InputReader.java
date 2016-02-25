//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: InputReader.java$
//
//	$Date: 01/04/12$
//
//	Reads standard input, checks for format errors, and sends data
//	to the EventPlanner so they can be constructed into Teams and Employees
//*************************************************************

/** $Author: Ross Kahn $ */

package spotify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

//==============================================================
public class InputReader {

	private EventPlanner planner;
	
	public InputReader(EventPlanner ep){
		planner = ep;
	}
	
	/**
	 * Reads from and loops on standard input. Reads in the first line (number of
	 * teams), but then calls to process() for parsing of employee IDs.
	 */
	public void read(){
		
		// Scanner for standard input
		Scanner scanner;
	    scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	    
	    // Input does not have a team number. Input is either
	    // empty or a violation of format
	    if(!scanner.hasNextInt()){
	    	if(scanner.hasNext()){
	    		System.out.println("Illegal value for team number: " + scanner.next());
	    	}else{
	    		System.out.println("Error: Empty input");
	    	}
	    	return;
	    }
	    
	    // Team number
	    int numTotalTeams = scanner.nextInt();
	    
	    // Checks if team number is in legal range
	    if(numTotalTeams <= 0 || numTotalTeams > Constants.MAX_TEAMS){
	    	System.out.println("Illegal amount of teams (must be between 1-10000)");
	    	return;
	    }    
	    
	    boolean keepReading = true;		// While true, keep reading from input
	    
	    while (keepReading) {
	    	keepReading = process(scanner);	
	    }
	    
	}
	
	/**
	 * Processes a single team. Checks for format errors, then sends to EventPlanner
	 * @param scanner	Input reader with stdin tied to it
	 * @return			True if there is more input to be read. False if EOF is
	 * 					reached or there is an error.
	 */
	private boolean process(Scanner scanner){
		
		int teamMember;	// For-loop index for current team member (0 or 1)
		
		// List of all employee IDs read in
		int[] eList = new int[Constants.TEAM_SIZE];
		
		// TEAM_SIZE refers to how many employees will be on a team line
		for(teamMember = 0; teamMember < Constants.TEAM_SIZE; teamMember++){
		
			// There is an ID to be read
			if(scanner.hasNextInt()){
				int empID = scanner.nextInt();	// Employee ID number
				
				// If ID is within legal range, put into eList. If not, error.
				if(empID >= Constants.MIN_ID_NUMBER && empID <= Constants.MAX_ID_NUMBER){
					//System.out.println(empID);
					eList[teamMember] = empID;
					
				}else{
					System.out.println("Employee ID out of range (1000-2999): " + empID);
					return false;
				}
				
			// There is nothing to be read (a.k.a EOF)
			}else if(!scanner.hasNext()){
				//System.out.println("EOF");
				return false;
				
			// There is some unknown non-integer input. Error.
			}else{
				System.out.println("Illegal input: " + scanner.next());
				return false;
			}
		}
		
		// Return false and error if there was a problem with making a team
		if(!planner.addTeam(eList)){
			System.out.println("Illegal team: " + eList[0] + " " + eList[1]);
			return false;
		}else{
			return true;
		}
		
	}
}
