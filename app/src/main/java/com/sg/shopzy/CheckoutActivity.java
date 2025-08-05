package com.sg.shopzy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private ImageView productImage;
    private TextView productName, productPrice, userName, userEmail;
    private Button confirmOrderButton;
    private ApiService checkoutService;
    private SharedPreferences sharedPreferences;

    private String productId, name, price, image;
    private String userId, userNameStr, userEmailStr;

    private static final String CHANNEL_ID = "order_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        init();
        getIntentData();
        getUserData();
        displayDetails();
        createNotificationChannel();

        confirmOrderButton.setOnClickListener(v -> placeOrder());
    }

    private void init() {
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        checkUserSession();

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        checkoutService = RetrofitClient.getClient().create(ApiService.class);
    }

    private void checkUserSession() {
        if (sharedPreferences.getString("USER_ID", null) == null) {
            showToast("Session expired. Please login.");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            productId = String.valueOf(intent.getIntExtra("PRODUCT_ID", -1));
            name = intent.getStringExtra("PRODUCT_NAME");
            price = intent.getStringExtra("PRODUCT_PRICE");
            image = intent.getStringExtra("PRODUCT_IMAGE");
        } else {
            Log.e("CheckoutActivity", "Intent is null. No data received.");
        }
    }

    private void getUserData() {
        userId = sharedPreferences.getString("USER_ID", "");
        userNameStr = sharedPreferences.getString("USER_NAME", "N/A");
        userEmailStr = sharedPreferences.getString("USER_EMAIL", "N/A");
    }

    private void displayDetails() {
        productName.setText(name != null ? name : "N/A");
        productPrice.setText("\u20B9" + (price != null ? price : "0"));

        Glide.with(this)
                .load(image != null ? image : "")
                .placeholder(R.drawable.placeorder_image)
                .error(R.drawable.error_image)
                .into(productImage);

        userName.setText("Name: " + userNameStr);
        userEmail.setText("Email: " + userEmailStr);
    }

    private void placeOrder() {
        if (productId.equals("-1") || userId.isEmpty()) {
            showToast("Error: Missing required details.");
            return;
        }

        int quantity = 1;
        String paymentMethod = "Cash on Delivery";

        checkoutService.placeOrder(
                Integer.parseInt(productId),
                Integer.parseInt(userId),
                quantity,
                paymentMethod
        ).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body() != null && "success".equals(response.body().getStatus())) {
                    handleOrderResponse(response.body());
                } else {
                    showToast(response.body() != null ? response.body().getMessage() : "Order failed! Empty response.");
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                showToast("Network Error: " + t.getMessage());
            }
        });
    }

    private void handleOrderResponse(OrderResponse orderResponse) {
        OrderResponse.OrderData data = orderResponse.getData();

        Order order = new Order(
                String.valueOf(data.getOrderId()),
                name,
                image,
                1,
                data.getTotal(),
                data.getGst()
        );

        showToast("🎉 Order placed successfully!");
        sendOrderNotification(order);
        vibratePhone(); // Add vibration here

        Intent intent = new Intent(CheckoutActivity.this, OrderdetailActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
    }

    private void sendOrderNotification(Order order) {
        Intent intent = new Intent(this, OrderdetailActivity.class);
        intent.putExtra("order", order);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Order Placed Successfully......✅")
                .setContentText("Your order for " + order.getProductName() + " has been placed.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Notifications";
            String description = "Notifications for order status updates";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void vibratePhone() {
        // Get the system service Vibrator
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 500 milliseconds
            vibrator.vibrate(500); // Adjust duration if needed
        } else {
            Log.e("CheckoutActivity", "Vibrator service not available.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
