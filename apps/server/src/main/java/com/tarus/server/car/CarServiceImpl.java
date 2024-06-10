package com.tarus.server.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarus.server.Util.JsonUtil;
import com.tarus.server.reason.ReasonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
    private final JsonUtil jsonUtil;
    private final CarRepository carRepository;
    private final ReasonRepository reasonRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Car> saveJsonFile(MultipartFile file) throws IOException {
//        List<JsonDto> dataList = jsonUtil.parseJSONFile(file);
//        List<CarReasonDto> dtoList = jsonUtil.parseDataList(dataList);
//        for (CarReasonDto dto : dtoList) {
//            Car newVehicle = Car.builder()
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
List<Car> cars = objectMapper.readValue(file.getInputStream(),
        objectMapper.getTypeFactory().constructCollectionType(List.class,Car.class));
        return carRepository.saveAll(cars);

    }


}
