package com.example.bot.spring.tables;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="food")
public class Food {
	
	/** This is the table model for the food in the database.
	 * 
	 */
	
	/** This is the unique ID of the food.
	 * 
	 */
	@Id
	@Column(name="foodID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	long foodID;
	
	/** This is the name of the food.
	 * 
	 */
	@Column(name="name")
	String name;
	
	/** This is the name of the category of the food.
	 * 
	 */
	@Column(name="category")
	String category;
	
	/** This is the amount of calories in this food.
	 * 
	 */
	@Column(name="calories")
	double calories;
	
	/** This is the amount of sodium in this food.
	 * 
	 */
	@Column(name="sodium")
	double sodium;
	
	/** This is the amount of saturated fat in this food.
	 * 
	 */
	@Column(name="saturatedFat")
	double saturatedFat;
	
	/** This is the amount of protein in this food.
	 * 
	 */
	@Column(name="protein")
	double protein;
	
	/** This is the amount of carbohydrate in this food.
	 * 
	 */
	@Column(name="carbohydrate")
	double carbohydrate;
	
	/** This method is the getter of the food's ID.
	 * 
	 * @return the ID of the food
	 */
	public long getFoodID() {
		return foodID;
	}
	
	/** This method is the getter of the name of the food.
	 * 
	 * @return the name of the food
	 */
	public String getName() {
		return name;
	}
	
	/** This method is the setter for the name of the food.
	 * 
	 * @param name the name of the food
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** This method is the getter of the name of the category of the food.
	 * 
	 * @return the name of the category of the food
	 */
	public String getCategory() {
		return category;
	}
	
	/** This method is the setter of the name of the category of the food.
	 * 
	 * @param cat the name of the category of the food
	 */
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	/** This method is the getter of the amount of calories in this food.
	 * 
	 * @return the amount of calories in this food
	 */
	public double getCalories() {
		return calories;
	}
	
	/** This method is the setter of the amount of calories in this food.
	 * 
	 * @param cal the amount of calories in this food
	 */
	public void setCalories(double cal) {
		this.calories = cal;
	}
	
	/** This method is the getter of the amount of sodium in this food.
	 * 
	 * @return the amount of sodium in this food
	 */
	public double getSodium() {
		return sodium;
	}
	
	/** This method is the setter of the amount of sodium in this food.
	 * 
	 * @param sod the amount of sodium in this food
	 */
	public void setSodium(double sod) {
		this.sodium = sod;
	}
	
	/** This method is the getter of the amount of saturated fat in this food.
	 * 
	 * @return the amount of saturated fat in this food
	 */
	public double getSaturatedFat() {
		return saturatedFat;
	}
	
	/** This method is the setter of the amount of saturated fat in this food.
	 * 
	 * @param fat the amount of saturated fat in this food
	 */
	public void setSaturatedFat(double fat) {
		this.saturatedFat = fat;
	}
	
	/** This method is the getter of the amount of protein in this food.
	 * 
	 * @return the amount of protein in this food
	 */
	public double getProtein() {
		return protein;
	}
	
	/** This method is the setter of the amount of protein in this food.
	 * 
	 * @param protein the amount of protein in this food
	 */
	public void setProtein(double protein) {
		this.protein = protein;
	}
	
	/** This method is the getter of the amount of carbohydrate in this food.
	 * 
	 * @return the amount of carbohydrate in this food
	 */
	public double getCarbohydrate() {
		return carbohydrate;
	}
	
	/** This method is the setter of the amount of carbohydrate in this food.
	 * 
	 * @param carb the amount of carbohydrate in this food
	 */
	public void setCarbohydrate(double carb) {
		this.carbohydrate = carb;
	}
	
	/** This method provides the details of the food in a String format.
	 * 
	 * @return the details of the food in a String format 
	 */
	public String getDetails() {
		String details =
				"Calories: " + getCalories() + "\n" +
				"Sodium: " + getSodium() + "\n" +
				"Saturated Fat: " + getSaturatedFat() + "\n" +
				"Protein: " + getProtein() + "\n" +
				"Carbohydrate: " + getCarbohydrate();
		
		return details;
	}

}