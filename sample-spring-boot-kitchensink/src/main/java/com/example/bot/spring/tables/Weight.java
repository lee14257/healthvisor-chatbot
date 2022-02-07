package com.example.bot.spring.tables;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Weight {
	
	/**This is the table model for the weights of the user in the database.
	 * 
	 */
	
	/** This is the unique ID of the weight of the user.
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	/** This is the ID of the user.
	 * 
	 */
	String userID;
	
	/** This is the weight of the user.
	 * 
	 */
	private double weight;
	
	/** This is the time when the user input this weight.
	 * 
	 */
	private Timestamp time;
	
	/** This method is the getter of the user's ID.
	 * 
	 * @return the ID of the user
	 */
	public String getUserID() {
		return userID;
	}
	
	/** This method is the getter of the user's weight.
	 * 
	 * @return the weight of the user
	 */
	public double getWeight() {
		return weight;
	}
	
	/** This method is the getter of the time when the user inputs this weight.
	 * 
	 * @return the time when the user inputs this weight
	 */
	public Timestamp getTime() {
		return time;
	}
	
	/** This method is the setter of the user's ID.
	 * 
	 * @param id the ID of the user
	 */
	public void setUserID(String id) {
		userID = id;
	}
	
	/** This method is the setter of the user's weight.
	 * 
	 * @param w the weight of the user
	 */
	public void setWeight(double w) {
		weight = w;
	}
	
	/** This method is the setter of the time when the user inputs this weight.
	 * 
	 */
	public void setTime() {
		time = new Timestamp(System.currentTimeMillis());
	}
	
}