package hms.specification;

import hms.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {
    public static Specification<Patient> hasName(String name) {
        return((root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern)
            );
        });
    }
}
