package org.example;

import java.util.Calendar;

// Subclass Doctor
public class Doctor extends Person {
    // Instance variables
    private int medicalLicenseNumber;
    private String specialisation;
    private Calendar availableDate;

    // Constructor
    public Doctor(int medicalLicenseNumber ,String name, String surname, Calendar dob, String mobileNumber, String specialisation, Calendar availableDate) {
        super(name,surname,dob,mobileNumber);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialisation = specialisation;
        this.availableDate=availableDate;
    }
    // Getters and setters
    public int getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(int medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public Calendar getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Calendar availableDate) {
        this.availableDate = availableDate;
    }
}