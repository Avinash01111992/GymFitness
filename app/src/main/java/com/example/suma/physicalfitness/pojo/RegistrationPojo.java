package com.example.suma.physicalfitness.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by avinash on 28/3/18.
 */

@IgnoreExtraProperties
public class RegistrationPojo implements Serializable {

    public String name;
    public String email;
    public String pw;
    public String mobileNumber;
    public String address;
    public String ro;



    public RegistrationPojo() {

    }


    public RegistrationPojo(String name, String role, String email, String pw, String mobileNum,String adrs)
    {
        this.name = name;
        this.email =email;
        this.mobileNumber = mobileNum;
        this.pw = pw;
        this.address = adrs;
        this.ro = role;

    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Default constructor required for calls to
    // DataSnapshot.getValue(RegistrationPojo.class)


    public RegistrationPojo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRo() {
        return ro;
    }

    public void setRo(String ro) {
        this.ro = ro;
    }



}
