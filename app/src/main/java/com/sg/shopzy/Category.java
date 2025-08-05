package com.sg.shopzy;

public class Category {
    private int id;
    private String name;
    private String image; // API returns the image URL

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        // Replace 'localhost' with '10.0.2.2' for emulator access
        if (image != null && image.contains("localhost")) {
            return image.replace("localhost", " 10.135.241.21");
        }
        return image;
       /* return image; // Returns full URL of image*/
    }
}
