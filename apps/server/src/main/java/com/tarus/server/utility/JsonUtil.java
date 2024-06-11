package com.tarus.server.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class JsonUtil<T> {

    public List<T> parseJSONFile(final MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> items = objectMapper.readValue(file.getInputStream(), new TypeReference<List<T>>() {
        });
        return items;
    }
}