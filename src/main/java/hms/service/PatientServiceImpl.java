package hms.service;

import hms.dto.PatientRequestDto;
import hms.dto.PatientRequestSearchDto;
import hms.dto.PatientResponseDto;
import hms.entity.Patient;
import hms.exception.PatientNotFoundException;
import hms.mapper.PatientMapper;
import hms.repository.PatientRepository;
import hms.specification.PatientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Override
    public Page<PatientResponseDto> searchPatients(String g,String firstName,
                                                   String lastName,
                                                   String email,
                                                   String phone,
                                                   Pageable pageable) {

        Specification<Patient> specification;
                Specification.where(null);
        if(g != null&&!g.isBlank()) {
            specification=PatientSpecification.globalSearch(g);
        }else{
            specification=Specification
            .where(PatientSpecification.firstNameContains(firstName))
            .and(PatientSpecification.lastNameContains(lastName))
            .and(PatientSpecification.emailContains(email))
            .and(PatientSpecification.phoneContains(phone));
        }


        return patientRepository.findAll(specification,pageable).map(PatientMapper::toResponse);
    }
    @Override
    public Page<PatientResponseDto>advancedSearch(PatientRequestSearchDto request,Pageable pageable){
        Specification<Patient>specification=Specification
                .where(PatientSpecification.firstNameContains(request.getFirstName())
                .and(PatientSpecification.lastNameContains(request.getLastName()))
                .and(PatientSpecification.emailContains(request.getEmail()))
                .and(PatientSpecification.phoneContains(request.getPhone())));
        return patientRepository.findAll(specification,pageable).map(PatientMapper::toResponse);
    }

}
