package hms.service;

import hms.entity.Patient;

import java.util.List;

public interface PatientService {
    Patient createPatient(Patient patient);
    Patient updatePatient(Patient patient);
    void deletePatient(Long id);
    Patient getPatientById(Long id);
    List<Patient> getAllPatients();
}
