package com.tarus.server.model;

import org.springframework.stereotype.Service;

@Service
public interface ModelService {
    Model saveCarModel(String name);
}
