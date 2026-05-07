package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.infrastructure.persistence.entity.InStockOrderEntity;
import ru.sivak.infrastructure.repositories.jpa.JpaDomainMapper;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class InStockOrderEntitySpecifications {
    private InStockOrderEntitySpecifications() {
    }

    public static Specification<InStockOrderEntity> byQuery(InStockOrderQuery query) {
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
                predicates.add(criteriaBuilder.equal(root.get("state"), JpaDomainMapper.inStockStateCode(query.getStateType())));
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

