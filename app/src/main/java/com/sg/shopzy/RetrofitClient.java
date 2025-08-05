package com.sg.shopzy;  // 📂 Package where this class belongs


// ✅ Import Gson for JSON serialization/deserialization.
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// ✅ Import OkHttpClient for making HTTP requests.
import okhttp3.OkHttpClient;

// ✅ Import HttpLoggingInterceptor to log requests and responses for debugging.
import okhttp3.logging.HttpLoggingInterceptor;

// ✅ Import Retrofit for API calls.
import retrofit2.Retrofit;

// ✅ Import GsonConverterFactory to convert JSON responses to Java objects.
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * ✅ This class is responsible for:
 * - Creating a single instance of Retrofit.
 * - Adding logging for API requests/responses.
 * - Attaching a Gson converter to parse JSON.
 */
public class RetrofitClient {

    // ✅ This variable will hold the single instance of Retrofit.
    private static Retrofit retrofit;

    // ✅ This is the base URL where all your API endpoints will be called.
    // ✅ Use "http://10.0.2.2/" if you're testing on Android Emulator (localhost).
    private static final String BASE_URL = "http://10.135.241.21/shopzy-admin/api/";


    /**
     * ✅ This method will create and return a single instance of Retrofit.
     * ✅ It uses Singleton Design Pattern (one instance only).
     */
    public static Retrofit getClient() {

        // ✅ Check if Retrofit instance is already created or not.
        if (retrofit == null) {

            // ✅ STEP 1: Enable HTTP request/response logging (for debugging)
            // 🔥 This will show complete API Request & Response in Logcat.
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 🚀 Now every API request/response will be logged (including Body, Headers, etc.)



            // ✅ STEP 2: Create an OkHttpClient and attach the logging interceptor.
            // ✅ OkHttpClient is responsible for making network requests.
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // ✅ Attach logging here.
                    .build();
            // 🚀 Now every API call will pass through OkHttpClient, allowing logging and modifications.



            // ✅ STEP 3: Build a Gson instance to parse JSON to Java objects.
            Gson gson = new GsonBuilder()
                    .setLenient() // ✅ Allow lenient JSON parsing (for faulty API responses)
                    .create();
            // 🚀 This Gson will convert the API's JSON response to Java objects (models).
            // 🚀 If the API returns faulty or incomplete JSON, it will still parse without crashing.



            // ✅ STEP 4: Build the Retrofit instance.
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // ✅ Set the API Base URL
                    .client(client)     // ✅ Attach the OkHttpClient (with logging)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // ✅ Attach Gson converter
                    .build();
            // 🚀 This Retrofit instance will handle all your API calls.
        }


        // ✅ Always return the same Retrofit instance.
        return retrofit;
    }
}
