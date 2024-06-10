package com.tarus.server.car;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CarService {
    List<Car> saveJsonFile(MultipartFile file) throws IOException;
}