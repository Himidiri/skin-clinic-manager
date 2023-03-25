package org.example;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    // Array to store the list of doctors
    private static Doctor[] doctors = new Doctor[10];
    // Number of doctors in the list
    private static int numDoctors = 0;

    public static Doctor[] getDoctors() {
        return doctors;
    }

    // Add a method to retrieve the number of doctors
    public static int getNumDoctors() {
        if (doctors == null) {
            return 0;
        } else {
            return doctors.length;
        }
    }

    @Override
    public void addDoctor(Doctor doctor) {
        // Check if the medical licence number is already in the list
        for (int i = 0; i < numDoctors; i++) {
            if (doctors[i].getMedicalLicenseNumber() == doctor.getMedicalLicenseNumber()) {
                System.out.println();
                System.out.println("Medical Licence Number Already In The System");
                return;
            }
        }

        // If the list is full, throw an exception
        if (numDoctors == doctors.length) {
            System.out.println();
            throw new IllegalStateException("Sorry! Can't Add the Doctor. Skin Consultation Centre is Full.");
        }
        // Add the doctor to the list
        doctors[numDoctors] = doctor;
        // Increment the number of doctors
        numDoctors++;

        // Print a success message
        System.out.println();
        System.out.println("Doctor Successfully Added To The System");
    }

    @Override
    public void deleteDoctor(int medicalLicenceNumber, int deleteDoctorMedicalLicenceNumber) {
        // Find the index of the doctor to delete
        int index = -1;
        for (int i = 0; i < numDoctors; i++) {
            if (doctors[i].getMedicalLicenseNumber() == medicalLicenceNumber) {
                index = i;
                break;
            }
        }
        // If the doctor is not found, throw an exception
        if (index == -1) {
            System.out.println();
            System.out.println("Doctor With Medical Licence Number " + medicalLicenceNumber + " Not In the System");
            return;
        }
        // Save a reference to the deleted doctor
        Doctor deletedDoctor = doctors[index];
        // Shift the elements in the list to the left to fill the gap
        for (int i = index; i < numDoctors - 1; i++) {
            doctors[i] = doctors[i + 1];
        }
        // Decrement the number of doctors
        numDoctors--;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        // Print the information of the deleted doctor and the total number of doctors in the centre
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Doctor's Medical Licence Number : " + deletedDoctor.getMedicalLicenseNumber());
        System.out.println("Doctor's First Name : " + deletedDoctor.getName());
        System.out.println("Doctor's Surname : " + deletedDoctor.getSurname());
        System.out.println("Doctor's Date Of Birth : " + sdf.format(deletedDoctor.getDob().getTime()));
        System.out.println("Doctor's Specialisation : " + deletedDoctor.getSpecialisation());
        System.out.println("Doctor's Mobile Number : " + deletedDoctor.getMobileNumber());
        System.out.println("Doctors Available Date : " + sdf.format(deletedDoctor.getAvailableDate().getTime()));
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("This Doctor Successfully Remove in the System");
        System.out.println();
        System.out.println();
        System.out.println("Total Number Of Doctors In The Centre : " + numDoctors);

        // pass the medicalLicenceNumberToDelete variable to the createDoctorInformationPanel method in the SkinConsultationCentreGUI class to delete the row in the table
        SkinConsultationCentreGUI.createDoctorInformationPanel(deleteDoctorMedicalLicenceNumber);
    }

    @Override
    public List<Doctor> getDoctorsAlphabeticalOrder() {
        // Create a list to store the doctors
        List<Doctor> doctorsList = new ArrayList<>();
        // Add the doctors from the array to the list
        for (int i = 0; i < numDoctors; i++) {
            doctorsList.add(doctors[i]);
        }
        // Sort the list alphabetically by doctor surname
        doctorsList.sort(Comparator.comparing(Doctor::getSurname));
        // Return the sorted list
        return doctorsList;
    }

    public static void main(String[] args) throws IOException{
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        // Create a scanner to read user input
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("----Welcome To The Skin Consultation Manager System----");
        System.out.println();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println();
            System.out.println("*****************************************************************");
            System.out.println("Information of all Doctors that have Already Saved In The File.");
            System.out.println("*****************************************************************");
            // Load the doctors' information from the file
            Doctor[] doctors = loadDoctorsInformationFromFile();
            // Print the information of each doctor
            for (Doctor doctor : doctors) {
                System.out.println("--------------------------------------------------");
                System.out.println("Doctor's Medical Licence Number : " + doctor.getMedicalLicenseNumber());
                System.out.println("Doctor's First Name : " + doctor.getName());
                System.out.println("Doctor's Surname : " + doctor.getSurname());
                System.out.println("Doctor's Date Of Birth : " + sdf.format(doctor.getDob().getTime()));
                System.out.println("Doctor's Specialisation : " + doctor.getSpecialisation());
                System.out.println("Doctor's Mobile Number : " + doctor.getMobileNumber());
                System.out.println("Doctor Available Date : " + sdf.format(doctor.getAvailableDate().getTime()));
                System.out.println("--------------------------------------------------");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        while (true) {
            // Print the menu options
            System.out.println();
            System.out.println("-----------Enter Your Choice-----------");
            System.out.println();
            System.out.println("""           
                    A : Add a new Doctor\s
                    D : Delete a Doctor\s
                    P : Print the list of the Doctors\s
                    S : Save in a file\s
                    L : Load Data From File\s
                    G : To Open GUI\s
                    E : To Exit the Program
                    """
            );
            System.out.println("---------------------------------------");
            System.out.println();
            System.out.print("Please Enter your Choice : ");
            // Read the user's selection
            String choice = input.nextLine().toUpperCase();
            // Process the command
            switch (choice) {
                case "A":
                    int medicalLicenceNumber;
                    try {

                        // Read the doctor's information
                        System.out.println();
                        System.out.print("Enter Doctor's First Name : ");
                        String name = input.nextLine();
                        // Validate the name input
                        while (!isValidName(name)) {
                            System.out.println();
                            System.out.println("Invalid Input. Please Enter String Values for the First Name : ");
                            name = input.nextLine();
                        }
                        System.out.println();
                        System.out.print("Enter Doctor's Surname : ");
                        String surname = input.nextLine();
                        // Validate the surname input
                        while (!isValidName(surname)) {
                            System.out.println();
                            System.out.println("Invalid Input. Please Enter String Values for the Surname : ");
                            surname = input.nextLine();
                        }
                        System.out.println();
                        System.out.print("Enter Doctor's Date Of Birth this format (dd/mm/yyyy) : ");
                        Calendar dob = parseDate(input.nextLine());
                        // Validate the date of birth input
                        while (dob == null) {
                            System.out.println();
                            System.out.print("Invalid Input. Please Enter valid Doctor's Date Of Birth this format (dd/mm/yyyy) : ");
                            dob = parseDate(input.nextLine());
                        }
                        System.out.println();
                        System.out.print("Enter Doctor's Mobile Number : ");
                        String mobileNumber = input.nextLine();
                        // Validate the mobile number input
                        while (!isValidMobileNumber(mobileNumber)) {
                            System.out.println();
                            System.out.println("Invalid Input. Please Enter the Mobile Number format like this (0763689998) or (+94763689998) : ");
                            mobileNumber = input.nextLine();
                        }
                        System.out.println();
                        System.out.print("Enter Doctor's Medical Licence Number : ");
                        medicalLicenceNumber = parseInt(input.nextLine());
                        // Validate the medical licence number input
                        while (medicalLicenceNumber == -1) {
                            System.out.println();
                            System.out.println("Invalid Input. Please Enter Integer values for the Medical Licence Number : ");
                            medicalLicenceNumber = parseInt(input.nextLine());
                        }
                        System.out.println();
                        System.out.print("Enter Doctor's Specialisation (Ex : Cosmetic Dermatology, Medical Dermatology, etc.) : ");
                        String specialisation = input.nextLine();
                        // Validate the specialisation input
                        while (!isValidSpecialisation(specialisation)) {
                            System.out.println();
                            System.out.println("Invalid Input. Please Enter String Values for the Specialisation : ");
                            specialisation = input.nextLine();
                        }
                        System.out.println();
                        System.out.print("Enter Doctor Available Date this format (dd/mm/yyyy) : ");
                        Calendar availableDate = parseDate(input.nextLine());
                        // Validate the date of birth input
                        while (availableDate == null) {
                            System.out.println();
                            System.out.print("Invalid Input. Please Enter valid Doctor Available Date this format (dd/mm/yyyy) : ");
                            availableDate = parseDate(input.nextLine());
                        }

                        // Create a new doctor object with the input information
                        Doctor doctor = new Doctor(medicalLicenceNumber, name, surname, dob, mobileNumber, specialisation, availableDate);
                        // Add the doctor to the consultation centre
                        manager.addDoctor(doctor);
                    }  catch (IllegalStateException e) {
                        // Print the error message to the console
                        System.out.println(e.getMessage());
                    }
                    break;
                case "D":
                    System.out.println();
                    System.out.print("Enter Doctor's Medical Licence Number You Need To Remove : ");
                    medicalLicenceNumber = parseInt(input.nextLine());
                    // Validate the medical licence number input
                    while (medicalLicenceNumber == -1) {
                        System.out.println();
                        System.out.println("Invalid Input. Please Enter Integer values for the Medical Licence Number : ");
                        medicalLicenceNumber = parseInt(input.nextLine());
                    }
                    // Delete the doctor from the consultation centre
                    manager.deleteDoctor(medicalLicenceNumber, medicalLicenceNumber);
                    break;
                case "P":
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    // Print the list of doctors in the consultation centre
                    System.out.println();
                    System.out.println("************************************************************");
                    System.out.println("All Store Doctor's Information In Surname Alphabetical Order");
                    System.out.println("************************************************************");
                    for (Doctor d : manager.getDoctorsAlphabeticalOrder()) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Doctor's Medical Licence Number : " + d.getMedicalLicenseNumber());
                        System.out.println("Doctor's First Name : " + d.getName());
                        System.out.println("Doctor's Surname : " + d.getSurname());
                        System.out.println("Doctor's Date Of Birth : " + sdf.format(d.getDob().getTime()));
                        System.out.println("Doctor's Specialisation : " + d.getSpecialisation());
                        System.out.println("Doctor's Mobile Number : " + d.getMobileNumber());
                        System.out.println("Doctor Available Date : " + sdf.format(d.getAvailableDate().getTime()));
                        System.out.println("--------------------------------------------------");
                    }
                    break;
                case "S":
                    // Save the data to a file
                    manager.saveDoctorsInformationToFile(manager.doctors, manager.numDoctors);
                    System.out.println();
                    System.out.println("Doctor's Information Successfully Saved to File.");
                    break;
                case "L":
                    // Load the data from file
                    try {
                        SimpleDateFormat load = new SimpleDateFormat("dd-MM-yyyy");
                        System.out.println();
                        System.out.println("*****************************************************************");
                        System.out.println("Information of all Doctors that have Already Saved In The File.");
                        System.out.println("*****************************************************************");
                        // Load the doctors' information from the file
                        Doctor[] doctors = loadDoctorsInformationFromFile();
                        // Print the information of each doctor
                        for (Doctor lDoctor : doctors) {
                            System.out.println("--------------------------------------------------");
                            System.out.println("Doctor's Medical Licence Number : " + lDoctor.getMedicalLicenseNumber());
                            System.out.println("Doctor's First Name : " + lDoctor.getName());
                            System.out.println("Doctor's Surname : " + lDoctor.getSurname());
                            System.out.println("Doctor's Date Of Birth : " + load.format(lDoctor.getDob().getTime()));
                            System.out.println("Doctor's Specialisation : " + lDoctor.getSpecialisation());
                            System.out.println("Doctor's Mobile Number : " + lDoctor.getMobileNumber());
                            System.out.println("Doctor Available Date : " + load.format(lDoctor.getAvailableDate().getTime()));
                            System.out.println("--------------------------------------------------");
                        }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "G":
                    // Open GUI
                    JFrame frame = SkinConsultationCentreGUI.createFrame();
                    SkinConsultationCentreGUI.viewGui(frame);
                    System.out.println();
                    System.out.println("GUI Successfully Open");
                    break;
                case "E":
                    // Exit the program
                    System.out.println();
                    System.out.println("Thank you for using this Skin Consultation Manager System.");
                    System.out.println("Have a nice day!");
                    input.close();
                    return;
                default:
                    // Invalid Input
                    System.out.println();
                    System.out.println("The input is invalid, Please Enter a Valid Input (Eg: A, D, P, S, L, G, Or E)");
                    break;
            }
        }
    }

    // Method to validate a name input
    private static boolean isValidName(String name) {
        // A name must not be empty and must contain only letters
        return name != null && name.matches("[a-zA-Z ]+");
    }

    // Method to parse a date input in the format dd/mm/yyyy
    private static Calendar parseDate(String date) {
        // Try to parse the date
        try {
            // Split the date into its parts
            String[] parts = date.split("/");
            // Create a new calendar object
            Calendar calendar = Calendar.getInstance();
            // Validate the month
            int month = Integer.parseInt(parts[1]);
            if (month < 1 || month > 12) {
                // Return null if the month is invalid
                return null;
            }
            // Validate the day
            int day = Integer.parseInt(parts[0]);
            int numDaysInMonth = 31; // Assume that the month has 31 days
            if (month == 2) { // If the month is February, set the number of days to 28 or 29
                numDaysInMonth = 28;
                int year = Integer.parseInt(parts[2]);
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    // If the year is a leap year, set the number of days to 29
                    numDaysInMonth = 29;
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                // If the month is April, June, September, or November, set the number of days to 30
                numDaysInMonth = 30;
            }
            if (day < 1 || day > numDaysInMonth) {
                // Return null if the day is invalid for the given month
                return null;
            }
            // Set the year, month, and day of the calendar
            calendar.set(Integer.parseInt(parts[2]), month - 1, day);
            // Return the calendar object
            return calendar;
        } catch (Exception e) {
            // Return null if the date is invalid
            return null;
        }
    }

    // Method to validate a mobile number input in the Sri Lankan format
    private static boolean isValidMobileNumber(String mobileNumber) {
        // A mobile number must have 9 digits and start with 07 or have 12 digits and start with +947
        return mobileNumber != null && (mobileNumber.matches("07[0-9]{8}") || mobileNumber.matches("\\+947[0-9]{8}"));
    }

    // Method to validate a specialisation input
    private static boolean isValidSpecialisation(String specialisation) {
        // A specialisation must not be empty and must contain only letters and spaces
        return specialisation != null && specialisation.matches("[a-zA-Z ]+");
    }

    // Method to parse an integer input
    private static int parseInt(String str) {
        // Try to parse the integer
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // Return -1 if the integer is invalid
            return -1;
        }
    }

    // Method to save the information of the doctors to a text file
    public void saveDoctorsInformationToFile(Doctor[] doctors, int numDoctors) throws IOException {
        // Create a File object to represent the file to write to
        File file = new File("doctors.txt");
        // Create a BufferedWriter object to write to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        // Write the number of doctors to the file
        writer.write(String.valueOf(numDoctors));
        writer.newLine();
        // Write the information of each doctor to the file
        for (int i = 0; i < numDoctors; i++) {
            writer.write(String.valueOf(doctors[i].getMedicalLicenseNumber()));
            writer.newLine();
            writer.write(doctors[i].getName());
            writer.newLine();
            writer.write(doctors[i].getSurname());
            writer.newLine();
            // Format the date of birth using the SimpleDateFormat class
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dobString = sdf.format(doctors[i].getDob().getTime());
            writer.write(dobString);
            writer.newLine();
            writer.write(doctors[i].getSpecialisation());
            writer.newLine();
            writer.write(doctors[i].getMobileNumber());
            writer.newLine();
            String availableDateString = sdf.format(doctors[i].getAvailableDate().getTime());
            writer.write(availableDateString);
            writer.newLine();
        }
        // Close the writer to save the changes
        writer.close();
    }

    // Method to read the information from the file and add the doctors to the list
    public static Doctor[] loadDoctorsInformationFromFile() throws IOException, ParseException {
        // Create a File object to represent the file to read from
        File file = new File("doctors.txt");
        // Create a BufferedReader object to read from the file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Read the number of doctors from the file
        int numDoctors = Integer.parseInt(reader.readLine());
        // Create an array to store the doctors
        Doctor[] doctors = new Doctor[numDoctors];
        // Read the information of each doctor from the file
        for (int i = 0; i < numDoctors; i++) {
            int medicalLicenceNumber = Integer.parseInt(reader.readLine());
            String name = reader.readLine();
            String surname = reader.readLine();
            // Parse the date of birth from the string read from the file
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar dob = Calendar.getInstance();
            dob.setTime(sdf.parse(reader.readLine()));
            String specialisation = reader.readLine();
            String mobileNumber = reader.readLine();
            Calendar availableDate = Calendar.getInstance();
            availableDate.setTime(sdf.parse(reader.readLine()));
            // Create a new Doctor object with the information read from the file
            Doctor doctor = new Doctor(medicalLicenceNumber,name,surname,dob,mobileNumber,specialisation,availableDate);
            // Add the doctor to the array
            doctors[i] = doctor;
        }
        // Close the reader
        reader.close();
        // Return the array of doctors
        return doctors;
    }

}


