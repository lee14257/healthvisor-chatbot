package com.example.bot.spring.tables;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meal{
	
	/**This is the table model for the meal in the database.
	 * 
	 */
	
	/** This is the unique ID of the meal.
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	/** This is the ID of the user who consumed this meal.
	 * 
	 */
	private String userID;
	
	/** This is the name of the food that the user consumed.
	 * 
	 */
	private String food;
	
	/** This is the timestamp when the user input this meal.
	 * 
	 */
	private Timestamp time;
	
	/** This method is the getter of the user's ID who consumed this meal.
	 * 
	 * @return the ID of the user
	 */
	public String getUserID() {
		return userID;
	}
	
	/** This method is the getter of the food that the user consumed.
	 * 
	 * @return the food that the user consumed
	 */
	public String getFood() {
		return food;
	}
	
	/** This method is the getter of the timestamp.
	 * 
	 * @return the timestamp
	 */
	public Timestamp getTime() {
		return time;
	}
	
	/** This is the setter of the user's ID who consumed this meal.
	 * 
	 * @param id the ID of the user
	 */
	public void setUserID(String id) {
		userID = id;
	}
	
	/** This is the setter of the food that the user has consumed.
	 * 
	 * @param fd the name of the food that the user has consumed
	 */
	public void setFood(String fd) {
		food = fd;
	}
	
	/** This is the setter of the timestamp when the user inputs this meal.
	 * 
	 */
	public void setTime() {
		time = new Timestamp(System.currentTimeMillis());
	}
}