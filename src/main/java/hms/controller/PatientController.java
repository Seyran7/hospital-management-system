package hms.controller;

import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.exception.ErrorResponse;
import hms.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @GetMapping("/{id}")
    @Operation(summary = "Get patient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description ="Patient found",content = @Content(schema=@Schema(implementation=PatientResponseDto.class))),
            @ApiResponse(responseCode = "404",description ="Patient not found",content = @Content(schema=@Schema(implementation= ErrorResponse.class))),
    })
    public  ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
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
