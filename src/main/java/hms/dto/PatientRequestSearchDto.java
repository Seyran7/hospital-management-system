package hms.dto;

import lombok.Data;

@Data
public class PatientRequestSearchDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private int page=0;
    private int size=10;

    private String sortBy="id";
    private String direction="asc";
}
