package com.ubs.forex.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObjectMapperUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException exception) {
            log.error("Error while reading json value {}", json);
        }
        return null;
    }

    public String writeValue(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            log.error("Error while writing object value {}", object);
        }
        return null;
    }
}
