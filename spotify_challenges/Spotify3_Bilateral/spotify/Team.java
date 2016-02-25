//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: Team.java$
//
//	$Date: 01/04/12$
//
//	Represents a Team within the company. By default, a Team is made up of
//	2 members. One member MUST work in Stockholm, and the other MUST work in
//	London, otherwise the Team is invalid. Provides functionality for finding
//	the Employee in the team by their city index, or by their ID number. Can 
//	also find the Employee in the team, if any, that is working on the most 
//	number of projects (used in the algorithm for creating the guest list).
//*************************************************************

/** $Author: Ross Kahn $ */

package spotify;

import java.util.ArrayList;

//===============================================================
public class Team {
	
	private Employee[] employeeList;	// List of Employees on the Team
	private boolean validTeam;			// True if the Team is valid
	
	/**
	 * Constructor. Verifies validity of team (one employee from Stockholm,
	 * the other from London), and also assigns the Employees to the 
	 * employeeList using their city index.
	 * @param employee1
	 * @param employee2
	 */
	public Team(Employee employee1, Employee employee2){
		employeeList = new Employee[Constants.TEAM_SIZE];
		if(employee1.getCity() == employee2.getCity()){
			validTeam = false;
			employeeList[0] = employee1;
			employeeList[1] = employee2;
		}else{
			employeeList[employee1.getCity()] = employee1;
			employeeList[employee2.getCity()] = employee2;
			validTeam = true;
		}

	}
	
	/**
	 * Secondary constructor. Takes a list of Employees and feeds them
	 * into the primary constructor
	 * @param eList		List of Employees to be added to the Team
	 */
	public Team(ArrayList<Employee> eList){
		this(eList.get(0), eList.get(1));
	}
	
	/**
	 * 
	 * @return	True if there is one Employee from London and one Employee
	 * 			from Stockholm on the Team
	 */
	public boolean isTeamValid(){
		return validTeam;
	}
	
	/**
	 * Returns the employee at a given index (designed to be used with the
	 * city index constants)
	 * @param index		City index of the desired employee on the Team
	 * @return			Employee who works at the given city
	 */
	public Employee getEmployeeAtIndex(int cityIndex){
		return employeeList[cityIndex];
	}
	
	/**
	 * 
	 * @return 	Entire employee list
	 */
	public Employee[] getEmployees(){
		return employeeList;
	}
	
	/**
	 * Checks if the given ID is held by an Employee on the Team
	 * @param ID	ID to check against Employees on the Team
	 * @return		True if there is an Employee on the Team with the ID
	 */
	public boolean containsID(int ID){
		for(Employee e: employeeList){
			if(e.getID() == ID){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the Employee on the Team with the given ID
	 * @param ID	ID to use as search parameter
	 * @return		Employee on team with ID. Returns null if there is no Employee
	 * 				with that ID
	 */
	public Employee getEmployeeWithID(int ID){
		for(Employee e: employeeList){
			if(e.getID() == ID){
				return e;
			}
		}
		return null;
		
	}
	
	/**
	 * Returns the Employee on the team with the highest number of projects, or 
	 * null if all of the Employees have an equal number of projects
	 * @return
	 */
	public Employee getBusiestEmployee(){
		boolean allEqual = true;
		Employee busiestEmployee = null;
		
		// Loops through employee list and if there is a difference in the
		// number of projects the Employees work on, return the highest
		for(Employee e: employeeList){
			if(busiestEmployee == null){
				busiestEmployee = e;
				
			}else if(busiestEmployee.getNumProjects() != e.getNumProjects()){
				allEqual = false;
				busiestEmployee = e.getNumProjects() > busiestEmployee.getNumProjects() ? e : busiestEmployee;
			}
		}
		
		// If all the employees have an equal number of projects they're working
		// on, return null. Otherwise, return the Employee with the highest
		if(allEqual){
			return null;
		}else{
			return busiestEmployee;
		}
		
	}
	
	/**
	 * Two Teams are equal if all the Employees in the Team are equal to the other's
	 */
	public boolean equals(Object o){
		if(o instanceof Team){
			Team compare = (Team) o;
			boolean arrayCheck = true;
			for(int empIndex=0; empIndex < employeeList.length; empIndex++){
				if(!compare.getEmployeeAtIndex(empIndex).equals(this.getEmployeeAtIndex(empIndex))){
					arrayCheck = false;
				}
			}
			return arrayCheck;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns a String representation of a Team
	 */
	public String toString(){
		String returnString = "\n(";
		for(Employee e : employeeList){
			returnString = returnString + e.toString() + ", ";
		}
		return returnString.substring(0, returnString.lastIndexOf(',')) + ")";
		
	}
}
