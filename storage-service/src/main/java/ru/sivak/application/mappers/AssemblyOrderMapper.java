package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.AssemblyOrderDto;
import ru.sivak.infrastructure.persistence.entity.AssemblyOrderEntity;

@Mapper(componentModel = "spring")
public interface AssemblyOrderMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "sourceOrderId", source = "entity.sourceOrderId")
    @Mapping(target = "sourceOrderType", source = "entity.sourceOrderType")
    @Mapping(target = "carId", source = "entity.carId")
    @Mapping(target = "warehouseEmployeeId", source = "entity.warehouseEmployeeId")
    @Mapping(target = "requiredComponentIds", source = "entity.requiredComponentIds")
    @Mapping(target = "status", expression = "java(ru.sivak.domain.entities.AssemblyOrder.Status.valueOf(entity.getStatus()))")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    AssemblyOrderDto mapFromEntity(AssemblyOrderEntity entity);
}
