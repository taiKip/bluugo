package com.tarus.server.car;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarus.server.carmodel.CarModel;
import com.tarus.server.carmodel.CarModelDto;
import com.tarus.server.carmodel.CarModelService;
import com.tarus.server.rejectionreason.RejectionReason;
import com.tarus.server.rejectionreason.RejectionReasonRepository;
import com.tarus.server.rejectionreason.RejectionReasonService;
import com.tarus.server.util.JsonUtil;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private CarModelService carModelService;
    @Autowired
    private RejectionReasonService reasonService;
    @Autowired
    private YearlyRejectionStatRepository yearlyRejectionStatRepository;
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;

    @Override
    public void uploadCarData(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
   // var data =     jsonUtil.parseJSONFile(file);
List<Car>  data=    objectMapper.readValue(file.getInputStream(), new TypeReference<List<Car>>() {
        });
saveCarData(data);

    }

    public void saveCarData(List<Car> carList) {
for(var car:carList ){
    CarModel carModel = carModelService.findOrSaveCarModel(new CarModelDto(car.getModel(),car.getMake()));

    YearlyRejectionStat yearlyRejectionStat  = new YearlyRejectionStat();
    yearlyRejectionStat.setCarModel(carModel);
    yearlyRejectionStat
            .setRejectionPercentage(
                    Double.parseDouble(
                            car.getRejectionPercentage().replace(',','.')));
    yearlyRejectionStat = yearlyRejectionStatRepository.save(yearlyRejectionStat);
reasonService.SaveAllRejectionReasons(
        List.of(
                reasonService.createRejectionReason(car.getReason1(),yearlyRejectionStat),
                reasonService.createRejectionReason(car.getReason1(),yearlyRejectionStat),
                reasonService.createRejectionReason(car.getReason1(),yearlyRejectionStat)
        )
);


        }
    }


}
