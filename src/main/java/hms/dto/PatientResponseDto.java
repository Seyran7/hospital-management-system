package hms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="Patient response payload")
public class PatientResponseDto {
    Long id;
    @Schema(example = "John")
    String Firstname;
    @Schema(example = "Michel")
    String Lastname;
    @Schema(example = "john@gmail.com")
    String Email;
    @Schema(example = "055444333222")
    String PhoneNumber;


}
