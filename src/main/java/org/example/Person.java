package org.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Super class Person
public class Person {
    // Instance variables
    private String name;
    private String surname;
    private Calendar dob;
    private String mobileNumber;

    static List<Patient> patients = new ArrayList<>();

    // Constructor
    public Person(String name, String surname, Calendar dob, String mobileNumber) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.mobileNumber = mobileNumber;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}