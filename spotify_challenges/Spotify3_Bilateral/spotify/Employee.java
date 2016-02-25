//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: Employee.java$
//
//	$Date: 01/04/12$
//
//	Represents an Employee in the company. Works in one city and may be working
//	on several projects. Has a unique employee ID number. 
//*************************************************************

/** $Author: Ross Kahn $ */
package spotify;


//=============================================================
public class Employee {
	
	private int ID;				// Unique ID of the employee instance
	private int numProjects;	// Total number of projects employee works on
	private int city;			// City employee works in
	
	/**
	 * Constructor. Determines if the ID is a Stockholm or London ID, and sets
	 * the employee's city automatically.
	 * @param ID	unique employee ID number
	 */
	public Employee(int ID){
		this.ID = ID;
		numProjects = 1;
		if(Constants.STOCKHOLM_MIN_ID <= ID && ID <= Constants.STOCKHOLM_MAX_ID){
			city = Constants.STOCKHOLM;
		}else{
			city = Constants.LONDON;
		}
	}
	
	/**
	 * 
	 * @return	City index constant of the city the employee works in
	 */
	public int getCity(){
		return city;
	}
	
	/**
	 * 
	 * @return	Unique ID number of the employee
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * 
	 * @return	How many total projects the employee works on
	 */
	public int getNumProjects(){
		return numProjects;
	}
	
	/**
	 * Increments the number of projects the employee works on
	 */
	public void incrementNumProjects(){
		numProjects++;
	}
	
	/**
	 * Two Employees are equal if they have the same ID
	 */
	public boolean equals(Object o){
		if(o instanceof Employee){
			Employee compare = (Employee) o;
			if(compare.getID() == this.getID()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * String representation of an Employee
	 */
	public String toString(){
		return "-" + ID + "/" + Constants.CITY_STR[city] + " #" + numProjects + "-";
	}
}
