package com.tarus.server.Util;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarus.server.dto.JsonDto;
import com.tarus.server.dto.CarReasonDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonUtil {
    public List<JsonDto> parseJSONFile(final MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file.getInputStream(), new TypeReference<List<JsonDto>>() {
            });
        } catch (StreamReadException e) {

            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<CarReasonDto> parseDataList(List<JsonDto> jsonDtoList) {
        List<CarReasonDto> dataList = new ArrayList<>();
        for (var data : jsonDtoList) {
            List<String> reasons = retrieveReasons(data);
            dataList.add(new CarReasonDto(data.model_year(), data.make(), data.model(),
                    data.rejection_percentage(), reasons));
        }
        return dataList;
    }

    private List<String> retrieveReasons(JsonDto data) {
        List<String> reasons = new ArrayList<>();
        for (Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().toLowerCase().startsWith("reason")) {
                try {
                    String value = (String) field.get(data);
                    if (value != null && !value.isEmpty()) {
                        reasons.add(value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return reasons;
    }

};

