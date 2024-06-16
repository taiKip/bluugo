package com.tarus.server.carmodel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tarus.server.dto.PageResponseDto;
import com.tarus.server.make.Make;
import com.tarus.server.make.MakeService;
import com.tarus.server.model.Model;
import com.tarus.server.model.ModelService;
import com.tarus.server.rejectionreason.RejectionReason;
import com.tarus.server.rejectionreason.RejectionReasonService;
import com.tarus.server.util.JsonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {
    private final CarModelRepository carModelRepository;
    private final MakeService makeService;
    private final ModelService modelService;
    private final RejectionReasonService reasonService;


    @Transactional
    private void saveOrUpdateCarModel(CarModel carModel, Map<String, Model> modelCache, Map<String, Make> makeCache, Map<String, RejectionReason> reasonCache) {
        Model model = modelCache.computeIfAbsent(carModel.getModel().getName(), name -> modelService.saveCarModel(name));
        Make make = makeCache.computeIfAbsent(carModel.getMake().getName(), name -> makeService.saveCarMake(name));

        Set<RejectionReason> reasons = carModel.getRejectionReasons().stream()
                .map(reason -> reasonCache.computeIfAbsent(reason.getReason(), reasonService::saveRejectionReason))
                .collect(Collectors.toSet());

        carModel.setModel(model);
        carModel.setMake(make);
        carModel.setRejectionReasons(reasons);

        Optional<CarModel> existingCarModel = carModelRepository
                .findByModelYearAndMakeAndModel(carModel.getModelYear(), carModel.getMake(), carModel.getModel());
        if (existingCarModel.isPresent()) {
            CarModel carModelDb = existingCarModel.get();
            boolean updated = false;

            if (!carModelDb.getRejectionPercentage().equals(carModel.getRejectionPercentage())) {
                carModelDb.setRejectionPercentage(carModel.getRejectionPercentage());
                updated = true;
            }

            if (!carModelDb.getRejectionReasons().equals(reasons)) {
                carModelDb.setRejectionReasons(reasons);
                updated = true;
            }

            if (updated) {
                carModelRepository.save(carModelDb);
            }
        } else {
            carModelRepository.save(carModel);
        }
    }



    @Transactional
    private void saveOrUpdateCarModels(List<CarModel> carModels) {
        //cache for batch operations
        Map<String, Model> modelCache = new HashMap<>();
        Map<String, Make> makeCache = new HashMap<>();
        Map<String, RejectionReason> reasonCache = new HashMap<>();
        for (CarModel carModel : carModels) {

            saveOrUpdateCarModel(carModel,modelCache,makeCache,reasonCache);

        }

    }


    private Set<RejectionReason> retrieveReasons(CarModelDto carModelDto) {
        Set<RejectionReason> reasons = new HashSet<>();
        for (Field field : carModelDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().startsWith("reason")) {
                try {
                    String value = (String) field.get(carModelDto);
                    if (value != null && !value.isEmpty()) {
                        reasons.add(new RejectionReason(value));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }
        return reasons;
    }


    @Transactional
    public void saveCarModels(MultipartFile file) {
        JsonUtil<CarModelDto> jsonUtil = new JsonUtil<>();
        try {
            List<CarModelDto> carModelDtoList = jsonUtil.parseJSONFile(file, new TypeReference<List<CarModelDto>>() {
            });
            List<CarModel> carModels = carModelDtoList.stream()
                    .map(dto -> CarModel.builder()
                            .modelYear(dto.getModelYear())
                            .make(new Make(dto.getMake()))
                            .model(new Model(dto.getModel()))
                            .rejectionPercentage(dto.getRejectionPercentage())
                            .rejectionReasons(retrieveReasons(dto))
                            .build())
                    .collect(Collectors.toList());

            saveOrUpdateCarModels(carModels);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file", e);
        }
    }

    @Override
    public PageResponseDto<CarModelResponseDto> getAllCarModels(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        PageResponseDto<CarModelResponseDto> response = new PageResponseDto<>();
        Page<CarModel> carModelsPage = carModelRepository.findAll(pageable);
        List<CarModelResponseDto> models = carModelsPage.stream()
                .map(item -> CarModelResponseDto
                        .builder()
                        .model(item.getModel().getName())
                        .make(item.getMake().getName())
                        .modelYear(item.getModelYear())
                        .rejectionPercentage(item.getRejectionPercentage())
                        .reasons(item.getRejectionReasons().stream().map(reason -> reason.getReason()).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        response.setContent(models);
        response.setPageNo(carModelsPage.getNumber());
        response.setPageSize(carModelsPage.getSize());
        response.setHasNext(carModelsPage.hasNext());
        response.setHasPrev(carModelsPage.hasPrevious());
        response.setTotalItems(carModelsPage.getTotalElements());
        response.setTotalPages(carModelsPage.getTotalPages());
        return  response;
    }
}
