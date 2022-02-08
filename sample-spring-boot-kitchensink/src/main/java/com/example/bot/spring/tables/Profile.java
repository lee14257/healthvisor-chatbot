package com.example.bot.spring.tables;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity

public class Profile {
	
	/**This is the table model for the profile of the user in the database.
	 * 
	 */
	
	/** This is the unique ID of the profile of the user.
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	/** This is the ID of the user.
	 * 
	 */
	String userID;
	
	/** This is the gender of the user.
	 * 
	 */
	String gender;
	
	/** This is the age of the user.
	 * 
	 */
	Integer age;
	
	/** This is the height of the user.
	 * 
	 */
	Double height;
	
	/** This is the user's interests in food.
	 * 
	 */
	private String[] interests;
	
	/** This is the time when the user register for this chat bot.
	 * 
	 */
	private Timestamp registeredTime;
	
	/** This shows whether or not the user has claimed the new user coupon.
	 * 
	 */
	private boolean claimedNewUserCoupon;
	
	/**
	 * This checks whether or not the user is an administrator.
	 */
	private boolean admin;
	
	/** This method is the getter for the profile's unique ID.
	 * 
	 * @return the ID of the profile
	 */
	public long getID() {
		return id;
	}
	
	/** This method is the getter for the user's ID.
	 * 
	 * @return the ID of the user
	 */
	public String getUserID () {
		return userID;
	}
	
	/** This method is the getter of the user's gender.
	 * 
	 * @return the gender of the user
	 */
	public String getGender() {
		return gender;
	}
	
	/** This method is the getter of the user's age.
	 * 
	 * @return the age of the user
	 */
	public int getAge() {
		if(age == null || age == 0) {
			return 44;
		}
		return age;
	}
	
	/** This method is the getter of the user's height.
	 * 
	 * @return the height of the user
	 */
	public double getHeight() {
		if(height == null || height == 0) {
			return 177.0;
		}
		return height;
	}
	
	/** This method is the getter of the user's interests.
	 * 
	 * @return the interests of the user
	 */
	public String[] getInterests () {
		return interests;
	}
	
	/** This method is the getter of the user's registration time.
	 * 
	 * @return the timestamp when the user registers
	 */
	public Timestamp getRegisteredTime() {
		return registeredTime;
	}
	
	/** This method is the getter of whether or not the user has claimed the new user coupon.
	 * 
	 * @return whether or not the user has claimed the new user coupon
	 */
	public boolean getClaimedNewUserCoupon() {
		return claimedNewUserCoupon;
	}
	
	/** This method is the getter of whether or not the user is an administrator.
	 * 
	 * @return whether or not the user is an administrator
	 */
	public boolean getAdmin() {
		return admin;
	}
	
	/** This method is the setter for the user's ID.
	 * 
	 * @param id the ID of the user
	 */
	public void setUserID(String id) {
		userID = id;
	}
	
	/** This method is the setter for the user's gender.
	 * 
	 * @param gender the gender of the user
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/** This method is the setter for the user's age.
	 * 
	 * @param age the age of the user
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/** This method is the setter for the user's height.
	 * 
	 * @param height the height of the user
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/** This method is the setter for the user's interests.
	 * 
	 * @param interestArray the interests of the user
	 */
	public void setInterest (String[] interestArray) {
		interests = interestArray;
	}
	
	/** This method is the setter for when the user registers.
	 * 
	 */
	public void setTime() {
		registeredTime = new Timestamp(System.currentTimeMillis());
	}
	
	/** This method is the setter for whether or not the user has claimed the new user coupon.
	 * 
	 * @param claimed whether or not the user has claimed the new user coupon
	 */
	public void setClaimedNewUserCoupon(boolean claimed) {
		claimedNewUserCoupon = claimed;
	}
	
	/** This method is the setter for whether or not the user is an administrator.
	 * 
	 * @param admin whether or not the user is an administrator
	 */
	public void setAdmin(boolean admin) {
		this.admin=admin;
	}
	
	
	
}
