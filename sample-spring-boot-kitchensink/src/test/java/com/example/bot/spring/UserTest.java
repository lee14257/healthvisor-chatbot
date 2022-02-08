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

import com.example.bot.spring.controllers.*;
import com.example.bot.spring.tables.*;
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
		UserTest.class, 
		MenuController.class,
		User.class })
public class UserTest {
	@Autowired
	private User user;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private WeightRepository weightRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Before
	public void executedBeforeEach() {	
		Food food = new Food();
        food.setName("rice");
        food.setCategory("Meals, Entrees, and Sidedishes");
        food.setCalories(358);
        food.setSodium(1238);
        food.setSaturatedFat(1.32);
        food.setProtein(10.64);
        food.setCarbohydrate(75.8);
        foodRepository.save(food);
        
        food.setName("candy");
        food.setCategory("Sweets");
        food.setCalories(384);
        food.setSodium(28);
        food.setSaturatedFat(7.17);
        food.setProtein(2.19);
        food.setCarbohydrate(80.99);
        foodRepository.save(food);
        
		user.addUser("1");
		user.inputGender("1","Male");
		user.inputAge("1",20);
		user.inputHeight("1",170.0);
		user.addUser("2");
		user.inputGender("2","Female");
		user.inputAge("2",22);
		user.inputHeight("2",140.0);
		user.inputWeight("2",55.0);
		user.inputMeal("2","Rice");
		user.addUser("3");
	}
	
	@Test
	public void testInputInterest() {
		String notFound = "Those interests are not valid.";
		String already = "I already recorded that";
		
		String invalidInput = user.inputInterest("1","Sweets, Cereal Grains and Pasta");
		assertEquals(invalidInput,notFound);

		String validInput = user.inputInterest("1","Sweets");
		assertEquals(validInput,"");
		
		invalidInput = user.inputInterest("1","Sweets");
		assertEquals(invalidInput,already);
		
	}
	
	@Test
	public void testOutputInterest() {
		String notFound = "You did not tell me your food interests yet.";
		
		String invalidInput = user.outputInterest("2");
		assertEquals(invalidInput,notFound);
		user.inputInterest("2","Sweets");

		String validInput = user.outputInterest("2");
		boolean valid = validInput.contains("Sweets");
		assertEquals(valid,true);
	}
	
	@Test
	public void testOutputWeight() {
		String notFound = "You did not log any weight";
		
		String invalidInput = user.outputWeight("1");
		assertEquals(invalidInput,notFound);
		
		user.inputWeight("1",85.0);
		String validInput = user.outputWeight("1");
		assertNotEquals(validInput,notFound);
		
	}
	
	@Test
	public void testOutputMeal() {
		String notFound = "You did not log any meal";
		
		
		String invalidInput = user.outputMeal("1");
		assertEquals(invalidInput,notFound);
		
		user.inputMeal("1","Rice");
		String validInput = user.outputMeal("1");
		assertNotEquals(validInput,notFound);
	}
	
	@Test
	public void testOutputGeneral() {
		assertEquals(user.outputGeneral("1"),"Gender: Male\nAge: 20\nHeight: 170.0cm\n\n");
		assertEquals(user.outputGeneral("2"),"Gender: Female\nAge: 22\nHeight: 140.0cm\n\n");
		assertEquals(user.outputGeneral("3"),"Gender: Male\nAge: 44\nHeight: 177.0cm\n\n");
	}
	
	@Test
	public void testShowDailyProgress() {
		String result = user.showDailyProgress("1");
		assertEquals(result,"Basal Metabolic Rate (BMR): 1857.50\n" + 
				"Body Mass Index (BMI): 30.80\n" + 
				"Body Fat Percentage (BFP): 36.16\n" + 
				"Current Status: Obese\n" + 
				"\n" + 
				"Remaining Nutrients for today: \n" + 
				"Calories: 1857.50\n" + 
				"Protein: 92.88\n" + 
				"Carbohydrate: 255.41\n" + 
				"Fat: 51.60\n");
	}
}
