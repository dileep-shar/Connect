package com.example.connect.models;

import java.util.ArrayList;

public class Post {
    String content;
    User createdBy;
    Long createdAt = 0L;
    String ID;
    ArrayList<String> likedBy;

    public Post() {

    }

    public Post(String cont, User cBy, Long createdAt, ArrayList<String> lby) {
        this.content = cont;
        this.createdBy = cBy;
        this.createdAt = createdAt;
        if (lby == null) this.likedBy = new ArrayList<>();
        else this.likedBy = lby;


    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getID() {
        return this.ID;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }
}
