package com.sg.shopzy;  // 📂 Package where this class belongs

import android.content.Context;  // ✅ To access SharedPreferences and Context
import android.content.Intent;   // ✅ To navigate from one Activity to another
import android.content.SharedPreferences;  // ✅ To store data locally in the app

// ✅ This class is responsible for managing the user session using SharedPreferences.
public class SharedPrefManager {

	// ✅ Name of the SharedPreferences file where data will be saved.
	private static final String SHARED_PREF_NAME = "shopzy_pref";

	// ✅ Key to store the user ID in SharedPreferences.
	private static final String KEY_USER_ID = "user_id";

	// ✅ This is a Singleton instance of this class. It ensures that only one object of SharedPrefManager exists.
	private static SharedPrefManager instance;

	// ✅ This is the actual SharedPreferences object that will store/retrieve data.
	private final SharedPreferences sharedPreferences;


	// ✅ Private Constructor (Singleton Pattern)
	// ✅ It is private because we don't want other classes to directly create its object.
	// ✅ This constructor will only be called through the getInstance() method.
	private SharedPrefManager(Context context) {
		sharedPreferences = context.getApplicationContext()
				.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		// 🔥 This line creates the SharedPreferences file with the name "shopzy_pref"
		// 🔥 Context.MODE_PRIVATE means that only this app can access this data.
	}


	// ✅ Public method to get the instance of SharedPrefManager (Singleton Pattern)
	// ✅ This ensures that only **one instance** of this class will exist throughout the app.
	public static synchronized SharedPrefManager getInstance(Context context) {
		if (instance == null)
			instance = new SharedPrefManager(context);
		return instance;
		// 🔥 This ensures that even if you call this method 100 times,
		// 🔥 it will return the same object (same instance) without creating a new one.
	}


	// ✅ Method to SAVE user ID in SharedPreferences after successful login.
	public void saveUserId(String userId) {
		sharedPreferences.edit().putString(KEY_USER_ID, userId).apply();
		// 🔥 Explanation:
		// ✅ .edit() -> Open the SharedPreferences in Edit Mode.
		// ✅ putString() -> Save a string value with the key "user_id".
		// ✅ apply() -> Save the changes asynchronously (without delay).
		//
		// Example:
		// If user ID = 123, it will be stored like this:
		// shopzy_pref.xml ->
		// <map>
		//    <string name="user_id">123</string>
		// </map>
	}


	// ✅ Method to CHECK IF USER IS LOGGED IN or NOT.
	public boolean isLoggedIn() {
		return getUserId() != null;
		// 🔥 Explanation:
		// ✅ It simply checks if the user_id exists or not.
		// ✅ If getUserId() returns NULL -> User is NOT logged in.
		// ✅ If getUserId() returns a value -> User IS logged in.
		//
		// Example:
		// If user_id exists -> true
		// If user_id doesn't exist -> false
	}


	// ✅ Method to GET the currently logged-in User ID.
	public String getUserId() {
		return sharedPreferences.getString(KEY_USER_ID, null);
		// 🔥 Explanation:
		// ✅ It simply retrieves the User ID from SharedPreferences.
		// ✅ If User ID exists, it will return it.
		// ✅ If User ID does not exist, it will return NULL.
		//
		// Example:
		// If you previously saved "user_id=123", it will return "123".
		// If no user is logged in, it will return NULL.
	}


	// ✅ Method to LOGOUT the user and clear all session data.
	public void logout(Context context) {
		sharedPreferences.edit().clear().apply();
		// 🔥 Explanation:
		// ✅ .edit() -> Open the SharedPreferences in Edit Mode.
		// ✅ clear() -> Clears all data from SharedPreferences (logout).
		// ✅ apply() -> Apply the changes asynchronously.

		// ✅ Now we are redirecting the user to the LoginActivity after logout.
		context.startActivity(new Intent(context, LoginActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
		// 🔥 Explanation:
		// ✅ new Intent(context, LoginActivity.class) -> Open Login Page.
		// ✅ setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
		//    - FLAG_ACTIVITY_NEW_TASK: Treat this as a new task (no previous activities).
		//    - FLAG_ACTIVITY_CLEAR_TASK: Clear all previous activities from stack.
		//
		// ✅ This ensures that after logout, the user cannot press "Back" to return to the home screen.
	}
}
