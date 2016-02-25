/**
 * 
 */
package edu.rit.se.model;

/**
 * 
 */
import java.util.Date;

/**
 * @author Ross
 *
 */
public class Alarm {

	private User owner;
	private Date startTime;
	
	public Alarm(Date alarmStart, User ownerUser){
		owner = ownerUser;
		startTime= alarmStart;
	}
	
	public User Owner(){
		return owner;
	}
	
	public Date StartTime(){
		return startTime;
	}
}
