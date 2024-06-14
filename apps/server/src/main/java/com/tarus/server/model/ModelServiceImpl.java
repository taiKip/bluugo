package com.tarus.server.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    @Override
    public Model saveCarModel(String name) {
        return modelRepository.findByNameIgnoreCase(name)
                .orElseGet(()->modelRepository.save(new Model(name)));
    }
}
