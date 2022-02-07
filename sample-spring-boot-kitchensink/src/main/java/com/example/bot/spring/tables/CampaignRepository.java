package com.example.bot.spring.tables;

import org.springframework.data.repository.CrudRepository;
import com.example.bot.spring.tables.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
	
	/** This is the repository for the campaign table.
	 * 
	 */

}