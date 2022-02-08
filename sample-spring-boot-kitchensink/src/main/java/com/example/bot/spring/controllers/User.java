package com.example.bot.spring.controllers;
import java.util.*;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import com.example.bot.spring.tables.*;
import com.example.bot.spring.controllers.MenuController;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.function.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Service;

@Service
public class User {
	
	/** This controller has access to the models corresponding to the users' information
	 * and use them to process the requests from the users.
	 */
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private WeightRepository weightRepository;

	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private RecommendationRepository recommendationRepository;

	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private MenuController mc;
	
	/** This method creates a new user in the database.
	 * 
	 * @param id the ID of the new user
	 */
	public void addUser(String id) {
		Profile pf = profileRepository.findByUserID(id);
		if (pf == null) {
			pf = new Profile();
			pf.setUserID(id);
			pf.setTime();
			if (profileRepository.count() == 0) {
				pf.setAdmin(true);
			}
			else {
				pf.setAdmin(false);
			}
			profileRepository.save(pf);
		}
	}
	
	/** This saves the gender of the user in the database.
	 * 
	 * @param id the ID of the user
	 * @param gender the gender of the user
	 */
	public void inputGender(String id, String gender) {
		Profile pf = profileRepository.findByUserID(id);
		pf.setGender(gender);
		profileRepository.save(pf);
	}
	
	/** This saves the age of the user in the database.
	 * 
	 * @param id the ID of the user
	 * @param age the age of the user
	 */
	public void inputAge(String id, int age) {
		Profile pf = profileRepository.findByUserID(id);
		pf.setAge(age);
		profileRepository.save(pf);
	}
	
	/** This saves the height of the user in the database.
	 * 
	 * @param id the ID of the user
	 * @param height the height of the user
	 */
	public void inputHeight(String id, Double height) {
		Profile pf = profileRepository.findByUserID(id);
		pf.setHeight(height);
		profileRepository.save(pf);
	}
	
	/** This saves the weight of the user in the database.
	 * 
	 * @param id the ID of the user
	 * @param weight the weight of the user
	 */
	public void inputWeight(String id, Double weight) {		
		Weight wt = new Weight();
		wt.setUserID(id);
		wt.setTime();
		wt.setWeight(weight);
		weightRepository.save(wt);
	}
	
	/** This is the getter for the user's weight.
	 * 
	 * @param id the ID of the user
	 * @return the log of the user's weights
	 */
	public String outputWeight(String id) {		
		boolean weightFound = false;
		String outputStr = "";
		for(Weight wt : weightRepository.findAll()) {
			if(wt.getUserID().equals(id)) { 
	        		weightFound = true;
	        		
	        		Date date = new Date();
	        		date.setTime(wt.getTime().getTime());
	        		
	        		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
	        		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
	        		String formattedDate = simpleDateFormat.format(date);
	        		if (!outputStr.equals("")) {
	        			outputStr += "\n";
	        		}
	        		outputStr += formattedDate + ":" + "\n" + wt.getWeight() + "kg";
	        }
		}
		if (!weightFound) {
			outputStr += "You did not log any weight";
		}
		
		return outputStr;
	}
	
	/** This method resets the interests that have been chosen by the user.
	 * 
	 * @param id the ID of the user
	 * @return the response informing the user that his/her interests have been reset.
	 */
	public String resetInterest(String id) {	
		Profile pf = profileRepository.findByUserID(id);
		pf.setInterest(null);
		profileRepository.save(pf);
		return "Your interest records were deleted. Tell me your interests again.";
	}
	
	/** This saves the interests of the user in the database.
	 * 
	 * @param id the ID of the user
	 * @param interest the String containing the interests of the user
	 * @return responses according to the user's actions
	 */
	public String inputInterest(String id, String interest) {	
		int categoryFound = 0;
		String[] splitInterest = interest.split("/ ");

		//Check for validity/existence of interest
		for(int i=0; i<splitInterest.length; i++) {
			for(Food fd : foodRepository.findAll()) {
				if(fd.getCategory().equals(splitInterest[i])) {
					categoryFound++;
					break;
				}
			}
			continue;
		}
		
		if(categoryFound != splitInterest.length) {
			return "Those interests are not valid.";
		}
		
		Profile pf = profileRepository.findByUserID(id);	
		if(pf.getInterests() == null) {
			pf.setInterest(splitInterest);
		} else {
			ArrayList<String> temp = new ArrayList<String>(Arrays.asList(pf.getInterests()));
			for(int i=0; i<splitInterest.length; i++) {
				if(!temp.contains(splitInterest[i])) {
					temp.add(splitInterest[i]);
				} else {
					return "I already recorded that";
				}
			}
			String[] tempInterest = new String[temp.size()];
			temp.toArray(tempInterest);
			pf.setInterest(tempInterest);
		}
		profileRepository.save(pf);
		return "";
	}
	
	/** This method gets the general profile of the user, including his/her gender, age, and height.
	 * 
	 * @param id the ID of the user
	 * @return the user's general profile
	 */
	public String outputGeneral(String id) {
		Profile pf = profileRepository.findByUserID(id);
		String outputStr = "Gender: ";
		Integer age = pf.getAge();
		String gender = pf.getGender();
		Double height = pf.getHeight();
		if(pf.getGender()=="Female") {
			outputStr += "Female\n";
		}
		else {
			outputStr += "Male\n";
		}
		outputStr += "Age: ";
		outputStr += age.toString()+"\n";
		outputStr += "Height: ";
		outputStr += height.toString()+"cm\n\n";
		return outputStr;
	}
	
	/** This is the getter for the user's interests.
	 * 
	 * @param id the ID of the user
	 * @return the user's interests in a String
	 */
	public String outputInterest(String id) {
		String outputStr = "";
		Profile pf = profileRepository.findByUserID(id);
		if(pf.getInterests() != null) {
			outputStr += "Your interests in food are: \n";
			for(int i=0; i<pf.getInterests().length; i++) {
				outputStr += "-" + pf.getInterests()[i] + "\n";
			}
			return outputStr;
		}
		outputStr += "You did not tell me your food interests yet.";		
		return outputStr;
	}

	/** This saves the meal that the user consumed in the database.
	 * 
	 * @param id the ID of the user
	 * @param food the String of the meal that the user consumed
	 */
	public void inputMeal(String id, String food) {		
		Meal ml = new Meal();
		ml.setUserID(id);
		ml.setTime();
		ml.setFood(food);
		mealRepository.save(ml);	
	}

	/** This is the getter for the meals that the user has consumed.
	 * 
	 * @param id the ID of the user
	 * @return the log of the meals that the user has consumed
	 */
	public String outputMeal(String id) {		
		boolean mealFound = false;
		String outputStr = "";
		for(Meal ml : mealRepository.findAll()) {
			if(ml.getUserID().equals(id)) { 
	        		mealFound = true;
	        		
	        		Date date = new Date();
	        		date.setTime(ml.getTime().getTime());
	        		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	        		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
	        		String formattedDate = simpleDateFormat.format(date);
	        		if (!outputStr.equals("")) {
	        			outputStr += "\n";
	        		}
	        		outputStr += formattedDate + ":" + "\n" + ml.getFood();
	        }
		}
		if (!mealFound) {
			outputStr += "You did not log any meal";
		}
		
		return outputStr;
	}
	
	/** This creates a new recommendation and provides the user with a unique code.
	 * 
	 * @param id the ID of the user
	 * @return the unique 6-digit code for the user
	 */
	public String makeRecommendation(String id) {	
		if (campaignRepository.count() == 0) {
			return null;
		}
		Recommendation rd = new Recommendation();
		rd.setUserID(id);
		rd.setClaimed(false);
		recommendationRepository.save(rd);
		long code = rd.getID()%1000000;
		if (code<100000) {
			code += 100000;
		}
		rd.setUniqueCode(code);
		recommendationRepository.save(rd);
		return Long.toString(rd.getUniqueCode());
	}
	

	/** This allows the user the accept the recommendation.
	 * 
	 * @param uniqueCode the 6-digit unique code
	 * @param userID the ID of the user
	 * @return the corresponding responses base on the state of the user
	 */
	public String acceptRecommendation(String uniqueCode, String userID) {
		
		
		if (uniqueCode == null||uniqueCode.length() != 6 || !isInteger(uniqueCode)) {
			return "Error: That is not a 6 digit number";
		}
		
		Recommendation rd = recommendationRepository.findByUniqueCode(Long.parseLong(uniqueCode));
		if (rd!=null) {
			if (!rd.getClaimed()) {
				if (!rd.getUserID().equals(userID)) {
					rd.setClaimed(true);
					recommendationRepository.save(rd);
					
					Profile pf = profileRepository.findByUserID(userID);
					pf.setClaimedNewUserCoupon(true);
					profileRepository.save(pf);
					return rd.getUserID();
				}
				
				else {
					return "Error: You made this recommendation";
				}
			}
			else {
				return "Error: has already been claimed";
			}
		}
		else {
			return "Error: There is no such code";
		}
	}
	
	/** This method checks whether or not the String is an integer.
	 * 
	 * @param string a String
	 * @return whether or not the String is an integer
	 */
	public boolean isInteger (String string) {
		int size = string.length();
		
		for (int i = 0; i < size; i++) {
	        if (!Character.isDigit(string.charAt(i))) {
	            return false;
	        }
	    }

	    return size > 0;
	}
		
	/** This method allows the administrator to upload a coupon to start a campaign.
	 * 
	 * @param is the input stream of the image of the coupon
	 */	
	public void uploadCouponCampaign(InputStream is) {
		Campaign campaign = null;
		for(Campaign cp : campaignRepository.findAll()) {
			campaign = cp;
		}
		
		if (campaign == null) {
			campaign = new Campaign();
			campaign.setTime();
		}
		
		try {
			campaign.setCouponImage(readImage(is));
		} catch (IOException e) {
			
		}
		campaignRepository.save(campaign);	
	}
	
	/** This method is a getter for the coupon image.
	 * 
	 * @return the byte format of the coupon image
	 */
	public byte[] getCoupon() {	
		Campaign campaign;
		for(Campaign cp : campaignRepository.findAll()) {
			cp.incrementCount();
			campaignRepository.save(cp);
			return cp.getCouponImage();
		}
		return null;
	}
	
	/** This method checks the validitiy for the user to claim the coupon.
	 * 
	 * @param id the ID of the user
	 * @return the state of the user
	 */
	public String checkValidityOfUser (String id) {
		Profile pf = profileRepository.findByUserID(id);
		if (pf.getClaimedNewUserCoupon()) {
			
			return "claimed";
		}
		
		Campaign campaign=null;
		for(Campaign cp : campaignRepository.findAll()) {
			campaign = cp;
		}
		
		if (campaign != null) {
			if (campaign.getCount()>=5000) {
				//5000 coupons already taken
				return "taken";
			}
			if (campaign.getTime().getTime()>pf.getRegisteredTime().getTime()) {
				//user registered before campaign began
				return "before";
			}
			else {
				return "valid";
			}	
		}
		else {
			//no campaign
			return "none";
		}
		
		
	}
	
	/** This method checks whether or not the user is an administrator.
	 * 
	 * @param userID the ID of the user
	 * @return whether or not the user is an administrator
	 */
	public boolean isAdmin(String userID) {
		Profile pf = profileRepository.findByUserID(userID);
		if (pf !=null) {
			if (pf.getAdmin()) {
				return true;
			}
		}
		return false;
	}
	
	/** This method reads the image by converting it from an input stream to an array of bytes.
	 * 
	 * @param is the input stream of the image
	 * @return the byte array of the image
	 * @throws IOException
	 */
	public byte[] readImage(InputStream is) throws IOException
	{
	    byte[] buffer = new byte[8192];
	    int bytesRead;
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    while ((bytesRead = is.read(buffer)) != -1)
	    {
	        output.write(buffer, 0, bytesRead);
	    }
	    return output.toByteArray();

	}
	
	/** This method gets the latest row fo the user's weight in the database.
	 * 
	 * @param userID the ID of the user
	 * @return the latest weight of the user
	 */
	private Double getLastWeight(String userID) {
		Date closest = new Date(0);
		Weight lastWeight = null;
		for(Weight wt : weightRepository.findAll()) {
			if(wt.getUserID().equals(userID)) { 
	        		Date weightTime = new Date(wt.getTime().getTime());
	        		if(weightTime.after(closest)) {
	        			closest = weightTime;
	        			lastWeight = wt;
	        		}
	        }
		}
		if(lastWeight == null) {
			return null;
		}
		else{
			return lastWeight.getWeight();
		}
	}
	
	/** This method calculates the BMR of the user.
	 * 
	 * @param userID the ID of the user
	 * @return the BMR of the user
	 */
	public double getBMR(String userID) {		
		Profile pf = profileRepository.findByUserID(userID);
		Double weight = getLastWeight(userID);
		Double height = pf.getHeight();
		Integer age = pf.getAge();
		if(weight == null) {
			weight = 89.0;
		}
		double bmr = 10*weight + 6.25*height - 5*age;
		if(pf.getGender()=="Female") {
			bmr -= 161;
		}
		else {
			bmr += 5;
		}
		return bmr;
	}

	/** This method calculates the BMI of the user.
	 * 
	 * @param userID the ID of the user
	 * @return the BMI of the user
	 */
	public double getBMI(String userID) {		
		Profile pf = profileRepository.findByUserID(userID);
		Double weight = getLastWeight(userID);
		Double height = pf.getHeight()/100.0;
		if(weight == null) {
			weight = 89.0;
		}
		return weight/(height*height);
	}
	
	/** This method determines the BMI category of the user.
	 * 
	 * @param userID the ID of the user
	 * @return the BMI category of the user
	 */
	public String getBMICategory(String userID) {		
		double bmi = getBMI(userID);
		if(bmi<18.5) {
			return "Underweight";
		}
		else if(bmi<25) {
			return "Normal";
		}
		else if(bmi<30) {
			return "Overweight";
		}
		else {
			return "Obese";
		}
	}
	
	/** This method calculates the BFP of the user.
	 * 
	 * @param userID the ID of the user
	 * @return the BFP of the user
	 */
	public double getBFP (String userID) {		
		Profile pf = profileRepository.findByUserID(userID);
		Integer age = pf.getAge();
		double bfp = 1.2*getBMI(userID) + 0.23*age;
		if(pf.getGender()=="Female") {
			bfp -= 16.2;
		}
		else {
			bfp -= 5.4;
		}
		return bfp;
	}
	
	/** This method gets the set of food consumed by the user today.
	 * 
	 * @param userID the ID of the user
	 * @return the set of food consumed by the user today
	 */
	private Set<Food> getFoodsFromToday(String userID){
		Set<Food> foods = new HashSet<Food>();
		for(Meal ml : mealRepository.findAll()) {
			if(ml.getUserID().equals(userID)) { 
	        		Calendar today = Calendar.getInstance();
	        		Calendar mealTime = Calendar.getInstance();
	        		Date mealDate = new Date(ml.getTime().getTime());
	        		mealTime.setTime(mealDate);
	        		if((today.get(Calendar.ERA) == mealTime.get(Calendar.ERA) &&
	        			today.get(Calendar.YEAR) == mealTime.get(Calendar.YEAR) &&
	        			today.get(Calendar.DAY_OF_YEAR) == mealTime.get(Calendar.DAY_OF_YEAR))) {
	        			foods.addAll(mc.generateFoods(ml.getFood()));
	        		}
	        }
		}
		return foods;
	}
	
	/** This calculates the remainig calories for the user today.
	 * 
	 * @param userID the ID of the user
	 * @return the remainig calories for the user today.
	 */
	public double getRemainingCalories(String userID) {		
		double currentCalories = 0;
		Set<Food> mealsToday = getFoodsFromToday(userID);
		for(Food fd : mealsToday) {
			currentCalories += fd.getCalories();
		}
		return getBMR(userID) - currentCalories;
	}
	
	/** This calculates the remainig protein for the user today.
	 * 
	 * @param userID the ID of the user
	 * @return the remainig protein for the user today.
	 */
	public double getRemainingProtein(String userID) {		
		double currentProtein = 0;
		Set<Food> mealsToday = getFoodsFromToday(userID);
		for(Food fd : mealsToday) {
			currentProtein += fd.getProtein();
		}
		return getBMR(userID)*0.2/4.0 - currentProtein;
	}
	
	/** This calculates the remainig carbohydrate for the user today.
	 * 
	 * @param userID the ID of the user
	 * @return the remainig carbohydrate for the user today.
	 */
	public double getRemainingCarbohydrate(String userID) {		
		double currentCarbohydrate = 0;
		Set<Food> mealsToday = getFoodsFromToday(userID);
		for(Food fd : mealsToday) {
			currentCarbohydrate += fd.getCarbohydrate();
		}
		return getBMR(userID)*0.55/4.0 - currentCarbohydrate;
	}
	
	/** This calculates the remainig fat for the user today.
	 * 
	 * @param userID the ID of the user
	 * @return the remainig fat for the user today.
	 */
	public double getRemainingFat(String userID) {		
		double currentFat = 0;
		Set<Food> mealsToday = getFoodsFromToday(userID);
		for(Food fd : mealsToday) {
			currentFat += fd.getSaturatedFat();
		}
		return getBMR(userID)*0.25/9.0 - currentFat;
	}

	/** This shows the daily progress on nutrients and other information of the user.
	 * 
	 * @param userID the ID of the user
	 * @return daily report
	 */
	public String showDailyProgress(String userID) {
		DecimalFormat format = new DecimalFormat("##.00");
		return "Basal Metabolic Rate (BMR): "+format.format(getBMR(userID))+"\n"+
				"Body Mass Index (BMI): "+format.format(getBMI(userID))+"\n"+
				"Body Fat Percentage (BFP): "+format.format(getBFP(userID))+"\n"+
				"Current Status: "+getBMICategory(userID)+"\n"+"\n"+
				"Remaining Nutrients for today: \n"+
				"Calories: "+format.format(getRemainingCalories(userID))+"\n"+
				"Protein: "+format.format(getRemainingProtein(userID))+"\n"+
				"Carbohydrate: "+format.format(getRemainingCarbohydrate(userID))+"\n"+
				"Fat: "+format.format(getRemainingFat(userID))+"\n";			
	}
}