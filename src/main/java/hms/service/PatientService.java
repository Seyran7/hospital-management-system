package hms.service;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto requestDto);
    PatientResponseDto updatePatient(Long id, PatientRequestDto requestDto);
    void deletePatient(Long id);
    PatientResponseDto getPatientById(Long id);
    List<PatientResponseDto> getAllPatients();
}
