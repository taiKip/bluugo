package com.tarus.server.carmodel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping("api/v1/vehicles")
public class CarModelController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<String> saveVehicles(@RequestParam(value=  "files") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
          carService.saveJsonFile(file);
        }
        return null;
    }
}