package com.ubs.forex.client.service;

import com.ubs.forex.client.config.ClientProperties;
import com.ubs.forex.client.util.FileReaderUtil;
import com.ubs.forex.client.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {

    private final RestTemplate restTemplate;
    private final FileReaderUtil fileReaderUtil;
    private final ObjectMapperUtil objectMapperUtil;
    private final ClientProperties clientProperties;

    public void testValidateTransactionsRequest() {
        String jsonBody = fileReaderUtil.getTextFromFile(clientProperties.getRequestJsonFilepath());
        Object requestBody = objectMapperUtil.readValue(jsonBody, Object.class);
        ResponseEntity<Object> responseEntity =
                restTemplate.postForEntity(clientProperties.getUrl(), requestBody, Object.class);

        log.info("Response status: {}.\nResponse object: {}\nRequest object: {}",
                responseEntity.getStatusCode(), objectMapperUtil.writeValue(responseEntity.getBody()),
                jsonBody);
    }
}
