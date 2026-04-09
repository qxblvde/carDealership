package ru.sivak.integration.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sivak.application.dto.*;
import ru.sivak.application.services.*;
import ru.sivak.integration.rest.dto.ComponentRequests;
import ru.sivak.integration.rest.mapper.ComponentQueryMapper;
import ru.sivak.integration.rest.mapper.ComponentRequestMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/components")
@RequiredArgsConstructor
public class ComponentsController {
    private final IBodyService bodyService;
    private final IBrandService brandService;
    private final IColorService colorService;
    private final IDriveService driveService;
    private final IEngineService engineService;
    private final IFuelService fuelService;
    private final ITransmissionService transmissionService;
    private final ISteeringService steeringService;
    private final IWheelService wheelService;
    private final IInteriorService interiorService;
    private final IModelService modelService;
    private final ComponentRequestMapper componentRequestMapper;
    private final ComponentQueryMapper componentQueryMapper;

    @GetMapping("/bodies")
    public List<BodyDto> queryBodies(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return bodyService.query(componentQueryMapper.toBodyQuery(type, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/bodies/{id}")
    public BodyDto getBody(@PathVariable UUID id) {
        return bodyService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/bodies")
    public BodyDto saveBody(@RequestBody ComponentRequests.BodySaveRequest request) {
        return bodyService.create(componentRequestMapper.toCreateBody(request));
    }

    @PutMapping("/bodies/{id}")
    public BodyDto updateBody(@PathVariable UUID id, @RequestBody ComponentRequests.BodySaveRequest request) {
        return bodyService.update(componentRequestMapper.toUpdateBody(id, request));
    }

    @DeleteMapping("/bodies/{id}")
    public ResponseEntity<Void> deleteBody(@PathVariable UUID id) {
        bodyService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/brands")
    public List<BrandDto> queryBrands(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return brandService.query(componentQueryMapper.toBrandQuery(brandName, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/brands/{id}")
    public BrandDto getBrand(@PathVariable UUID id) {
        return brandService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/brands")
    public BrandDto saveBrand(@RequestBody ComponentRequests.BrandSaveRequest request) {
        return brandService.create(componentRequestMapper.toCreateBrand(request));
    }

    @PutMapping("/brands/{id}")
    public BrandDto updateBrand(@PathVariable UUID id, @RequestBody ComponentRequests.BrandSaveRequest request) {
        return brandService.update(componentRequestMapper.toUpdateBrand(id, request));
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colors")
    public List<ColorDto> queryColors(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return colorService.query(componentQueryMapper.toColorQuery(color, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/colors/{id}")
    public ColorDto getColor(@PathVariable UUID id) {
        return colorService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/colors")
    public ColorDto saveColor(@RequestBody ComponentRequests.ColorSaveRequest request) {
        return colorService.create(componentRequestMapper.toCreateColor(request));
    }

    @PutMapping("/colors/{id}")
    public ColorDto updateColor(@PathVariable UUID id, @RequestBody ComponentRequests.ColorSaveRequest request) {
        return colorService.update(componentRequestMapper.toUpdateColor(id, request));
    }

    @DeleteMapping("/colors/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable UUID id) {
        colorService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/drives")
    public List<DriveDto> queryDrives(
            @RequestParam(required = false) String driveType,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return driveService.query(componentQueryMapper.toDriveQuery(driveType, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/drives/{id}")
    public DriveDto getDrive(@PathVariable UUID id) {
        return driveService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/drives")
    public DriveDto saveDrive(@RequestBody ComponentRequests.DriveSaveRequest request) {
        return driveService.create(componentRequestMapper.toCreateDrive(request));
    }

    @PutMapping("/drives/{id}")
    public DriveDto updateDrive(@PathVariable UUID id, @RequestBody ComponentRequests.DriveSaveRequest request) {
        return driveService.update(componentRequestMapper.toUpdateDrive(id, request));
    }

    @DeleteMapping("/drives/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable UUID id) {
        driveService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/engines")
    public List<EngineDto> queryEngines(
            @RequestParam(required = false) Integer power,
            @RequestParam(required = false) Integer volume,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return engineService.query(componentQueryMapper.toEngineQuery(power, volume, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/engines/{id}")
    public EngineDto getEngine(@PathVariable UUID id) {
        return engineService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/engines")
    public EngineDto saveEngine(@RequestBody ComponentRequests.EngineSaveRequest request) {
        return engineService.create(componentRequestMapper.toCreateEngine(request));
    }

    @PutMapping("/engines/{id}")
    public EngineDto updateEngine(@PathVariable UUID id, @RequestBody ComponentRequests.EngineSaveRequest request) {
        return engineService.update(componentRequestMapper.toUpdateEngine(id, request));
    }

    @DeleteMapping("/engines/{id}")
    public ResponseEntity<Void> deleteEngine(@PathVariable UUID id) {
        engineService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fuels")
    public List<FuelDto> queryFuels(
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return fuelService.query(componentQueryMapper.toFuelQuery(fuelType, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/fuels/{id}")
    public FuelDto getFuel(@PathVariable UUID id) {
        return fuelService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/fuels")
    public FuelDto saveFuel(@RequestBody ComponentRequests.FuelSaveRequest request) {
        return fuelService.create(componentRequestMapper.toCreateFuel(request));
    }

    @PutMapping("/fuels/{id}")
    public FuelDto updateFuel(@PathVariable UUID id, @RequestBody ComponentRequests.FuelSaveRequest request) {
        return fuelService.update(componentRequestMapper.toUpdateFuel(id, request));
    }

    @DeleteMapping("/fuels/{id}")
    public ResponseEntity<Void> deleteFuel(@PathVariable UUID id) {
        fuelService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transmissions")
    public List<TransmissonDto> queryTransmissions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return transmissionService.query(componentQueryMapper.toTransmissionQuery(type, componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/transmissions/{id}")
    public TransmissonDto getTransmission(@PathVariable UUID id) {
        return transmissionService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/transmissions")
    public TransmissonDto saveTransmission(@RequestBody ComponentRequests.TransmissionSaveRequest request) {
        return transmissionService.create(componentRequestMapper.toCreateTransmission(request));
    }

    @PutMapping("/transmissions/{id}")
    public TransmissonDto updateTransmission(@PathVariable UUID id, @RequestBody ComponentRequests.TransmissionSaveRequest request) {
        return transmissionService.update(componentRequestMapper.toUpdateTransmission(id, request));
    }

    @DeleteMapping("/transmissions/{id}")
    public ResponseEntity<Void> deleteTransmission(@PathVariable UUID id) {
        transmissionService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/steerings")
    public List<SteeringDto> querySteerings(
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return steeringService.query(componentQueryMapper.toSteeringQuery(componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/steerings/{id}")
    public SteeringDto getSteering(@PathVariable UUID id) {
        return steeringService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/steerings")
    public SteeringDto saveSteering(@RequestBody ComponentRequests.SteeringSaveRequest request) {
        return steeringService.create(componentRequestMapper.toCreateSteering(request));
    }

    @PutMapping("/steerings/{id}")
    public SteeringDto updateSteering(@PathVariable UUID id, @RequestBody ComponentRequests.SteeringSaveRequest request) {
        return steeringService.update(componentRequestMapper.toUpdateSteering(id, request));
    }

    @DeleteMapping("/steerings/{id}")
    public ResponseEntity<Void> deleteSteering(@PathVariable UUID id) {
        steeringService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/wheels")
    public List<WheelDto> queryWheels(
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return wheelService.query(componentQueryMapper.toWheelQuery(componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/wheels/{id}")
    public WheelDto getWheel(@PathVariable UUID id) {
        return wheelService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/wheels")
    public WheelDto saveWheel(@RequestBody ComponentRequests.WheelSaveRequest request) {
        return wheelService.create(componentRequestMapper.toCreateWheel(request));
    }

    @PutMapping("/wheels/{id}")
    public WheelDto updateWheel(@PathVariable UUID id, @RequestBody ComponentRequests.WheelSaveRequest request) {
        return wheelService.update(componentRequestMapper.toUpdateWheel(id, request));
    }

    @DeleteMapping("/wheels/{id}")
    public ResponseEntity<Void> deleteWheel(@PathVariable UUID id) {
        wheelService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/interiors")
    public List<InteriorDto> queryInteriors(
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return interiorService.query(componentQueryMapper.toInteriorQuery(componentName, modelName, minPrice, maxPrice));
    }

    @GetMapping("/interiors/{id}")
    public InteriorDto getInterior(@PathVariable UUID id) {
        return interiorService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/interiors")
    public InteriorDto saveInterior(@RequestBody ComponentRequests.InteriorSaveRequest request) {
        return interiorService.create(componentRequestMapper.toCreateInterior(request));
    }

    @PutMapping("/interiors/{id}")
    public InteriorDto updateInterior(@PathVariable UUID id, @RequestBody ComponentRequests.InteriorSaveRequest request) {
        return interiorService.update(componentRequestMapper.toUpdateInterior(id, request));
    }

    @DeleteMapping("/interiors/{id}")
    public ResponseEntity<Void> deleteInterior(@PathVariable UUID id) {
        interiorService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/models")
    public List<ModelDto> queryModels(
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) String componentName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return modelService.query(componentQueryMapper.toModelQuery(modelName, componentName, minPrice, maxPrice));
    }

    @GetMapping("/models/{id}")
    public ModelDto getModel(@PathVariable UUID id) {
        return modelService.get(componentQueryMapper.toId(id));
    }

    @PostMapping("/models")
    public ModelDto saveModel(@RequestBody ComponentRequests.ModelSaveRequest request) {
        return modelService.create(componentRequestMapper.toCreateModel(request));
    }

    @PutMapping("/models/{id}")
    public ModelDto updateModel(@PathVariable UUID id, @RequestBody ComponentRequests.ModelSaveRequest request) {
        return modelService.update(componentRequestMapper.toUpdateModel(id, request));
    }

    @DeleteMapping("/models/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable UUID id) {
        modelService.delete(componentQueryMapper.toId(id));
        return ResponseEntity.noContent().build();
    }
}
