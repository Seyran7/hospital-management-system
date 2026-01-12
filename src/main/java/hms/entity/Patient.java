package hms.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
