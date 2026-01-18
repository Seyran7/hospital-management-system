package hms.service;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.entity.Patient;
import hms.exception.PatientNotFoundException;
import hms.mapper.PatientMapper;
import hms.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto requestDto) {
      Patient patient = PatientMapper.toEntity(requestDto);
      Patient saved = patientRepository.save(patient);
      return PatientMapper.toResponse(saved);

    }
    @Override
    public PatientResponseDto updatePatient(Long id,PatientRequestDto requestDto) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException(id));
        existingPatient.setFirstName(requestDto.getFirstName());
        existingPatient.setLastName(requestDto.getLastName());
        existingPatient.setEmail(requestDto.getEmail());

        Patient updatedPatient = patientRepository.save(existingPatient);

        return PatientMapper.toResponse(updatedPatient);
    }
    @Override
    public void deletePatient(Long id) {
        if(patientRepository.existsById(id)) {
            throw new PatientNotFoundException(id);
        }

    }
    @Override
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException(id));
        return PatientMapper.toResponse(patient);
    }

    @Override
    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toResponse)
                .collect(Collectors.toList());
    }

}
