package com.sg.shopzy;  // ✅ This is your package name (your app's unique identifier)

import android.content.Intent;  // ✅ Required to switch between activities (Splash → Login/Main)
import android.content.SharedPreferences; // ✅ Used to store user login status (like IS_LOGGED_IN)
import android.os.Bundle;  // ✅ Helps to save the current state of the app when destroyed
import android.os.Handler; // ✅ Introduces a delay before moving to MainActivity or LoginActivity
import androidx.appcompat.app.AppCompatActivity;  // ✅ This is the parent class for all activities
import android.widget.ImageView; // ✅ Used to display the GIF (Shopzy Logo)
import com.bumptech.glide.Glide;  // ✅ Third-party library to load GIF/Images
import android.view.animation.AnimationUtils; // ✅ Provides pre-built animations (like fade-in, bounce-in)

/**
 * ✅ This is your Splash Screen Activity that runs when the app is opened.
 * ✅ It shows a GIF, plays an animation, and then redirects to:
 *    - MainActivity (if the user is logged in).
 *    - LoginActivity (if the user is not logged in).
 */
public class SplashActivity extends AppCompatActivity {

    // ✅ Time for the splash screen to stay visible (5.5 seconds = 5500 milliseconds)
    private static final int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // ✅ This connects your layout to the activity

        /*
         * ===========================================
         * ✅ Step 1: Load GIF using Glide Library
         * ===========================================
         */
        Glide.with(this)  // ✅ Initialize Glide to load image/GIF
                .asGif()  // ✅ Specify that we are loading a GIF
                .load(R.drawable.your_gif)  // ✅ Load your GIF from "res/drawable"
                .into((ImageView) findViewById(R.id.logoImageView));
        // ✅ Actual Use: It loads the GIF into the ImageView without lag or delay.


        /*
         * ===========================================
         * ✅ Step 2: Apply Text Animation
         * ===========================================
         */
//        findViewById(R.id.appNameTextView)
//                .startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_animation));
        // ✅ Actual Use: This applies a custom animation effect (like fade-in, pop-in)
        // ✅ to your "Shopzy" text. The animation file is located in:
        // --> res/anim/text_animation.xml


        /*
         * ===========================================
         * ✅ Step 3: Delay Using Handler (Splash Timer)
         * ===========================================
         */
        new Handler().postDelayed(this::redirectUser, SPLASH_TIME);
        // ✅ Actual Use:
        // - This Handler() introduces a delay of 5.5 seconds (SPLASH_TIME).
        // - After 5.5 seconds, it calls the redirectUser() method automatically.
    }

    /*
     * ===========================================
     * ✅ Step 4: Redirect User Based on Login Status
     * ===========================================
     */
    private void redirectUser() {
        // ✅ Use SharedPreferences to get user login state (IS_LOGGED_IN)
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        // ✅ Check if the user is logged in or not
        boolean isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false);

        /*
         * ✅ Redirect to Activity Based on Login Status:
         * - If user is logged in → Redirect to MainActivity
         * - If user is not logged in → Redirect to LoginActivity
         */
        startActivity(new Intent(this, isLoggedIn ? MainActivity.class : LoginActivity.class));

        // ✅ Close this SplashActivity so that the user can't return to it.
        finish();
    }
}
