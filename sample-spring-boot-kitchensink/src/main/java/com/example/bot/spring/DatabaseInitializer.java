package com.example.bot.spring;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.bot.spring.tables.Food;
import com.example.bot.spring.tables.FoodRepository;

import java.io.BufferedReader;
import java.io.FileReader;

public class DatabaseInitializer {
	
	/** This class initializes the food database.
	 * 
	 */
	
	@Autowired
	private static FoodRepository foodRepository;
	
	/** This method initializes the food database.
	 * 
	 */
    public static void initializeDatabase() {
        try {
            String fileName = "FOOD_DATA.txt";
            String line = null;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);

                String[] foodData = line.split("^");
                String foodName = foodData[0];
                String category = foodData[1];
                double calories = Double.parseDouble(foodData[2]);
                double sodium = Double.parseDouble(foodData[3]);
                double fat = Double.parseDouble(foodData[4]);
                double protein = Double.parseDouble(foodData[5]);
                double carbohydrate = Double.parseDouble(foodData[6]);
                Food food = new Food();
                food.setName(foodName);
                food.setCategory(category);
                food.setCalories(calories);
                food.setSodium(sodium);
                food.setSaturatedFat(fat);
                food.setProtein(protein);
                food.setCarbohydrate(carbohydrate);
                foodRepository.save(food);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
