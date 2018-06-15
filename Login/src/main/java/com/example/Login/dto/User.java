package com.example.Login.dto;

public class User {
    private String userName;
    private String password;
    private String address;
    private String emailId;
    private String contactNumber;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserName() {

        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
