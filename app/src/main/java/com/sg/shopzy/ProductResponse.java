package com.sg.shopzy;

import java.util.List;

public class ProductResponse {
    private String status;
    private List<Product> data; // Ensure this matches API response

    public String getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return data; // Corrected getter to return products list
    }
}
