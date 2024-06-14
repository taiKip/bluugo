package com.tarus.server.make;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakeServiceImpl implements MakeService{
    private final MakeRepository makeRepository;
    @Override
    public Make saveCarMake(String name) {
        return makeRepository.findByNameIgnoreCase(name)
                .orElseGet(()->makeRepository.save(new Make(name)));
    }
}
