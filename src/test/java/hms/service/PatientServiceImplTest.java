package hms.service;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.entity.Patient;
import hms.exception.PatientNotFoundException;
import hms.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest {
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientServiceImpl patientServiceImpl;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void savePatient_shouldReturnCreatedPatient() {
        PatientRequestDto patientRequestDto = new PatientRequestDto();
        patientRequestDto.setFirstName("<John>");
        patientRequestDto.setLastName("<Duran>");
        patientRequestDto.setPhone("055111222333");

        Patient savedPatient = new Patient();
        savedPatient.setId(1L);
        savedPatient.setFirstName("John");
        savedPatient.setLastName("Duran");
        savedPatient.setPhoneNumber("055111222333");

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        PatientResponseDto patientResponse = patientServiceImpl.createPatient(patientRequestDto);

        assertNotNull(patientResponse);
        assertEquals("John Duran",patientResponse.getFirstname());
        verify(patientRepository,times(1)).save(any(Patient.class));
    }
    @Test
    void getPatientById_whenNoPatient_shouldThrowException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class,()->patientServiceImpl.getPatientById(1L));
    }
    @Test
    void getPatientById_shouldReturnPatient() {
        Patient p1 = new Patient();
        p1.setId(1L);
        p1.setFirstName("John");
        p1.setLastName("Duran");
        p1.setPhoneNumber("055111222333");

        Patient p2 = new Patient();
        p2.setId(2L);
        p2.setFirstName("Jack");
        p2.setLastName("Doe");
        p2.setPhoneNumber("055444555666");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(p1));

        List<PatientResponseDto> responseList= patientServiceImpl.getAllPatients();

        assertEquals(2,responseList.size());
        verify(patientRepository,times(1)).findAll();
    }
    @Test
    void deletePatientById_whenNoPatient_shouldThrowException() {
        doNothing().when(patientRepository).deleteById(1L);
        patientServiceImpl.deletePatient(1L);
        verify(patientRepository,times(1)).deleteById(1L);
    }
}
