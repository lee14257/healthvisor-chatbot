package com.example.bot.spring;

import java.util.ArrayList;
import java.util.List;

import com.example.bot.spring.KitchenSinkController.Categories;
import com.example.bot.spring.KitchenSinkController.Menu;
import com.example.bot.spring.KitchenSinkController.Profile;

public class ListSingleton {
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
	Categories categories=null;
	Profile profile=null;
	Menu menu=null;
	
	private ListSingleton()
	{
		userList = new ArrayList<String>();
		catList = new ArrayList<Categories>();
		profList = new ArrayList<Profile>();
		menuList = new ArrayList<Menu>();
	}

	public static ListSingleton getInstance()
	{    
		return instance;
	}

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
	
	public Categories getCategories() {
		return categories;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public Menu getMenu() {
		return menu;
	}
	public void setValues(int index, Categories cat, Profile prof, Menu men) {
		catList.set(index, cat);
		profList.set(index, prof);
		menuList.set(index, men);
	}

}
