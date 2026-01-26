package hms.specification;

import hms.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {
    public static Specification<Patient> globalSearch(String search) {
        return((root, query, criteriaBuilder) -> {
            if (search == null || search.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),likePattern)

            );
        });
    }
    public static Specification<Patient> firstNameContains(String firstName) {
        return (root, query, criteriaBuilder) ->
                firstName==null|| firstName.isBlank()?criteriaBuilder.conjunction():criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%"+firstName.toLowerCase()+"%");
            }
    public static Specification<Patient> lastNameContains(String lastName) {
        return (root, query, criteriaBuilder) ->
                lastName==null||lastName.isBlank()?criteriaBuilder.conjunction()
                        :criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%"+lastName.toLowerCase()+"%");
    }
    public static Specification<Patient> emailContains(String email) {
        return (root, query, criteriaBuilder) ->
                email==null|| email.isBlank()?criteriaBuilder.conjunction()
                        :criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" +email.toLowerCase()+"%");
    }
    public static Specification<Patient> phoneContains(String phone) {
        return (root, query, criteriaBuilder) ->
                phone==null||phone.isBlank()? criteriaBuilder.conjunction()
                        :criteriaBuilder.like(root.get("phone"),"%" +phone+"%");
    }

}
