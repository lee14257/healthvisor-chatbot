package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Food;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {
	
	/** This is the repository for the food table.
	 */
	
	/** This method returns the food with the corresponding ID.
	 * 
	 * @param foodID the ID of the food
	 * @return the food with the corresponding ID
	 */
	public Food findByFoodID(Long foodID);
}