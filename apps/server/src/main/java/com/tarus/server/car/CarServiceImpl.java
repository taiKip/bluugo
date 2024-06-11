package com.tarus.server.car;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarus.server.carmodel.CarModel;
import com.tarus.server.carmodel.CarModelRepository;
import com.tarus.server.dto.JsonDto;
import com.tarus.server.rejectionreason.RejectionReason;
import com.tarus.server.rejectionreason.RejectionReasonRepository;
import com.tarus.server.rejectionreason.RejectionReasonService;
import com.tarus.server.utility.JsonUtil;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStat;
import com.tarus.server.yearlyrejectionstat.YearlyRejectionStatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private RejectionReasonService reasonService;
    @Autowired
    private YearlyRejectionStatRepository yearlyRejectionStatRepository;

    @Override
    public void uploadCarData(MultipartFile file) throws IOException {

        List<Car> data = jsonUtil.parseJSONFile(file);
        log.info("DATA", data.toString());
    }

    public void saveCarData(List<Car> carList) {
for(Car car:carList ){
    CarModel carModel = carModelRepository.findByMakeAndModel(car.getMake(),car.getModel());
    if(carModel==null){
        carModel = new CarModel();
        carModel.setModel(car.getModel());
        carModel.setMake(car.getMake());
        carModel = carModelRepository.save(carModel);
    }

    YearlyRejectionStat yearlyRejectionStat  = new YearlyRejectionStat();
    yearlyRejectionStat.setCarModel(carModel);

        }
    }
}
