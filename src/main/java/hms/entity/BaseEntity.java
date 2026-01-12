package hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
        modificationDate = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        modificationDate = LocalDateTime.now();
    }
}
