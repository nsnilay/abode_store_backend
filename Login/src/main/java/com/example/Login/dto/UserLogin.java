package com.example.Login.dto;

public class UserLogin {
    private String email;
    private  String password;

    public String getEmailId() {
        return email;
    }

    public void setEmailId(String emailId) {
        this.email = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
