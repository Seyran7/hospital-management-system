package hms.service;

import hms.dto.PatientRequestDto;
import hms.dto.PatientRequestSearchDto;
import hms.dto.PatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto requestDto);
    PatientResponseDto updatePatient(Long id, PatientRequestDto requestDto);
    void deletePatient(Long id);
    PatientResponseDto getPatientById(Long id);
    List<PatientResponseDto> getAllPatients();
    Page<PatientResponseDto> searchPatients(String g ,String firstName, String lastName, String email,
                                            String phone,Pageable pageable);
    Page<PatientResponseDto> advancedSearch(
            PatientRequestSearchDto request,
            Pageable pageable
    );

}
