package com.tarus.server.carmodel;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarService {
    @Override
    public String saveJsonFile(MultipartFile file) throws IOException {

        return "";
    }

    // @Override
    //  public List<CarModel> saveJsonFile(MultipartFile file) throws IOException {
//        List<JsonDto> dataList = jsonUtil.parseJSONFile(file);
//        List<CarReasonDto> dtoList = jsonUtil.parseDataList(dataList);
//        for (CarReasonDto dto : dtoList) {
//            CarModel newVehicle = CarModel.builder()
//                    .year(Integer.parseInt(dto.model_year()))
//                    .make(dto.make())
//                    .model(dto.model())
//                    .rejectionPercentage(dto.rejection_percentage())
//                    .build();
//            var savedVehicle = carRepository.save(newVehicle);
//            for (String reason : dto.reasons()) {
//                Reason newReason = Reason.builder()
//                        .reason(reason)
//                        .vehicle(savedVehicle).build();
//                reasonRepository.save(newReason);
//            }
//        }
//List<CarModel> carModels = objectMapper.readValue(file.getInputStream(),
//        objectMapper.getTypeFactory().constructCollectionType(List.class, CarModel.class));
//        return carRepository.saveAll(carModels);
//
//    }


    // }
}