package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long>{
	
	/** This is the repository for the profile table.
	 */
	
	/** This method finds the profile of the user by his/her ID.
	 * 
	 * @param userID the ID of the user
	 * @return the profile of the user
	 */
	public Profile findByUserID(String userID);
}