package com.sg.shopzy;  // 📂 Package where this interface belongs


// ✅ Import necessary Retrofit and OkHttp libraries
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * ✅ This interface contains all your API Endpoints.
 * ✅ Retrofit uses this interface to make API requests.
 * ✅ It contains:
 *     - POST Requests (for sending data)
 *     - GET Requests (for fetching data)
 *     - Query Parameters (for passing data in URL)
 *     - Form-Encoded Data (for POST form data)
 */
public interface ApiService {

    // ==============================================
    // ✅ 1. LOGIN API (POST)
    // ==============================================
    /**
     * ✅ This API logs in a user with email and password.
     * ✅ It sends POST request with email & password.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/login_api.php
     *
     * 📩 Request Body:
     * - email: example@gmail.com
     * - password: 123456
     *
     * 📬 Expected Response (ApiResponse):
     * {
     *    "status": true,
     *    "message": "Login Successful",
     *    "user_id": "123"
     * }
     */
    @FormUrlEncoded // ✅ Converts form-data to x-www-form-urlencoded body
    @POST("login_api.php")
    Call<ApiResponse> login(
            @Field("email") String email,         // 📩 User Email
            @Field("password") String password    // 🔑 User Password
    );


    // ==============================================
    // ✅ 2. USER REGISTRATION API (POST)
    // ==============================================
    /**
     * ✅ This API registers a new user with email, password, and personal info.
     * ✅ It sends POST request with user details.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/register.php
     *
     * 📩 Request Body:
     * - email: example@gmail.com
     * - password: 123456
     * - name: John Doe
     * - phoneno: 9876543210
     * - address: New York City
     *
     * 📬 Expected Response (RegisterResponse):
     * {
     *    "status": true,
     *    "message": "User registered successfully",
     *    "user_id": "123"
     * }
     */
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> registerUser(
            @Field("email") String email,         // 📩 User Email
            @Field("password") String password,   // 🔑 User Password
            @Field("name") String name,           // 👤 User Name
            @Field("phoneno") String phone,       // 📞 User Phone Number
            @Field("address") String address      // 🏠 User Address
    );


    // ==============================================
    // ✅ 3. GET ALL CATEGORIES API (GET)
    // ==============================================
    /**
     * ✅ This API fetches all product categories from the database.
     * ✅ It uses GET request with no parameters.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/get_categories.php
     *
     * 📬 Expected Response (CategoryResponse):
     * {
     *    "status": true,
     *    "categories": [
     *        {
     *           "id": "1",
     *           "name": "Electronics"
     *        },
     *        {
     *           "id": "2",
     *           "name": "Clothing"
     *        }
     *    ]
     * }
     */
    @GET("get_categories.php")
    Call<CategoryResponse> getCategories();


    // ==============================================
    // ✅ 4. GET PRODUCTS BY CATEGORY API (GET)
    // ==============================================
    /**
     * ✅ This API fetches products from a specific category.
     * ✅ It uses GET request and sends category_id in URL.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/get_products.php?category_id=1
     *
     * 📬 Expected Response (ProductResponse):
     * {
     *    "status": true,
     *    "products": [
     *        {
     *           "id": "101",
     *           "name": "Smartphone",
     *           "price": "12000"
     *        },
     *        {
     *           "id": "102",
     *           "name": "Smartwatch",
     *           "price": "5000"
     *        }
     *    ]
     * }
     */
    @GET("get_products.php")
    Call<ProductResponse> getProductsByCategory(
            @Query("category_id") int categoryId // ✅ Passing category_id in URL as Query
    );


    // ==============================================
    // ✅ 5. PLACE ORDER API (POST)
    // ==============================================
    /**
     * ✅ This API places a new order.
     * ✅ It sends POST request with product_id, user_id, quantity, and payment method.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/place_order.php
     *
     * 📩 Request Body:
     * - product_id: 101
     * - user_id: 123
     * - quantity: 2
     * - payment_method: COD
     *
     * 📬 Expected Response (OrderResponse):
     * {
     *    "status": true,
     *    "message": "Order placed successfully",
     *    "order_id": "987"
     * }
     */
    @FormUrlEncoded
    @POST("place_order.php")
    Call<OrderResponse> placeOrder(
            @Field("product_id") int productId,         // 🛍️ Product ID
            @Field("user_id") int userId,               // 👤 User ID
            @Field("quantity") int quantity,            // 📊 Quantity of Product
            @Field("payment_method") String paymentMethod // 💳 Payment Method (COD, UPI, etc.)
    );


    // ==============================================
    // ✅ 6. FETCH USER ORDERS API (GET)
    // ==============================================
    /**
     * ✅ This API fetches all orders placed by a user.
     * ✅ It uses GET request and sends user_id in URL.
     * ✅ Example URL: http://10.0.2.2/shopzy-admin/api/orders.php?user_id=123
     *
     * 📬 Expected Response (OrderResponse):
     * {
     *    "status": true,
     *    "orders": [
     *        {
     *           "order_id": "987",
     *           "product_name": "Smartphone",
     *           "quantity": 2,
     *           "total_price": "24000"
     *        }
     *    ]
     * }
     */
    @GET("orders.php")
    Call<OrderResponse> getUserOrders(
            @Query("user_id") int userId // ✅ Passing user_id in URL as Query
    );
}
