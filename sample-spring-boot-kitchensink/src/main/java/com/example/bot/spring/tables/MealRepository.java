package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Meal;

public interface MealRepository extends CrudRepository<Meal, Long> {
	
	/** This is the repository for the meal table.
	 */
	
}