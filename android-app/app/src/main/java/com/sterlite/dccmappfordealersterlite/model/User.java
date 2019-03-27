package com.sterlite.dccmappfordealersterlite.model;

/**
 * Created by etech-10 on 30/8/18.
 */

public class User {
    private String fname,lname,email,mobileNo,gender,password;

    public User() {
    }

    public User(String fname, String lname, String email, String mobileNo, String gender, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
