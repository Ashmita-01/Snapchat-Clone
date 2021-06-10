package com.example.snapy;

public class NewUserInfo {

    private String id;
    private String fullName;
    private String imageURL;

    public NewUserInfo(String id,String fullName,String imageURL){
        this.id = id;
        this.fullName = fullName;
        this.imageURL = imageURL;
    }

    public NewUserInfo(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
