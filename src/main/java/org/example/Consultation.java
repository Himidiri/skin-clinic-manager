package org.example;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Consultation {
    // Instance variables
    private Calendar consultationDate; // I assume that doctor available date as consultation date
    private Calendar startTime; // Start time for the consultation
    private Calendar endTime; // End time for the consultation
    private double cost;
    private String notes;
    private int doctorMedicalLicenseNo;
    private int patientId;
    private static List<Consultation> consultations = new ArrayList<>();


    // Constructor that takes in a medical license number, patient id, consultation date, start and end time, and cost,note
    public Consultation(int doctorMedicalLicenseNo, int patientId, Calendar consultationDate, Calendar startTime, Calendar endTime, double cost, String notes) {
        this.doctorMedicalLicenseNo = doctorMedicalLicenseNo;
        this.patientId = patientId;
        this.consultationDate = consultationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.notes = notes;
    }

    public Calendar getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(Calendar consultationDate) {
        this.consultationDate = consultationDate;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDoctorMedicalLicenseNo() {
        return doctorMedicalLicenseNo;
    }

    public void setDoctorMedicalLicenseNo(int doctorMedicalLicenseNo) {
        this.doctorMedicalLicenseNo = doctorMedicalLicenseNo;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public static List<Consultation> getConsultations() {
        return consultations;
    }

    public static void setConsultations(List<Consultation> consultations) {
        Consultation.consultations = consultations;
    }

    public static boolean overlapsWithExistingConsultations(int doctorId, Calendar desiredStartTime, Calendar desiredEndTime) {
        // Get the list of consultations
        List<Consultation> consultations = Consultation.getConsultations();
        // Loop through the consultations and check if the desired start and end times overlap with the start and end times of any existing consultations for the specific doctor
        for (Consultation consultation : consultations) {
            if (consultation.getDoctorMedicalLicenseNo() == doctorId) {
                Calendar startTime = consultation.getStartTime();
                Calendar endTime = consultation.getEndTime();
                // Check if the desired start and end times overlap with the start and end times of the existing consultation
                if ((desiredStartTime.after(startTime) && desiredStartTime.before(endTime)) || (desiredEndTime.after(startTime) && desiredEndTime.before(endTime))) {
                    // The desired start and end times overlap with the start and end times of the existing consultation
                    return true;
                }
            }
        }
        // The desired start and end times do not overlap with the start and end times of any existing consultations for the specific doctor
        return false;
    }


    public static boolean hasPatientHadConsultation(int patientId) {
        // Get the list of consultations
        List<Consultation> consultations = Consultation.getConsultations();

        // Loop through the consultations and check if the patient has had a consultation before
        for (Consultation consultation : consultations) {
            if (consultation.getPatientId() == patientId) {
                // The patient has had a consultation before
                return true;
            }
        }

        // The patient has not had a consultation before
        return false;
    }

    public static double calculateCost(int patientId, Calendar startTime, Calendar endTime) {
        // Calculate the duration of the consultation in hours
        long durationInMilliseconds = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        double durationInHours = (double) durationInMilliseconds / TimeUnit.HOURS.toMillis(1);

        // Check if the patient has had a previous consultation
        boolean hasHadPreviousConsultation = hasPatientHadConsultation(patientId);
        if (hasHadPreviousConsultation) {
            // The patient has had a previous consultation, so use the normal rate of £25 per hour
            return durationInHours * 25;
        } else {
            // The patient has not had a previous consultation, so use the discounted rate of £15 per hour
            return durationInHours * 15;
        }
    }

    public static void saveConsultationsToFile(String fileName) {
        try {
            // Open the file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            // Write the header line to the file
            writer.write("Doctor MLicence No,Patient Id,Booking Date,Start Time,End Time,Cost,Notes");
            writer.newLine();
            // Get the list of consultations
            List<Consultation> consultations = Consultation.getConsultations();
            // Loop through the consultations and write each one to the file
            for (Consultation consultation : consultations) {
                // Format the date and time using a DateFormat
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String consultationDate = dateFormat.format(consultation.getConsultationDate().getTime());
                String startTime = timeFormat.format(consultation.getStartTime().getTime());
                String endTime = timeFormat.format(consultation.getEndTime().getTime());
                // Write the consultation information to the file
                writer.write(consultation.getDoctorMedicalLicenseNo() + "," + consultation.getPatientId() + "," + consultationDate + "," + startTime + "," + endTime + "," + consultation.getCost() + "," + consultation.getNotes());
                writer.newLine();
            }
            // Close the writer
            writer.close();
            // Show a success message
            JOptionPane.showMessageDialog(null, "Consultations successfully saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            // Show an error message
            JOptionPane.showMessageDialog(null, "Error saving consultations to file.");
        }
    }
}
