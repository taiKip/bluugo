package com.tarus.server.carmodel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tarus.server.make.Make;
import com.tarus.server.make.MakeService;
import com.tarus.server.model.Model;
import com.tarus.server.model.ModelService;
import com.tarus.server.rejectionreason.RejectionReason;
import com.tarus.server.rejectionreason.RejectionReasonService;
import com.tarus.server.util.JsonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    /**
     * @param carModel
     */
    @Transactional
    private void saveOrUpdateCarModel(CarModel carModel) {
        Model model = modelService.saveCarModel(carModel.getModel().getName());
        Make make = makeService.saveCarMake(carModel.getMake().getName());

        Set<RejectionReason> reasons = new HashSet<>();

        for (RejectionReason reason : carModel.getRejectionReasons()) {
            RejectionReason rejectionReason = reasonService.saveRejectionReason(reason.getReason());
            reasons.add(rejectionReason);
        }

        carModel.setModel(model);
        carModel.setMake(make);
        carModel.setRejectionReasons(reasons);

        Optional<CarModel> existingCarModel = carModelRepository
                .findByModelYearAndMakeAndModel(carModel.getModelYear(), carModel.getMake(), carModel.getModel());
        if (existingCarModel.isPresent()) {
            CarModel carModelDb = existingCarModel.get();
            carModelDb.setRejectionPercentage(carModel.getRejectionPercentage());
            carModelDb.setRejectionReasons(reasons);
            carModelRepository.save(carModelDb);
        } else {
            carModelRepository.save(carModel);
        }
    }

    /**
     * @param carModels
     * @desc
     */
    @Transactional
    private void saveOrUpdateCarModels(List<CarModel> carModels) {
        for (CarModel carModel : carModels) {

            saveOrUpdateCarModel(carModel);

        }

    }

    /**
     * @param carModelDto
     * @return
     */
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

    /**
     * @param file
     */
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
    public List<CarModelResponseDto> getAllCarModels() {
        return carModelRepository.findAll().stream()
                .map(item -> CarModelResponseDto
                        .builder()
                        .model(item.getModel().getName())
                        .make(item.getMake().getName())
                        .modelYear(item.getModelYear())
                        .rejectionPercentage(item.getRejectionPercentage())
                        .reasons(item.getRejectionReasons().stream().map(reason -> reason.getReason()).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
