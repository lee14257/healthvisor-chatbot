package com.example.bot.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu {
	
	/** This model handles the JSON format of the menu.
	 * 
	 */
	
	/**
	 * The name of the choice in the menu.
	 */
	private String name;
	
	/** 
	 * The price of the choice in the menu.
	 */
	private int price;
	
	/**
	 * The ingredients of the choice in the menu.
	 */
	private String[] ingredients;
	
	/** The constructor of the menu.
	 * 
	 */
	public Menu() {
		
	}
	
	/** The getter of the name of the choice in the menu.
	 * 
	 * @return the name of the choice in the menu
	 */
	public String getName() {
		return name;
	}
	
	/** The setter of the name of the choice in the menu.
	 * 
	 * @param name the name of the choice in the menu
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** The getter of the price of the choice in the menu.
	 * 
	 * @return the price of the choice in the menu
	 */
	public int getPrice() {
		return price;
	}
	
	/** The setter of the price of the choice in the menu.
	 * 
	 * @param price the price of the choice in the menu
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/** The getter of the ingredients of the choice in the menu.
	 * 
	 * @return the ingredients of the choice in the menu
	 */
	public String[] getIngredients() {
		return ingredients;
	}
	
	/** The setter of the ingredients of the choice in the menu.
	 * 
	 * @param ingredients the ingredients of the choice in the menu
	 */
	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
}
