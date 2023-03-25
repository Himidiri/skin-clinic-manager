package org.example;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface SkinConsultationManager {

    void addDoctor(Doctor doctor);


    void deleteDoctor(int medicalLicenceNumber, int deleteDoctorMedicalLicenceNumber);

    List<Doctor> getDoctorsAlphabeticalOrder();

    void saveDoctorsInformationToFile(Doctor[] doctors, int numDoctors) throws IOException;

}
