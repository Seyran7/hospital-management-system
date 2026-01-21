package hms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.exception.PatientNotFoundException;
import hms.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPatient_shouldReturn201() throws Exception {
        PatientRequestDto patientRequestDto = new PatientRequestDto();
        patientRequestDto.setFirstName("John");
        patientRequestDto.setLastName("Duran");
        patientRequestDto.setPhone("055111222333");

        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setFirstname("John");
        patientResponseDto.setLastname("Duran");

        when(patientService.createPatient(any(PatientRequestDto.class))).thenReturn(patientResponseDto);

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value("John"));
    }
    @Test
    void shouldReturn400_whenValidationFail() throws Exception {
        PatientRequestDto patientRequestDto = new PatientRequestDto();

        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors.firstName").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
    @Test
    void shouldReturn404_whenPatientNotFound() throws Exception {

        when(patientService.getPatientById(99L))
                .thenThrow(new PatientNotFoundException("Patient not found"));
        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"))
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.timestamp").exists());

    }
}
