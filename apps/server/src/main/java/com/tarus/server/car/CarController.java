package com.tarus.server.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadCarData(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            carService.uploadCarData(file);
        }
        return ResponseEntity.ok("Saved");
    }
}
