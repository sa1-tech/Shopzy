package com.sg.shopzy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextAddress, editTextPhone;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phoneno);
        editTextPassword = findViewById(R.id.password);
        editTextAddress = findViewById(R.id.address);
        buttonRegister = findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneno = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (validateInput(name, email, phoneno, password, address)) {
            registerUser(name, email, phoneno, password, address);
        }
    }

    private boolean validateInput(String name, String email, String phone, String password, String address) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(address)) {
            showToast("Please fill all fields");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email format");
            return false;
        }

        if (password.length() < 6) {
            showToast("Password must be at least 6 characters");
            return false;
        }

        // ✅ Check if second character in password is 'A'
//        if (password.length() >= 2 && password.charAt(1) != "A"  && password.charAt(1) != 'a') {
//            showToast("Password's second character must be 'A'");
//            return false;
//        }
//        if (password.length() >= 2 && password.charAt(1) != 'A' && password.charAt(1) != 'a') {
//            showToast("Password's second character must be 'A' or 'a'");
//            return false;
//        }


        if (phone.length() < 10) {
            showToast("Invalid phone number");
            return false;
        }

        return true;
    }

    private void registerUser(String name, String email, String phone, String password, String address) {
        buttonRegister.setEnabled(false);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<RegisterResponse> call = apiService.registerUser(email, password, name, phone, address);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                buttonRegister.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    showToast(response.body().getMessage());

                    if ("success".equals(response.body().getStatus())) {
                        navigateToLogin();
                    }
                } else {
                    showToast("Registration failed. Try again.");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                buttonRegister.setEnabled(true);
                showToast("Error: " + t.getMessage());
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
