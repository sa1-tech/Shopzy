package com.sg.shopzy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private int categoryId;
    private SharedPreferences sharedPreferences;

    // ✅ Sidebar Click Listener handles navigation to other activities
    private final View.OnClickListener sidebarClickListener = v -> {
        int id = v.getId();
        if (id == R.id.btnHome) {
            navigateTo(MainActivity.class); // ✅ Navigate to MainActivity
        } else if (id == R.id.btnLocation) {
            showComingSoonDialog(); // ✅ Show "Coming Soon" dialog
        } else if (id == R.id.btnProfile) {
             navigateTo(ProfileActivity.class);
            // ✅ This line is commented because ProfileActivity is not yet implemented.
        } else if (id == R.id.btnLogout) {
            showLogoutDialog(); // ✅ Show Logout Confirmation Dialog
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // ✅ Initialize SharedPreferences to store and retrieve user session
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        initUI();       // ✅ Initialize RecyclerView and get Category ID
        setupSidebar(); // ✅ Set Click Listeners on Sidebar Buttons
        fetchProducts(); // ✅ Fetch Products from API based on Category ID
    }

    // 🚀 Initialize UI components
    private void initUI() {
        // ✅ Get CATEGORY_ID from Intent. If no category is selected, close the activity.
        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        if (categoryId == -1) {
            showToast("Invalid Category ID");
            finish(); // ✅ Finish the activity if no valid category is selected
            return;
        }

        // ✅ Setup RecyclerView for displaying products
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // 📜 Setup Sidebar Navigation Click Listeners
    private void setupSidebar() {
        findViewById(R.id.btnHome).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnLocation).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnProfile).setOnClickListener(sidebarClickListener);
        findViewById(R.id.btnLogout).setOnClickListener(sidebarClickListener);
    }

    // ✅ Fetch Products by Category from API
    private void fetchProducts() {
        RetrofitClient.getClient().create(ApiService.class)
                .getProductsByCategory(categoryId)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Product> products = response.body().getProducts();
                            if (products != null && !products.isEmpty()) {
                                displayProducts(products);
                            } else {
                                showToast("No products available.");
                            }
                        } else {
                            // ✅ Handle Error if response is not successful
                            showToast("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        // ✅ Handle Network Error or API Failure
                        showToast("Network Error: " + t.getMessage());
                    }
                });
    }

    // ✅ Display the list of products in RecyclerView
    private void displayProducts(List<Product> products) {
        productRecyclerView.setAdapter(new ProductAdapter(this, products));
    }

    // ✅ Show Logout Confirmation Dialog with Yes/No Options
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    clearUserSession(); // ✅ Clear User Session
                    navigateTo(LoginActivity.class, true); // ✅ Navigate to Login Page
                    showToast("Logged out successfully.");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ✅ Clear user session from SharedPreferences
    private void clearUserSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    // ✅ Show a "Coming Soon" Dialog for Features not yet Implemented
    private void showComingSoonDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_coming_soon, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        // ✅ Set Background Transparent for Smooth Design
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // ✅ Close the dialog when "Close" button is clicked
        dialogView.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ✅ Utility Function to Navigate to Another Activity
    private void navigateTo(Class<?> targetActivity) {
        navigateTo(targetActivity, false);
    }

    // ✅ Navigate to Another Activity with an Option to Clear Stack
    private void navigateTo(Class<?> targetActivity, boolean clearStack) {
        Intent intent = new Intent(ProductActivity.this, targetActivity);
        if (clearStack) intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (clearStack) finish();
    }

    // ✅ Utility Function to Show Toast Messages
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
