package com.sg.shopzy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    // ✅ Declare UI Components
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerText;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ✅ Initialize all views, services, and check user session
        initViews();
        setupClickListeners();
        initServices();
        checkUserSession();
    }

    // ✅ Initialize views, SharedPreferences, and API service
    private void initViews() {
        emailEditText = findViewById(R.id.email); // Email input field
        passwordEditText = findViewById(R.id.password); // Password input field
        loginButton = findViewById(R.id.login_button); // Login button
        registerText = findViewById(R.id.register_text); // Register TextView

        // ✅ Create a SharedPreferences to store user session data
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
    }

    // ✅ Initialize Retrofit API Service for login request
    private void initServices() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    // ✅ Handle button click events (login and register)
    private void setupClickListeners() {
        // ✅ Login Button Click Event
        loginButton.setOnClickListener(v -> validateAndLogin());

        // ✅ Register Text Click Event (Redirect to RegisterActivity)
        registerText.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    // ✅ Check if user is already logged in (Session Active)
    private void checkUserSession() {
        // ✅ Check if USER_ID exists in SharedPreferences
        if (sharedPreferences.getString("USER_ID", null) != null) {
            // ✅ Redirect to MainActivity if user is already logged in
            navigateToMain();
        }
    }

    // ✅ Validate Email and Password before making API call
    private void validateAndLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // ✅ Check if Email or Password is empty
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please enter email and password");
            return;
        }

        // ✅ Call the API to login user
        loginUser(email, password);
    }

    // ✅ Call the API to login user
    private void loginUser(String email, String password) {
        // ✅ Retrofit API Call
        apiService.login(email, password).enqueue(new Callback<ApiResponse>() {

            // ✅ Handle API Response if it is successful
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // ✅ Handle login success if response is not null
                    handleLoginSuccess(response.body());
                } else {
                    // ✅ Handle login failure (invalid credentials or server error)
                    handleLoginFailure(response);
                }
            }

            // ✅ Handle Network Failure (like no internet or server down)
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showToast("Network error: " + t.getMessage());
                Log.e("LoginActivity", "Login Request Failed", t);
            }
        });
    }

    // ✅ Handle Successful Login Response
    private void handleLoginSuccess(ApiResponse apiResponse) {
        // ✅ Check if the API response status is success
        if (apiResponse.isSuccess()) {
            // ✅ Show success message
            showToast("🎉 Login Successful");

            // ✅ Save user session data to SharedPreferences
            saveUserSession(apiResponse);

            // ✅ Redirect to MainActivity
            navigateToMain();
        } else {
            // ✅ Show error message from API
            showToast(apiResponse.getMessage());
        }
    }

    // ✅ Save User Data in SharedPreferences (User Session)
    private void saveUserSession(ApiResponse apiResponse) {
        // ✅ Store user data in SharedPreferences
        sharedPreferences.edit()
                .putString("USER_ID", apiResponse.getUserId())
                .putString("USER_NAME", apiResponse.getUserName())
                .putString("USER_EMAIL", apiResponse.getUserEmail())
                .apply();
    }

    // ✅ Handle Failed Login Response (Invalid Credentials)
    private void handleLoginFailure(Response<ApiResponse> response) {
        try {
            // ✅ Check if API returned an error response
            if (response.errorBody() != null) {
                // ✅ Read error response from API
                String errorResponse = response.errorBody().string();
                Log.e("LoginActivity", "Login failed: " + errorResponse);
                showToast("Login failed: " + errorResponse);
            } else {
                // ✅ Default error message if no response
                showToast("Login failed. Please try again.");
            }
        } catch (IOException e) {
            // ✅ Handle Error Response Exception
            Log.e("LoginActivity", "Error parsing error response", e);
            showToast("An error occurred. Please try again.");
        }
    }

    // ✅ Redirect to MainActivity after Login Success
    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // ✅ Clear all previous activities from backstack (so user can't go back to login)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // ✅ Show Toast Message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
