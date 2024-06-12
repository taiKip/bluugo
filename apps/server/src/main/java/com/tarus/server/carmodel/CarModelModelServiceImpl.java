package com.tarus.server.carmodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarModelModelServiceImpl implements CarModelService {
    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public CarModel findOrSaveCarModel(CarModelDto carModelDto) {
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