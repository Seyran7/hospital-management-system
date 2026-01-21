package hms.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response ")
public class ErrorResponse {
    @Schema(example = "400")
    private int status;
    @Schema(example = "Validation failed")
    private String message;

    @Schema(description = "Validation errors by field name",
    example = "{firstName:must not be blank,phone:size must be between 10 and 13}")

    private Map<String,String> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2026-01-21T20:27:00")
    private LocalDateTime timestamp;
}
