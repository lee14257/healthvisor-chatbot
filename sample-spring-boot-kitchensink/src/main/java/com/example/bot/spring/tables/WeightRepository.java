package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Weight;

public interface WeightRepository extends CrudRepository<Weight, Long>{
	
	/** This is the repository for the weight table.
	 */
	
}