package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.infrastructure.persistence.entity.TestDriveRequestEntity;

import java.util.ArrayList;

public final class TestDriveRequestEntitySpecifications {
    private TestDriveRequestEntitySpecifications() {
    }

    public static Specification<TestDriveRequestEntity> byQuery(TestDriveRequestQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("removed")));

            if (query.getClientId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("client").get("id"), query.getClientId().getId()));
            }
            if (query.getCarId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("car").get("id"), query.getCarId().getId()));
            }
            if (query.getFromDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scheduledTime"), query.getFromDate()));
            }
            if (query.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scheduledTime"), query.getToDate()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
