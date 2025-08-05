package com.sg.shopzy;

import com.google.gson.annotations.SerializedName;

public class Product {
    private int id;

    @SerializedName("product_name")
    private String productName;

    private String price;
    private String description;
    private String image;

    @SerializedName("cat_id")
    private int categoryId;

    // ✅ Base URL for images (adjust this according to your backend)
    private static final String BASE_IMAGE_URL = "http://10.0.2.2/shopzy-admin/product_image/";

    // ⭐ Constructor
    public Product(int id, String productName, String price, String description, String image, int categoryId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.image = image;
        this.categoryId = categoryId;
    }

    // 🎯 Getters
    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        // Replace 'localhost' with '10.0.2.2' for emulator access
        if (image != null && image.contains("localhost")) {
            return image.replace("localhost", "192.168.182.21");
        }
        return image;
    }


    public int getCategoryId() {
        return categoryId;
    }

    // ✨ Setters (if needed)
    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
