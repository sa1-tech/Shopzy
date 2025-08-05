package com.sg.shopzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sg.shopzy.Order;

public class OrderdetailActivity extends AppCompatActivity {

    private TextView orderId, productName, quantity, productPrice, gst, grandTotal;
    private ImageView productImage;
    private Button homePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        // ✅ Initialize Views
        orderId = findViewById(R.id.orderId);
        productName = findViewById(R.id.productName);
        quantity = findViewById(R.id.quantity);
        productPrice = findViewById(R.id.productPrice);
        gst = findViewById(R.id.gst);
        grandTotal = findViewById(R.id.grandTotal);
        productImage = findViewById(R.id.productImage);
        homePageButton = findViewById(R.id.homePageButton);

        // ✅ Get Data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("order")) {
            Order order = (Order) intent.getSerializableExtra("order");

            if (order != null) {
                // ✅ Set Data to Views
                orderId.setText("Order ID: #" + order.getOrderId());
                productName.setText(order.getProductName());
                quantity.setText("Quantity: " + order.getQuantity());
                productPrice.setText("Price: ₹" + order.getTotal());
                gst.setText("GST: ₹" + order.getGst());

                // ✅ Grand Total Calculation (Correct Way)
                double grandTotalValue = order.getTotal();
                grandTotal.setText("Grand Total: ₹" + grandTotalValue);

                // ✅ Load Product Image using Glide
                Glide.with(this)
                        .load(order.getProductImage())
                        .placeholder(R.drawable.placeorder_image)
                        .error(R.drawable.error_image)
                        .into(productImage);
            }
        } else {
            // ✅ If no data is received
            Toast.makeText(this, "No order details found!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // ✅ Go to Home Page Button
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ✅ Redirect to Home Page (without reopening it)
                Intent intent = new Intent(OrderdetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                // ✅ Show Order Confirmation on Home Page
//                Toast.makeText(OrderdetailActivity.this, "🎉 Order placed successfully!", Toast.LENGTH_SHORT).show();

                // ✅ Close this Activity
                finish();
            }
        });
    }
}
