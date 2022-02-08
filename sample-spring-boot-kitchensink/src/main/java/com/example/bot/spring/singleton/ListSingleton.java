package com.example.bot.spring;

import java.util.ArrayList;
import java.util.List;

import com.example.bot.spring.KitchenSinkController.Categories;
import com.example.bot.spring.KitchenSinkController.Menu;
import com.example.bot.spring.KitchenSinkController.Profile;

public class ListSingleton {
	/** This creates an instance of the List Singleton at the time of class loading
	 * 
	 */
	private static ListSingleton instance = new ListSingleton();
	
	/** This is the list of the users currently using the chat bot.
	 * 
	 */
	private static List<String> userList;
	
	/** This is the list of the category variable that each user is on.
	 * 
	 */
	private static List<Categories> catList;
	
	/** This is the list of the profile variable that each user is on.
	 * 
	 */
	private static List<Profile> profList;
	
	/** This is the list of the menu variable that each user is on.
	 * 
	 */
	private static List<Menu> menuList;
	
	/** This is to store categories for the user that is using the Singleton object.
	 * 
	 */
	Categories categories=null;
	/** This is to store profile for the user that is using the Singleton object.
	 * 
	 */
	Profile profile=null;
	
	/** This is to store menu for the user that is using the Singleton object.
	 * 
	 */
	Menu menu=null;
	
	/** This is the private constructor of the ListSingleton Class.
	 * 
	 */
	private ListSingleton()
	{
		userList = new ArrayList<String>();
		catList = new ArrayList<Categories>();
		profList = new ArrayList<Profile>();
		menuList = new ArrayList<Menu>();
	}
	
	/** This is a public method to get the instance of the singleton class from anywhere
	 * 
	 * @return The single instance
	 */
	public static ListSingleton getInstance()
	{    
		return instance;
	}

	/** This is the method that needs to be used first if the instance is accessed
	 * 
	 * @param userID This is the the userID of the user that just typed something
	 * @return this returns the index of the user in each index
	 */
	public int initiate(String userID)
	{
		int index = -1;
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).equals(userID)) {
				index = i;
				break;
			}
		}
		
	
		if(index == -1) {
			categories = null;
			profile = null;
			menu = null;
			index = userList.size();
			userList.add(userID);
			catList.add(categories);
			profList.add(profile);
			menuList.add(menu);
		}
		else {
			categories = catList.get(index);
			profile = profList.get(index);
			menu = menuList.get(index);
		}
		return index;
	}
	/** This is the getter of the categories
	 * 
	 * @return categories of the singleton object
	 */
	public Categories getCategories() {
		return categories;
	}
	
	/** This is the getter of the profile
	 * 
	 * @return profile of the singleton object
	 */
	public Profile getProfile() {
		return profile;
	}
	
	/** This is the getter of the menu
	 * 
	 * @return menu of the singleton object
	 */
	public Menu getMenu() {
		return menu;
	}
	
	/** This is the method for setting values of index, categories, profile, and menu
	 * 
	 */
	public void setValues(int index, Categories cat, Profile prof, Menu men) {
		catList.set(index, cat);
		profList.set(index, prof);
		menuList.set(index, men);
	}

}
