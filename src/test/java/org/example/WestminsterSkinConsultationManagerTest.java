package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WestminsterSkinConsultationManagerTest {
    private WestminsterSkinConsultationManager manager;
    private Doctor doctor1;
    private Doctor doctor2;
    private Doctor doctor3;
    private Doctor doctor4;

    @Before
    public void setUp() {
        // Create an instance of the WestminsterSkinConsultationManager class
        manager = new WestminsterSkinConsultationManager();
        // Create a SimpleDateFormat object to parse the date strings
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Create two instances of the Doctor class
        Calendar dob1 = Calendar.getInstance();
        try {
            dob1.setTime(sdf.parse("17/04/2001"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar availableDate1 = Calendar.getInstance();
        try {
            availableDate1.setTime(sdf.parse("06/01/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doctor1 = new Doctor(20201021, "Himidiri", "Pushpakumara", dob1, "Cosmetic Dermatology", "0763689998", availableDate1);
        Calendar dob2 = Calendar.getInstance();
        try {
            dob2.setTime(sdf.parse("06/09/1999"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar availableDate2 = Calendar.getInstance();
        try {
            availableDate2.setTime(sdf.parse("08/01/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doctor2 = new Doctor(20181010, "Kasun", "Rajitha", dob2, "Medical Dermatology", "07712345679", availableDate2);
        Calendar dob3 = Calendar.getInstance();
        try {
            dob3.setTime(sdf.parse("29/01/2000"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar availableDate3 = Calendar.getInstance();
        try {
            availableDate3.setTime(sdf.parse("06/01/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doctor3 = new Doctor(20191122, "Lakmali", "De Seram", dob3, "Medical Dermatology", "0716147885", availableDate3);
        Calendar dob4 = Calendar.getInstance();
        try {
            dob4.setTime(sdf.parse("10/10/2002"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar availableDate4 = Calendar.getInstance();
        try {
            availableDate4.setTime(sdf.parse("07/01/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doctor4 = new Doctor(20210100, "Aman", "Rummy", dob4, "Cosmetic Dermatology", "07012345679", availableDate4);
    }

    @Test
    public void testAddDoctor() {
        // Add the first doctor to the list of doctors
        manager.addDoctor(doctor1);
        // Check that the list of doctors contains the first doctor
        Doctor[] doctors = manager.getDoctors();
        assertTrue(Arrays.asList(doctors).contains(doctor1));
        // Check that the list of doctors does not contain the second doctor
        assertFalse(Arrays.asList(doctors).contains(doctor2));
    }

    @Test
    public void testRemoveDoctor() {
        // Add the first doctor to the list of doctors
        manager.addDoctor(doctor1);
        // Remove the first doctor from the list of doctors
        manager.deleteDoctor(doctor1.getMedicalLicenseNumber(), doctor1.getMedicalLicenseNumber());
        // Check that the list of doctors does not contain the first doctor
        Doctor[] doctors = manager.getDoctors();
        assertFalse(Arrays.asList(doctors).contains(doctor1));
        // Check that the list of doctors does not contain the second doctor
        assertFalse(Arrays.asList(doctors).contains(doctor2));
    }

    @Test
    public void testGetDoctorsAlphabeticalOrder() {
        // Add some doctors to the list of doctors
        manager.addDoctor(doctor3);
        manager.addDoctor(doctor1);
        manager.addDoctor(doctor2);
        manager.addDoctor(doctor4);

        // Get the list of doctors in alphabetical order
        List<Doctor> doctors = manager.getDoctorsAlphabeticalOrder();

        // Check that the list of doctors is in alphabetical order
        assertEquals(doctor3, doctors.get(0));
        assertEquals(doctor1, doctors.get(1));
        assertEquals(doctor2, doctors.get(2));
        assertEquals(doctor4, doctors.get(3));
    }

    @Test
    public void testSaveDoctorsInformationToFile() throws IOException {
        // Create an array of doctors
        Doctor[] doctors = {
                new Doctor(20201021, "Himidiri", "Pushpakumara",
                        Calendar.getInstance(), "Cosmetic Dermatology", "0763689998",
                        Calendar.getInstance()),
                new Doctor(20181010, "Kasun", "Rajitha",
                        Calendar.getInstance(), "Medical Dermatology", "07712345679",
                        Calendar.getInstance()),
                new Doctor(20191122, "Lakmali", "De Seram",
                        Calendar.getInstance(), "Medical Dermatology", "0716147885",
                        Calendar.getInstance())
        };

        // Save the information of the doctors to the file
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.saveDoctorsInformationToFile(doctors, 3);

        // Read the contents of the file into a list
        List<String> lines = Files.readAllLines(Paths.get("doctors.txt"));

        // Create a list of the expected contents of the file
        List<String> expected = Arrays.asList(
                "3",
                "20201021", "Himidiri", "Pushpakumara", "17-04-2001", "Cosmetic Dermatology", "0763689998", "06-01-2023",
                "20181010", "Kasun", "Rajitha", "06-09-1999", "Medical Dermatology", "07712345679", "08-01-2023",
                "20191122", "Lakmali", "De Seram", "29-01-2000", "Medical Dermatology", "0716147885", "06-01-2023");
        // Check that the contents of the file are as expected
        assertEquals(expected, lines);
    }

        @Test
    public void testLoadDoctorsInformationFromFile() throws IOException, ParseException {
        // Create an array of doctors to save to the file
        Doctor[] doctors = {doctor1, doctor2, doctor3};
        // Create a File object to represent the file to write to
        File file = new File("test.txt");
        // Save the information of the doctors to the file
        manager.saveDoctorsInformationToFile(doctors, 3);
        // Load the information of the doctors from the file
        Doctor[] loadedDoctors = manager.loadDoctorsInformationFromFile();
        // Check that the loaded doctors array has the same length as the original array
        assertEquals(doctors.length, loadedDoctors.length);
        // Check that the elements of the loaded doctors array are the same as the original array
        for (int i = 0; i < doctors.length; i++) {
            assertEquals(doctors[i].getMedicalLicenseNumber(), loadedDoctors[i].getMedicalLicenseNumber());
            assertEquals(doctors[i].getName(), loadedDoctors[i].getName());
            assertEquals(doctors[i].getSurname(), loadedDoctors[i].getSurname());
            assertEquals(doctors[i].getDob(), loadedDoctors[i].getDob());
            assertEquals(doctors[i].getSpecialisation(), loadedDoctors[i].getSpecialisation());
            assertEquals(doctors[i].getMobileNumber(), loadedDoctors[i].getMobileNumber());
            assertEquals(doctors[i].getAvailableDate(), loadedDoctors[i].getAvailableDate());
        }
    }
}

