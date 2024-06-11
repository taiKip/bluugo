package com.tarus.server.carmodel;

import org.springframework.stereotype.Service;


@Service
public interface CarModelService {


   CarModel saveCarModel(CarModelDto carModelDto);
}