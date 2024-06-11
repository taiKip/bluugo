package com.tarus.server.carmodel;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CarService {
   String saveJsonFile(MultipartFile file) throws IOException;
}