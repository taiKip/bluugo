package com.tarus.server.carmodel;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class CarModelModelServiceImpl implements CarModelService {
    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public CarModel saveCarModel(CarModelDto carModelDto) {
        CarModel carModel = carModelRepository
                .findByMakeAndModel(carModelDto.make(), carModelDto.model());
        if(carModel== null){
            carModel= new CarModel();
            carModel.setMake(carModelDto.make());
            carModel.setModel(carModelDto.model());
            carModel = carModelRepository.save(carModel);
        }
        return carModel;
    }
}