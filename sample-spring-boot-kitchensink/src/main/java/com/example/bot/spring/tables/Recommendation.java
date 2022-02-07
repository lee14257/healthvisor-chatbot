package com.example.bot.spring.tables;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.*;

import java.sql.Timestamp;
import java.util.Date;


@Entity

@SequenceGenerator(name="seq", initialValue=1, allocationSize=1)
public class Recommendation {
	
	/** This is the table model for recommendation in the database.
	 * 
	 */
	
	/** This is the unique ID of the recommendation.
	 * 
	 */
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id long id;
	
	/** This is the ID of the user of the recommendation.
	 * 
	 */
	private String userID;
	
	/** This shows whether or not the recommendation has been claimed.
	 * 
	 */
	private boolean claimed;
	
	/** This is the unique 6-digit code of the recommendation.
	 * 
	 */
	private long uniqueCode;
	
	/** This method is the getter of the ID of the recommendation.
	 * 
	 * @return the ID of the recommendation
	 */
	public long getID () {
		return id;
	}
	
	/** This method is the getter of the user's ID.
	 * 
	 * @return the ID of the user
	 */
	public String getUserID () {
		return userID;
	}
	
	/** This method is the getter of whether or not the recommendation has been claimed.
	 * 
	 * @return whether or not the recommendation has been claimed
	 */
	public boolean getClaimed() {
		return claimed;
	}
	
	/** This method is the getter of the unique 6-digit code.
	 * 
	 * @return the unique 6-digit code
	 */
	public long getUniqueCode() {
		return uniqueCode;
	}
	
	/** This method is the setter of the user's ID.
	 * 
	 * @param id the ID of the user
	 */
	public void setUserID(String id) {
		userID = id;
	}
	
	/** This method is the setter of whether or not the recommendation has been claimed.
	 * 
	 * @param claim whether or not the recommendation has been claimed
	 */
	public void setClaimed(boolean claim) {
		claimed = claim;
	}
	
	/** This method is the setter of the unique 6-digit code.
	 * 
	 * @param unique the unique 6-digit code
	 */
	public void setUniqueCode(long unique) {
		uniqueCode = unique;
	}
}