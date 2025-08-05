package com.sg.shopzy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

	private TextView txtPrivacyPolicy;
	private ImageView appLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// ✅ Initialize Privacy Policy Button
		txtPrivacyPolicy = findViewById(R.id.txtPrivacyPolicy);

		// ✅ Click Event to Show Privacy Policy
		txtPrivacyPolicy.setOnClickListener(v -> showPrivacyPolicyDialog());
	}

	// ✅ Show Privacy Policy Dialog Box
	public void showPrivacyPolicyDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// ✅ Set App Logo in Dialog
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.logo_login);  // ✅ Make sure you have app_logo in drawable
//		imageView.setPadding(20, 20, 20, 20);

		// ✅ Set Dialog Title, Message, and Icon
		builder.setTitle("Shopzy - Privacy Policy")
				.setView(imageView)
				.setMessage("We value your privacy. This app does not share your data with any third-party service. " +
						"Your personal information, orders, and activities are fully secure.\n\n" +
						"For more information, please contact us at:\n" +
						"📧 support@shopzy.com")
				.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

		// ✅ Show the Dialog
		builder.create().show();
	}

	// ✅ Handle BACK Button to Redirect to MainActivity
	@Override
	public void onBackPressed() {
		// ✅ Redirect to MainActivity when back button pressed
		Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		finish();
	}
}
