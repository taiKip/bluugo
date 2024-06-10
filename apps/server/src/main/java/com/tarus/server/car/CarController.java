package com.tarus.server.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping("api/v1/vehicles")
public class CarController {
    private final CarService carService;
    @GetMapping
    public ResponseEntity<CarDto> getVehicles() {
        return ResponseEntity.ok(new CarDto("Toyota","Black"));
    }

    @PostMapping
    public ResponseEntity saveVehicles(@RequestParam(value=  "files") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
           return ResponseEntity.ok( carService.saveJsonFile(file));
        }
        return null;
    }
}