package com.example.bot.spring.tables;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Basic;

@Entity
public class Campaign {
	
	/** This is the table model for the campaign in the database.
	 * 
	 */
	
	/**
	 * This is the unique ID of the campaign.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	/** This is the timestamp of the campaign.
	 * 
	 */
	private Timestamp time;
	
	/** This is the image of the coupon.
	 * 
	 */
	@Lob
	private byte [] couponImage;
	
	/**
	 * This is the number of people in this campaign at this moment.
	 */
	private int count;
	
	/** This method is the getter of the timestamp.
	 * 
	 * @return the timestamp
	 */
	public Timestamp getTime() {
		return time;
	}
	
	/** This method is the getter of the coupon image.
	 * 
	 * @return the image of the coupon
	 */
	public byte [] getCouponImage () {
		return couponImage;
	}
	
	/** This method is the getter of the number of people in this campaign at this moment.
	 * 
	 * @return the number of people in this campaign at this moment
	 */
	public int getCount() {
		return count;
	}
	
	/** This method is the setter of the timestamp.
	 * 
	 */
	public void setTime() {
		time = new Timestamp (System.currentTimeMillis());
	}
	
	/** This method is the setter of the coupon image.
	 * 
	 * @param couponImg the byte array of the coupon image
	 */
	public void setCouponImage(byte [] couponImg) {
		couponImage = couponImg;
	}
	
	/** This method increments the number of people in this campaign.
	 * 
	 */
	public void incrementCount() {
		count +=2;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}