package hms.mapper;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.entity.Patient;

public class PatientMapper {
    private PatientMapper() {
    }
    public static Patient toEntity(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientRequestDto.getFirstName());
        patient.setLastName(patientRequestDto.getLastName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setPhoneNumber(patientRequestDto.getPhone());

        return patient;
    }
    public static PatientResponseDto toResponse(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setId(patient.getId());
        patientResponseDto.setFirstname(patient.getFirstName());
        patientResponseDto.setLastname(patient.getLastName());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setPhoneNumber(patient.getPhoneNumber());
        return patientResponseDto;
    }
}
