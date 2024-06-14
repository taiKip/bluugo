package com.tarus.server.carmodel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarModelController {

    private final CarModelService carModelService;
    @PostMapping("upload")
    public ResponseEntity<String> uploadCarData(
            @RequestParam(value = "files") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            carModelService.saveCarModels(file);
        }
        return ResponseEntity.ok("Saved");
    }
    @GetMapping
    public ResponseEntity<List<CarModelResponseDto>> getAllCarModels(){
        return ResponseEntity.ok(carModelService.getAllCarModels());
    }
}
