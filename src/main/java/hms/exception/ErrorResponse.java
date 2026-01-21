package hms.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Standard error response ")
public class ErrorResponse {
    @Schema(example = "404")
    private int status;
    @Schema(example = "Patient is not found with id x")
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2026-01-21T20:27:00")
    private LocalDateTime timestamp;
}
