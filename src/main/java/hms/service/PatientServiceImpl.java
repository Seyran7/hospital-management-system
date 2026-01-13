package hms.service;

import hms.entity.Patient;
import hms.exception.PatientNotFoundException;
import hms.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }
    @Override
    public Patient updatePatient(Patient patient) {
        Patient existingPatient = patientRepository.findById(patient.getId()).orElseThrow(()-> new PatientNotFoundException(patient.getId()));
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setEmail(patient.getEmail());

        return patientRepository.save(patient);
    }
    @Override
    public void deletePatient(Long id) {

    }
    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found with id:"+id));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

}
