package com.sg.shopzy;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("status")  // Matches JSON key "status"
    private String status;

    @SerializedName("message") // Matches JSON key "message"
    private String message;

    @SerializedName("user_id") // 🔥 Added userId field (if returned by API)
    private String userId;

    // ✅ Getter methods
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    // ✅ Check if registration was successful
    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }

    // ✅ For debugging purposes
    @Override
    public String toString() {
        return "RegisterResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
