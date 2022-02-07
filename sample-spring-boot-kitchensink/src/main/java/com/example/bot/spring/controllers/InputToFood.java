package com.example.bot.spring.controllers;

import com.example.bot.spring.tables.*;
import com.example.bot.spring.controllers.MenuController;

import java.util.*;
import java.util.function.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.example.bot.spring.models.Menu;
import com.example.bot.spring.models.OCRResponse;
import com.example.bot.spring.models.Response;
import com.example.bot.spring.models.TextAnnotation;
import com.example.bot.spring.KitchenSinkController.DownloadedContent;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;

/** This controller takes in the menu inputs from the user, turn them into text, and then pass it to 
 *  MenuController to pick the food for the user. It also provides the details of the food that the 
 *  user has requested for.
 */

import lombok.extern.slf4j.Slf4j;

@Service

public class InputToFood {
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private MenuController menuController;
	
	/** This method reads the text menu passed in by the user and passes it to MenuController.
	 * 
	 * @param userId the ID of the user
	 * @param text the text menu passed in by the user
	 * @return the choice from the menu
	 */
    public String readFromText(String userId, String text) {
		menuController.setUserID(userId);
		menuController.setMenu(text);
    	return menuController.pickFood();
    }

    /**
     * This method is used to convert JSON HTTP response to menu format.  
     * @param url URL to the JSON source
     * @return String Formatted String of the menu
     */
    public String readFromJSON(String url) {
    	if (url == null) {
    		return "Invalid input";
    	}
    	
    	try {
    		RestTemplate restTemplate = new RestTemplate();
        	Menu[] menuList = restTemplate.getForObject(url, Menu[].class);
        	StringBuilder builder = new StringBuilder();
        	int counter = 1;
        	builder.append("The Foods in each entree are as followed:\n");
        	for (Menu menu : menuList) {
        		String[] ingredients = menu.getIngredients();
        		builder.append(counter);
        		builder.append(". ");
        		for (String ingredient : ingredients) {
        			builder.append(ingredient);
        			builder.append(", ");
        		}
        		counter++;
        		builder.append("\n");
        	}
        	builder.deleteCharAt(builder.length() - 1);
        	
        	return builder.toString();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return "Failed to load URL.";
    	}
    }

    /**
     * This method uses OCR feature of the Google Vision API to extract menu String
     * from the image uploaded by the user through Line
     * @param jpeg DownloadedContent instance of the image file uploaded by the user
     * @return String Menu String processed from the image using Google Vision API
     */
    public String readFromJPEG(DownloadedContent jpeg) {
    	if (jpeg == null || jpeg.getPath() == null || jpeg.getUri() == null) {
    		return "Invalid input";
    	}
    	
    	String menu = "";
    	RestTemplate restTemplate = new RestTemplate();
    	String apiKey = "AIzaSyCrPOUDlYLaAQLAXbFSiRgb16OSikBooP8";
    	String url = "https://vision.googleapis.com/v1/images:annotate?key=" + apiKey;
    	String json = buildJson(jpeg);
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	OCRResponse ocrResponse = restTemplate.postForObject(url, entity, OCRResponse.class);
    	Response response = ocrResponse.getResponses()[0];
    	TextAnnotation textAnnotation = response.getTextAnnotations()[0];
    	menu = textAnnotation.getDescription();
    	String[] menuList = menu.split("\\r?\\n");
    	StringBuilder builder = new StringBuilder();
    	for (String m : menuList) {
    		if (!isNumeric(m)) {
    			builder.append(m);
    			builder.append("\n");
    		}
    	}
    	
    	if (builder.length() > 1) {
    		builder.deleteCharAt(builder.length() - 1);
    	}
    	
    	return builder.toString();
    }
    
    /** This method builds the menu in JSON format from a picture of the menu
     * 
     * @param jpeg the picture of the menu
     * @return the JSON format of the menu
     */
    private String buildJson(DownloadedContent jpeg) {
    	try {
    		StringBuilder jsonBuilder = new StringBuilder();
        	File file = jpeg.getPath().toFile();
        	String imageCode = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        	jsonBuilder.append("{\"requests\":[");
        	jsonBuilder.append("{\"image\":{");
        	jsonBuilder.append("\"content\":\"");
        	jsonBuilder.append(imageCode);
        	jsonBuilder.append("\"},");
        	jsonBuilder.append("\"features\":[{");
        	jsonBuilder.append("\"type\":\"TEXT_DETECTION\"");
        	jsonBuilder.append("}]");
        	jsonBuilder.append("}");
        	jsonBuilder.append("]}");
        	return jsonBuilder.toString();
    	} catch (IOException e) {
    		e.printStackTrace();
    		return "";
    	}
    }
    
    /** This method returns the details of the food that the user requested.
     * 
     * @param food the name of the food the user requested
     * @return the details of the food
     */
    public String getFoodDetails(String food) {
    		if(food == null || food == "") {
    			return "You have not entered any food";
    		}
    		
    		String resultFood = "You have entered " + food + ".\n";
    		String[] splitFood = food.split("\\s+");
    		
	    	for (int i = 0; i < splitFood.length; i++) {	
	    		for (Food fd : foodRepository.findAll()) {
	    		    String fdName = fd.getName().toLowerCase();
	            	if (fdName.contains(",")) {
	            		fdName = fdName.substring(0,fdName.indexOf(","));
	            	}
	            	
	            	if (fdName.endsWith("s")) {
	            		fdName = fdName.substring(0, fdName.length()-1);
	            	}
	            	
	    		    if (splitFood[i].toLowerCase().contains(fdName)) { 
	    		    		resultFood += "Here are the details for " + fdName + "\n" + fd.getDetails() + "\n" + "\n";
	    		    		break;
	    		    }
	    		}
	    	}
    		
    		return resultFood;
    }
    
    /**
     * Helper method to check if the given String is numeric
     * @param str String to test
     * @return True if the String is numeric; false otherwise
     */
    private boolean isNumeric(String str) {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}

