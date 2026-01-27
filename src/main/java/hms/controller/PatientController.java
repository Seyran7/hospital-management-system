package hms.controller;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.exception.ErrorResponse;
import hms.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Tag(name="Patient Controller", description ="Patient management APIs")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @PostMapping
    @Operation(
            summary = "Create new patient",
            description = "Creates new patients and returns patient details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Patient created successfully",content=@Content(schema =  @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "400",description = "validation error",content=@Content(schema =@Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto response =patientService.createPatient(patientRequestDto);

        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(summary="Get all Patients")
    @ApiResponse(responseCode = "200",description = "Patients retrieved successfully")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @GetMapping("/search")
    @Operation(
            summary="Search  patients",
            description="Global search by first name, last name, email or phone with pagination"

            )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description="Patients found",
                    content=@Content(mediaType = "application/json")
            )

    })
    public ResponseEntity<Page<PatientResponseDto>> searchPatients(
            @RequestParam(required = false)
            @Parameter(description = "Global search (first name, last name, email, phone)")
            String g,

            @RequestParam(defaultValue = "0")
            @Parameter(description = "Page number (starts from 0)")int page,

            @RequestParam(defaultValue = "10")
            @Parameter(description = "Page size")int size,

            @RequestParam(defaultValue = "id,asc")
            @Parameter(description = "Sorting format:field,asc/desc")
            String[] sort
    ){
        Sort.Direction direction = sort[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(patientService.searchPatients(g,pageable));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get patient by id",
            description = "Returns patient details if found, otherwise 404 error")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description ="Patient found",content = @Content(schema=@Schema(implementation=PatientResponseDto.class))),
            @ApiResponse(responseCode = "404",description ="Patient not found",content = @Content(schema=@Schema(implementation= ErrorResponse.class))),
    })
    public  ResponseEntity<PatientResponseDto> getPatientById(@Parameter(description = "Patient ID",example = "1",required = true) @PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary="Update patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description="Patient updated"),
            @ApiResponse(responseCode = "404",description="patient not found")
    })
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description="Patient deleted"),
            @ApiResponse(responseCode = "404",description="Patient not found")
    })
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
