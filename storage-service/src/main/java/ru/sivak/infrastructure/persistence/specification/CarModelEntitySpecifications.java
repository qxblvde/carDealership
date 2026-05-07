package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.infrastructure.persistence.entity.CarModelEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class CarModelEntitySpecifications {
    private CarModelEntitySpecifications() {
    }

    public static Specification<CarModelEntity> byQuery(ModelQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("removed")));

            if (null != query.getModelName()) {
                predicates.add(criteriaBuilder.equal(root.get("modelName"), query.getModelName().getName()));
            }
            if (null != query.getComponentName()) {
                predicates.add(criteriaBuilder.equal(root.get("componentName"), query.getComponentName().getName()));
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
