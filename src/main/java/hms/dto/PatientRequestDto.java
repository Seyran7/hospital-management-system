package hms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Patient request payload")
public class PatientRequestDto {
    @NotBlank(message="First name is required")
    @Schema(example = "John")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Schema(example = "Michel")
    private String lastName;
    @Email(message = "Email is not valid")
    @Schema(example = "John@gmail.com")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Schema(example = "055444333222")
    private String phone;
}
