package com.example.bot.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.containsString;

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
import com.example.bot.spring.tables.FoodRepository;
import com.example.bot.spring.tables.Food;
import com.example.bot.spring.tables.WeightRepository;
import com.example.bot.spring.tables.Weight;
import com.example.bot.spring.tables.MealRepository;
import com.example.bot.spring.tables.Meal;
import com.example.bot.spring.tables.ProfileRepository;
import com.example.bot.spring.tables.Profile;
import com.example.bot.spring.DatabaseInitializer;
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
		InputToFoodTest.class, 
		InputToFood.class, 
		MenuController.class,
		DatabaseInitializer.class,
		User.class })
public class InputToFoodTest {
	@Autowired
	private InputToFood inputToFood;
	
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
	
	@Autowired 
	private DatabaseInitializer init;
	
	@Before
	public void executedBeforeEach() {
		init.initializeDatabase();
		
		user.addUser("1");
		user.inputGender("1","Male");
		user.inputAge("1",20);
		user.inputHeight("1",170.0);
		user.inputWeight("1",85.0);
		user.inputInterest("1","Sweets, Cereal Grains and Pasta");
		user.inputMeal("1","Rice");
		user.addUser("2");
		user.inputGender("2","Female");
		user.inputAge("2",22);
		user.inputHeight("2",140.0);
		user.inputWeight("2",55.0);
		user.inputInterest("2","Sweets");
		user.inputMeal("2","Rice");
		user.addUser("3");
	}
	
	@Test
	public void testreadFromText() {
		assertEquals(inputToFood.readFromText("1",""),"No menu is entered.");
		assertNotEquals(inputToFood.readFromText("1","Candy ice-cream\nRice"),
				"I know that you have eaten rice in the past few days. But I still recommend you to choose Rice. This choice has the most suitable amount of calories, protein, carbohydrate.");
		assertNotEquals(inputToFood.readFromText("2","Syrup Candy ice-cream\nRice"),
				"I recommend you to choose Syrup Candy ice-cream because I know that you like foods that are sweets. This choice has the most suitable amount of calories, carbohydrate, fat.");
		assertNotEquals(inputToFood.readFromText("3","Candy ice-cream\nRice"),
				"I recommend you to choose Rice. This choice has the most suitable amount of calories, protein, carbohydrate, fat.");
	}
	
	@Test
	public void testReadFromJSON() throws Exception {
		String nullStr = null;
		String emptyStr = "";
		String invalidUrl = "htt:asdf1234.com";
		String jsonUrl = "https://randomjsonserver.herokuapp.com";
		
		String nullResult = inputToFood.readFromJSON(nullStr);
		assertEquals(nullResult, "Invalid input");
		
		String emptyResult = inputToFood.readFromJSON(emptyStr);
		assertEquals(emptyResult, "Failed to load URL.");
		
		String invalidResult = inputToFood.readFromJSON(invalidUrl);
		assertEquals(invalidResult, "Failed to load URL.");
		
		String result = inputToFood.readFromJSON(jsonUrl);
		assertEquals(result, "The Foods in each entree are as followed:\n" + 
				"1. Pork, Bean curd, Rice, \n" + 
				"2. Pork, Sweet and Sour Sauce, Pork, \n" + 
				"3. Chili, Chicken, Rice, ");
	}
	
	@Test
	public void testReadFromJPEG() throws Exception {
		DownloadedContent nullContent = null;
		DownloadedContent invalidFormatContent = new DownloadedContent(null, "");
		URI uri = getClass().getResource("/static/test/sample-menu2.jpg").toURI();
		File file = new File(uri);
		Path path = file.toPath();
		DownloadedContent jpeg = new DownloadedContent(path, uri.toString());
		
		String nullResult = inputToFood.readFromJPEG(nullContent);
		assertEquals(nullResult, "Invalid input");
		
		String invalidResult = inputToFood.readFromJPEG(invalidFormatContent);
		assertEquals(nullResult, "Invalid input");
		
		String result = inputToFood.readFromJPEG(jpeg);
		System.out.println(result);
		assertEquals(result, "Spicy Bean curd with Minced Pork served with Rice\n" + 
				"Sweet and Sour Pork served with Rice\n" + 
				"Chili Chicken on Rice\n" + 
				"Fried instance noodle with Luncheon Meat\n" + 
				"28 F");
	}

	@Test
	public void testGetFoodDetails() throws Exception {
		Food food = new Food();
        food.setName("pork");
        food.setCategory("meat");
        food.setCalories(300);
        food.setSodium(30);
        food.setSaturatedFat(10);
        food.setProtein(10);
        food.setCarbohydrate(10);
        foodRepository.save(food);
        
		String foodName = "pork";
		String result = inputToFood.getFoodDetails(foodName);
		System.out.println(result);
		assertEquals(result, "You have entered pork.\n" + 
				"Here are the details for pork\n" + 
				"Calories: 300.0\n" + 
				"Sodium: 30.0\n" + 
				"Saturated Fat: 10.0\n" + 
				"Protein: 10.0\n" + 
				"Carbohydrate: 10.0\n" + 
				"\n");
	}
}
