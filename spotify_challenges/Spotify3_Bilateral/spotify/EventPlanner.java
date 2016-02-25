//************************************************************
//	2011 Ross Kahn
//************************************************************
//
//	$Id: EventPlanner.java$
//
//	$Date: 01/04/12$
//
//	Holds and maintains all data structures relating to Teams and Employees.
//	Also contains the algorithm to construct the guest list.
//*************************************************************

/** $Author: Ross Kahn $ */

package spotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//=============================================================
public class EventPlanner {

	private HashMap<Integer, Employee> employeeMap;	// Map of ID to Employee object
	private ArrayList<Team> teamList;				// All teams in order of projects
	private int numStockholmEmployees;				// Number of employees in the Stockholm office
	private int numLondonEmployees;					// Number of employees in the London office
	private int lowerCityCode;						// Which city has lower number of employees
	
	/**
	 * Constructor
	 */
	public EventPlanner(){
		employeeMap = new HashMap<Integer, Employee>();
		teamList = new ArrayList<Team>();
		numStockholmEmployees = 0;
		numLondonEmployees = 0;
	}
	
	/**
	 * Adds a single employee with given ID to employee dictionary. If the
	 * employee is already in the system, increment the number of projects
	 * that employee works on. If not, put the Employee into the system and 
	 * increment the number of employees for that city.
	 * @param ID	ID of the employee to add into the system
	 * @return		Employee object created with, or found with, the ID
	 */
	private Employee addEmployee(int ID){
		if(employeeMap.containsKey(ID)){
			
			// Updates the Employee instance in the Map so state is correct
			employeeMap.get(ID).incrementNumProjects();
			
		}else{
			employeeMap.put(ID, new Employee(ID));
			incrementEmployeeNumber(ID);
		}
		return employeeMap.get(ID);
	}
	
	/**
	 * Given an Employee's ID, find the Employee's city and increment the number
	 * of employees that work in that city.
	 * @param ID	ID of the Employee
	 */
	private void incrementEmployeeNumber(int ID){
		if(ID >= Constants.STOCKHOLM_MIN_ID && ID <= Constants.STOCKHOLM_MAX_ID){
			numStockholmEmployees++;
		}else{
			numLondonEmployees++;
		}
	}
	
	/**
	 * Of the two cities, find which one has less employees working in it.
	 * Both cities may have an equal number of employees.
	 * @return		City index with lower num of employees, or a constant
	 * 				that means they are equal
	 */
	private int getLowerCityCode(){
		int lower;
		if(numStockholmEmployees > numLondonEmployees){
			lower = Constants.LONDON;
		}else if(numLondonEmployees > numStockholmEmployees){
			lower = Constants.STOCKHOLM;
		}else{
			lower = Constants.EQUAL_CITY_CODES;
		}
		return lower;
	}
	
	/**
	 * Takes a list of IDs of employees on a team. These employees may or may 
	 * not already exist in the system, so calls addEmployee to ensure they are.
	 * Then, a new Team object is created with the employees. If there is not 
	 * already a team with the same members, and the team has both a London and
	 * Stockholm employee in it, then return true
	 * @param eList		Array of employee IDs
	 * @return			True if team made with eList is valid. False if not.
	 */
	public boolean addTeam(int[] eList){
		ArrayList<Employee> teamMembers = new ArrayList<Employee>();
		for(int id : eList){
			addEmployee(id);
			teamMembers.add(employeeMap.get(id));	
		}
		
		Team newTeam = new Team(teamMembers);
		
		// Ensures there are no teams with the same 2 members
		if(teamList.contains(newTeam) || !newTeam.isTeamValid()){
			return false;
		}else{
			teamList.add(newTeam);
			return true;
		}
	}
	
	/**
	 * Cycles through a given guest list and checks if any members of the given
	 * team are already on that guest list
	 * @param guestList		Employees already chosen for the retreat
	 * @param team			An employee on the team may or may not already be on the guest list
	 * @return				True if there is an employee on the team that is on the guest list
	 */
	private boolean employeeInTeamOnGuestlist(HashSet<Employee> guestList, Team team){
		for(Employee e: team.getEmployees()){
			if(guestList.contains(e)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Main algorithm of the program. Creates a guest list for the company 
	 * retreat based on a set of prioritized conditions. Essentially traverses
	 * a binary tree (made up of Employees grouped into teams of 2) to find the
	 * least number of people who should go on the retreat. Compared to the brute
	 * force solution of finding every combination of people (which easily reaches
	 * exponential big-O complexities), this solution is always O(n), n being the
	 * total number of projects.
	 * 
	 * At each node on the tree, the program decides 1 of 2 choices based on 
	 * prioritized conditionals. The conditions in order of greatest priority
	 * to least is:
	 * 
	 * 1. One of the Employees being observed is already on the guest list
	 * 2. One city code (which refers to how many employees work in that city)
	 * 	  is lower than the other. Take the employee from that city.
	 * 3. One of the employees is working on a more projects than the other.
	 * 4. One of the employees is your friend.
	 * 5. Either employee will work for the optimal solution. Take the first one 
	 * 	  in the team.
	 */
	public void plan(){
		lowerCityCode = getLowerCityCode();
		HashSet<Employee> guestList = new HashSet<Employee>();
		
		for(Team curTeam : teamList){
			
			// Find the employee on the team working on the most projects
			// (May not be needed)
			Employee highestProjects = curTeam.getBusiestEmployee();
			
			// Priority 1
			if(employeeInTeamOnGuestlist(guestList, curTeam)){
				// Optimal employee is already in guest list, so do nothing
				
			// Priority 2
			}else if(lowerCityCode != Constants.EQUAL_CITY_CODES){
				
				// Add the employee from the city with the lower number of
				// workers
				guestList.add(curTeam.getEmployeeAtIndex(lowerCityCode));
				
			// Priority 3
			}else if(highestProjects != null){
				
				// Add the employee who works on more projects
				guestList.add(highestProjects);
				
			// Priority 4
			}else if(curTeam.containsID(Constants.FRIEND_ID)){
				
				// Add your friend to the guest list (either employee would
				// be optimal, but favor your friend for the guest list)
				guestList.add(curTeam.getEmployeeWithID(Constants.FRIEND_ID));
			
			// Priority 5
			}else{
				
				// Add the first member of the team to the guest list
				guestList.add(curTeam.getEmployeeAtIndex(0));
				
			}
			
		}
		
		// Prints out the optimal lowest number of employees needed to have
		// a representative from each project
		System.out.println(guestList.size());
		for(Employee e: guestList){
			System.out.println(e.getID());
		}
	}
	
}
