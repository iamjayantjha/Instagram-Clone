package com.instagramclone.Model;

public class User {
    private String id;
    private String username;
    private String name;
    private String imageURL;
    private String bio;

    public User(String id, String username, String name, String imageURL, String bio) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imageURL = imageURL;
        this.bio = bio;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
