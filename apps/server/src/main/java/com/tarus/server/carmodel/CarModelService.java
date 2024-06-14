package com.tarus.server.carmodel;

import com.tarus.server.rejectionreason.RejectionReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public interface CarModelService {

     void saveCarModels(MultipartFile file);

     List<CarModelResponseDto> getAllCarModels();
}
