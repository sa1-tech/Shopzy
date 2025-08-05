package com.sg.shopzy;

import java.util.Map;

public class ApiResponse {
    private boolean success;
    private String message;
    private Map<String, String> user;

    // ✅ Getter for success
    public boolean isSuccess() {
        return success;
    }

    // ✅ Getter for message with a default fallback
    public String getMessage() {
        return message != null ? message : "No message available";
    }

    // ✅ Getter for user map
    public Map<String, String> getUser() {
        return user;
    }

    // ⭐ Enhanced: Get userId from user map (Handles variations in key naming)
    public String getUserId() {
        if (user != null) {
            return user.containsKey("id") ? user.get("id") : user.get("user_id");
        }
        return null;
    }

    // 🆕 New: Get userName from user map
    public String getUserName() {
        return user != null ? user.getOrDefault("name", "N/A") : null;
    }

    // 🆕 New: Get userEmail from user map
    public String getUserEmail() {
        return user != null ? user.getOrDefault("email", "N/A") : null;
    }

    // 🆕 New: Get userPhone from user map
    public String getUserPhone() {
        return user != null ? user.getOrDefault("phoneno", "N/A") : null;
    }

    // 🆕 New: Get userAddress from user map
    public String getUserAddress() {
        return user != null ? user.getOrDefault("address", "N/A") : null;
    }
}
