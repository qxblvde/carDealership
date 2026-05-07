package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.CarQuery;
import ru.sivak.infrastructure.persistence.entity.CarEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class CarEntitySpecifications {
    private CarEntitySpecifications() {
    }

    public static Specification<CarEntity> byQuery(CarQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("removed")));

            if (null != query.getBrandName()) {
                predicates.add(criteriaBuilder.equal(root.join("brand").get("brandName"), query.getBrandName().getName()));
            }
            if (null != query.getModelName()) {
                predicates.add(criteriaBuilder.equal(root.join("model").get("modelName"), query.getModelName().getName()));
            }
            if (null != query.getBodyType()) {
                predicates.add(criteriaBuilder.equal(root.join("body").get("bodyType"), query.getBodyType().getType()));
            }
            if (null != query.getColor()) {
                predicates.add(criteriaBuilder.equal(root.join("color").get("colorValue"), query.getColor().getColor()));
            }
            if (null != query.getDriveType()) {
                predicates.add(criteriaBuilder.equal(root.join("drive").get("driveType"), query.getDriveType().getType()));
            }
            if (null != query.getEnginePower()) {
                predicates.add(criteriaBuilder.equal(root.join("engine").get("enginePower"), query.getEnginePower().getPower()));
            }
            if (null != query.getEngineVolume()) {
                predicates.add(criteriaBuilder.equal(root.join("engine").get("engineVolume"), query.getEngineVolume().getVolume()));
            }
            if (null != query.getFuelType()) {
                predicates.add(criteriaBuilder.equal(root.join("fuel").get("fuelType"), query.getFuelType().getType()));
            }
            if (null != query.getTransmissionType()) {
                predicates.add(criteriaBuilder.equal(root.join("transmission").get("transmissionType"), query.getTransmissionType().getType()));
            }

            Expression<BigDecimal> price = root.get("price").as(BigDecimal.class);
            if (null != query.getMinPrice()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(price, query.getMinPrice().getAmount()));
            }
            if (null != query.getMaxPrice()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(price, query.getMaxPrice().getAmount()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
