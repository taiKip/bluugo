package com.tarus.server.carmodel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping("api/v1/vehicles")
public class CarModelController {
    private final CarModelService modelService;

    @PostMapping
    public ResponseEntity<CarModel> saveCarModel(@RequestBody CarModelDto carModelDto) {

       return ResponseEntity.ok(modelService.saveCarModel(carModelDto));
    }
}