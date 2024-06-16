package com.tarus.server.carmodel;

import com.tarus.server.dto.PageResponseDto;
import com.tarus.server.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/car-models")
@RequiredArgsConstructor
public class CarModelController {

    private final CarModelService carModelService;

    @PostMapping(value = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadCarData(
            @RequestParam(value = "files") MultipartFile[] files) throws IOException {

        for (MultipartFile file : files) {
           carModelService.saveCarModels(file);

        }
        return ResponseEntity.ok("Saved");
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CarModelResponseDto>> getAllCarModels(
            @RequestParam(value = "pageNo", defaultValue
                    = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue
                    = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
        return ResponseEntity.ok(carModelService.getAllCarModels(pageNo,pageSize));
    }
}
