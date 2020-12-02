package com.example.sqlite.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String first;
    private String last;
    private String address;
    private Code code;
    private String phone;
    private String email;
    private String dates;


    //empty constructor
    public Contact() {

    }

    //Constructor ilma ID
    public Contact(String first, String last, String address, Code code, String phone, String email, String dates) {
        this.first = first;
        this.last = last;
        this.address = address;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.dates = dates;
    }

    //Constructor with all
    public Contact(int id, String first, String last, String address, Code code, String phone, String email, String dates) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.address = address;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.dates = dates;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    @Override
    @NonNull
    public String toString(){
        return first + " " + last;
    }
}
