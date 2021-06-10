package com.example.snapy;

public class User {

    public String fullName,dob,email;

    public User(){

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String fullName, String dob, String email){
        this.fullName = fullName;
        this.dob = dob;
        this.email = email;
    }
}
