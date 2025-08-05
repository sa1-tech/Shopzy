package com.sg.shopzy;  // Defines the package where this class belongs. Helps in organizing the code.

import java.util.List;  // Imports the List class from Java's utility package to store multiple categories.

public class CategoryResponse {  // Defines a public class that serves as a response model for category-related API calls.

    private boolean success;  // A boolean variable to indicate if the API request was successful or not.

    private List<Category> categories;  // A list that holds multiple Category objects received from the API.

    public boolean isSuccess() {  // Getter method to access the success status from outside the class.
        return success;  // Returns the value of the success variable.
    }

    public List<Category> getCategories() {  // Getter method to retrieve the list of categories.
        return categories;  // Returns the list of categories.
    }

}
