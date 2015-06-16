package info.androidhive.slidingmenu;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManager {
	
	
	SharedPreferences pref;
	Editor editor;
	
	Context cntxt;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	private static final String SHARED_PREFER_FILE_NAME = "AndroidExamplePref";
	private static final String SHARED_PREF_KEY = "IsUserLoggedIn";
	
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASS = "pass";
	public static final String KEY_FRIDGE = "fridge";
	
	public UserSessionManager(Context context){
		this.cntxt = context;
		pref = cntxt.getSharedPreferences(SHARED_PREFER_FILE_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	//Create login session
	public void createUserLoginSession(String name, String email, String pass){
		// Storing login value as TRUE
		editor.putBoolean(SHARED_PREF_KEY, true);
		
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		
		editor.putString(KEY_PASS, pass);
		
		// commit changes
		editor.commit();
	}	
	//Create login session
	public void createUserFridgeSession(int fridgeID){
		// Storing login value as TRUE
		editor.putInt(KEY_FRIDGE, fridgeID);
		
	
		
		// commit changes
		editor.commit();
	}	
	/**
	 * Check login method will check user login status
	 * If false it will redirect user to login page
	 * Else do anything
	 * */
	public boolean checkLogin(){
		// Check login status
		if(!this.isUserLoggedIn()){
			
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(cntxt, LoginActivity.class);
			
			// Closing all the Activities from stack
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			cntxt.startActivity(i);
			
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		
		//Use hashmap to store user credentials
		HashMap<String, String> user = new HashMap<String, String>();
		
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// user email id
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		
		user.put(KEY_PASS, pref.getString(KEY_PASS, null));

		
		// return user
		return user;
	}
	public HashMap<String, Integer> getUserFridge(){
		
		//Use hashmap to store user credentials
		HashMap<String, Integer> user = new HashMap<String, Integer>();
		
		// user name
		user.put(KEY_FRIDGE, pref.getInt(KEY_FRIDGE, -1));
	

		
		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		
		// Clearing all user data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Login Activity
		Intent i = new Intent(cntxt, LoginActivity.class);
		
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		cntxt.startActivity(i);
	}
	
	
	// Check for login
	public boolean isUserLoggedIn(){
		return pref.getBoolean(SHARED_PREF_KEY, false);
	}
}
