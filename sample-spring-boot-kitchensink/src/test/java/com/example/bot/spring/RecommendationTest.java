package com.example.bot.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.google.common.io.ByteStreams;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.controllers.InputToFood;
import com.example.bot.spring.controllers.MenuController;
import com.example.bot.spring.controllers.User;
import com.example.bot.spring.tables.Profile;
import com.example.bot.spring.tables.ProfileRepository;
import com.example.bot.spring.tables.FoodRepository;
import com.example.bot.spring.tables.Food;
import com.example.bot.spring.tables.RecommendationRepository;
import com.example.bot.spring.tables.Recommendation;
import com.example.bot.spring.tables.CampaignRepository;
import com.example.bot.spring.tables.Campaign;


import com.example.bot.spring.KitchenSinkController.DownloadedContent;
import com.example.bot.spring.RepoFactory4Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.*;
import org.springframework.test.context.transaction.*;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.*;
import org.dbunit.*;
import org.h2.*;
import org.h2.tools.*;
import org.junit.*;
import junit.framework.*;
import org.dbunit.operation.*;
import java.nio.charset.StandardCharsets;
import org.springframework.transaction.annotation.*;

@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@Transactional
@SpringBootTest(classes={ RepoFactory4Test.class, 
		RecommendationTest.class, 
		MenuController.class, 
		User.class })
public class RecommendationTest {

	@Autowired
	private User user;
	
	@Autowired
	private RecommendationRepository recommendationRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	
	@Autowired
	private ProfileRepository profileRepository;
	
	private String userID1 = "1234567890123456789012345678901234";
	private String userID2 = "1234567890123456789012345678901235";
	private String userID3 = "1234567890123456789012345678901230";	
	private long uniqueCode1 = 999999;
	private long uniqueCode2 = 999997;

	@Before
	public void before () {
		Profile pf1 = new Profile();
		pf1.setUserID(userID1);
		pf1.setClaimedNewUserCoupon(false);
		pf1.setAdmin(false);
		pf1.setTime();
		profileRepository.save(pf1);
		
		Profile pf2 = new Profile();
		pf2.setUserID(userID2);
		pf2.setClaimedNewUserCoupon(false);
		pf2.setAdmin(true);
		profileRepository.save(pf2);
		
		Profile pf3 = new Profile();
		pf3.setUserID(userID3);
		pf3.setClaimedNewUserCoupon(true);
		pf3.setAdmin(false);
		profileRepository.save(pf3);
		
		
		Recommendation rec = new Recommendation();
		rec.setUserID(userID2);
		rec.setClaimed(false);
		rec.setUniqueCode(uniqueCode1);
		recommendationRepository.save(rec);
		
		Recommendation rec2 = new Recommendation();
		rec2.setUserID(userID1);
		rec2.setClaimed(true);
		rec2.setUniqueCode(uniqueCode2);
		recommendationRepository.save(rec2);
		
	}
	
	
	@Test
	public void testAcceptRecommendation() throws Exception {
		
		String nullStr = null;
		String emptyStr = "";
		String notSixDigits = "1234";
		String notSixDigitNumber = "ABCDEF";
		
		String validButNullCode = "999998";
		String validButClaimedCode;
		String validButOwnCode;
		
		String sixDigitError = "Error: That is not a 6 digit number";
		String noSuchError = "Error: There is no such code";
		String alreadyClaimedError= "Error: has already been claimed";
		String madeError = "Error: You made this recommendation";
				
		String invalidInput = user.acceptRecommendation(nullStr,userID2);
		assertEquals(invalidInput,sixDigitError);
		
		invalidInput = user.acceptRecommendation(emptyStr,userID2);
		assertEquals(invalidInput,sixDigitError);

		invalidInput = user.acceptRecommendation(notSixDigits,userID2);
		assertEquals(invalidInput,sixDigitError);

		invalidInput = user.acceptRecommendation(notSixDigitNumber,userID2);
		assertEquals(invalidInput,sixDigitError);	
		
		invalidInput = user.acceptRecommendation(validButNullCode,userID2);
		assertEquals(invalidInput,noSuchError);	
		
		invalidInput = user.acceptRecommendation(Long.toString(uniqueCode2),userID2);
		assertEquals(invalidInput,alreadyClaimedError);	
		
		invalidInput = user.acceptRecommendation(Long.toString(uniqueCode1),userID2);
		assertEquals(invalidInput,madeError);	
		
		String validInput = user.acceptRecommendation(Long.toString(uniqueCode1),userID1);
		assertEquals(validInput,userID2);
	}
	
	@Test
	public void testIsAdmin() throws Exception {
		boolean validInput = user.isAdmin(userID2);
		boolean invalidInput = user.isAdmin(userID1);

		assertEquals(validInput,true);
		assertEquals(invalidInput,false);
		invalidInput = user.isAdmin("1234567890");
		assertEquals(invalidInput,false);

	}
	
	@Test
	public void testMakeRecommendation() throws Exception {
		String invalidInput = user.makeRecommendation(userID1);
		assertEquals(invalidInput,null);
		
		Campaign cp = new Campaign();
		campaignRepository.save(cp);
		String validInput = user.makeRecommendation(userID1);
		assertNotEquals(validInput,null);
	}
	
	@Test
	public void testCheckValidityOfUser() throws Exception {
		String claimed = "claimed";
		String taken = "taken";
		String before = "before";
		String valid = "valid";
		String none = "none";
		
		String invalidInput = user.checkValidityOfUser(userID2);
		assertEquals(invalidInput,none);
		
		Campaign cp = new Campaign();
		cp.setCount(5000);
		campaignRepository.save(cp);
		
		invalidInput = user.checkValidityOfUser(userID1);
		assertEquals(invalidInput,taken);
		
		cp.setCount(4000);
		cp.setTime();
		campaignRepository.save(cp);
		
		
		invalidInput = user.checkValidityOfUser(userID1);
		assertEquals(invalidInput,before);
		
		String userID4 = "0987654321098765432109876543214321";
		Profile pf1 = new Profile();
		pf1.setUserID(userID4);
		pf1.setClaimedNewUserCoupon(false);
		pf1.setAdmin(false);
		pf1.setTime();
		profileRepository.save(pf1);
		
		invalidInput = user.checkValidityOfUser(userID3);
		assertEquals(invalidInput,claimed);
		
		
		String validInput = user.checkValidityOfUser(userID4);
		assertEquals(validInput,valid);
	}
	
	@Test
	public void testCoupon() throws Exception {
		
		user.uploadCouponCampaign(getClass().getResourceAsStream("/static/test/menuProfile.jpg"));
		byte[] validInput = user.getCoupon();
		
		assertNotEquals(validInput,null);
		
		user.uploadCouponCampaign(getClass().getResourceAsStream("/static/test/menuProfile.jpg"));
		
		validInput = user.getCoupon();
		
		assertNotEquals(validInput,null);
		
		
	}

	
}
