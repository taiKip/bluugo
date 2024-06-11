package com.tarus.server.car;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public interface CarService {



    void uploadCarData(MultipartFile file) throws IOException;
}
