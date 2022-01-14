package com.ubs.forex.client.service;

import com.ubs.forex.client.config.ClientProperties;
import com.ubs.forex.client.util.FileReaderUtil;
import com.ubs.forex.client.util.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
public class ValidationServiceTest {

    @InjectMocks
    private ValidationService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FileReaderUtil fileReaderUtil;

    @Mock
    private ObjectMapperUtil objectMapperUtil;

    @Mock
    private ClientProperties clientProperties;

    @Test
    void shouldValidateAndLogTransactionsRequest(CapturedOutput output) {
        // given
        given(clientProperties.getRequestJsonFilepath()).willReturn("filepath");
        given(fileReaderUtil.getTextFromFile("filepath")).willReturn("json request");
        Object requestBody = new Object();
        given(objectMapperUtil.readValue("json request", Object.class)).willReturn(requestBody);
        given(clientProperties.getUrl()).willReturn("url");
        Object responseBody = new Object();
        ResponseEntity<Object> responseEntity = ResponseEntity.ok(responseBody);
        given(restTemplate.postForEntity("url", requestBody, Object.class)).willReturn(responseEntity);
        given(objectMapperUtil.writeValue(responseBody)).willReturn("json response");

        // when
        service.testValidateTransactionsRequest();

        // then
        assertThat(output.getAll(), containsString("Response status: 200 OK.\nResponse object: json response\nRequest object: json request"));
    }
}
