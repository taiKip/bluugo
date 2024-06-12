package com.tarus.server.car;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public interface CarService {

    void uploadCarData(MultipartFile file) throws IOException;
}
