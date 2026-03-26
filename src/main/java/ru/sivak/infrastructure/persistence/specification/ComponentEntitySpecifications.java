package ru.sivak.infrastructure.persistence.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.infrastructure.persistence.entity.BodyEntity;
import ru.sivak.infrastructure.persistence.entity.BrandEntity;
import ru.sivak.infrastructure.persistence.entity.ColorEntity;
import ru.sivak.infrastructure.persistence.entity.DriveEntity;
import ru.sivak.infrastructure.persistence.entity.EngineEntity;
import ru.sivak.infrastructure.persistence.entity.FuelEntity;
import ru.sivak.infrastructure.persistence.entity.InteriorEntity;
import ru.sivak.infrastructure.persistence.entity.SteeringEntity;
import ru.sivak.infrastructure.persistence.entity.TransmissionEntity;
import ru.sivak.infrastructure.persistence.entity.WheelEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ComponentEntitySpecifications {
    private ComponentEntitySpecifications() {
    }

    public static Specification<BodyEntity> bodyByQuery(BodyQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getType()) {
                predicates.add(criteriaBuilder.equal(root.get("bodyType"), query.getType().getType()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<BrandEntity> brandByQuery(BrandQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getBrandName()) {
                predicates.add(criteriaBuilder.equal(root.get("brandName"), query.getBrandName().getName()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<ColorEntity> colorByQuery(ColorQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getColor()) {
                predicates.add(criteriaBuilder.equal(root.get("colorValue"), query.getColor().getColor()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<DriveEntity> driveByQuery(DriveQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getDriveType()) {
                predicates.add(criteriaBuilder.equal(root.get("driveType"), query.getDriveType().getType()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<EngineEntity> engineByQuery(EngineQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getPower()) {
                predicates.add(criteriaBuilder.equal(root.get("enginePower"), query.getPower().getPower()));
            }
            if (null != query.getVolume()) {
                predicates.add(criteriaBuilder.equal(root.get("engineVolume"), query.getVolume().getVolume()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<FuelEntity> fuelByQuery(FuelQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getFuelType()) {
                predicates.add(criteriaBuilder.equal(root.get("fuelType"), query.getFuelType().getType()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<TransmissionEntity> transmissionByQuery(TransmissionQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = baseComponentPredicates(
                    root, criteriaQuery, criteriaBuilder,
                    query.getComponentName() == null ? null : query.getComponentName().getName(),
                    query.getModelName() == null ? null : query.getModelName().getName(),
                    query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                    query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
            );
            if (null != query.getType()) {
                predicates.add(criteriaBuilder.equal(root.get("transmissionType"), query.getType().getType()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<SteeringEntity> steeringByQuery(SteeringQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                baseComponentPredicates(
                        root, criteriaQuery, criteriaBuilder,
                        query.getComponentName() == null ? null : query.getComponentName().getName(),
                        query.getModelName() == null ? null : query.getModelName().getName(),
                        query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                        query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
                ).toArray(Predicate[]::new)
        );
    }

    public static Specification<WheelEntity> wheelByQuery(WheelQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                baseComponentPredicates(
                        root, criteriaQuery, criteriaBuilder,
                        query.getComponentName() == null ? null : query.getComponentName().getName(),
                        query.getModelName() == null ? null : query.getModelName().getName(),
                        query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                        query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
                ).toArray(Predicate[]::new)
        );
    }

    public static Specification<InteriorEntity> interiorByQuery(InteriorQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                baseComponentPredicates(
                        root, criteriaQuery, criteriaBuilder,
                        query.getComponentName() == null ? null : query.getComponentName().getName(),
                        query.getModelName() == null ? null : query.getModelName().getName(),
                        query.getMinPrice() == null ? null : query.getMinPrice().getAmount(),
                        query.getMaxPrice() == null ? null : query.getMaxPrice().getAmount()
                ).toArray(Predicate[]::new)
        );
    }

    private static <T> List<Predicate> baseComponentPredicates(
            Root<T> root,
            CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder,
            String componentName,
            String modelName,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        if (criteriaQuery != null) {
            criteriaQuery.distinct(true);
        }

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isFalse(root.get("removed")));

        if (componentName != null) {
            predicates.add(criteriaBuilder.equal(root.get("componentName"), componentName));
        }
        if (modelName != null) {
            predicates.add(criteriaBuilder.equal(root.join("suitableModels").get("modelName"), modelName));
        }

        Expression<BigDecimal> price = root.get("price").as(BigDecimal.class);
        if (minPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(price, minPrice));
        }
        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(price, maxPrice));
        }

        return predicates;
    }
}
