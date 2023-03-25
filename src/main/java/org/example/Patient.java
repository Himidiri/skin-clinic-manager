package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Patient extends Person {
    // Instance variables
    private int patientId;
    private static List<Patient> patients = new ArrayList<>();

    // Constructor
    public Patient(int patientId, String name, String surname, Calendar dob, String mobileNumber) {
        super(name, surname, dob, mobileNumber);
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public static List<Patient> getPatients() {
        return patients;
    }

    public static void setPatients(List<Patient> patients) {
        Patient.patients = patients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId == patient.patientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }

    public static void savePatientsToFile(String filePath) {
        // Create a StringBuilder to store the data
        StringBuilder sb = new StringBuilder();
        // Add the headers to the StringBuilder
        sb.append("Patient ID,Name,Surname,Date of Birth,Mobile Number\n");
        // Create a date formatter for the date of birth
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        // Loop through the patients and add them to the StringBuilder
        for (Patient patient : Patient.getPatients()) {
            sb.append(patient.getPatientId()).append(",")
                    .append(patient.getName()).append(",")
                    .append(patient.getSurname()).append(",")
                    .append(df.format(patient.getDob().getTime())).append(",")
                    .append(patient.getMobileNumber()).append("\n");
        }
        // Convert the StringBuilder to a String and write it to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
