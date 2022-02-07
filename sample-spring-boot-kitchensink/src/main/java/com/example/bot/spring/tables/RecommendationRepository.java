package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Profile;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long>{
	
	/** This is the repository for the recommendation table.
	 */
	
	/** This method finds the recommendation by ID.
	 * 
	 * @param id the ID of the recommendation
	 * @return the corresponding recommendation
	 */
	public Recommendation findById(long id);
	
	/** This method finds the recommendation by its unique 6-digit code.
	 * 
	 * @param uniqueCode the unique 6-digit code
	 * @return the corresponding recommendation
	 */
	public Recommendation findByUniqueCode(long uniqueCode);
}