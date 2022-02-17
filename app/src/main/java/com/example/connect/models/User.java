package com.example.connect.models;

public class User {
    String UserId = "";
    String userName = "";
    String imageUrl = "";
    public User() {

    }

    public User(String id, String name, String imageUrl) {
        this.UserId = id;
        this.userName = name;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
