package com.sg.shopzy;

public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String phoneno;  // Added phone field
    private String address;

    public RegisterRequest(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneno = phoneno;  // Assigning phone field
        this.address = address;
    }

    // Getters (if needed)
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phoneno;
    }

    public String getAddress() {
        return address;
    }
}
