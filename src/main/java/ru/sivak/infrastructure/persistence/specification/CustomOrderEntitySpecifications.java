package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.infrastructure.persistence.entity.CustomOrderEntity;
import ru.sivak.infrastructure.repositories.jpa.JpaDomainMapper;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class CustomOrderEntitySpecifications {
    private CustomOrderEntitySpecifications() {
    }

    public static Specification<CustomOrderEntity> byQuery(CustomOrderQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("removed")));

            if (query.getClientId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("client").get("id"), query.getClientId().getId()));
            }
            if (query.getManagerId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("manager").get("id"), query.getManagerId().getId()));
            }
            if (query.getStateType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("state"), JpaDomainMapper.customStateCode(query.getStateType())));
            }

            Expression<BigDecimal> price = root.get("price").as(BigDecimal.class);
            if (query.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(price, query.getMinPrice().getAmount()));
            }
            if (query.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(price, query.getMaxPrice().getAmount()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}

