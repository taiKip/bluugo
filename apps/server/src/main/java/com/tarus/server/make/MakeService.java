package com.tarus.server.make;

import org.springframework.stereotype.Service;

@Service
public interface MakeService {
    Make saveCarMake(String name);
}
