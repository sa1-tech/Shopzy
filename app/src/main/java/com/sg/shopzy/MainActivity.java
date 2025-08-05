package com.sg.shopzy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // ✅ RecyclerView to display categories dynamically
    private RecyclerView categoryRecyclerView;

    // ✅ SharedPreferences to manage user session (like user_id, etc.)
    private SharedPreferences sharedPreferences;

    // ✅ ApiService to make API requests (like fetching categories)
    private ApiService apiService;

    // ✅ Handle sidebar button clicks (Home, Location, Profile, Logout)
    private final View.OnClickListener sidebarClickListener = v -> {
        int id = v.getId();

        // ✅ Check which button is clicked and call appropriate method
        if (id == R.id.btnHome) showToast("Home Clicked");
        else if (id == R.id.btnLocation) showComingSoonDialog();
        else if (id == R.id.btnProfile) navigateTo(ProfileActivity.class);
        else if (id == R.id.btnLogout) showLogoutDialog();
    };

    // ✅ This method runs when the Activity starts (on launch)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Initialize SharedPreferences to store user session data
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // ✅ Initialize Retrofit API service
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // ✅ Check if the user is already logged in or not
        checkUserSession();

        // ✅ Initialize Views like RecyclerView and Sidebar Buttons
        initViews();

        // ✅ Fetch categories from the API (like Electronics, Clothing, etc.)
        fetchCategories();
    }

    // ✅ Bind all Views (like RecyclerView, Buttons) to the layout
    private void initViews() {
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);

        // ✅ Set Linear Layout so that categories are shown one after another (Vertical List)
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Attach Click Listeners to Sidebar buttons
        findViewById(R.id.btnHome).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnLocation).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnProfile).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnLogout).setOnClickListener(sidebarClickListener);
    }

    // ✅ Check if the user is already logged in (Session Management)
    private void checkUserSession() {
        // ✅ If user_id is NULL, navigate to LoginActivity (because user is not logged in)
        if (sharedPreferences.getString("USER_ID", null) == null) {
            navigateTo(LoginActivity.class);
        }
    }

    // ✅ Call API to fetch categories (like: Electronics, Clothing, etc.)
    private void fetchCategories() {
        apiService.getCategories().enqueue(new Callback<CategoryResponse>() {

            // ✅ If API response is successful (status 200)
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    // ✅ If categories are fetched successfully, show in RecyclerView
                    displayCategories(response.body().getCategories());
                } else {
                    // ✅ If categories failed to load, show error toast
                    showToast("Failed to load categories");
                }
            }

            // ✅ If API call failed (due to no internet or server issue)
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                // ✅ Display error message with Throwable exception
                showToast("Error: " + t.getMessage());
            }
        });
    }

    // ✅ Bind Category List to RecyclerView using CategoryAdapter
    private void displayCategories(List<Category> categories) {
        categoryRecyclerView.setAdapter(new CategoryAdapter(this, categories, category -> {

            // ✅ When any category is clicked, open ProductActivity with CATEGORY_ID
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra("CATEGORY_ID", category.getId());
            startActivity(intent);
        }));
    }

    // ✅ Show Logout Confirmation Dialog (with Yes/No)
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logout())
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ✅ Perform Logout by clearing SharedPreferences and going to LoginActivity
    private void logout() {
        // ✅ Clear user session from SharedPreferences
        sharedPreferences.edit().clear().apply();

        // ✅ Show logout confirmation toast
        showToast("Logged out successfully");

        // ✅ Redirect to LoginActivity
        navigateTo(LoginActivity.class);
    }

    // ✅ Reusable Method to Navigate to Any Activity
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(MainActivity.this, targetActivity);

        // ✅ Clear the backstack (so user can't go back to MainActivity after Logout)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // ✅ Finish the current activity
    }

    // ✅ Show a "Coming Soon" Dialog when Location Button is clicked
    private void showComingSoonDialog() {
        // ✅ Inflate custom layout for Dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_coming_soon, null);

        // ✅ Create AlertDialog with Custom Layout
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        // ✅ Set Dialog Background Transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // ✅ Close the dialog when button is clicked
        dialogView.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ✅ Reusable Toast Message Method
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
