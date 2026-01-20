package hms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hms.dto.PatientRequestDto;
import hms.dto.PatientResponseDto;
import hms.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
}
